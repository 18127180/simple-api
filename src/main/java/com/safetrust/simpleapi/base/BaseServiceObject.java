package com.safetrust.simpleapi.base;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.Optional;

import static com.safetrust.simpleapi.util.IdConverter.toId;

@Log4j2
@Getter
//@Setter
@SuppressWarnings("unchecked")
/**
 *  T = Entity Model/Type
 *  D = Primary DTO Object
 *  R = Repository
 */
public abstract class BaseServiceObject<T extends BaseModel, D extends BaseDTO, R extends BaseRepository> extends BaseRestController {

    @Autowired
    protected EntityResolver resolver;

    R repository;
    Class<?> primaryModelClass;
    Class<?> primaryRepoClass;
    Class<?> primaryDTOClass;
    Class<?> primaryDTOMin;
    Class<?> primaryDTOName;

    /**
     * Use to initialise the object requirement to automate the base CRUD the default CRUD** to run.
     *
     * @param repository     primary Repository
     * @param primaryModel   entityModel.class -> primary entity class being handled by this Service
     * @param primaryRepo    primaryRepository.class -> primary class of the repository for this service
     * @param primaryDTO     primaryDTO.class -> full object resource class
     * @param primaryDTOMin  primaryDTOMin.class -> Minimum info object resource class
     * @param primaryDTOName primaryDTOName.class -> simple info
     */
    protected BaseServiceObject(R repository,
                                Class<?> primaryModel,
                                Class<?> primaryRepo,
                                Class<?> primaryDTO,
                                Class<?> primaryDTOMin,
                                Class<?> primaryDTOName
    ) {
        this.repository = repository;
        this.primaryModelClass = primaryModel;
        this.primaryRepoClass = primaryRepo;
        this.primaryDTOClass = primaryDTO;
        this.primaryDTOMin = primaryDTOMin;
        this.primaryDTOName = primaryDTOName;
    }

    /**
     * this will get the dtoType you need base on the current type of object
     *
     * @param dtoType [full|min|name]
     * @return class type of the selected type
     */
    protected Class<?> getDTOClassType(String dtoType) {
        dtoType = dtoType.toLowerCase();
        if (dtoType.equals("full"))
            return getPrimaryDTOClass();
        else if (dtoType.equals("min"))
            return getPrimaryDTOMin();
        else
            return getPrimaryDTOName();
    }


    /**
     * transform entity to the selected Class of DTO type object
     *
     * @param entity   the entity to transform
     * @param dtoClass the selected class to transform this entity to.
     * @return object equevalent of the entity base on the selected class
     */
    public Object entityToDTO(T entity, Class<?> dtoClass) {
        BaseMergable ret = modelMapper.map(entity, (Type) dtoClass);
        ret = (BaseMergable) ret.from(entity, dtoClass, resolver, modelMapper);

        return ret;
    }

    /**
     * Get One Eniity base on Primary DTO base on ID
     *
     * @param id
     * @return
     */
    public D getOneDTO(long id) {
        return (D) entityToDTO(getOne(id), getPrimaryDTOClass());
    }

    /**
     * Get One Entity base on ID
     *
     * @param id
     * @return
     */
    public T getOne(long id) {
        Optional<T> ret = (Optional<T>) (getRepository()).findById(id);
        if (ret.isEmpty()) {
            String className = getPrimaryModelClass().getSimpleName();
            throw new RuntimeException(className + " : Record Not found / Invalid data access");
        }
        return ret.get();
    }

    /**
     * Create a new entity base on the resource feed to it
     *
     * @param resource
     * @return
     */
//    @Transactional(rollbackFor = Exception.class)
    public T createOne(D resource) {

        resource.setId("");                     // Create entity should not match any ID if any

        // resource.setTimeCreated(---);        // TODO: get the current time of Compny timezone

        // Use model mapper to initially Map the data to entity class
        T entity = (T) modelMapper.map(resource, getPrimaryModelClass());

        // call external -> entity mapper to further update  the entity data manually
        entity = (T) ((D) resource).to(entity, resolver, modelMapper);

        return (T) (getRepository()).save(entity);
    }

