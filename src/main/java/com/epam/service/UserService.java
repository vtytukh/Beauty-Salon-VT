package com.epam.service;

import com.epam.dao.User.UserDAO;
import com.epam.model.Role;
import com.epam.model.User;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;

public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    private UserDAO userDao;

    public UserService(UserDAO userDao) {
        LOGGER.info("Initializing UserServiceImpl");

        this.userDao = userDao;
    }

    public List<User> findAllUsers(int offset, int limit) {
        LOGGER.info("Find all users with offset {} and limit {}", offset, limit);
        return userDao.findAllUsers(offset, limit);
    }

    public boolean checkEmailAvailability(String email) {
        LOGGER.info("Checking availability of email {}", email);

        if (email == null) {
            return false;
        }

        User user = userDao.findUserByEmail(email);
        return user == null;
    }

    public boolean registerUser(User user) {
        LOGGER.info("New user registration");
        return user != null && userDao.createUser(user).getId() != null;
    }

    public User getUserByCredentials(String email, String password) {
        LOGGER.info("Getting user by credentials");

        if (email == null || password == null) {
            return null;
        }

        return userDao.findUserByEmailAndPassword(email, password);
    }

    public User findUserByEmail(String email) {
        LOGGER.info("Finding user by email {}", email);

        if (email == null) {
            return null;
        }

        return userDao.findUserByEmail(email);
    }

    public User findUserById(Long id) {
        LOGGER.info("Finding user by id {}", id);

        if (id == null) {
            return null;
        }

        return userDao.findUserById(id);
    }

    public boolean updateRole(Long id, Role role) {
        LOGGER.info("Updating role " + id + " to " + role.value());
        return userDao.updateRole(id, role);
    }

    public int getCountUsers() {
        LOGGER.info("Getting count of users");

        return userDao.getCountUsers();
    }

}
