package com.safetrust.simpleapi.feature.contact;

import com.safetrust.simpleapi.base.SearchDTO;
import org.springframework.data.domain.Pageable;

public interface ContactService {

    SearchDTO findAllByName(String search, Pageable p, String dtoType);

    Object getOneDTO(String id, String dtoType);

    Object getOneDTO(String id, Class<?> cls);

    Contact getOne(long id);

    ContactDTO createOneDTO(final ContactDTO resource);

    Contact createOne(final ContactDTO resource);

    ContactDTO updateOneDTO(String id, ContactDTO resource);

    Contact updateOne(String id, ContactDTO resource);

    void deleteOne(String id);

    Object entityToDTO(Contact entity, Class<?> dtoClass);

}
