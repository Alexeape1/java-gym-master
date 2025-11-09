package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    @DisplayName("Проверяет корректное добавление одной тренировки на понедельник")
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        Assertions.assertEquals(1, mondaySessions.size()); //Проверить, что за понедельник вернулось одно занятие

        Assertions.assertTrue(tuesdaySessions.isEmpty()); //Проверить, что за вторник не вернулось занятий
    }

    @Test
    @DisplayName("Проверяет добавление нескольких тренировок на разные дни недели")
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        Assertions.assertEquals(1, mondaySessions.size()); //Проверить, что за понедельник вернулось одно занятие

        Assertions.assertEquals(2, thursdaySessions.size());
        Assertions.assertEquals(new TimeOfDay(13, 0), thursdaySessions.get(0).getTimeOfDay());
        Assertions.assertEquals(new TimeOfDay(20, 0), thursdaySessions.get(1).getTimeOfDay());
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00

        Assertions.assertTrue(tuesdaySessions.isEmpty());  // Проверить, что за вторник не вернулось занятий
    }

    @Test
    @DisplayName("Проверяет корректное добавление тренировки на конкретное время")
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        TrainingSession monday13Session = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession monday14Session = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        Assertions.assertNotNull(monday13Session); //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertNull(monday14Session);//Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    @DisplayName("Подсчёт занятий для одного тренера")
    void testGetCountByCoachesSingleCoach() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession trainingSession = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(trainingSession);

        List<CounterOfTrainings> counts = timetable.getCountByCoaches();
        Assertions.assertEquals(1, counts.size());
        Assertions.assertEquals(coach, counts.get(0).getCoach());
        Assertions.assertEquals(1, counts.get(0).getCount());
    }

    @Test
    @DisplayName("Подсчет занятий для нескольких тренеров")
    void testGetCountByCoachesMultipleCoaches() {
        Timetable timetable = new Timetable();

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        Coach coachChild = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coachAdult = new Coach("Петрова", "Анна", "Ивановна");

        TrainingSession childSession1 = new TrainingSession(groupChild, coachChild, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession childSession2 = new TrainingSession(groupChild, coachChild, DayOfWeek.TUESDAY, new TimeOfDay(14, 0));
        TrainingSession adultSession = new TrainingSession(groupAdult, coachAdult, DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(childSession1);
        timetable.addNewTrainingSession(childSession2);
        timetable.addNewTrainingSession(adultSession);

        List<CounterOfTrainings> counts = timetable.getCountByCoaches();
        Assertions.assertEquals(2, counts.size());
        Assertions.assertEquals(coachChild, counts.get(0).getCoach());
        Assertions.assertEquals(2, counts.get(0).getCount());
        Assertions.assertEquals(coachAdult, counts.get(1).getCoach());
        Assertions.assertEquals(1, counts.get(1).getCount());
    }

    @Test
    @DisplayName("Проверка порядок сортировки по количеству занятий")
    void testGetCountByCoachesSorting() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Петрова", "Анна", "Ивановна");
        Coach coach3 = new Coach("Смирнов", "Иван", "Петрович");

        TrainingSession session1 = new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession session2 = new TrainingSession(group, coach2, DayOfWeek.TUESDAY, new TimeOfDay(14, 0));
        TrainingSession session3 = new TrainingSession(group, coach3, DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        TrainingSession session4 = new TrainingSession(group, coach1, DayOfWeek.FRIDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);
        timetable.addNewTrainingSession(session4);

        List<CounterOfTrainings> counts = timetable.getCountByCoaches();
        Assertions.assertEquals(3, counts.size());
        Assertions.assertEquals(counts.get(0).getCoach(), coach1);
        Assertions.assertTrue(counts.get(1).getCoach().equals(coach2) || counts.get(1).getCoach().equals(coach3));
        Assertions.assertTrue(counts.get(2).getCoach().equals(coach2) || counts.get(2).getCoach().equals(coach3));
    }
}
