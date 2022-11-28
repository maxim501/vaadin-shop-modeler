package com.example.application.client;

import com.am.basketballshop.api.CompanyEndpoint;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "company-client", url = "${app.shop-server.baseUrl}", path = "/company")
public interface CompanyClient extends CompanyEndpoint {
}
