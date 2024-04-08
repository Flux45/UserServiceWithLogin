package com.example.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Role extends BaseModel {


    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1)
    private String name;
}
