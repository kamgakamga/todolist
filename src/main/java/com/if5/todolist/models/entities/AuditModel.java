package com.if5.todolist.models.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
    value = {"createdAt", "updatedAt", "updatedBy", "createdBy", "version"}, allowGetters = true
)
@Data
public abstract class AuditModel implements Serializable {

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Date updatedAt = new Date();

    @Column(name = "updated_by", nullable = false)
    @LastModifiedBy
    private String updatedBy = "SYSTEM";

    @Column(name = "created_by", nullable = false, updatable = false)
    @CreatedBy
    private String createdBy = "SYSTEM";

    @Column(name = "version", nullable = false)
    @Version
    protected Long version = 0L;
    
}
