package com.example.application.client;

import com.am.basketballshop.api.RemainderProductEndpoint;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "remainder-client", url = "${app.shop-server.baseUrl}", path = "/remainder")
public interface RemainderClient extends RemainderProductEndpoint {
}
