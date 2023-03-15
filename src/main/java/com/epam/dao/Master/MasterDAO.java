package com.epam.dao.Master;

import com.epam.connection.ConnectionPool;
import com.epam.model.Master;
import com.epam.model.User;
import com.epam.utility.ParseSqlProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MasterDAO implements IMasterDAO {
    private static final Logger LOGGER = LogManager.getLogger(MasterDAO.class);
    private static MasterDAO INSTANCE;
    private static ConnectionPool connectionPool;

    private static String updateQuery;
    private static String findByIdQuery;
    private static String findAllQuery;
    private static String findMasterByName;
    private static String findByUserIdQuery;
    private static String updateRate;

    private  MasterDAO() {
        connectionPool = ConnectionPool.getInstance();

        ParseSqlProperties properties = ParseSqlProperties.getInstance();
        updateQuery = properties.getProperty("createMaster");
        findByIdQuery = properties.getProperty("findMasterById");
        findAllQuery = properties.getProperty("findAllMastersWithName");
        findMasterByName = properties.getProperty("findMasterByName");
        findByUserIdQuery = properties.getProperty("findMasterByUserId");
        updateRate = properties.getProperty("updateRate");
    }

    public static MasterDAO getInstance(){
        if(INSTANCE == null){
            return new MasterDAO();
        }
        return INSTANCE;
    }

    public Master setMaster(Long id){
        LOGGER.info("Creating master");
        Master master = new Master();
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            statement.setFloat(2, 0);
            int resQuery = statement.executeUpdate();
            if(resQuery == 0){
                LOGGER.error("Creation master failed");
            } else {
                try(ResultSet resultSet = statement.getGeneratedKeys()){
                    if(resultSet.next()) {
                        master.setId(resultSet.getLong(1));
                        //master.setUser(user);
                        master.setUser_id(id);
                        master.setMark(0);
                    } else {
                        LOGGER.error("Failed to create user, no ID found.");
                    }
                }
                LOGGER.info("Successful creation master");
            }
            LOGGER.info("Master added to data base");
        } catch (SQLException e) {
            LOGGER.error("Error to add to data base" + Arrays.toString(e.getStackTrace()));
        }
        return master;
    }

    public List<Master> findAllWithName() {
        LOGGER.info("Getting all masters with name");
        List<Master> listMasters = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(findAllQuery)){
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                Master master = new Master();
                master.setId(result.getLong("id"));
                //master.setUser_id(result.getLong("user_id"));
                master.setMark(result.getFloat("rate"));
                master.setUser(
                        new User(
                                result.getString("first_name"),
                                result.getString("last_name")));

                listMasters.add(master);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return listMasters;
    }


    public List<Master> findAllWithNameOrder(boolean isByRate, boolean isDescending) {
        LOGGER.info("Getting all masters with name");
        List<Master> listMasters = new ArrayList<>();
        String sql = findAllQuery;
        String order = " ORDER BY ";
        if(isByRate) order += "master.rate";
        else order += "user.first_name"; //, user.last_name";

        sql += order;
        if(isDescending) sql += " DESC";

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                Master master = new Master();
                master.setId(result.getLong("id"));
                //master.setUser_id(result.getLong("user_id"));
                master.setMark(result.getFloat("rate"));
                master.setUser(
                        new User(
                                result.getString("first_name"),
                                result.getString("last_name")));

                listMasters.add(master);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return listMasters;
    }

    public Master findMasterWithNameById(Long id) {
        LOGGER.info("Getting master with name by id = {}", id);
        Master master = null;

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(findMasterByName)){
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            master = new Master();
            User user = new User();
            if(result.next()) {
                user.setFirstName(result.getString("user.first_name"));
                user.setLastName(result.getString("last_name"));
                master.setUser(user);
                master.setMark(result.getFloat("rate"));
            }


        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return master;
    }

    public List<Master> findAll() {
        LOGGER.info("Getting all masters");
        List<Master> listMasters = new ArrayList<>();

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(findAllQuery)){
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                Master master = new Master();
                master.setId(result.getLong("id"));
                master.setUser_id(result.getLong("user_id"));
                master.setMark(result.getFloat("rate"));

                listMasters.add(master);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return listMasters;
    }


    public boolean updateMasterRate(Long id, float rate){
        LOGGER.info("Updating master id = {}, rate = {}", id, rate);
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(updateRate)){
            statement.setFloat(1, rate);
            statement.setLong(2, id);

            int res = statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }

        return true;
    }

    public Master findMasterByUserId(Long id){
        LOGGER.info("Getting master by user id = {}", id);
        Master master = null;
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(findByUserIdQuery)){
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                master = new Master();
                master.setId(result.getLong("id"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return master;
    }


    public Master findMasterById(Long id){
        LOGGER.info("Getting master by id = {}", id);
        Master master = null;
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(findByIdQuery)){
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            master = getMaster(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return master;
    }

    private Master getMaster(ResultSet resultSet) {
        Master master = null;

        try {
            if(resultSet.next()) {
                master = new Master();
                master.setId(resultSet.getLong("id"));
                master.setUser_id(resultSet.getLong("user_id"));
                master.setMark(resultSet.getFloat("rate"));

            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }

        return master;
    }

}
