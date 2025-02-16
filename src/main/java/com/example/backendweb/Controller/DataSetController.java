package com.example.backendweb.Controller;

import com.example.backendweb.Entity.Info.Attraction;
import com.example.backendweb.Entity.Info.Flight;
import com.example.backendweb.Entity.Info.Hotel;
import com.example.backendweb.Services.DataSetService;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class DataSetController {

    private final DataSetService dataSetService;

    public DataSetController(DataSetService dataSetService) {
        this.dataSetService = dataSetService;
    }

    @GetMapping("/getDataSet")
    public ResponseEntity<String> getDataSet(@RequestParam(name = "dataset") String dataSet,
                                             @RequestParam(name = "sort", required = false) String sort,
                                             @RequestParam(name = "order", required = false) String order,
                                             @RequestParam(name = "distinct", required = false) String distinct) {
        try {
            List<Object> result = dataSetService.getDataSet(dataSet, sort, order, distinct);

            if (result != null && !result.isEmpty()) {
                Object firstElement = result.get(0);
                if (firstElement instanceof List<?> list) {
                    if (!list.isEmpty()) {
                        Object firstItem = list.get(0);
                        if (firstItem instanceof Attraction) {
                            List<Attraction> attractions = (List<Attraction>) list;
                            return ResponseEntity.ok(new Gson().toJson(attractions));
                        } else if (firstItem instanceof Hotel) {
                            List<Hotel> hotels = (List<Hotel>) list;
                            return ResponseEntity.ok(new Gson().toJson(hotels));
                        } else if (firstItem instanceof Flight) {
                            List<Flight> flights = (List<Flight>) list;
                            return ResponseEntity.ok(new Gson().toJson(flights));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body("Error processing request");
    }

}
