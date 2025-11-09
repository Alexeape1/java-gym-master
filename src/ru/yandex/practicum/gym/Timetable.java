package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, Map<TimeOfDay, TrainingSession>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        if (!timetable.containsKey(dayOfWeek)) {
            timetable.put(dayOfWeek, new TreeMap<>());
        }

        Map<TimeOfDay, TrainingSession> sessionsForDay = timetable.get(dayOfWeek);
        sessionsForDay.put(timeOfDay, trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        Map<TimeOfDay, TrainingSession> sessionsForDay = timetable.get(dayOfWeek);
        if (sessionsForDay == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(sessionsForDay.values());
    }

    public TrainingSession getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Map<TimeOfDay, TrainingSession> sessionsForDay = timetable.get(dayOfWeek);
        if (sessionsForDay == null) {
            return null;
        }
        return sessionsForDay.get(timeOfDay);
    } //как реализовать, тоже непонятно, но сложность должна быть О(1)

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachCounts = new HashMap<>();

        for (Map<TimeOfDay, TrainingSession> sessionsForDay : timetable.values()) {
            for (TrainingSession session : sessionsForDay.values()) {
                Coach coach = session.getCoach();
                coachCounts.put(coach, coachCounts.getOrDefault(coach, 0) + 1);
            }
        }

        List<CounterOfTrainings> counterOfTrainingsList = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachCounts.entrySet()) {
            counterOfTrainingsList.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        counterOfTrainingsList.sort((a, b) -> b.getCount() - a.getCount());

        return counterOfTrainingsList;
    }
}