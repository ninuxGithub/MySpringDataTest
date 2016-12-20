package com.hundsun.springdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hundsun.springdata.dao.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Transactional
	public void updatePerson(Integer id, String email) {
		personRepository.updatePerson(id, email);
	}
}
