package com.safetrust.simpleapi.feature.contact;

import com.safetrust.simpleapi.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "contact")
@Getter
@Setter
public class Contact extends BaseModel {

    @Column(name = "name")
    private String name;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column(name = "postal_address")
    private String postalAddress;

}
