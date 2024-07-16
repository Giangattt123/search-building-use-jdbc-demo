package com.example.search_building.repository.impl;

import com.example.search_building.db.DBConnect;
import com.example.search_building.repository.DistrictRepository;
import com.example.search_building.repository.entity.DistrictEntity;
import org.springframework.stereotype.Repository;

import java.sql.*;
@Repository
public class DistrictRepositoryImpl implements DistrictRepository {
    static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
    static final String USER = "root";
    static final String PASS = "";
    @Override
    public DistrictEntity findNameById(Long id) {
        DistrictEntity districtEntity = new DistrictEntity();
        String sql = "SELECT d.name FROM district d WHERE d.id = " + id + ";";
        try (Connection conn = DBConnect.getCon();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                districtEntity.setName(rs.getString("name"));
            }
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return districtEntity;
    }
}
