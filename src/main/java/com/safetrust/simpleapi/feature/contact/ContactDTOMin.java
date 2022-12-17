package com.safetrust.simpleapi.feature.contact;

import com.safetrust.simpleapi.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDTOMin extends BaseDTO<Contact> {
    private String name;
    private String emailAddress;
}
