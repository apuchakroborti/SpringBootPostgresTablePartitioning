package com.example.electricity.services.impls;

import com.example.electricity.models.DivisionWiseLoadSheddingBroadcasting;
import com.example.electricity.repository.DivisionWiseLoadSheddingBroadcastingRepository;
import com.example.electricity.services.DivisionWiseLoadSheddingBroadcastingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DivisionWiseLoadSheddingBroadcastingServiceImpls implements DivisionWiseLoadSheddingBroadcastingService {

    private final DivisionWiseLoadSheddingBroadcastingRepository divisionWiseLoadSheddingBroadcastingRepository;

    @Autowired
    DivisionWiseLoadSheddingBroadcastingServiceImpls(DivisionWiseLoadSheddingBroadcastingRepository loadSheddingBroadcastingRepository){
        this.divisionWiseLoadSheddingBroadcastingRepository = loadSheddingBroadcastingRepository;
    }

    @Override
    public DivisionWiseLoadSheddingBroadcasting saveData(DivisionWiseLoadSheddingBroadcasting divisionWiseLoadSheddingBroadcasting){
        divisionWiseLoadSheddingBroadcasting = divisionWiseLoadSheddingBroadcastingRepository.save(divisionWiseLoadSheddingBroadcasting);
        return divisionWiseLoadSheddingBroadcasting;
    }

    @Override
    public List<DivisionWiseLoadSheddingBroadcasting> getAllData(){
        List<DivisionWiseLoadSheddingBroadcasting> list = divisionWiseLoadSheddingBroadcastingRepository.getAllNews();

        return list;
    }
}
