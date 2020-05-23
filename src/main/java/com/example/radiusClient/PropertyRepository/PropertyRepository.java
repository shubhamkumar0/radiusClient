package com.example.radiusClient.PropertyRepository;

import com.example.radiusClient.Dao.PropertyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends CrudRepository<PropertyEntity, Long> {
}
