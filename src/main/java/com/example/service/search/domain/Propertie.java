package com.example.service.search.domain;


import com.example.service.search.model.ContractStatus;
import com.example.service.search.model.ContractType;
import com.example.service.search.model.PropertyType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "properties")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Propertie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quantitys_rooms",nullable = false)
    private Integer quantityRoom;
    @Column(name = "prices",nullable = false)
    private BigDecimal price;
    @Column(name = "descriptions")
    private String description;
    @Column(name = "types",nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractType type;
    @Column(name = "properties_types",nullable = false)
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;
    @Column(name ="actives",nullable = false)
    private Boolean active = true;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address",nullable = false,unique = true)
    private Address address;
    @Column(name ="contract_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractStatus status;




}
