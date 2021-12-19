package com.vapeshop.controllers.feigns;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


//TODO: feign для лохов, мы используем урл (я не знаю в чем проблема)
@FeignClient(name = "notificator", url = "http://localhost:8001/", decode404 = true)
public interface NotificatorFeign {

    @GetMapping("main/example")
    String getMessage();
}
