package com.epam.service;

import com.epam.dao.Record.RecordDAO;
import com.epam.model.Record;
import com.epam.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

    @Mock
    private RecordDAO recordDAOMock;

    private RecordService recordService;
    private Record testRecord;
    private List<Record> testRecordsList;
    private Long testId = 1L;

    @BeforeEach
    void setUp() {
        recordService = new RecordService(recordDAOMock);
        testRecord = new Record(testId, testId, testId, testId, "2023");
        testRecordsList = new ArrayList<>();
        testRecordsList.add(testRecord);
    }

    @Test
    void testAddRecord() {
        when(recordDAOMock.createRecord(any(Record.class))).thenReturn(testRecord);
        assertFalse(recordService.addRecord(null));
        assertTrue(recordService.addRecord(testRecord));
    }

    @Test
    void testFindRecord() {
        when(recordDAOMock.findRecord(anyLong())).thenReturn(testRecord);
        assertNull(recordService.findRecord(null));
        assertEquals(testRecord, recordService.findRecord(testId));
    }

    @Test
    void testFindAllRecord() {
        when(recordDAOMock.findAllRecords()).thenReturn(testRecordsList);
        assertEquals(1, recordService.findAllRecord().size());
    }

    @Test
    void testFindRecordsWithLimit() {
        when(recordDAOMock.findRecordsWithLimit(anyInt(), anyInt())).thenReturn(testRecordsList);
        assertEquals(0, recordService.findRecordsWithLimit(-1, 5).size());
        assertEquals(0, recordService.findRecordsWithLimit(1, 0).size());
        assertEquals(1, recordService.findRecordsWithLimit(1, 5).size());
    }

    @Test
    void testGetCountRecords() {
        when(recordDAOMock.getCountRecords()).thenReturn(100);
        assertEquals(100, recordService.getCountRecords());
    }

    @Test
    void testGetCountRecordsByUserId() {
        when(recordDAOMock.getCountRecordsByUserId(anyLong())).thenReturn(100);
        assertEquals(0, recordService.getCountRecordsByUserId(null));
        assertEquals(100, recordService.getCountRecordsByUserId(testId));
    }

    @Test
    void testGetAvgRecords() {
        when(recordDAOMock.getAvgRecords(anyList())).thenReturn(5.0f);
        List<Long> ms = new ArrayList<>();
        ms.add(testId);
        assertEquals(5.0f, recordService.getAvgRecords(ms));
    }

    @Test
    void testFindAllRecordsByUserId() {
        when(recordDAOMock.findRecordsByUserId(anyLong())).thenReturn(testRecordsList);
        assertEquals(0, recordService.findAllRecordsByUserId(null).size());
        assertEquals(1, recordService.findAllRecordsByUserId(testId).size());
    }

    @Test
    void testFindRecordsByUserIdWithLimit() {
        when(recordDAOMock.findRecordsByUserIdWithLimit(anyLong(), anyInt(), anyInt())).thenReturn(testRecordsList);
        assertEquals(0, recordService.findRecordsByUserIdWithLimit(null, 1, 5).size());
        assertEquals(0, recordService.findRecordsByUserIdWithLimit(testId, -1, 5).size());
        assertEquals(0, recordService.findRecordsByUserIdWithLimit(testId, 1, 0).size());
        assertEquals(1, recordService.findRecordsByUserIdWithLimit(testId, 1, 5).size());
    }

    @Test
    void testFindAllRecordsTime() {
        when(recordDAOMock.findAllRecordsByDate(anyLong(), anyString(), anyBoolean())).thenReturn(testRecordsList);
        assertEquals(0, recordService.findAllRecordsTime(null, "2023-01-02", Boolean.TRUE).size());
        assertEquals(1, recordService.findAllRecordsTime(testId, "2023-01-02", Boolean.TRUE).size());
    }

    @Test
    void testFindPreviousDayRecordsWithoutFeedback() {
        when(recordDAOMock.findPreviousDayRecordsWithoutFeedback()).thenReturn(testRecordsList);
        assertEquals(1, recordService.findPreviousDayRecordsWithoutFeedback().size());
    }

    @Test
    void testUpdateStatus() {
        when(recordDAOMock.updateStatus(anyLong(), any(Status.class))).thenReturn(Boolean.TRUE);
        assertFalse(recordService.updateStatus(null,Status.PAID));
        assertTrue(recordService.updateStatus(testId,Status.PAID));
    }

    @Test
    void testUpdateTime() {
        when(recordDAOMock.updateTime(anyLong(), anyString())).thenReturn(Boolean.TRUE);
        assertFalse(recordService.updateTime(null, "2023-01-03"));
        assertTrue(recordService.updateTime(testId, "2023-01-03"));
    }

    @Test
    void testUpdateMark() {
        when(recordDAOMock.updateMark(anyLong(), anyInt(), anyString())).thenReturn(Boolean.TRUE);
        assertFalse(recordService.updateMark(null, 5, "Feedback"));
        assertTrue(recordService.updateMark(testId, 5, "Feedback"));
    }
}