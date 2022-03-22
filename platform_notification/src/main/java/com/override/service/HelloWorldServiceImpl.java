package com.override.service;

import com.override.models.HelloWorldModel;
import com.override.repositories.HelloWorldModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldServiceImpl implements HelloWorldService{
    @Autowired
    HelloWorldModelRepository helloWorldModelRepository;

    @Override
    public void save(HelloWorldModel helloWorldModel) {
        helloWorldModelRepository.save(helloWorldModel);
    }
}
