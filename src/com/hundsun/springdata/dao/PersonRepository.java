package com.hundsun.springdata.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hundsun.springdata.domain.Person;

//@RepositoryDefinition(domainClass=Person.class, idClass=Integer.class)
public interface PersonRepository extends JpaRepository<Person, Integer>, JpaSpecificationExecutor<Person>, PersonDao {

	Person findPersonById(Integer id);

	List<Person> findAll();

	List<Person> findPersonByBirthdayGreaterThan(Date d);

	@Query(value = "from Person p where p.name=?1 and p.age >=?2")
	List<Person> findByParam1(String name, Integer age);

	@Query(value = "from Person p where p.name= :name and p.age >= :age")
	List<Person> findByParam2(@Param("name") String name, @Param("age") Integer age);

	@Query(value = "from Person p where p.name like %:name% and age>=:age")
	List<Person> findByParam3(@Param("name") String name, @Param("age") Integer age);

	// use native sql
	@Query(nativeQuery = true, value = "select count(*) from jpa_person")
	int getRecords();

	// 修改要有事务的支持 ：故所以要添加service层， 还有加入@modifying 注解
	@Modifying
	@Query("update Person p set p.email = :email where p.id=:id")
	void updatePerson(@Param("id") Integer id, @Param("email") String email);

}
