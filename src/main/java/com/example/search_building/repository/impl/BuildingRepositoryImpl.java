package com.example.search_building.repository.impl;

import com.example.search_building.Utils.NumberUtil;
import com.example.search_building.Utils.StringUtil;
import com.example.search_building.db.DBConnect;
import com.example.search_building.repository.BuildingRepository;
import com.example.search_building.repository.entity.BuildingEntity;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
//    static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
//    static final String USER = "root";
//    static final String PASS = "";

    public static void joinTable(Map<String, Object> params, List<String> typeCode , StringBuilder sql) {
        String staffId = (String) params.get("staffId");
        if (StringUtil.checkString(staffId)) {
            sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
        }
        if (typeCode != null && !typeCode.isEmpty()) {
            sql.append(" INNER JOIN buildingrenttype on b.id = buildingrenttype.buildingid ");
            sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
        }
        String rentAreaFrom = (String) params.get("areaFrom");
        String rentAreaTo = (String) params.get("areaTo");
        if (StringUtil.checkString(rentAreaFrom) || StringUtil.checkString(rentAreaTo)) {
            sql.append(" INNER JOIN rentarea ON rentarea.buildingid = b.id ");
        }
    }

    public static void queryNormal(Map<String , Object> params , StringBuilder where) {
        Set<Map.Entry<String , Object>> entrySet = params.entrySet();
        for (Map.Entry<String , Object> it : entrySet) {
            if (!it.getKey().equals("staffId") && !it.getKey().equals("typeCode") && !it.getKey().startsWith("area")
            && !it.getKey().startsWith("rentPrice")) {
                String value = it.getValue().toString();
                if (StringUtil.checkString(value)) {
                    if (NumberUtil.isNumber(value)) {
                        where.append(" AND b." + it.getKey() + " = " + value);
                    } else {
                        where.append(" AND b." + it.getKey() + " LIKE '%" + value + "%' ");
                    }
                }
            }
        }
    }
    public static void querySpecial(Map<String , Object> params , List<String> typeCode , StringBuilder where) {
        String staffId = (String) params.get("staffId");
        if (StringUtil.checkString(staffId)) {
            where.append(" AND assignmentbuilding.staffid = " + staffId);
        }
        String rentPriceFrom = (String) params.get("rentPriceFrom");
        String rentPriceTo = (String) params.get("rentPriceTo");
        if (StringUtil.checkString(rentPriceFrom) || StringUtil.checkString(rentPriceTo)) {
            if (StringUtil.checkString(rentPriceFrom))
                where.append(" AND rentarea.value >= " + rentPriceFrom);
            if (StringUtil.checkString(rentPriceTo))
                where.append(" AND rentarea.value <= " + rentPriceTo);
        }
        if (typeCode != null && !typeCode.isEmpty()) {
//            where.append(" AND renttype.code IN(" + String.join("," , typeCode) + ")");
            // Java 7 not use Java Stream API
//            List<String> lists = new ArrayList<>();
//            for (String item : lists) {
//                lists.add("'" + item + "'");
//            }
//            where.append(" AND renttype.code IN(" + String.join("," , lists) + ") ");
            // Java 8 use Java Stream API
            List<String> lists = typeCode.stream()
                    .map(item -> "'" + item + "'")
                    .collect(Collectors.toList());
            where.append(" AND renttype.code IN(" + String.join("," , lists) + ") ");
        }
    }
    @Override
    public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode) {
        StringBuilder sql = new StringBuilder("select b.id , b.name , b.districtid , b.street , b.ward , b.numberofbasement , " +
                "b.floorarea , b.rentprice , b.managername , b.managerphonenumber , b.servicefee , b.brokeragefee FROM building b");
        StringBuilder where = new StringBuilder("WHERE 1=1 ");
        joinTable(params , typeCode , sql);
        queryNormal(params , where);
        querySpecial(params , typeCode , where);
        where.append(" GROUP BY b.id;");
        sql.append(where);
        List<BuildingEntity> result = new ArrayList<>();
        System.out.println(sql);
        try (Connection conn = DBConnect.getCon();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString());){
            while (rs.next()) {
                BuildingEntity buildingEntity = new BuildingEntity();
                buildingEntity.setId(rs.getLong("b.id"));
                buildingEntity.setName(rs.getString("b.name"));
                buildingEntity.setStreet(rs.getString("b.street"));
                buildingEntity.setWard(rs.getString("b.ward"));
                buildingEntity.setDistricid(rs.getLong("b.districtid"));
                buildingEntity.setFloorArea(rs.getLong("b.floorarea"));
                buildingEntity.setRentPrice(rs.getLong("b.rentprice"));
                buildingEntity.setServiceFee(rs.getString("b.serviceFee"));
                buildingEntity.setManagerName(rs.getString("b.managername"));
                buildingEntity.setManagerPhoneNumber(rs.getString("b.managerphonenumber"));
                buildingEntity.setBrokerageFee(rs.getLong("b.brokeragefee"));
                result.add(buildingEntity);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
