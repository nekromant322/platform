package com.override.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/actuator")
public class ActuatorRestController {

//    @Autowired
//    private BuildProperties buildProperties;
//
//    @Autowired
//    private GitProperties gitProperties;
//
//    @GetMapping("/info/build-info")
//    public BuildProperties getBuildProperties(){
//        return buildProperties;
//    }
//
//    @GetMapping("/info/git-info")
//    public GitProperties getGitProperties(){
//        return gitProperties;
//    }
}
