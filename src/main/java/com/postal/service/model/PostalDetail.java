package com.postal.service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Table("postal_details")
@Data
public class PostalDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column("city_name")
    private String cityName;

    @Column("area_name")
    private String areaName;

    @Column("postal_code")
    private String postalCode;

    public boolean hasIdentity(){
        return this.id != null && this.id != 0;
    }
}
