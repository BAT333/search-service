package com.example.service.search.service;

import com.example.service.search.config.exceptions.SearchExceptions;
import com.example.service.search.model.ContractStatus;
import com.example.service.search.model.ContractType;
import com.example.service.search.model.DataPropertieSearch;
import com.example.service.search.model.PropertyType;
import com.example.service.search.repository.PropertieRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SearchService {
    //vai ser por chamada assicrona
    @Autowired
    private PropertieRepository repository;

    private Page<DataPropertieSearch> searchContract(ContractType type, ContractStatus available, Pageable pageable) {
        var propertie = repository.findByActiveTrueAndTypeAndStatus(type,available,pageable);
        return propertie.map(DataPropertieSearch::new);
    }

    public Page<DataPropertieSearch> search(ContractType type, List<PropertyType> propertyType, String price, Pageable pageable) {
        if(propertyType != null && price != null){
            return this.completeSearch(type,propertyType,price,ContractStatus.AVAILABLE,pageable);
        } else if(propertyType != null){
            return this.searchPropertyType(type,propertyType,ContractStatus.AVAILABLE,pageable);
        } else if (price != null) {
            return this.searchPrice(type,price,ContractStatus.AVAILABLE,pageable);
        }else {
            return this.searchContract(type,ContractStatus.AVAILABLE,pageable);
        }
    }

    private Page<DataPropertieSearch> searchPrice(ContractType type, String price, ContractStatus available, Pageable pageable) {
        var validPrice =  this.validPrice(price);
        if(validPrice.size() == 1){
            var propertie = repository.findByActiveTrueAndTypeAndPriceGreaterThanEqualAndStatus(type,validPrice.getFirst(),available,pageable);
            return  propertie.map(DataPropertieSearch::new);
        }
        var propertie = repository.findByActiveTrueAndTypeAndPriceBetweenAndStatus(type,validPrice.getFirst(),validPrice.getLast(),available,pageable);
        return propertie.map(DataPropertieSearch::new);
    }

    private Page<DataPropertieSearch> searchPropertyType(ContractType type, List<PropertyType> propertyType, ContractStatus available, Pageable pageable) {
        var propertie = repository.findByActiveTrueAndTypeAndPropertyTypeInAndStatus(type,propertyType,available,pageable);
        return  propertie.map(DataPropertieSearch::new);
    }

    private Page<DataPropertieSearch> completeSearch(ContractType type, List<PropertyType> propertyType, String price, ContractStatus available, Pageable pageable) {
        var validPrice =  this.validPrice(price);
        if(validPrice.size() == 1){
            var propertie = repository.findByActiveTrueAndTypeAndPropertyTypeInAndPriceGreaterThanEqualAndStatus(type,propertyType,validPrice.getFirst(),available,pageable);
            return  propertie.map(DataPropertieSearch::new);
        }
        var propertie = repository.findByActiveTrueAndTypeAndPropertyTypeInAndPriceBetweenAndStatus(type,propertyType,validPrice.getFirst(),validPrice.getLast(),available,pageable);
        return propertie.map(DataPropertieSearch::new);
    }

    private List<BigDecimal> validPrice(String price) {
        if(Pattern.matches("([0-9]*\\sa\\s[0-9]+)|([0-9]+)", price)){
            if(Pattern.matches("([0-9]+)", price)){
                return List.of(new BigDecimal(price));
            }
            List<String> list = new ArrayList<>(List.of(price.split("a")));
            var prices =list.stream().map(p->new BigDecimal(p.trim())).collect(Collectors.toList());
            if(prices.getFirst().equals(prices.getLast())||prices.getFirst().longValue()>prices.getLast().longValue()){
                throw new SearchExceptions();
            }
            return prices;
        }else {
            throw new SearchExceptions();
        }
    }
}
