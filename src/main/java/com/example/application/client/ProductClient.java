package com.example.application.client;

import com.am.basketballshop.api.ProductEndpoint;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-client", url = "${app.shop-server.baseUrl}", path = "/product")
public interface ProductClient extends ProductEndpoint {
}
