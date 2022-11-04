package com.example.electricity.controllers;

import com.example.electricity.dto.response.ServiceResponse;
import com.example.electricity.models.DivisionWiseLoadSheddingBroadcasting;
import com.example.electricity.models.LoadSheddingBroadcasting;
import com.example.electricity.services.LoadSheddingBroadcastingService;
import com.example.electricity.services.DivisionWiseLoadSheddingBroadcastingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/load-shedding")
public class LoadSheddingNewsAlertController {

    @Autowired
    LoadSheddingBroadcastingService loadSheddingBroadcastingService;

    @Autowired
    DivisionWiseLoadSheddingBroadcastingService divisionWiseLoadSheddingBroadcastingService;

    @PostMapping("/save")
    public ServiceResponse saveData(@RequestBody LoadSheddingBroadcasting loadSheddingBroadcasting){
        loadSheddingBroadcasting = loadSheddingBroadcastingService.saveData(loadSheddingBroadcasting);
        return new ServiceResponse(null, loadSheddingBroadcasting);
    }

    @GetMapping("/get-all")
    public ServiceResponse getAllData(){

        return new ServiceResponse(null, loadSheddingBroadcastingService.getAllData());
    }

    @PostMapping("/division/save")
    public ServiceResponse saveDivisionWiseData(@RequestBody DivisionWiseLoadSheddingBroadcasting divisionWiseLoadSheddingBroadcasting){
        divisionWiseLoadSheddingBroadcasting = divisionWiseLoadSheddingBroadcastingService.saveData(divisionWiseLoadSheddingBroadcasting);
        return new ServiceResponse(null, divisionWiseLoadSheddingBroadcasting);
    }

    @GetMapping("/division/get-all")
    public ServiceResponse getDivisionWiseAllData(){

        return new ServiceResponse(null, divisionWiseLoadSheddingBroadcastingService.getAllData());
    }

}
