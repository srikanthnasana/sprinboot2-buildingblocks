package com.stacksimply.restservices.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Specifications<T> {

	 Predicate toPredicate(Root<T> root, CriteriaQuery query, CriteriaBuilder cb);
}
