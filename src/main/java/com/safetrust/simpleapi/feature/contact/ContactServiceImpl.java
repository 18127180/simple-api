package com.safetrust.simpleapi.feature.contact;

import com.safetrust.simpleapi.base.BaseServiceObject;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl extends BaseServiceObject<Contact, ContactDTO, ContactRepository> implements ContactService {

    private final ContactRepository repository;

    protected ContactServiceImpl(ContactRepository repository) {
        super(repository,
            Contact.class,
            ContactRepository.class,
            ContactDTO.class,
            ContactDTOMin.class,
            ContactDTOName.class);
        this.repository = repository;
    }

}
