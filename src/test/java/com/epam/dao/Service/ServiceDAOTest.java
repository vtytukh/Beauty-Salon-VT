package com.epam.dao.Service;

import com.epam.connection.ConnectionPool;
import com.epam.model.Service;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceDAOTest {

    @Mock
    private ConnectionPool connectionPoolMock;
    @Mock
    private Connection connectionMock;
    @Mock
    private PreparedStatement preparedStatementMock;
    @Mock
    private ResultSet resultSetMock;

    private ServiceDAO serviceDAO;
    private Service testService;
    private final Long serviceId = 100L;

    @BeforeEach
    void setUp() {
        try (MockedStatic<ConnectionPool> connectionPoolMockedStatic = mockStatic(ConnectionPool.class)) {
            connectionPoolMockedStatic.when(ConnectionPool::getInstance).thenReturn(connectionPoolMock);
            serviceDAO = ServiceDAO.getInstance();
        }

        testService = new Service(serviceId, "Hair", "New hair");

        when(connectionPoolMock.getConnection()).thenReturn(connectionMock);
    }

    @Test
    void testCreateServiceWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setString(anyInt(), anyString());
        when(preparedStatementMock.executeUpdate()).thenReturn(1);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE);
        when(resultSetMock.getLong(1)).thenReturn(serviceId);

        serviceDAO.createService(testService);

        verify(connectionMock, times(1)).prepareStatement(anyString(), anyInt());
        verify(preparedStatementMock, times(2)).setString(anyInt(), anyString());
        verify(preparedStatementMock, times(1)).executeUpdate();
        verify(preparedStatementMock, times(1)).getGeneratedKeys();
        verify(resultSetMock, times(1)).next();
        verify(resultSetMock, times(1)).getLong(1);
    }

    @Test
    void testFindAllWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(resultSetMock.getLong("id")).thenReturn(serviceId);
        when(resultSetMock.getString("name")).thenReturn("Hair");
        when(resultSetMock.getString("description")).thenReturn("New hair");

        List<Service> serviceList = serviceDAO.findAll();

        assertEquals(2, serviceList.size());
    }

    @Test
    void testFindServiceWithoutException() throws SQLException {
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        doNothing().when(preparedStatementMock).setLong(anyInt(), anyLong());
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(Boolean.TRUE);
        when(resultSetMock.getLong("id")).thenReturn(serviceId);
        when(resultSetMock.getString("name")).thenReturn("Hair");
        when(resultSetMock.getString("description")).thenReturn("New hair");

        assertNotNull(serviceDAO.findService(serviceId));
    }
}