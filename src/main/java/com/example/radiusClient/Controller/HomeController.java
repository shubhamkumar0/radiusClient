package com.example.radiusClient.Controller;

import com.example.radiusClient.Dao.PropertyEntity;
import com.example.radiusClient.Dao.RequirementEntity;
import com.example.radiusClient.Service.MatchingService;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private RequirementEntity requirementEntity;

    @Autowired
    private MatchingService matchingService;

    @RequestMapping("/")
    public ModelAndView home() {
        String viewName="PropertySearchPage";
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("requirementEntity",requirementEntity);
        return new ModelAndView(viewName,model);
    }

    @PostMapping("/")
    public ModelAndView submitHome(RequirementEntity requirementEntity) {
        logger.info(requirementEntity.toString());
        String viewName = "matchingListings";
        //fetch matches from 3 services, location service, room service (:P), price service
        //all services return Map<Float, Object> which we will combine via code here
        List<Pair<Double, PropertyEntity>> matches = matchingService.getMatchesByLocation(requirementEntity);
        final Comparator<Pair<Double, PropertyEntity>> c = reverseOrder(comparing(Pair::getKey));
        Collections.sort(matches,c);

        Map<String, Object> model = new HashMap<>();
        model.put("listingsWithMatchPercentage", matches);
        return new ModelAndView(viewName, model);
    }

    static class DescOrder implements Comparator<Double> {

        @Override
        public int compare(Double o1, Double o2) {
            return o2.compareTo(o1);
        }
    }
}
