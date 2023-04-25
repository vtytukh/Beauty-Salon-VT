package com.epam.dao.Record;

import com.epam.connection.ConnectionPool;
import com.epam.dao.Master.MasterDAO;
import com.epam.model.Record;
import com.epam.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class RecordDAOTest {

    @Mock
    private ConnectionPool connectionPoolMock;
    @Mock
    private Connection connectionMock;
    @Mock
    private PreparedStatement preparedStatementMock;
    @Mock
    private ResultSet resultSetMock;

    private RecordDAO recordDAO;
    private final Long testId = 100L;

    @BeforeEach
    void setUp() {
        try (MockedStatic<ConnectionPool> connectionPoolMockedStatic = mockStatic(ConnectionPool.class)) {
            connectionPoolMockedStatic.when(ConnectionPool::getInstance).thenReturn(connectionPoolMock);
            recordDAO = RecordDAO.getInstance();
        }

        when(connectionPoolMock.getConnection()).thenReturn(connectionMock);
    }

    @Test
    void testCreateRecord() throws SQLException {
        when(connectionMock.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatementMock).setString(anyInt(), anyString());
        when(preparedStatementMock.executeUpdate()).thenReturn(1);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE);
        when(resultSetMock.getLong(1)).thenReturn(testId);

        recordDAO.createRecord(new Record(testId, testId, testId, testId, "2023-01-03"));

        verify(connectionMock, times(1)).prepareStatement(anyString(), anyInt());
        verify(preparedStatementMock, times(3)).setLong(anyInt(), anyLong());
        verify(preparedStatementMock, times(1)).setString(anyInt(), anyString());
        verify(preparedStatementMock, times(1)).executeUpdate();
        verify(preparedStatementMock, times(1)).getGeneratedKeys();
        verify(resultSetMock, times(1)).next();
        verify(resultSetMock, times(1)).getLong(1);
    }

    @Test
    void testGetAvgRecords() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE);
        when(resultSetMock.getFloat("avg")).thenReturn(4.5f);

        List<Long> masterService = new ArrayList<>();
        masterService.add(4L);
        masterService.add(5L);

        assertEquals(4.5f, recordDAO.getAvgRecords(masterService));
    }

    @Test
    void testGetCountRecords() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE);
        when(resultSetMock.getInt("count")).thenReturn(100);

        assertEquals(100, recordDAO.getCountRecords());
    }

    @Test
    void testGetCountRecordsByUserId() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE);
        when(resultSetMock.getInt("count")).thenReturn(100);

        assertEquals(100, recordDAO.getCountRecordsByUserId(testId));
    }

    @Test
    void testFindRecordsWithLimit() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setInt(anyInt(), anyInt());
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(resultSetMock.getLong(anyString())).thenReturn(testId);
        when(resultSetMock.getString("time")).thenReturn("2023-01-03");

        List<Record> recordList = recordDAO.findRecordsWithLimit(1, 5);

        assertEquals(2, recordList.size());
    }

    @Test
    void testFindAllRecords() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(resultSetMock.getLong(anyString())).thenReturn(testId);
        when(resultSetMock.getString("time")).thenReturn("2023-01-03");

        List<Record> recordList = recordDAO.findAllRecords();

        assertEquals(2, recordList.size());
    }

    @Test
    void testFindAllRecordsByDate() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatementMock).setString(anyInt(), anyString());
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(resultSetMock.getLong(anyString())).thenReturn(testId);
        when(resultSetMock.getString("time")).thenReturn("2023-01-03");

        List<Record> recordList = recordDAO.findAllRecordsByDate(testId, "2023-01-03", true);

        assertEquals(2, recordList.size());
    }

    @Test
    void testFindPreviousDayRecordsWithoutFeedback() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(resultSetMock.getLong(anyString())).thenReturn(testId);
        when(resultSetMock.getString("time")).thenReturn("2023-01-03");

        List<Record> recordList = recordDAO.findPreviousDayRecordsWithoutFeedback();

        assertEquals(2, recordList.size());
    }

    @Test
    void testUpdateMark() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatementMock).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatementMock).setString(anyInt(), anyString());
        when(preparedStatementMock.executeUpdate()).thenReturn(1);

        assertTrue(recordDAO.updateMark(testId, 5, "feedback"));
    }

    @Test
    void testUpdateStatus() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        when(preparedStatementMock.executeUpdate()).thenReturn(1);

        assertTrue(recordDAO.updateStatus(testId, Status.PAID));
    }

    @Test
    void testUpdateTime() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setString(anyInt(), anyString());
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        when(preparedStatementMock.executeUpdate()).thenReturn(1);

        assertTrue(recordDAO.updateTime(testId, "2023-01-03"));
    }

    @Test
    void testFindRecordsByUserId() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(resultSetMock.getLong(anyString())).thenReturn(testId);
        when(resultSetMock.getString("time")).thenReturn("2023-01-03");

        List<Record> recordList = recordDAO.findRecordsByUserId(testId);

        assertEquals(2, recordList.size());
    }

    @Test
    void testFindRecordsByUserIdWithLimit() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatementMock).setInt(anyInt(), anyInt());
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(resultSetMock.getLong(anyString())).thenReturn(testId);
        when(resultSetMock.getString("time")).thenReturn("2023-01-03");

        List<Record> recordList = recordDAO.findRecordsByUserIdWithLimit(testId, 1, 5);

        assertEquals(2, recordList.size());
    }

    @Test
    void testFindRecord() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE);
        when(resultSetMock.getLong(anyString())).thenReturn(testId);
        when(resultSetMock.getString("time")).thenReturn("2023-01-03");

        assertNotNull(recordDAO.findRecord(testId));
    }
}