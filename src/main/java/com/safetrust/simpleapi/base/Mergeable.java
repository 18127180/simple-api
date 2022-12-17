package com.safetrust.simpleapi.base;

import org.modelmapper.ModelMapper;

import static com.safetrust.simpleapi.base.MergeFunctions.mergeBase;
import static com.safetrust.simpleapi.base.MergeFunctions.mergeBaseWithIgnoreNullCondition;

public interface Mergeable<A> {

    /**
     * Merges this resource into the entity. This should occur after validation,
     * so we make the assumption that it is safe. When merging, only updated properties
     * should be assigned to the entity to ensure they don't get dirty.
     */
    default A to(A entity, EntityResolver resolver) {
        return entity;
    }

    default A to(A entity, EntityResolver resolver, ModelMapper modelMapper) {
        mergeBase(this, entity, modelMapper);
        return entity;
    }

    default A to(A entity, EntityResolver resolver, ModelMapper modelMapper, boolean ignoreNullValue) {
        mergeBaseWithIgnoreNullCondition(this, entity, modelMapper, ignoreNullValue);
        return entity;
    }

    /**
     * default native/primary propertie to entity using model mapper
     * @param entity
     * @param modelMapper
     * @return
     */
    default A to(A entity, ModelMapper modelMapper) {
        mergeBase(this, entity, modelMapper);
        return entity;
    }

    /**
     * Merges from the entity to this resource.
     */
    default Mergeable<? super A> from(A entity) {
        return this;
    }

    default Mergeable<? super A> from(A entity, Class<?> dtoClass, EntityResolver resolver) {
        return from(entity, dtoClass);
    }

    default Mergeable<? super A> from(A entity, Class<?> dtoClass) {
        return from(entity);
    }

    default Mergeable<? super A> from(A entity, Class<?> dtoClass, EntityResolver resolver, ModelMapper modelMapper) {
        return from(entity, dtoClass, resolver);
    }
}
