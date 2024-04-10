package com.scaler.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class token extends baseModel {
    private String value;
    @ManyToOne
    private com.scaler.userservice.models.user user;
    private Date expiryAt;
}
