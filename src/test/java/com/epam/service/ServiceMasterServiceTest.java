package com.epam.service;

import com.epam.dao.ServiceMaster.ServiceMasterDAO;
import com.epam.model.ServiceMaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceMasterServiceTest {

    @Mock
    ServiceMasterDAO serviceMasterDAO;

    private ServiceMasterService serviceMasterService;
    private ServiceMaster testServiceMaster;
    private final Long testId = 100L;

    @BeforeEach
    void setUp() {
        serviceMasterService = new ServiceMasterService(serviceMasterDAO);
        testServiceMaster = new ServiceMaster(testId, testId, testId, new BigDecimal(100));
    }

    @Test
    void testAddServiceMaster() {
        when(serviceMasterDAO.createServiceMaster(any(ServiceMaster.class))).thenReturn(testServiceMaster);
        assertFalse(serviceMasterService.addServiceMaster(null));
        assertTrue(serviceMasterService.addServiceMaster(testServiceMaster));
    }

    @Test
    void testFindServiceMasterById() {
        when(serviceMasterDAO.findServiceMaster(anyLong())).thenReturn(testServiceMaster);
        assertNull(serviceMasterService.findServiceMasterById(null));
        assertEquals(testServiceMaster, serviceMasterService.findServiceMasterById(testId));
    }

    @Test
    void testFindMastersByService() {
        List<ServiceMaster> serviceMasterList = new ArrayList<>();
        serviceMasterList.add(testServiceMaster);

        when(serviceMasterDAO.findMasterByService(anyLong())).thenReturn(serviceMasterList);
        assertEquals(0, serviceMasterService.findMastersByService(null).size());
        assertEquals(1, serviceMasterService.findMastersByService(testId).size());
    }

    @Test
    void testFindServiceMasterByMasterId() {
        List<ServiceMaster> serviceMasterList = new ArrayList<>();
        serviceMasterList.add(testServiceMaster);

        when(serviceMasterDAO.findServiceMasterByMaster(anyLong())).thenReturn(serviceMasterList);
        assertEquals(0, serviceMasterService.findServiceMasterByMasterId(null).size());
        assertEquals(1, serviceMasterService.findServiceMasterByMasterId(testId).size());
    }

    @Test
    void testFindServiceMasterByMasterAndService() {
        when(serviceMasterDAO.findServiceMasterByMasterAndService(anyLong(), anyLong())).thenReturn(testServiceMaster);
        assertNull(serviceMasterService.findServiceMasterByMasterAndService(null, null));
        assertEquals(testServiceMaster, serviceMasterService.findServiceMasterByMasterAndService(testId, testId));
    }
}