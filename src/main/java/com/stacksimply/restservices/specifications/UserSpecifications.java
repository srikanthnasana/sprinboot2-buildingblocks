package com.stacksimply.restservices.specifications;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.query.criteria.internal.predicate.BooleanExpressionPredicate;
import org.springframework.data.jpa.domain.Specification;
import com.stacksimply.restservices.entities.User;

public class UserSpecifications {
	public static Specification<User> likeFirstName(String firstName) {

		if (firstName == null)
			return null;

		return (root, query, cb) -> {
			return cb.like(root.get("firstName"), "%" + firstName + "%");
		};

	}

	public static Specification<User> equalLastName(String lastName) {

		if (lastName == null)
			return null;

		return (root, query, cb) -> {
			return cb.equal(root.get("lastName"), lastName);
		};

	}

	public static Specification<User> notForm(Long orderId) {
		if (orderId == 0)
			return null;
		Collection<Long> list = new ArrayList<>();
		list.add(2004l);
		return (root, query, cb) -> {

			// return root.join("orders").get("oredrId").in(list);
			// return cb.not(cb.like(root.join("orders").get("orderdescription"),
			// "%"+order+"%"));
			return cb.equal(root.join("orders").get("oredrId"), orderId);
		};

	}

	/*
	 * public static Specification<Order> isLongTermCustomer() {
	 * 
	 * return (root, query,cb)-> { return cb.lessThan(root.get("Creationtime"), new
	 * LocalDate.minusYears(2));
	 * 
	 * }; }
	 */
	//Otherway of writing specifications[QueryDsl Support]
	/*
	 * findAll(notFirstName("srikanth")) BooleanExpressionPredicate
	 * notFirstName(String firstName) { return
	 * Quser.user.firstName.containsIgnoreCase(firstName).not(); }
	 */
}
