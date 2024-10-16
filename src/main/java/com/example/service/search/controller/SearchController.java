package com.example.service.search.controller;

import com.example.service.search.model.ContractType;
import com.example.service.search.model.DataPropertieSearch;
import com.example.service.search.model.PropertyType;
import com.example.service.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("search")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin(origins = "http://172.27.64.1:8082")
@Slf4j
public class SearchController {
    @Autowired
    private SearchService service;

    @GetMapping("{contract}")
    @Operation(summary = "Make a filtered search", description = "A filtered search with the main parameter being the type of contract you want, followed by the non-mandatory parameters being the type you want and the value")
    @Cacheable(value = "Search", key = "#type + '_' + #propertyType.toString() + '_' + #price + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public ResponseEntity<Page<DataPropertieSearch>> search(
            @PathVariable(value = "contract") ContractType type,
            @RequestParam(name = "type", required = false) List<PropertyType> propertyType,
            @RequestParam(name = "price", required = false) String price,
            @PageableDefault(sort = {"id"}) Pageable pageable) {

        log.info("doing a property search with such references: {}, {}, {}", type, propertyType, price);
        return ResponseEntity.ok(service.search(type, propertyType, price, pageable));
    }
}
