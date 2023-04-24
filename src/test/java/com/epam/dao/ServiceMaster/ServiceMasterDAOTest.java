package com.epam.dao.ServiceMaster;

import com.epam.connection.ConnectionPool;
import com.epam.dao.Service.ServiceDAO;
import com.epam.dao.User.UserDAO;
import com.epam.model.ServiceMaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ServiceMasterDAOTest {

    @Mock
    private ConnectionPool connectionPoolMock;
    @Mock
    private Connection connectionMock;
    @Mock
    private PreparedStatement preparedStatementMock;
    @Mock
    private ResultSet resultSetMock;

    private ServiceMasterDAO serviceMasterDAO;
    private ServiceMaster testServiceMaster;
    private final Long testId = 100L;
    private final BigDecimal price = new BigDecimal(100);

    @BeforeEach
    void setUp() {
        try (MockedStatic<ConnectionPool> connectionPoolMockedStatic = mockStatic(ConnectionPool.class)) {
            connectionPoolMockedStatic.when(ConnectionPool::getInstance).thenReturn(connectionPoolMock);
            serviceMasterDAO = ServiceMasterDAO.getInstance();
        }

        when(connectionPoolMock.getConnection()).thenReturn(connectionMock);

        testServiceMaster = new ServiceMaster(testId, testId, testId, new BigDecimal(100));
    }

    @Test
    void testCreateServiceMasterWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatementMock).setBigDecimal(anyInt(), any());
        when(preparedStatementMock.executeUpdate()).thenReturn(1);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE);
        when(resultSetMock.getLong(1)).thenReturn(testId);

        serviceMasterDAO.createServiceMaster(testServiceMaster);

        verify(connectionMock, times(1)).prepareStatement(anyString(), anyInt());
        verify(preparedStatementMock, times(2)).setLong(anyInt(), anyLong());
        verify(preparedStatementMock, times(1)).setBigDecimal(anyInt(), any());
        verify(preparedStatementMock, times(1)).executeUpdate();
        verify(preparedStatementMock, times(1)).getGeneratedKeys();
        verify(resultSetMock, times(1)).next();
        verify(resultSetMock, times(1)).getLong(1);
    }

    @Test
    void testFindMasterByServiceWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(resultSetMock.getLong("master_id")).thenReturn(testId);
        when(resultSetMock.getBigDecimal("price")).thenReturn(price);

        List<ServiceMaster> serviceMasterList = serviceMasterDAO.findMasterByService(testId);

        assertEquals(1, serviceMasterList.size());
    }

    @Test
    void testFindServiceMasterWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE);
        when(resultSetMock.getLong(anyString())).thenReturn(testId);
        when(resultSetMock.getBigDecimal(anyString())).thenReturn(price);

        assertNotNull(serviceMasterDAO.findServiceMaster(testId));
    }

    @Test
    void testFindServiceMasterByMasterAndServiceWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE);
        when(resultSetMock.getLong(anyString())).thenReturn(testId);
        when(resultSetMock.getBigDecimal(anyString())).thenReturn(price);

        assertNotNull(serviceMasterDAO.findServiceMasterByMasterAndService(testId, testId));
    }

    @Test
    void testFindServiceMasterByMasterWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(resultSetMock.getLong(anyString())).thenReturn(testId);

        List<ServiceMaster> serviceMasterList = serviceMasterDAO.findServiceMasterByMaster(testId);

        assertEquals(1, serviceMasterList.size());
    }
}