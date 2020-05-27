package com.example.radiusClient.Controller;

import com.example.radiusClient.Dao.PropertyEntity;
import com.example.radiusClient.Dao.RequirementEntity;
import com.example.radiusClient.PropertyRepository.PropertyRepository;
import com.example.radiusClient.Service.MatchingService;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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

    @Autowired
    private PropertyRepository propertyRepository;

    @RequestMapping("/")
    public ModelAndView home() {
        String viewName="PropertySearchPage";
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("requirementEntity",requirementEntity);
        model.put("propertyEntity",new PropertyEntity());
        return new ModelAndView(viewName,model);
    }

    @PostMapping("/")
    public ModelAndView submitHome(RequirementEntity requirementEntity) {
        logger.info(requirementEntity.toString());
        String viewName = "matchingListings";
        //fetch matches with percentage from matching service
        List<Pair<Double, Pair<Double, PropertyEntity>>> matches = matchingService.getMatches(requirementEntity);
        //display matches in descending order of percentage
        final Comparator<Pair<Double, Pair<Double, PropertyEntity>>> c = reverseOrder(comparing(Pair::getKey));
        Collections.sort(matches,c);

        Map<String, Object> model = new HashMap<>();
        model.put("listingsWithMatchPercentage", matches);
        return new ModelAndView(viewName, model);
    }

    @PostMapping("/addProperty")
    public ModelAndView submitHome(PropertyEntity propertyEntity) {
        RedirectView redirectView = new RedirectView();
        logger.info(propertyEntity.toString());
        propertyRepository.save(propertyEntity);
        redirectView.setUrl("/");
        return new ModelAndView(redirectView);
    }

    static class DescOrder implements Comparator<Double> {
        @Override
        public int compare(Double o1, Double o2) {
            return o2.compareTo(o1);
        }
    }
}
