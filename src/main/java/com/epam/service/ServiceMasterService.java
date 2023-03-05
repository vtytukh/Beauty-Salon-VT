package com.epam.service;

import com.epam.dao.ServiceMaster.ServiceMasterDAO;
import com.epam.model.ServiceMaster;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;

public class ServiceMasterService {
    private static final Logger LOGGER = LogManager.getLogger(ServiceMasterService.class);

    private ServiceMasterDAO serviceMasterDAO;

    public ServiceMasterService(ServiceMasterDAO serviceMasterDAO) {
        LOGGER.info("Initializing ServiceMasterServiceImpl");

        this.serviceMasterDAO = serviceMasterDAO;
    }

    public boolean addServiceMaster(ServiceMaster serviceMaster) {
        LOGGER.info("Add new service-master");
        serviceMasterDAO.createServiceMaster(serviceMaster);
        //return serviceMaster != null && serviceMasterDAO.createServiceMaster(serviceMaster).getId() != null;
        return true;
    }

    public ServiceMaster findServiceMasterById(Long id) {
        LOGGER.info("Finding a service by id = " + id);
        if (id == null) {
            return null;
        }
        return serviceMasterDAO.findServiceMaster(id);
    }

    public List<ServiceMaster> findMastersByService(Long id) {
        LOGGER.info("Finding masters by service id = " + id);
        if (id == null) {
            return new ArrayList<>();
        }
        return serviceMasterDAO.findMasterByService(id);
    }

    public List<ServiceMaster> findServiceMasterByMasterId(Long id) {
        LOGGER.info("Finding masters-service by master id = " + id);
        if (id == null) {
            return new ArrayList<>();
        }
        return serviceMasterDAO.findServiceMasterByMaster(id);
    }

    public ServiceMaster findServiceMasterByMasterAndService(Long master_id, Long service_id) {
        LOGGER.info("Finding masters-service by master id " + master_id + "and service id " + service_id);
        if (master_id == null || service_id == null) {
            return null;
        }
        return serviceMasterDAO.findServiceMasterByMasterAndService(master_id, service_id);
    }
}
