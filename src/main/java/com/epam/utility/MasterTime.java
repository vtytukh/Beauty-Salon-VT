package com.epam.utility;

import com.epam.model.Record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that give free hours and fill empty hours to timetable
 *
 * @author Volodymyr Tytukh
 */

public class MasterTime {
    private final static Integer[] HOURS = {9, 10, 11, 12, 13, 14, 15, 16};

    public static List<Integer> getFreeHours(List<Record> records) {

        List<Integer> freeHours = new ArrayList<>(Arrays.<Integer>asList(HOURS));
        if (records.size() != 0) {
            List<Integer> busyHours = new ArrayList<>();
            for (Record record : records) {
                busyHours.add(Integer.parseInt(record.getTime().substring(11, 13)));
            }
            for (Integer i : busyHours) {
                freeHours.remove(i);
            }
        }
        return freeHours;
    }

    public static List<Record> getRecordsWithEmptySpace(List<Record> records) {
        List<Record> rec = new ArrayList<>();

        rec.add(new Record(9));
        rec.add(new Record(10));
        rec.add(new Record(11));
        rec.add(new Record(12));
        rec.add(new Record(13));
        rec.add(new Record(14));
        rec.add(new Record(15));
        rec.add(new Record(16));
        for (int i = 0; i < HOURS.length; i++) {
            for (Record r : records) {
                if (HOURS[i] == r.getHour()) rec.set(i, r);
            }
        }
        return rec;
    }

}
