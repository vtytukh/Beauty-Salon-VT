package com.epam.service;

import com.epam.dao.Master.MasterDAO;
import com.epam.model.Master;
import com.epam.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MasterServiceTest {

    @Mock
    private MasterDAO masterDAOMock;

    private MasterService masterService;
    private Master testMaster;
    private final Long testId = 1L;
    private List<Master> testMastersList;

    @BeforeEach
    void setUp() {
        masterService = new MasterService(masterDAOMock);
        testMaster = new Master(testId, new User(), 5.0f);
        testMastersList = new ArrayList<>();
        testMastersList.add(testMaster);
    }

    @Test
    void testAddMaster() {
        when(masterDAOMock.setMaster(anyLong())).thenReturn(testMaster);
        assertFalse(masterService.addMaster(null));
        assertTrue(masterService.addMaster(testId));
    }

    @Test
    void testFindMasterById() {
        when(masterDAOMock.findMasterById(anyLong())).thenReturn(testMaster);
        assertNull(masterService.findMasterById(null));
        assertEquals(testMaster, masterService.findMasterById(testId));
    }

    @Test
    void testFindAllWithName() {
        when(masterDAOMock.findAllWithName()).thenReturn(testMastersList);
        assertEquals(1, masterService.findAllWithName().size());
    }

    @Test
    void testFindMastersWithNameByServiceId() {
        when(masterDAOMock.findMastersWithNameByServiceId(anyBoolean(), anyBoolean(), anyLong())).thenReturn(testMastersList);
        assertEquals(1, masterService.findMastersWithNameByServiceId(true, true, testId).size());
    }

    @Test
    void testFindMastersWithNameOrderBy() {
        when(masterDAOMock.findMastersWithNameOrderBy(anyBoolean(), anyBoolean())).thenReturn(testMastersList);
        assertEquals(1, masterService.findMastersWithNameOrderBy(true, true).size());
    }

    @Test
    void testFindMasterWithNameById() {
        when(masterDAOMock.findMasterWithNameById(anyLong())).thenReturn(testMaster);
        assertNull(masterService.findMasterWithNameById(null));
        assertEquals(testMaster, masterService.findMasterWithNameById(testId));
    }

    @Test
    void testFindMasterByUserId() {
        when(masterDAOMock.findMasterByUserId(anyLong())).thenReturn(testMaster);
        assertNull(masterService.findMasterByUserId(null));
        assertEquals(testMaster, masterService.findMasterByUserId(testId));
    }

    @Test
    void updateMasterRate() {
        when(masterDAOMock.updateMasterRate(anyLong(), anyFloat())).thenReturn(Boolean.TRUE);
        assertFalse(masterService.updateMasterRate(null, 5.0f));
        assertTrue(masterService.updateMasterRate(testId, 5.0f));
    }
}