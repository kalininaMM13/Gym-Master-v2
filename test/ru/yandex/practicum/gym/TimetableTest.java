package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        //Проверить, что за вторник не вернулось занятий
        Assertions.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
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

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Assertions.assertEquals(new TimeOfDay(13, 0), timetable.
                getTrainingSessionsForDay(DayOfWeek.THURSDAY).navigableKeySet().getFirst());
        Assertions.assertEquals(new TimeOfDay(20, 0), timetable.
                getTrainingSessionsForDay(DayOfWeek.THURSDAY).navigableKeySet().getLast());

        // Проверить, что за вторник не вернулось занятий
        Assertions.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));

    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)));
    }

    @org.junit.Test
    public void testGetCoachCountAll() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Петр", "Семенович");
        Coach coach3 = new Coach("Смирнова", "Елизовета", "Павловна");
        TrainingSession singleTrainingSession1 = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession2 = new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession3 = new TrainingSession(group, coach3,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession1);
        timetable.addNewTrainingSession(singleTrainingSession2);
        timetable.addNewTrainingSession(singleTrainingSession3);

        //Проверить, что все 3 тренера есть в получаемом списке
        Assertions.assertEquals(3, timetable.getCountByCoaches().size());
    }

    @org.junit.Test
    public void testGetCoachCorrectCountForEveryCoach() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Смирнова", "Елизовета", "Павловна");
        TrainingSession singleTrainingSession1 = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession2 = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession3 = new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession4 = new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession5 = new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession1);
        timetable.addNewTrainingSession(singleTrainingSession2);
        timetable.addNewTrainingSession(singleTrainingSession3);
        timetable.addNewTrainingSession(singleTrainingSession4);
        timetable.addNewTrainingSession(singleTrainingSession5);

        //Проверить, что тренер Васильев провел 2 тренировки
        Assertions.assertEquals(2, timetable.getCountByCoaches().get(coach1));

        //Проверить, что тренер Смирнова провела 3 тренировки
        Assertions.assertEquals(3, timetable.getCountByCoaches().get(coach2));
    }

    @org.junit.Test
    public void testGetCoachCorrectSequence() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Смирнова", "Елизовета", "Павловна");
        TrainingSession singleTrainingSession1 = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession2 = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession3 = new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession4 = new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession5 = new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession1);
        timetable.addNewTrainingSession(singleTrainingSession2);
        timetable.addNewTrainingSession(singleTrainingSession3);
        timetable.addNewTrainingSession(singleTrainingSession4);
        timetable.addNewTrainingSession(singleTrainingSession5);
        List<Map.Entry<Coach, Integer>> coachList = new ArrayList<Map.Entry<Coach, Integer>>(timetable.
                getCountByCoaches().entrySet());

        //Проверить, что тренер Смирнова будет первым в списке по кол-ву тренировок
        Assertions.assertEquals(coach2, coachList.getFirst().getKey());

        //Проверить, что тренер Васильев будет вторым в списке по кол-ву тренировок
        Assertions.assertEquals(coach1, coachList.getLast().getKey());
    }
}
