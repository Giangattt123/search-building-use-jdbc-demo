package com.example.search_building.service;

import com.example.search_building.model.dto.BuildingDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BuildingService {
    public List<BuildingDTO> findAll(Map<String , Object> params , List<String> typeCode);
}
