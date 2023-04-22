package com.epam.service;

import com.epam.dao.Service.ServiceDAO;
import com.epam.model.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceServiceTest {

    @Mock
    private ServiceDAO serviceDAO;

    private ServiceService serviceService;
    private Service testService;

    @BeforeEach
    void setUp() {
        serviceService = new ServiceService(serviceDAO);
        testService = new Service(1L, "hair", "new hair");
    }

    @Test
    void testAddService() {
        when(serviceDAO.createService(any(Service.class))).thenReturn(testService);
        assertFalse(serviceService.addService(null));
        assertTrue(serviceService.addService(testService));
    }

    @Test
    void testFindServiceById() {
        when(serviceDAO.findService(anyLong())).thenReturn(testService);
        assertNull(serviceService.findServiceById(null));
        assertEquals(testService, serviceService.findServiceById(1L));
    }

    @Test
    void testFindAll() {
        List<Service> serviceList = new ArrayList<>();
        serviceList.add(testService);

        when(serviceDAO.findAll()).thenReturn(serviceList);
        assertEquals(1, serviceService.findAll().size());
    }
}