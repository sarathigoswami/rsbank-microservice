package com.rsbank.card.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@MappedSuperclass
public class BaseEntity {

    @CreatedBy
    @Column(name = "created_by", length = 10, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @LastModifiedBy
    @Column(name = "updated_by", length = 10)
    private String updatedBy;

    @LastModifiedDate
    @Column(name = "updated_date")    
    private Date updatedDate;

}
