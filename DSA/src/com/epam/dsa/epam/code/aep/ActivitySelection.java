package com.epam.dsa.epam.code.aep;
import java.util.*;

class Activity {
    int start, finish;

    Activity(int s, int f) {
        start = s;
        finish = f;
    }
}

public class ActivitySelection {

    public static int maxActivities(int[] start, int[] finish) {

        int n = start.length;
        List<Activity> activities = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            activities.add(new Activity(start[i], finish[i]));
        }

        // sort by finish time
        activities.sort(Comparator.comparingInt(a -> a.finish));

        int count = 1;
        int lastFinish = activities.get(0).finish;

        for (int i = 1; i < n; i++) {
            if (activities.get(i).start > lastFinish) {
                count++;
                lastFinish = activities.get(i).finish;
            }
        }

        return count;
    }

    public static void main(String[] args) {

        int[] start = {1, 3, 0, 5, 8, 5};
        int[] finish = {2, 4, 6, 7, 9, 9};

        System.out.println(maxActivities(start, finish));
    }
}