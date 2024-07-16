package com.example.search_building.controllers;

import com.example.search_building.model.dto.BuildingDTO;
import com.example.search_building.repository.DistrictRepository;
import com.example.search_building.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BuildingController {
    @Autowired
    private BuildingService buildingService;


    @GetMapping(value = "/api/building/")
    public List<BuildingDTO> getBuilding(@RequestParam Map<String , Object> params,
                                         @RequestParam(name = "typeCode" , required = false) List<String> typeCode) {
        List<BuildingDTO> result = buildingService.findAll(params , typeCode);
        return result;
    }
}
