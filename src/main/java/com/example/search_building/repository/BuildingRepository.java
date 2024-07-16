package com.example.search_building.repository;

import com.example.search_building.repository.entity.BuildingEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface BuildingRepository {
    public List<BuildingEntity> findAll(Map<String , Object> params , List<String> typeCode);
}
