package com.epam.service;

import com.epam.dao.Master.MasterDAO;
import com.epam.model.Master;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;

public class MasterService {
    private static final Logger LOGGER = LogManager.getLogger(MasterService.class);

    private MasterDAO masterDAO;

    public MasterService(MasterDAO masterDAO) {
        LOGGER.info("Initializing ServiceServiceImpl");
        this.masterDAO = masterDAO;
    }

    public boolean addMaster(Long id) {
        LOGGER.info("Add new service");
        return id != null && masterDAO.setMaster(id).getId() != null;
    }

    public Master findMasterById(Long id) {
        LOGGER.info("Finding a master by id = {}", id);
        if (id == null) {
            return null;
        }
        return masterDAO.findMasterById(id);
    }

    public List<Master> findAllWithName() {
        LOGGER.info("Getting all masters");

        return masterDAO.findAllWithName();
    }

    public List<Master> findMastersWithNameByServiceId(boolean isByRate, boolean isDescending, Long serviceId) {
        LOGGER.info("Getting masters by service id = {} with order", serviceId);

        return masterDAO.findMastersWithNameByServiceId(isByRate, isDescending, serviceId);
    }

    public List<Master> findMastersWithNameOrderBy(boolean isByRate, boolean isDescending) {
        LOGGER.info("Getting masters with order");

        return masterDAO.findMastersWithNameOrderBy(isByRate, isDescending);
    }

    public Master findMasterWithNameById(Long id) {
        LOGGER.info("Finding master with name by id = {}", id);
        if (id == null) {
            return null;
        }
        return masterDAO.findMasterWithNameById(id);
    }

    public Master findMasterByUserId(Long id) {
        LOGGER.info("Finding master by user id = {}", id);
        if (id == null) {
            return null;
        }
        return masterDAO.findMasterByUserId(id);
    }

    public boolean updateMasterRate(Long id, float rate) {
        LOGGER.info("Updating master rate with id = {}", id);
        if (id == null) {
            return false;
        }
        return masterDAO.updateMasterRate(id, rate);
    }
}
