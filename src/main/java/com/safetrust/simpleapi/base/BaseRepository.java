package com.safetrust.simpleapi.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import static com.safetrust.simpleapi.util.IdConverter.toId;

@NoRepositoryBean
public interface BaseRepository<T, P> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    /**
     * get entity by string ID
     * @param id String encrypted id
     * @return
     */
    default T getById(String id) {
        return getById(toId(id));
    }

    /**
     *
     * @param id String encrypted id
     * @return
     */
    default Optional<T> findById(String id) {
        return findById(toId(id));
    }

    //Only work with code and description in BaseModel
    @Query("Select t from #{#entityName} t where ((lower(isnull(t.code,'')) like :search ) or (lower(isnull(t.description,'')) like :search ))" )
    Page<T> findAllData(@Param("search") String search, Pageable page);

}
