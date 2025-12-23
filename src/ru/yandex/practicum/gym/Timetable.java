package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    //Добавление нового занятия в расписании
    public void addNewTrainingSession(TrainingSession trainingSession) {
        ArrayList<TrainingSession> trainingSessionList = new ArrayList<>();
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsByTime = new TreeMap<>();

        if (timetable.containsKey(trainingSession.getDayOfWeek())) {
            if (timetable.get(trainingSession.getDayOfWeek()).containsKey(trainingSession.getTimeOfDay())) {
                trainingSessionList.addAll(timetable.get(trainingSession.getDayOfWeek())
                        .get(trainingSession.getTimeOfDay()));
                trainingSessionList.add(trainingSession);
                timetable.get(trainingSession.getDayOfWeek()).put(trainingSession.getTimeOfDay(), trainingSessionList);
            } else {
                trainingSessionList.add(trainingSession);
                timetable.get(trainingSession.getDayOfWeek()).put(trainingSession.getTimeOfDay(), trainingSessionList);
            }
        } else {
            trainingSessionList.add(trainingSession);
            trainingSessionsByTime.put(trainingSession.getTimeOfDay(), trainingSessionList);
            timetable.put(trainingSession.getDayOfWeek(), trainingSessionsByTime);
        }
    }

    //Получение списка тренировок за день, сложность О(1)
    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    //Получение списка тренировок за день и время, но сложность О(1)
    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    //Получение списка тренеров с кол-вом их тренировок, упорядоченный по убыванию
    public HashMap<Coach, Integer> getCountByCoaches() {
        HashMap<Coach, Integer> coaches = new HashMap();
        for (DayOfWeek dayOfWeek : timetable.keySet()) {
            for (TimeOfDay timeOfDay : timetable.get(dayOfWeek).keySet()) {
                for (TrainingSession trainingSessionTmp : timetable.get(dayOfWeek).get(timeOfDay)) {
                    if (coaches.containsKey(trainingSessionTmp.getCoach())) {
                        coaches.put(trainingSessionTmp.getCoach(), coaches.get(trainingSessionTmp.getCoach()) + 1);
                    } else {
                        coaches.put(trainingSessionTmp.getCoach(), 1);
                    }
                }
            }
        }
        List<Map.Entry<Coach, Integer>> list = new ArrayList<Map.Entry<Coach, Integer>>(coaches.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Coach, Integer>>() {
            @Override
            public int compare(Map.Entry<Coach, Integer> e1, Map.Entry<Coach, Integer> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        });

        HashMap<Coach, Integer> sortedCoaches = new LinkedHashMap<Coach, Integer>();
        for (Map.Entry<Coach, Integer> entry : list) {
            sortedCoaches.put(entry.getKey(), entry.getValue());
        }
        return sortedCoaches;
    }
}
