package com.example.electricity.services.impls;

import com.example.electricity.models.LoadSheddingBroadcasting;
import com.example.electricity.repository.LoadSheddingBroadcastingRepository;
import com.example.electricity.services.LoadSheddingBroadcastingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LoadSheddingBroadcastingServiceImpls implements LoadSheddingBroadcastingService {

    private final LoadSheddingBroadcastingRepository loadSheddingBroadcastingRepository;

    @Autowired
    LoadSheddingBroadcastingServiceImpls(LoadSheddingBroadcastingRepository loadSheddingBroadcastingRepository){
        this.loadSheddingBroadcastingRepository = loadSheddingBroadcastingRepository;
    }

    @Override
    public LoadSheddingBroadcasting saveData(LoadSheddingBroadcasting loadSheddingBroadcasting){
        loadSheddingBroadcasting = loadSheddingBroadcastingRepository.save(loadSheddingBroadcasting);
        return loadSheddingBroadcasting;
    }

    @Override
    public List<LoadSheddingBroadcasting> getAllData(){
        List<LoadSheddingBroadcasting> list = loadSheddingBroadcastingRepository.getAllNews();

        return list;
    }
}
