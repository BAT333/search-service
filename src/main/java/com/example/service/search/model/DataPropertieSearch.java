package com.example.service.search.model;



import com.example.service.search.domain.Propertie;

import java.math.BigDecimal;

public record DataPropertieSearch(
        Long id,
        int quantityRoom,
        BigDecimal price,
        String description,
        ContractType type,
        PropertyType propertyType,
        DataAddressDTO address
) {
    public DataPropertieSearch(Propertie propertie) {
        this(propertie.getId(), propertie.getQuantityRoom(), propertie.getPrice(),
                propertie.getDescription(), propertie.getType(),propertie.getPropertyType(),new DataAddressDTO(propertie.getAddress()));
    }
}
