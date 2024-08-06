package com.example.service.search.controller;

import com.example.service.search.model.ContractType;
import com.example.service.search.model.DataPropertieSearch;
import com.example.service.search.model.PropertyType;
import com.example.service.search.service.SearchService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("search")
@SecurityRequirement(name = "bearer-key")
public class SearchController {
    @Autowired
    private SearchService service;

    @GetMapping("{contract}")
    public ResponseEntity<Page<DataPropertieSearch>> search(@PathVariable(value = "contract") ContractType type, @RequestParam(name = "type",required = false) List<PropertyType> propertyType, @RequestParam(name = "price", required = false)String price, @PageableDefault(sort = {"id"}) Pageable pageable){

        return ResponseEntity.ok(service.search(type,propertyType,price,pageable));
    }
}
