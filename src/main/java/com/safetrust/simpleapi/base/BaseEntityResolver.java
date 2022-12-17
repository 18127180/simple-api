package com.safetrust.simpleapi.base;

import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Used to resolve any primary entity type that is managed by the DB. Often this is used to create
 * entities that cannot be found using source data from the REST resource.
 */
@Service
public class BaseEntityResolver extends BaseRestController  implements EntityResolver{

    /**
     * This holds the mapping of simpleClass entity name to the repository it is responsible for
     */
    protected Map<String, Object> dataClass;

    /**
     * Get the entity base on ModelName using the repository in dataClassMap
     *
     * @param baseModelName  Actual Model Class Name / Simple Class Name (String)
     * @param id
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseModel> T getEntity(String baseModelName, String id) {
        // if(classSimpleName.equals("CountryRepository")) return (T) getEntity(classSimpleName, id, longId -> cityRepository.getById(id));
        Object repo = this.dataClass.get(baseModelName);
        return (T)getEntity(baseModelName, (BaseRepository<?,?>)repo, id);
    }

    /**
     * Actual getEntity on Repo on getting  the entity base on ID and repo from data class
     * @param classSimpleName
     * @param repo
     * @param id
     * @param <T>
     * @param <R>
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T extends BaseModel, R extends BaseRepository<?,?>> T getEntity(String classSimpleName, R repo, String id){
        T result;
        result = (T)repo.getById(id);
        if (result != null) {
            return result;
        }
        throw new RuntimeException();
    }

    /**
     * Get entity object base on the type of resource object (type automatically) and base on resource.id
     *
     * @param resource
     * @param <T>
     * @param <R>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T, R> T getEntityGeneric(R resource) {
        if(resource instanceof BaseDTO) {
            Type res = (((ParameterizedType) resource.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
            return (T) getEntity(((Class<?>) res).getSimpleName(), ((BaseDTO<?>) resource).getId());
        }
        else if(resource instanceof BaseMergable) {
            Type res = (((ParameterizedType) resource.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
            return (T) getEntity(((Class<?>) res).getSimpleName(), ((BaseMergable<?>) resource).getId());
        }
        else
            throw new RuntimeException();
    }

    /**
     * Returns the entity type referred to by the class type and the string id.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getEntity(Class<?> cls, String id) {
        return (T) getEntity(cls.getSimpleName(), id);
    }

    /**
     * Returns the entity based on the resource supplied and resource.id.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T, R> T getEntity(R resource) {
        return this.getEntityGeneric(resource);
    }
}