    public T createOne(D resource, long parentId) {

        resource.setId("");                     // Create entity should not match any ID if any

        // resource.setTimeCreated(---);        // TODO: get the current time of Compny timezone

        // Use model mapper to initially Map the data to entity calss
        T entity = (T) modelMapper.map(resource, getPrimaryModelClass());

        // call external -> entity mapper to further update  the entity data manually
        entity = (T) ((D) resource).to(entity, resolver, modelMapper);

        return (T) (getRepository()).save(entity);
    }

    /**
     * Update the exisitng entity base on ID -> resource payload
     *
     * @param id
     * @param resource
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public T updateOne(String id, D resource) {
        //TODO: validate ID match
        long longId = toId(id);

        //will check if item exists
        T entity = this.getOne(longId);


        resource.setId(Long.toString(longId));

        // resource.setTimeCreated(---);        // TODO: get the current time of Compny timezone

        entity = (T) ((D) resource).to(entity, resolver, modelMapper);

        entity = (T) (getRepository()).save(entity);

        return entity;
    }

//    /**
//     * default findall by name search query use for get all default CRUD implementation
//     *
//     * @param sbuId
//     * @param search
//     * @param isActive
//     * @param page
//     * @param dtoType
//     * @return
//     */
//    public SearchDTO findAllByName(long sbuId, String search, boolean isActive, Pageable page, String dtoType) {
//        Class<?> finalDtoClass = getDTOClassType(dtoType);
//        return findAllByName(sbuId, search, isActive, page, finalDtoClass);
//    }

    /**
     * default findall by name search query use for get all default CRUD implementation
     *
     * @param sbuId
     * @param search   search term by default search the name field
     * @param isActive show only actiive or deleted data
     * @param page
     * @param cls      actual Class to be use for dTO mapping (xxxDTO, xxxDTOMin, xxxDTOName)
     * @return
     */
//    public SearchDTO findAllByName(long sbuId, String search, boolean isActive, Pageable page, Class<?> cls) {
//        Page<T> pages = (Page<T>) ((R) getRepository()).findAllData(sbuId, "%" + search.trim().toLowerCase() + "%", isActive, page);
//        return SearchDTO.of(page.getPageNumber(), page.getPageSize(),
//            pages.getContent().stream().map(a -> modelMapper.map(a, cls)).collect(toList()), // it will display the data. igNore the jsonBackReferences
////                new ArrayList<>(pages.getContent()), // if have jsonBackReferences it will not display the Header from the details
//            pages.getTotalElements(),
//            pages.getTotalPages(), pages.getSize(), pages.getNumber(), pages.isLast());
//    }

    /**
     * default getOne item and convert it to DTO Object base on param
     *
     * @param id      long id of the record
     * @param dtoType String [full|min|name] -> DTO Type to return
     * @return DTO base on dtoType Param
     */
    public Object getOneDTO(String id, String dtoType) {
        long longId = toId(id);
        Class<?> finalDtoClass = getDTOClassType(dtoType);
        return entityToDTO(getOne(longId), finalDtoClass);
    }

    /**
     * default getOne item and convert it to DTO Object base on param (actual class)
     *
     * @param id
     * @param cls
     * @return
     */
    public Object getOneDTO(String id, Class<?> cls) {
        long longId = toId(id);
        return entityToDTO(getOne(longId), cls);
    }

    /**
     * Create One and Return as Primary DTO
     *
     * @param resource Primary DTO Object Struture -> entity to be created
     * @return return as newly created item as a primary DTO
     */
    public D createOneDTO(D resource) {
        final D data;
        try {
            T entity = createOne(resource);
            data = (D) entityToDTO(entity, getPrimaryDTOClass());
        } catch (Exception ex) {
            if (ex.getCause().getCause() != null) {
                throw new IllegalStateException(ex.getCause().getCause().getMessage());
            } else {
                throw new IllegalStateException(ex.getMessage());
            }
        }

        return data;
    }

    /**
     * Update entity base on ID and return a mapped to Priimariy DTO
     *
     * @param id
     * @param resource
     * @return
     */
    public D updateOneDTO(String id, D resource) {
        try {
            T entity = updateOne(id, resource);
            return (D) modelMapper.map(entity, getPrimaryDTOClass());
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    /**
     * Tag enity as deleted
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteOne(String id) {
        long longId = toId(id);
        final T one = this.getOne(longId);
    }

}
