package com.epam.service;

import com.epam.dao.User.UserDAO;
import com.epam.model.Role;
import com.epam.model.User;
import com.epam.model.UserBuilder;
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
class UserServiceTest {

    @Mock
    private UserDAO userDAO;
    private UserService userService;

    private User testUser;
    private final long userId = 100L;

    @BeforeEach
    void setUp() {
        userService = new UserService(userDAO);

        testUser = new UserBuilder().setFirstName("Name")
                .setLastName("Surname")
                .setEmail("test@mail.com")
                .setPassword("password")
                .setUserType(Role.CLIENT)
                .setId(userId)
                .build();
    }

    @Test
    void testFindAllUsers() {
        User testUser2 = new UserBuilder().setFirstName("Name2")
                .setLastName("Surname2")
                .setEmail("test2@mail.com")
                .setPassword("password2")
                .setUserType(Role.CLIENT)
                .setId(userId)
                .build();
        List<User> userList = new ArrayList<>();
        userList.add(testUser);
        userList.add(testUser2);
        when(userDAO.findAllUsers(anyInt(), anyInt())).thenReturn(userList);

        assertEquals(2, userService.findAllUsers(1, 5).size());

    }

    @Test
    void testCheckEmailAvailability() {
        when(userDAO.findUserByEmail(anyString())).thenReturn(testUser);
        assertFalse(userService.checkEmailAvailability(null));
        assertFalse(userService.checkEmailAvailability("test@mail.com"));
    }

    @Test
    void testRegisterUser() {
        when(userDAO.createUser(any(User.class))).thenReturn(testUser);
        assertTrue(userService.registerUser(testUser));
        assertFalse(userService.registerUser(null));
    }

    @Test
    void testGetUserByCredentials() {
        when(userDAO.findUserByEmailAndPassword(anyString(), anyString())).thenReturn(testUser);
        assertNull(userService.getUserByCredentials(null, null));
        assertEquals(testUser, userService.getUserByCredentials("test@mail.com", "password"));
    }

    @Test
    void testFindUserByEmail() {
        when(userDAO.findUserByEmail(anyString())).thenReturn(testUser);
        assertNull(userService.findUserByEmail(null));
        assertEquals(testUser, userService.findUserByEmail("test@mail.com"));
    }

    @Test
    void testFindUserById() {
        when(userDAO.findUserById(anyLong())).thenReturn(testUser);
        assertNull(userService.findUserById(null));
        assertEquals(testUser, userService.findUserById(userId));
    }

    @Test
    void testUpdateRole() {
        when(userDAO.updateRole(anyLong(), any(Role.class))).thenReturn(Boolean.TRUE);
        assertTrue(userService.updateRole(userId, Role.MASTER));
    }

    @Test
    void testGetCountUsers() {
        when(userDAO.getCountUsers()).thenReturn(100);
        assertEquals(100, userService.getCountUsers());
    }
}