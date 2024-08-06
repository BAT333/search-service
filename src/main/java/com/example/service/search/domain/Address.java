package com.example.service.search.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "street",nullable = false)
    private String street;
    @Column(name = "state",nullable = false)
    private String state;
    @Column(name = "neighborhood",nullable = false)
    private String neighborhood;
    @Column(name = "number",nullable = false,length = 4)
    private String number;
    @Column(name = "cep",nullable = false,length = 8)
    private String cep;
    @Column(name = "complemento",length = 100)
    private String complemento;
    @Column(name ="actives",nullable = false)
    private Boolean active = true;

}
