package com.epam.dao.ServiceMaster;

import com.epam.model.ServiceMaster;

import java.util.List;

public interface IServiceMasterDAO {
    ServiceMaster createServiceMaster(ServiceMaster masterService);

    List<ServiceMaster> findMasterByService(Long id);

    ServiceMaster findServiceMaster(Long id);

    ServiceMaster findServiceMasterByMasterAndService(Long master_id, Long service_id);

    List<ServiceMaster> findServiceMasterByMaster(Long id);

}
