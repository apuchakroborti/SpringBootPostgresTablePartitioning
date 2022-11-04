package com.example.electricity.repository;


import com.example.electricity.models.DivisionWiseLoadSheddingBroadcasting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DivisionWiseLoadSheddingBroadcastingRepository
        extends CrudRepository<DivisionWiseLoadSheddingBroadcasting, Long> {

    @Query("select dlb from DivisionWiseLoadSheddingBroadcasting  dlb")
    List<DivisionWiseLoadSheddingBroadcasting> getAllNews();
}
