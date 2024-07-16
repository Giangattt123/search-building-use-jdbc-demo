package com.example.search_building.repository;

import com.example.search_building.repository.entity.DistrictEntity;

public interface DistrictRepository {
    DistrictEntity findNameById(Long id);
}
