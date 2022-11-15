package com.example.application.service;

import com.am.basketbalshop.api.dto.product.ResponseProductDto;
import com.example.application.client.ProductClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ProductClient productClient;

    private ObjectMapper mapper = new ObjectMapper();

    public String getProductAsJson(String id) throws JsonProcessingException {
        ResponseProductDto product = productClient.getProduct(id);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(product);
    }
}
