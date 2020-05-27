package com.example.radiusClient.Service;

import com.example.radiusClient.Dao.PropertyEntity;
import com.example.radiusClient.Dao.RequirementEntity;
import com.example.radiusClient.DaoImpl.MatchingDaoImpl;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MatchingDaoImpl matchingDaoImpl;

    public List<Pair<Double, Pair<Double, PropertyEntity>>> getMatches(RequirementEntity requirementEntity) {
        //get matches with percentage from database layer
        List<Pair<Double, PropertyEntity>> matches = matchingDaoImpl.getAllMatches(requirementEntity);
        Double minBudget = requirementEntity.getMinBudget();
        Double maxBudget = requirementEntity.getMaxBudget();
        Double lowerPriceCutOff = null;
        Double higherPriceCutOff = null;
        if(minBudget == null) {
            lowerPriceCutOff = maxBudget*0.9;
            higherPriceCutOff = maxBudget*1.1;
        } else if(maxBudget == null) {
            lowerPriceCutOff = minBudget*0.9;
            higherPriceCutOff = minBudget*1.1;
        } else {
            lowerPriceCutOff = minBudget;
            higherPriceCutOff = maxBudget;
        }

        Integer minReqBedroom = requirementEntity.getMinBedroom();
        Integer maxReqBedroom = requirementEntity.getMaxBedroom();

        Integer minReqBathroom = requirementEntity.getMinBathroom();
        Integer maxReqBathroom = requirementEntity.getMaxBathroom();

        List<Pair<Double, Pair<Double, PropertyEntity>>> percentageMatches = new ArrayList<>();

        for(int i=0;i<matches.size();i++){
            Pair<Double,PropertyEntity> temp = matches.get(i);
            Double percentageMatch;

            //adding percentage for match, based on distance; less than 2 miles 30% else decreases linearly till 10 miles
            if(temp.getKey()<3.218688){//2 miles in kms
                percentageMatch = Double.valueOf(30);
            } else {
                percentageMatch = ((-15/4)*((temp.getKey()*0.621371) -10)) + 1;
            }

            //adding percentage for match, based on price of house
            percentageMatch = percentageMatch + internalPercentCalcBudget(minBudget, maxBudget,
                    lowerPriceCutOff, higherPriceCutOff, temp.getValue().getPrice());

            //adding percentage for match based on no. of bedrooms
            percentageMatch = percentageMatch +
                    internalPercentageCalcBasedOnRoom(minReqBedroom, maxReqBedroom, temp.getValue().getnBedroom());

            //adding percentage for match based on no. of bathrooms
            percentageMatch = percentageMatch +
                    internalPercentageCalcBasedOnRoom(minReqBathroom, maxReqBathroom, temp.getValue().getnBathroom());

            String result = String.format("%.2f", percentageMatch);
            if(percentageMatch>40D){
                Pair<Double,Pair<Double,PropertyEntity>> newPair = new Pair<>(Double.valueOf(result),temp);
                percentageMatches.add(newPair);
            }
        }
        return percentageMatches;
    }
    private Double internalPercentageCalcBasedOnRoom(Integer minReq, Integer maxReq, Integer val){
        Double percentageMatch = 0D;
        if(minReq!=null && maxReq!=null){
            if(val>=minReq && val<=maxReq){
                percentageMatch = percentageMatch + Double.valueOf(20);
            } else {
                percentageMatch = percentageMatch + Double.valueOf(10);
            }
        } else if(minReq==null) {
            if(val>=(maxReq-1) && val<=(maxReq+1)){
                percentageMatch = percentageMatch + Double.valueOf(20);
            } else {
                percentageMatch = percentageMatch + Double.valueOf(10);
            }
        } else if(maxReq==null){
            if(val>=(minReq-1) && val<=(minReq+1)){
                percentageMatch = percentageMatch + Double.valueOf(20);
            } else {
                percentageMatch = percentageMatch + Double.valueOf(10);
            }
        }
        return percentageMatch;
    }

    private Double internalPercentCalcBudget(Double minBudget, Double maxBudget,
                                             Double  lowerPriceCutOff, Double higherPriceCutOff, Double priceVal){
        Double percentageMatch = 0D;
        if(priceVal<=higherPriceCutOff && priceVal>=lowerPriceCutOff){
            percentageMatch = percentageMatch + Double.valueOf(30);
        } else {
            // for -/+15% we give 20% match
            if(minBudget == null) {
                if (priceVal <= (maxBudget * 1.15) && priceVal >= (maxBudget * 0.85)) {
                    percentageMatch = percentageMatch + Double.valueOf(20);
                } else if (priceVal <= (maxBudget * 1.20) && priceVal >= (maxBudget * 0.80)) {
                    percentageMatch = percentageMatch + Double.valueOf(10);//for -/+20% we give 10% match
                } else {
                    percentageMatch = percentageMatch + Double.valueOf(5);//for 20-25% we give 5% match
                }
            } else if(maxBudget == null) {
                if (priceVal <= (minBudget * 1.15) && priceVal >= (minBudget * 0.85)) {
                    percentageMatch = percentageMatch + Double.valueOf(20);
                } else if (priceVal <= (minBudget * 1.20) && priceVal >= (minBudget * 0.80)) {
                    percentageMatch = percentageMatch + Double.valueOf(10);//for -/+20% we give 10% match
                } else {
                    percentageMatch = percentageMatch + Double.valueOf(5);//for 20-25% we give 5% match
                }
            } else {
                if (priceVal <= (maxBudget * 1.15) && priceVal >= (minBudget * 0.85)) {
                    percentageMatch = percentageMatch + Double.valueOf(20);
                } else if (priceVal <= (maxBudget * 1.20) && priceVal >= (minBudget * 0.80)) {
                    percentageMatch = percentageMatch + Double.valueOf(10);//for -/+20% we give 10% match
                } else {
                    percentageMatch = percentageMatch + Double.valueOf(5);//for 20-25% we give 5% match
                }
            }
        }
        return percentageMatch;
    }
}
