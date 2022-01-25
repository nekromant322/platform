package com.override.feigns;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "notification")
public interface NotificatorFeign {

    @GetMapping("/main/example")
    String getMessage();
}
