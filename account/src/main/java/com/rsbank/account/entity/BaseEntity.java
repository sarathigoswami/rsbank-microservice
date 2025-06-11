package com.rsbank.account.entity;

import java.util.Date;

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

    @Column(name = "created_by", length = 10, updatable = false)
    private String createdBy;

    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Column(name = "updated_by", length = 10, insertable = false)
    private String updatedBy;

    @Column(name = "updated_at", insertable = false)    
    private Date updatedAt;

}
