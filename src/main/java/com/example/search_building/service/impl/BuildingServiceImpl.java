package com.example.search_building.service.impl;

import com.example.search_building.model.dto.BuildingDTO;
import com.example.search_building.repository.BuildingRepository;
import com.example.search_building.repository.DistrictRepository;
import com.example.search_building.repository.entity.BuildingEntity;
import com.example.search_building.repository.entity.DistrictEntity;
import com.example.search_building.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private DistrictRepository districtRepository;
    @Override
    public List<BuildingDTO> findAll(Map<String, Object> params, List<String> typeCode) {
        List<BuildingEntity> buildingEntities = buildingRepository.findAll(params , typeCode);
        List<BuildingDTO> result = new ArrayList<BuildingDTO>();
        for (BuildingEntity item : buildingEntities) {
            BuildingDTO building = new BuildingDTO();
            building.setName(item.getName());
            DistrictEntity districtEntity = districtRepository.findNameById(item.getDistricid());
            building.setAddress(item.getStreet() + ", " + item.getWard() + ", " + districtEntity.getName());
            result.add(building);
        }
        return result;
    }
}
