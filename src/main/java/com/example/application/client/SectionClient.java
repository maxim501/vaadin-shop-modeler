package com.example.application.client;

import com.am.basketballshop.api.SectionEndpoint;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "section-client", url = "${app.shop-server.baseUrl}", path = "/section")
public interface SectionClient extends SectionEndpoint {
}
