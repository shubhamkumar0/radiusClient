package com.example.radiusClient.DaoImpl;

import com.example.radiusClient.Dao.PropertyEntity;
import com.example.radiusClient.Dao.RequirementEntity;
import com.example.radiusClient.Dao.Table.TblPropertyEntity;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MatchingDaoImpl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Pair<Double, PropertyEntity>> getAllMatchesByLocation(RequirementEntity requirementEntity) {
        Double latitude = requirementEntity.getLatitude();
        Double longitude = requirementEntity.getLongitude();
        Double minBudget = requirementEntity.getMinBudget();
        Double maxBudget = requirementEntity.getMaxBudget();
        Double lowerPrice = null;
        Double higherPrice = null;
        if(minBudget == null) {
            lowerPrice = maxBudget*0.75;
            higherPrice = maxBudget*1.25;
        } else if(maxBudget == null) {
            lowerPrice = minBudget*0.75;
            higherPrice = minBudget*1.25;
        } else {
            lowerPrice = minBudget*0.75;
            higherPrice = maxBudget*1.25;
        }
        Integer minReqBedroom = requirementEntity.getMinBedroom();
        Integer maxReqBedroom = requirementEntity.getMaxBedroom();
        Integer minBedroom = null,maxBedroom = null;
        if(minReqBedroom == null) {
            minBedroom = maxReqBedroom - 2;
            maxBedroom = maxReqBedroom + 2;
        } else if (maxReqBedroom == null ) {
            minBedroom = minReqBedroom - 2;
            maxBedroom = minReqBedroom + 2;
        } else {
            minBedroom = minReqBedroom - 2;
            maxBedroom = maxReqBedroom + 2;
        }

        Integer minReqBathroom = requirementEntity.getMinBathroom();
        Integer maxReqBathroom = requirementEntity.getMaxBathroom();
        Integer minBathroom = null,maxBathroom = null;
        if(minReqBathroom == null) {
            minBathroom = maxReqBathroom - 2;
            maxBathroom = maxReqBathroom + 2;
        } else if (maxReqBathroom == null ) {
            minBathroom = minReqBathroom - 2;
            maxBathroom = minReqBathroom + 2;
        } else {
            minBathroom = minReqBathroom - 2;
            maxBathroom = maxReqBathroom + 2;
        }

        Double radDist = 16.09/6371;
        Double maxLat = latitude + Math.toDegrees(radDist);
        Double minLat = latitude - Math.toDegrees(radDist);
        Double deltaLon= Math.toDegrees(Math.asin(Math.sin(radDist) / Math.cos(Math.toRadians(latitude))));
        Double maxLon = longitude + deltaLon;
        Double minLon = longitude - deltaLon;
        logger.info(minLat + "," + minLon);
        logger.info(maxLat + "," + maxLon);

        String query = "Select *," +
                "acos(sin(:lat)*sin(radians(latitude)) + cos(:lat)*cos(radians(latitude))*cos(radians(longitude)-:lon)) * :R As D " +
        "From (" +
                "Select * " +
                "From property_entity " +
                "Where latitude Between :minLat And :maxLat " +
        "And longitude Between :minLon And :maxLon " +
        "And price Between :minBudget And :maxBudget " +
        "And n_Bedroom Between :minBedroom And :maxBedroom " +
        "And n_Bathroom Between :minBathroom And :maxBathroom " +
            ") As FirstCut " +
        "Where acos(sin(:lat)*sin(radians(latitude)) + cos(:lat)*cos(radians(latitude))*cos(radians(longitude)-:lon)) * :R < :rad " +
        "Order by D";

        Map<String, Object> map = new HashMap<>();
        map.put("lat",Math.toRadians(latitude));
        map.put("lon",Math.toRadians(longitude));
        map.put("minLat",minLat);
        map.put("maxLat",maxLat);
        map.put("minLon",minLon);
        map.put("maxLon",maxLon);
        map.put("rad",16.09);
        map.put("R",6371);
        map.put("minBudget", lowerPrice);
        map.put("maxBudget", higherPrice);
        map.put("minBedroom",minBedroom);
        map.put("maxBedroom",maxBedroom);
        map.put("minBathroom",minBathroom);
        map.put("maxBathroom",maxBathroom);
        List<Pair<Double, PropertyEntity>> matches = new ArrayList<>();
        try{
            matches = namedParameterJdbcTemplate.query(query,map,new TblPropertyEntity.TblPropertyEntityMapper());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("No result found", ex);
            return Collections.emptyList();
        }
        return matches;
    }
}
