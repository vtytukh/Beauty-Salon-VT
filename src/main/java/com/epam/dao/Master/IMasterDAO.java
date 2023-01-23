package com.epam.dao.Master;

import com.epam.model.Master;

import java.util.List;

public interface IMasterDAO {

    Master setMaster(Long id);

    List<Master> findAllWithName();

    List<Master> findAllWithNameOrder(boolean isByRate, boolean isDescending);

    Master findMasterWithNameById(Long id);

    List<Master> findAll();

    boolean updateMasterRate(Long id, float rate);

    Master findMasterByUserId(Long id);

    Master findMasterById(Long id);

}
