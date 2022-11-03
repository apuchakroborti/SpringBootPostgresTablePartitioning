package com.example.electricity.repository;


import com.example.electricity.models.LoadSheddingBroadcasting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoadSheddingBroadcastingRepository extends CrudRepository<LoadSheddingBroadcasting, Long> {

    @Query("select lb from LoadSheddingBroadcasting lb")
    List<LoadSheddingBroadcasting> getAllNews();
}
