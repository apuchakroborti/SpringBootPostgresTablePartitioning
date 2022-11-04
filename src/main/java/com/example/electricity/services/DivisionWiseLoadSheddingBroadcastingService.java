package com.example.electricity.services;

import com.example.electricity.models.DivisionWiseLoadSheddingBroadcasting;

import java.util.List;

public interface DivisionWiseLoadSheddingBroadcastingService {
    DivisionWiseLoadSheddingBroadcasting saveData(DivisionWiseLoadSheddingBroadcasting divisionWiseLoadSheddingBroadcasting);
    List<DivisionWiseLoadSheddingBroadcasting> getAllData();
}
