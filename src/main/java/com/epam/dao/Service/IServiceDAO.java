package com.epam.dao.Service;

import com.epam.model.Service;

import java.util.List;

public interface IServiceDAO {
    Service createService(Service service);

    List<Service> findAll();

    Service findService(Long id);

}
