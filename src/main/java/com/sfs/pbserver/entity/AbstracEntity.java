package com.sfs.pbserver.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstracEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "created_by")
    @CreatedBy
    @Column(
            nullable = false,
            updatable = false
    )
    private String createdBy;

    @JsonProperty(value = "created_time")
    @CreatedDate
    @Column(
            nullable = false,
            updatable = false
    )
    private Date createdTime = new Date();

    @JsonProperty(value = "last_modified_by")
    @LastModifiedBy
    @Column
    private String lastModifiedBy;

    @JsonProperty(value = "last_modified_time")
    @LastModifiedDate
    @Column
    private Date lastModifiedTime = new Date();

    @Column
    private Integer valid = 1;
}
