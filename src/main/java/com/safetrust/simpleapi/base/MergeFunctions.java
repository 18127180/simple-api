package com.safetrust.simpleapi.base;

import org.modelmapper.ModelMapper;

public class MergeFunctions {

    /**
     *  Merge Base data no set/list properties
     * @param from
     * @param to
     * @param modelMapper
     * @param <A>
     * @param <B>
     */
    public static <A, B> void mergeBase(A from, B to, ModelMapper modelMapper) {
        setModelMapperBaseMerge(modelMapper);
        modelMapper.map(from, to);
        modelMapper.getConfiguration().setPropertyCondition( a -> true);
    }

    /**
     * exclud lise/set in the modelmapper maapping -> should be reset right after use.
     * @param modelMapper
     */
    private static void setModelMapperBaseMerge(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setPropertyCondition(
            context -> {
                String type = context.getMapping().getSourceType().getTypeName();
                return !(type.contains("java/util/SortedSet")
                    || type.contains("java/util/Set")
                    || type.contains("java/util/List")
                    || type.contains("java.util.SortedSet")
                    || type.contains("java.util.Set")
                    || type.contains("java.util.List"));
            }
        );
    }

}
