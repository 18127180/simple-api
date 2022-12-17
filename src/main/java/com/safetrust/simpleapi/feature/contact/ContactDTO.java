package com.safetrust.simpleapi.feature.contact;

import com.safetrust.simpleapi.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class ContactDTO extends BaseDTO<Contact> {

    @NotNull(message = "Name cannot be null")
    private String name;
    @Email(message = "Email should be valid")
    private String emailAddress;
    @Pattern(regexp="(^$|[0-9]{10})")
    private String telephoneNumber;
    private String postalAddress;

}
