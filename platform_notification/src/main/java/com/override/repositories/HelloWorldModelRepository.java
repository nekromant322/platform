package com.override.repositories;

import com.override.models.HelloWorldModel;
import org.springframework.data.repository.CrudRepository;

public interface HelloWorldModelRepository extends CrudRepository<HelloWorldModel, Long> { }
