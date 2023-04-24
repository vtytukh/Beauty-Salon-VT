package com.epam.dao.Master;

import com.epam.connection.ConnectionPool;
import com.epam.model.Master;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class MasterDAOTest {

    @Mock
    private ConnectionPool connectionPoolMock;
    @Mock
    private Connection connectionMock;
    @Mock
    private PreparedStatement preparedStatementMock;
    @Mock
    private ResultSet resultSetMock;

    private MasterDAO masterDAO;
    private final Long testId = 100L;
    private final float testMark = 5.0f;

    @BeforeEach
    void setUp() {
        try (MockedStatic<ConnectionPool> connectionPoolMockedStatic = mockStatic(ConnectionPool.class)) {
            connectionPoolMockedStatic.when(ConnectionPool::getInstance).thenReturn(connectionPoolMock);
            masterDAO = MasterDAO.getInstance();
        }

        when(connectionPoolMock.getConnection()).thenReturn(connectionMock);
    }

    @Test
    void testSetMasterWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatementMock).setFloat(anyInt(), anyFloat());
        when(preparedStatementMock.executeUpdate()).thenReturn(1);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE);
        when(resultSetMock.getLong(1)).thenReturn(testId);

        masterDAO.setMaster(testId);

        verify(connectionMock, times(1)).prepareStatement(anyString(), anyInt());
        verify(preparedStatementMock, times(1)).setLong(anyInt(), anyLong());
        verify(preparedStatementMock, times(1)).setFloat(anyInt(), anyFloat());
        verify(preparedStatementMock, times(1)).executeUpdate();
        verify(preparedStatementMock, times(1)).getGeneratedKeys();
        verify(resultSetMock, times(1)).next();
        verify(resultSetMock, times(1)).getLong(1);
    }

    @Test
    void testFindAllWithNameWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(resultSetMock.getLong(anyString())).thenReturn(testId);
        when(resultSetMock.getFloat(anyString())).thenReturn(testMark);
        when(resultSetMock.getString("first_name")).thenReturn("first_name");
        when(resultSetMock.getString("last_name")).thenReturn("last_name");

        List<Master> listMasters = masterDAO.findAllWithName();

        assertEquals(1, listMasters.size());
    }

    @Test
    void testFindMastersWithNameByServiceIdWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(resultSetMock.getLong(anyString())).thenReturn(testId);
        when(resultSetMock.getFloat(anyString())).thenReturn(testMark);
        when(resultSetMock.getString("first_name")).thenReturn("first_name");
        when(resultSetMock.getString("last_name")).thenReturn("last_name");

        List<Master> listMasters = masterDAO.findMastersWithNameByServiceId(true, true, testId);

        assertEquals(1, listMasters.size());
    }

    @Test
    void testFindMastersWithNameOrderByWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(resultSetMock.getLong(anyString())).thenReturn(testId);
        when(resultSetMock.getFloat(anyString())).thenReturn(testMark);
        when(resultSetMock.getString("first_name")).thenReturn("first_name");
        when(resultSetMock.getString("last_name")).thenReturn("last_name");

        List<Master> listMasters = masterDAO.findMastersWithNameOrderBy(true, true);

        assertEquals(1, listMasters.size());
    }

    @Test
    void testFindMasterWithNameByIdWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE);
        when(resultSetMock.getString("user.first_name")).thenReturn("first_name");
        when(resultSetMock.getString("last_name")).thenReturn("last_name");
        when(resultSetMock.getFloat(anyString())).thenReturn(testMark);

        assertNotNull(masterDAO.findMasterWithNameById(testId));
    }

    @Test
    void testFindAllWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(resultSetMock.getLong(anyString())).thenReturn(testId);
        when(resultSetMock.getFloat(anyString())).thenReturn(testMark);

        List<Master> listMasters = masterDAO.findAll();

        assertEquals(1, listMasters.size());
    }

    @Test
    void testUpdateMasterRateWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setFloat(anyInt(), anyFloat());
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        when(preparedStatementMock.executeUpdate()).thenReturn(1);

        assertTrue(masterDAO.updateMasterRate(testId, testMark));
    }

    @Test
    void testFindMasterByUserIdWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE);
        when(resultSetMock.getLong(anyString())).thenReturn(testId);

        assertNotNull(masterDAO.findMasterByUserId(testId));
    }

    @Test
    void testFindMasterByIdWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE);
        when(resultSetMock.getLong(anyString())).thenReturn(testId);

        assertNotNull(masterDAO.findMasterById(testId));
    }
}