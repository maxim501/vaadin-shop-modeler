package com.example.application.client;

import com.am.basketballshop.api.SizeEndpoint;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "size-client", url = "${app.shop-server.baseUrl}", path = "/size")
public interface SizeClient extends SizeEndpoint {
}
