package com.example.electricity.services;

import com.example.electricity.models.LoadSheddingBroadcasting;

import java.util.List;

public interface LoadSheddingBroadcastingService {
    LoadSheddingBroadcasting saveData(LoadSheddingBroadcasting loadSheddingBroadcasting);
    List<LoadSheddingBroadcasting> getAllData();
}
