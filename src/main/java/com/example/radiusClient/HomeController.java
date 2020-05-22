package com.example.radiusClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private RequirementEntity requirementEntity;

    @RequestMapping("/")
    public ModelAndView home() {
        String viewName="PropertySearchPage";
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("requirementEntity",requirementEntity);
        return new ModelAndView(viewName,model);
    }

    @PostMapping("/")
    public ModelAndView submitHome(RequirementEntity requirementEntity) {
        List<PropertyEntity> matchedProperties = new ArrayList<PropertyEntity>();
        PropertyEntity one = new PropertyEntity(1D,1D,2D,3,4);
        PropertyEntity two = new PropertyEntity(1D,1D,2D,3,4);
        PropertyEntity three = new PropertyEntity(1D,1D,2D,3,4);
        Map<Float, Object> listingsWithMatchPercentage = new TreeMap<>(new DescOrder());
        listingsWithMatchPercentage.put(0.4F,one);
        listingsWithMatchPercentage.put(0.5F,two);
        listingsWithMatchPercentage.put(0.6F,three);
        logger.info(requirementEntity.toString());
        String viewName = "matchingListings";

        //fetch matches from 3 services, location service, room service (:P), price service
        //all services return Map<Float, Object> which we will combine via code here

        Map<String, Object> model = new HashMap<>();
        model.put("listingsWithMatchPercentage", listingsWithMatchPercentage);
        return new ModelAndView(viewName, model);
    }

    static class DescOrder implements Comparator<Float> {

        @Override
        public int compare(Float o1, Float o2) {
            return o2.compareTo(o1);
        }
    }
}
