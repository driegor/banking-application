package com.company.ebanking.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.dozer.DozerBeanMapper;

public class CustomDozerMapper extends DozerBeanMapper {

    public CustomDozerMapper() {
	super();
    }

    public CustomDozerMapper(List<String> mappingFiles) {
	super(mappingFiles);
    }

    public <S, T> List<T> map(Collection<S> sourceList, Class<T> targetClass) {
	return map(sourceList, targetClass, null);
    }

    public <S, T> List<T> map(Collection<S> sourceList, Class<T> targetClass, String mappingCase) {
	if (sourceList == null) {
	    return Collections.<T>emptyList();
	} else {
	    List<T> beanList = new ArrayList<>();
	    sourceList.stream().forEach(s -> beanList.add(map(s, targetClass, mappingCase)));
	    return beanList;
	}
    }
}
