package com.epam.service;

import com.epam.dao.Service.ServiceDAO;
import com.epam.model.Service;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;

public class ServiceService {

    private static final Logger LOGGER = LogManager.getLogger(ServiceService.class);

    private ServiceDAO serviceDAO;

    public ServiceService(ServiceDAO serviceDAO) {
        LOGGER.info("Initializing ServiceServiceImpl");
        this.serviceDAO = serviceDAO;
    }

    public boolean addService(Service service) {
        LOGGER.info("Add new service");
        return service != null && serviceDAO.createService(service).getId() != null;
    }

    public Service findServiceById(Long id) {
        LOGGER.info("Finding a service by id = {}", id);
        if (id == null) {
            return null;
        }
        return serviceDAO.findService(id);
    }

    public List<Service> findAll() {
        LOGGER.info("Getting all services");
        return serviceDAO.findAll();
    }

}