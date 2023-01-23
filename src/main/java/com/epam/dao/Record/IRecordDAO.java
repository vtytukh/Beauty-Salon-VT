package com.epam.dao.Record;

import com.epam.model.Record;
import com.epam.model.Status;

import java.util.List;

public interface IRecordDAO {
    Record createRecord(Record record);

    float getAvgRecords(List<Long> masterService);

    int getCountRecords();

    List<Record> findRecordsWithLimit(int offset, int limit);

    List<Record> findAllRecords();

    List<Record> findAllRecordsByDate(Long id, String date, boolean isReady);

    Boolean updateMark(Long id, int mark);

    Boolean updateStatus(Long id, Status status);

    Boolean updateTime(Long id, String date);

    List<Record> findRecordsByUserId(Long id);

    Record findRecord(Long id);
}
