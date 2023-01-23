package com.epam.dao.Service;

import com.epam.connection.ConnectionPool;
import com.epam.model.Service;
import com.epam.utility.ParseSqlProperties;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceDAO implements IServiceDAO {
    private static final Logger LOGGER = Logger.getLogger(ServiceDAO.class);
    private static ServiceDAO INSTANCE;
    private static ConnectionPool connectionPool;


    private static String createQuery;
    private static String findByIdQuery;
    private static String findAllQuery;

    private  ServiceDAO() {
        connectionPool = ConnectionPool.getInstance();

        ParseSqlProperties properties = ParseSqlProperties.getInstance();
        createQuery = properties.getProperty("createService");
        findByIdQuery = properties.getProperty("findServiceById");
        findAllQuery = properties.getProperty("findAllServices");
    }

    public static ServiceDAO getInstance(){
        if(INSTANCE == null){
            return new ServiceDAO();
        }
        return INSTANCE;
    }


    public Service createService(Service service){
        LOGGER.info("Creating service");
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, service.getName());
            statement.setString(2, service.getDescription());
            int resQuery = statement.executeUpdate();
            if(resQuery == 0){
                LOGGER.error("Creation service failed");
            } else {
                LOGGER.info("Successful creation service");
                try(ResultSet resultSet = statement.getGeneratedKeys()){
                    if(resultSet.next()) {
                        service.setId(resultSet.getLong(1));
                    } else {
                        LOGGER.error("Failed to create service, no ID found.");
                    }
                }
            }
            LOGGER.info("Service added to data base");
        } catch (SQLException e) {
            LOGGER.error("Error to add to data base" + Arrays.toString(e.getStackTrace()));
        }
        return service;
    }

    public List<Service> findAll() {
        LOGGER.info("Getting all services");
        List<Service> listService = new ArrayList<>();

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(findAllQuery)){
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                Service service = new Service();
                service.setId(result.getLong("id"));
                service.setName(result.getString("name"));
                service.setDescription(result.getString("description"));

                listService.add(service);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return listService;
    }

    public Service findService(Long id) {
        LOGGER.info("Getting service by id " + id);
        Service service = null;
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(findByIdQuery)){
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            service = getService(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return service;
    }


    private Service getService(ResultSet resultSet) {
        Service service = null;

        try {
            if(resultSet.next()) {
                service = new Service(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"));
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }

        return service;
    }


}
