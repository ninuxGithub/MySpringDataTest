package com.hundsun.springdata.impldao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hundsun.springdata.dao.PersonDao;
import com.hundsun.springdata.domain.Person;

public class PersonRepositoryImpl implements PersonDao {

	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public void test() {
		Person p = entityManager.find(Person.class, 22);
		System.out.println(p);
	}

}
