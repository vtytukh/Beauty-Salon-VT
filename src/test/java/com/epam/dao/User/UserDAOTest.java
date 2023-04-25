package com.epam.dao.User;

import com.epam.connection.ConnectionPool;
import com.epam.model.Role;
import com.epam.model.User;
import com.epam.model.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDAOTest {

    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    private UserDAO userDAO;
    private User testUser;
    private final long userId = 100L;

    @BeforeEach
    void setUp() {

        try (MockedStatic<ConnectionPool> connectionPoolMock = mockStatic(ConnectionPool.class)) {
            connectionPoolMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            userDAO = UserDAO.getInstance();
        }

        when(connectionPool.getConnection()).thenReturn(connection);

        testUser = new UserBuilder().setFirstName("Name")
                .setLastName("Surname")
                .setEmail("test@mail.com")
                .setPassword("password")
                .setUserType(Role.CLIENT)
                .setId(userId)
                .build();
    }

    @Test
    void testCreateUserWithoutException() throws SQLException {
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setString(anyInt(), anyString());
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE);
        when(resultSet.getLong(1)).thenReturn(userId);

        userDAO.createUser(testUser);

        verify(connection, times(1)).prepareStatement(anyString(), anyInt());
        verify(preparedStatement, times(4)).setString(anyInt(), anyString());
        verify(preparedStatement, times(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).getGeneratedKeys();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getLong(1);
    }

    @Test
    void testCreateUserThrowSQLException() throws SQLException {
        when(connection.prepareStatement(anyString(), anyInt())).thenThrow(new SQLException());
        userDAO.createUser(testUser);
    }

    @Test
    void testFindAllUsersWithoutExceptionWithOneUserReturn() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        when(resultSet.getLong("id")).thenReturn(userId);
        when(resultSet.getString("first_name")).thenReturn("Name");
        when(resultSet.getString("last_name")).thenReturn("Surname");
        when(resultSet.getString("email")).thenReturn("test@mail.com");
        when(resultSet.getInt("role_id")).thenReturn(Role.CLIENT.ordinal()+1);

        List<User> userList = userDAO.findAllUsers(1, 1);

        assertEquals(1, userList.size());
    }

    @Test
    void findUserByEmailAndPassword() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setString(anyInt(), anyString());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE);
        when(resultSet.getLong("id")).thenReturn(userId);
        when(resultSet.getString("first_name")).thenReturn("Name");
        when(resultSet.getString("last_name")).thenReturn("Surname");
        when(resultSet.getString("email")).thenReturn("test@mail.com");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getInt("role_id")).thenReturn(Role.CLIENT.ordinal()+1);

        assertNotNull(userDAO.findUserByEmailAndPassword("test@mail.com", "password"));
    }

    @Test
    void testUpdateRoleToMasterWithoutException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertTrue(userDAO.updateRole(userId, Role.MASTER));
    }

    @Test
    void testFindUserByEmailWithoutException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setString(anyInt(), anyString());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE);
        when(resultSet.getLong("id")).thenReturn(userId);
        when(resultSet.getString("first_name")).thenReturn("Name");
        when(resultSet.getString("last_name")).thenReturn("Surname");
        when(resultSet.getString("email")).thenReturn("test@mail.com");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getInt("role_id")).thenReturn(Role.CLIENT.ordinal()+1);

        assertNotNull(userDAO.findUserByEmail("test@mail.com"));
    }

    @Test
    void testFindUserByIdWithoutException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE);
        when(resultSet.getLong("id")).thenReturn(userId);
        when(resultSet.getString("first_name")).thenReturn("Name");
        when(resultSet.getString("last_name")).thenReturn("Surname");
        when(resultSet.getString("email")).thenReturn("test@mail.com");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getInt("role_id")).thenReturn(Role.CLIENT.ordinal()+1);

        assertNotNull(userDAO.findUserById(userId));
    }

    @Test
    void testGetCountUsersWithoutException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE);
        when(resultSet.getInt("count")).thenReturn(100);

        assertEquals(100, userDAO.getCountUsers());
    }
}