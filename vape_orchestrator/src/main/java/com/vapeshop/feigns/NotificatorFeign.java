package com.vapeshop.feigns;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


//TODO: вынести урл в yml, либо починить фейн (лучше) чтоб резолвил по имени сервиса
@FeignClient(name = "notificator", url = "http://localhost:8001/", decode404 = true)
public interface NotificatorFeign {

    @GetMapping("main/example")
    String getMessage();
}
