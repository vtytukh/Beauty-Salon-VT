package com.epam.dao.Record;

import com.epam.connection.ConnectionPool;
import com.epam.model.Record;
import com.epam.model.Status;
import com.epam.utility.ParseSqlProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecordDAO implements IRecordDAO {
    private static final Logger LOGGER = LogManager.getLogger(RecordDAO.class);
    private static RecordDAO INSTANCE;
    private static ConnectionPool connectionPool;
    private static String createQuery;
    private static String findByIdQuery;
    private static String findAllQuery;
    private static String updateQuery;
    private static String findAllByDateQuery;
    private static String findPreviousDayRecordsWithoutFeedback;
    private static String updateTimeQuery;
    private static String findAllByUserQuery;
    private static String findRecordsWithLimit;
    private static String findRecordsByUserWithLimit;
    private static String getCountRecords;
    private static String getCountRecordsByUserId;
    private static String getAvgMark;
    private static String updateMark;

    private RecordDAO() {

        connectionPool = ConnectionPool.getInstance();

        ParseSqlProperties properties = ParseSqlProperties.getInstance();
        createQuery = properties.getProperty("createRecord");
        findByIdQuery = properties.getProperty("findRecordById");
        findAllQuery = properties.getProperty("findRecords");
        updateQuery = properties.getProperty("updateStatus");
        findAllByDateQuery = properties.getProperty("findTimes");
        findPreviousDayRecordsWithoutFeedback = properties.getProperty("findPreviousDayRecordsWithoutFeedback");
        updateTimeQuery = properties.getProperty("updateTime");
        findAllByUserQuery = properties.getProperty("findRecordByUserId");
        findRecordsByUserWithLimit = properties.getProperty("findRecordsByUserWithLimit");
        findRecordsWithLimit = properties.getProperty("findRecordsWithLimit");
        getCountRecords = properties.getProperty("getCountRecords");
        getCountRecordsByUserId = properties.getProperty("getCountRecordsByUserId");
        getAvgMark = properties.getProperty("getAvgMark");
        updateMark = properties.getProperty("updateMark");
    }

    public static RecordDAO getInstance() {
        if (INSTANCE == null) {
            return new RecordDAO();
        }
        return INSTANCE;
    }

    public Record createRecord(Record record) {
        LOGGER.info("Creating record");
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, record.getUser_id());
            statement.setLong(2, record.getMaster_has_service_id());
            statement.setLong(3, record.getStatus_id());
            statement.setString(4, record.getTime());
            int resQuery = statement.executeUpdate();
            if (resQuery == 0) {
                LOGGER.error("Creation record failed");
            } else {
                LOGGER.info("Successful creation record");
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        record.setId(resultSet.getLong(1));
                    } else {
                        LOGGER.error("Failed to create record, no ID found.");
                    }
                }
            }
            LOGGER.info("Record added to data base");
        } catch (SQLException e) {
            LOGGER.error("Error to add to data base" + Arrays.toString(e.getStackTrace()));
        }
        return record;
    }


    public float getAvgRecords(List<Long> masterService) {
        LOGGER.info("Getting avg records");

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < masterService.size(); i++) {
            sb.append(" OR master_has_service_id = ").append(masterService.get(i));
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(getAvgMark + sb)) {
            statement.setLong(1, masterService.get(0));

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getFloat("avg");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return 0;
        }
        return 0;
    }

    public int getCountRecords() {
        LOGGER.info("Getting count records");

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(getCountRecords)) {
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt("count");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return 0;
        }
        return 0;
    }

    public int getCountRecordsByUserId(Long id) {
        LOGGER.info("Getting count records by user id = {}", id);

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(getCountRecordsByUserId)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt("count");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return 0;
        }
        return 0;
    }

    public List<Record> findRecordsWithLimit(int offset, int limit) {
        LOGGER.info("Getting records with limit");
        List<Record> listRecords = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(findRecordsWithLimit)) {
            statement.setInt(1, offset);
            statement.setInt(2, limit);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Record record = new Record();
                record.setId(result.getLong("id"));
                record.setUser_id(result.getLong("user_id"));
                record.setMaster_has_service_id(result.getLong("master_has_service_id"));
                record.setStatus_id(result.getLong("status_id"));
                record.setTime(result.getString("time"));

                listRecords.add(record);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return listRecords;
    }

    public List<Record> findAllRecords() {
        LOGGER.info("Getting all records");
        List<Record> listRecords = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(findAllQuery)) {
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Record record = new Record();
                record.setId(result.getLong("id"));
                record.setUser_id(result.getLong("user_id"));
                record.setMaster_has_service_id(result.getLong("master_has_service_id"));
                record.setStatus_id(result.getLong("status_id"));
                record.setTime(result.getString("time"));

                listRecords.add(record);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return listRecords;
    }

    public List<Record> findAllRecordsByDate(Long id, String date, boolean isReady) {
        LOGGER.info("Getting all records by date");
        List<Record> listRecords = new ArrayList<>();

        String sql = " AND status_id != 1";
        if (isReady)
            findAllByDateQuery += sql;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(findAllByDateQuery)) {
            statement.setLong(1, id);
            statement.setString(2, date + "%");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Record record = new Record();
                record.setId(result.getLong("id"));
                String s = result.getString("time");
                record.setTime(s);
                record.setUser_id(result.getLong("user_id"));
                record.setMaster_has_service_id(result.getLong("master_has_service_id"));
                record.setStatus_id(result.getLong("status_id"));

                listRecords.add(record);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return listRecords;
    }

    public List<Record> findPreviousDayRecordsWithoutFeedback() {
        LOGGER.info("Getting all previous day records without feedback");
        List<Record> listRecords = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(findPreviousDayRecordsWithoutFeedback)) {
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Record record = new Record();
                record.setId(result.getLong("id"));
                String s = result.getString("time");
                record.setTime(s);
                record.setUser_id(result.getLong("user_id"));
                record.setMaster_has_service_id(result.getLong("master_has_service_id"));
                record.setStatus_id(result.getLong("status_id"));

                listRecords.add(record);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return listRecords;
    }


    public Boolean updateMark(Long id, int mark, String feedback) {
        LOGGER.info("Update record id = {} with mark = {}", id, mark);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateMark)) {
            statement.setInt(1, mark);
            statement.setString(2, feedback);
            statement.setLong(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }

        return true;
    }


    public Boolean updateStatus(Long id, Status status) {
        LOGGER.info("Update status id = " + id + " to " + status.value());
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setLong(1, status.ordinal() + 1);
            statement.setLong(2, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }

        return true;
    }

    public Boolean updateTime(Long id, String date) {
        LOGGER.info("Update record id = {}, time = {}", id, date);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateTimeQuery)) {
            statement.setString(1, date);
            statement.setLong(2, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }

        return true;
    }

    public List<Record> findRecordsByUserId(Long id) {
        LOGGER.info("Getting service-master by user id = {}", id);
        List<Record> records = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(findAllByUserQuery)) {
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Record record = new Record();
                record.setId(result.getLong("id"));
                record.setUser_id(result.getLong("user_id"));
                record.setMaster_has_service_id(result.getLong("master_has_service_id"));
                record.setStatus_id(result.getLong("status_id"));
                record.setTime(result.getString("time"));

                records.add(record);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return records;
    }

    public List<Record> findRecordsByUserIdWithLimit(Long id, int offset, int limit) {
        LOGGER.info("Getting service-master by user id = {}", id);
        List<Record> records = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(findRecordsByUserWithLimit)) {
            statement.setLong(1, id);
            statement.setInt(2, offset);
            statement.setInt(3, limit);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Record record = new Record();
                record.setId(result.getLong("id"));
                record.setUser_id(result.getLong("user_id"));
                record.setMaster_has_service_id(result.getLong("master_has_service_id"));
                record.setStatus_id(result.getLong("status_id"));
                record.setTime(result.getString("time"));

                records.add(record);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return records;
    }

    public Record findRecord(Long id) {
        LOGGER.info("Getting service-master by id = {}", id);
        Record record = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(findByIdQuery)) {
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            record = getRecord(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return record;
    }

    private Record getRecord(ResultSet resultSet) {
        Record record = null;

        try {
            if (resultSet.next()) {
                record = new Record(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("master_has_service_id"),
                        resultSet.getLong("status_id"),
                        resultSet.getString("time"));
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }

        return record;
    }
}
