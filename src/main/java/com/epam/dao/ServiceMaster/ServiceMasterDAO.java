package com.epam.dao.ServiceMaster;

import com.epam.connection.ConnectionPool;
import com.epam.model.ServiceMaster;
import com.epam.utility.ParseSqlProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceMasterDAO implements IServiceMasterDAO {
    private static final Logger LOGGER = LogManager.getLogger(ServiceMasterDAO.class);
    private static ServiceMasterDAO INSTANCE;
    private static ConnectionPool connectionPool;


    private static String createQuery;
    private static String findByIdQuery;
    private static String findByService;
    private static String findByMasterIdQuery;
    private static String findByMasterAndServiceIdQuery;

    private  ServiceMasterDAO() {
        connectionPool = ConnectionPool.getInstance();

        ParseSqlProperties properties = ParseSqlProperties.getInstance();
        createQuery = properties.getProperty("createServiceMaster");
        findByIdQuery = properties.getProperty("findServiceMasterById");
        findByMasterIdQuery = properties.getProperty("findServiceMasterByMasterId");
        findByService = properties.getProperty("findMastersByService");
        findByMasterAndServiceIdQuery = properties.getProperty("findServiceMasterByServiceAndMaster");
    }

    public static ServiceMasterDAO getInstance(){
        if(INSTANCE == null){
            return new ServiceMasterDAO();
        }
        return INSTANCE;
    }

    public ServiceMaster createServiceMaster(ServiceMaster masterService){
        LOGGER.info("Creating service");
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, masterService.getMaster_id());
            statement.setLong(2, masterService.getService_id());
            statement.setBigDecimal(3, masterService.getPrice());
            int resQuery = statement.executeUpdate();
            if(resQuery == 0){
                LOGGER.error("Creation service-master failed");
            } else {
                LOGGER.info("Successful creation service-master");
                try(ResultSet resultSet = statement.getGeneratedKeys()){
                    if(resultSet.next()) {
                        masterService.setId(resultSet.getLong(1));
                    } else {
                        LOGGER.error("Failed to create service-master, no ID found.");
                    }
                }
            }
            LOGGER.info("Service-master added to data base");
        } catch (SQLException e) {
            LOGGER.error("Error to add to data base" + Arrays.toString(e.getStackTrace()));
        }
        return masterService;
    }

//    public ServiceMaster findServiceMasterBy(Long id) {
//        LOGGER.info("Getting service-master by id " + id);
//        ServiceMaster serviceMaster = null;
//        //try(Connection connection = connectionPool.getConnection()) {
//        try(PreparedStatement statement = connection.prepareStatement(findByIdQuery)){
//            statement.setLong(1, id);
//
//            ResultSet result = statement.executeQuery();
//
//            serviceMaster = getService(result);
//        } catch (SQLException e) {
//            LOGGER.error(e.getMessage());
//        }
//
//        return serviceMaster;
//    }


    public List<ServiceMaster> findMasterByService(Long id) {
        LOGGER.info("Getting masters by service id " + id);
        List<ServiceMaster> listMasters = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(findByService)){
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();
            while(result.next()) {
                ServiceMaster serviceMaster = new ServiceMaster();
                serviceMaster.setMaster_id(result.getLong("master_id"));
                serviceMaster.setPrice(result.getBigDecimal("price"));

                listMasters.add(serviceMaster);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return listMasters;
    }

    public ServiceMaster findServiceMaster(Long id) {
        LOGGER.info("Getting service-master by id " + id);
        ServiceMaster serviceMaster = null;
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(findByIdQuery)){
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            serviceMaster = getService(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return serviceMaster;
    }

    public ServiceMaster findServiceMasterByMasterAndService(Long master_id, Long service_id) {
        LOGGER.info("Getting service-master by master id "+master_id+"and service id " + service_id);
        ServiceMaster sm = null;
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(findByMasterAndServiceIdQuery)){
            statement.setLong(1, master_id);
            statement.setLong(2, service_id);

            ResultSet result = statement.executeQuery();

            if (result.next()){
                sm = new ServiceMaster();
                sm.setId(result.getLong("id"));
                sm.setPrice(result.getBigDecimal("price"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return sm;
    }

    public List<ServiceMaster> findServiceMasterByMaster(Long id) {
        LOGGER.info("Getting service-master by master id " + id);
        List<ServiceMaster> serviceMaster = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(findByMasterIdQuery)){
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            while (result.next()){
                ServiceMaster sm = new ServiceMaster();
                sm.setId(result.getLong("id"));
                sm.setService_id(result.getLong("service_id"));
                serviceMaster.add(sm);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return serviceMaster;
    }

    private ServiceMaster getService(ResultSet resultSet) {
        ServiceMaster serviceMaster = null;

        try {
            if(resultSet.next()) {
                serviceMaster = new ServiceMaster(
                        resultSet.getLong("id"),
                        resultSet.getLong("master_id"),
                        resultSet.getLong("service_id"),
                        resultSet.getBigDecimal("price"));
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }

        return serviceMaster;
    }
}
