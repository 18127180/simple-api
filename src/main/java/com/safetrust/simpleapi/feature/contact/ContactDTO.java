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

    @NotNull(message = "{dev.validation.name}")
    private String name;
    @Email(message = "{dev.validation.email}")
    private String emailAddress;
    @Pattern(regexp="(^$|[0-9]{10})", message = "{dev.validation.phoneNumber}")
    private String telephoneNumber;
    private String postalAddress;

}
