package com.epam.dao.User;

import com.epam.connection.ConnectionPool;
import com.epam.model.Role;
import com.epam.model.User;
import com.epam.model.UserBuilder;
import com.epam.service.Encrypt;
import com.epam.utility.ParseSqlProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDAO implements IUserDAO {
    private static final Logger LOGGER = LogManager.getLogger(UserDAO.class);
    private static UserDAO INSTANCE;
    private static ConnectionPool connectionPool;

    private static String createQuery;
    private static String updateQuery;
    private static String deleteQuery;
    private static String findByIdQuery;
    private static String findByEmailQuery;
    private static String findByEmailAndPasswordQuery;
    private static String findAllQuery;
    private static String updateUserToMaster;
    private static String getCountUsers;

    private  UserDAO(){
        connectionPool = ConnectionPool.getInstance();

        ParseSqlProperties properties = ParseSqlProperties.getInstance();
        createQuery = properties.getProperty("createUser");
        updateQuery = properties.getProperty("updateUserById");
        deleteQuery = properties.getProperty("deleteUserById");
        findByIdQuery = properties.getProperty("findUserById");
        findByEmailQuery = properties.getProperty("findUserByEmail");
        findByEmailAndPasswordQuery = properties.getProperty("findUserByEmailAndPassword");
        findAllQuery = properties.getProperty("findAllUsers");
        updateUserToMaster = properties.getProperty("updateUserToMaster");
        getCountUsers = properties.getProperty("getCountUsers");
    }

    public static UserDAO getInstance(){
        if(INSTANCE == null){
            return new UserDAO();
        }
        return INSTANCE;
    }

    public User createUser(User user){
        LOGGER.info("Creating user");
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, Encrypt.encrypt(user.getPassword()));
            statement.setInt(5, Role.CLIENT.ordinal()+1);
            int resQuery = statement.executeUpdate();
            if(resQuery == 0){
                LOGGER.error("Creation user failed");
            } else {
                LOGGER.info("Successful creation user");
                try(ResultSet resultSet = statement.getGeneratedKeys()){
                    if(resultSet.next()) {
                        user.setId(resultSet.getLong(1));
                    } else {
                        LOGGER.error("Failed to create user, no ID found.");
                    }
                }
            }
            LOGGER.info("User added to data base");
        } catch (SQLException e) {
            LOGGER.error("Error to add to data base {}", Arrays.toString(e.getStackTrace()));
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Something wrong with Encrypt {}", Arrays.toString(e.getStackTrace()));
        }

        return user;
    }

    public List<User> findAllUsers(int offset, int limit) {
        LOGGER.info("Getting all users by limit {} with offset {}", limit, offset);
        List<User> users = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(findAllQuery)){
            statement.setInt(1, offset);
            statement.setInt(2, limit);

            ResultSet result = statement.executeQuery();
            while(result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setFirstName(result.getString("first_name"));
                user.setLastName(result.getString("last_name"));
                user.setEmail(result.getString("email"));
                user.setRole(Role.values()[result.getInt("role_id") - 1]);

                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return users;
    }


    public User findUserByEmailAndPassword(String email, String password) {
        LOGGER.info("Getting user with email {}", email);
        User user = null;
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(findByEmailAndPasswordQuery)){
            statement.setString(1, email);
            statement.setString(2, Encrypt.encrypt(password));

            ResultSet result = statement.executeQuery();

            user = getUser(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Something wrong with Encrypt {}", Arrays.toString(e.getStackTrace()));
        }

        return user;
    }

    public Boolean updateRole(Long id, Role role) {
        LOGGER.info("Update role {} to {}", id, role.value());
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(updateUserToMaster)){
            statement.setLong(1, role.ordinal()+1);
            statement.setLong(2, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }

        return true;
    }

    public User findUserByEmail(String email) {
        LOGGER.info("Getting user with email {}", email);
        User user = null;
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(findByEmailQuery)){
            statement.setString(1, email);

            ResultSet result = statement.executeQuery();

            user = getUser(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return user;
    }
    public User findUserById(Long id) {
        LOGGER.info("Getting user with id {}", id);
        User user = null;
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(findByIdQuery)){
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            user = getUser(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return user;
    }

    public int getCountUsers() {
        LOGGER.info("Getting count users");

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(getCountUsers)) {
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return result.getInt("count");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return 0;
        }
        return 0;
    }

    private User getUser(ResultSet resultSet) {
        User user = null;

        try {
            if(resultSet.next()) {
                user = new UserBuilder().setId(resultSet.getLong("id"))
                        .setFirstName(resultSet.getString("first_name"))
                        .setLastName(resultSet.getString("last_name"))
                        .setEmail(resultSet.getString("email"))
                        //.setPassword(resultSet.getString("password"))
                        .setPassword(Encrypt.encrypt(resultSet.getString("password")))
                        .setUserType(Role.values()[resultSet.getInt("role_id") - 1])
                        .build();
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Something wrong with Encrypt {}", Arrays.toString(e.getStackTrace()));
        }

        return user;
    }

}
