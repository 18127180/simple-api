package com.safetrust.simpleapi.feature.contact;

import com.safetrust.simpleapi.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends BaseRepository<Contact, Long> {

    //Only work with code and description in BaseModel
    @Query("select t from Contact t where " +
        "((lower(isnull(t.name,'')) like :search) " +
        "or (lower(isnull(t.emailAddress,'')) like :search) " +
        "or (lower(isnull(t.telephoneNumber,'')) like :search) " +
        "or (lower(isnull(t.postalAddress,'')) like :search))")
    Page<Contact> findAllData(@Param("search") String search, Pageable page);

}
