package com.safetrust.simpleapi.feature.contact;

public interface ContactService {

    Object getOneDTO(String id, String dtoType);

    Object getOneDTO(String id, Class<?> cls);

    Contact getOne(long id);

    ContactDTO createOneDTO(final ContactDTO resource);

    Contact createOne(final ContactDTO resource);

    ContactDTO updateOneDTO(String id, ContactDTO resource);

    Contact updateOne(String id, ContactDTO resource);

//    void deleteOne(String id);

    Object entityToDTO(Contact entity, Class<?> dtoClass);

}
