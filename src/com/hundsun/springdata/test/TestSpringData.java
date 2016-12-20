package com.hundsun.springdata.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;

import com.hundsun.springdata.dao.PersonRepository;
import com.hundsun.springdata.domain.Person;
import com.hundsun.springdata.service.PersonService;

public class TestSpringData {
	ApplicationContext ac = null;
	PersonRepository personReposi;
	PersonService personService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	{
		ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		personReposi = ac.getBean(PersonRepository.class);
		personService = ac.getBean(PersonService.class);
	}

	@Test
	public void pridicatPage() {
		int page = 3 - 1;
		int size = 10;
		Pageable pageable = new PageRequest(page, size);
		Specification<Person> specific = new Specification<Person>() {

			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Path<Integer> path = root.get("id");
				return cb.gt(path, 4);
			}

		};
		Page<Person> pages = personReposi.findAll(specific, pageable);
		System.out.println("总记录数：" + pages.getTotalElements());
		System.out.println("当前页数：" + (pages.getNumber() + 1));
		System.out.println("总页数：" + pages.getTotalPages());
		System.out.println("当前页的记录集合：" + pages.getContent());
		System.out.println("当前页数的第几条：" + pages.getNumberOfElements());

	}

	@Test
	public void page1() {
		int page = 3 - 1;
		int size = 10;
		Order orders1 = new Order(Direction.ASC, "id");
		Order orders2 = new Order(Direction.DESC, "name");
		List<Order> orders = new ArrayList<>();
		orders.add(orders1);
		orders.add(orders2);
		Sort sort = new Sort(orders);
		Pageable pageable = new PageRequest(page, size, sort);
		Page<Person> pages = personReposi.findAll(pageable);
		System.out.println("总记录数：" + pages.getTotalElements());
		System.out.println("当前页数：" + (pages.getNumber() + 1));
		System.out.println("总页数：" + pages.getTotalPages());
		System.out.println("当前页的记录集合：" + pages.getContent());
		System.out.println("当前页数的第几条：" + pages.getNumberOfElements());

	}

	@Test
	public void testFind() {
		personReposi.test();
	}

	@Test
	public void update() {
		personService.updatePerson(5, "java@qq.com");
	}

	@Test
	public void nativeSql() {
		int count = personReposi.getRecords();
		System.out.println(count);
	}

	@Test
	public void testByparams() {
		List<Person> list = personReposi.findByParam1("aa", 66);
		List<Person> list2 = personReposi.findByParam2("aa", 66);
		List<Person> list3 = personReposi.findByParam3("a", 66);
		System.out.println(list.size());
		System.out.println(list2.size());
		System.out.println(list3.size());
	}

	@Test
	public void testFindPersonByBithday() {
		try {
			Date d = sdf.parse("2013-7-7 0:0:0");
			System.out.println(d);
			List<Person> list = personReposi.findPersonByBirthdayGreaterThan(d);
			System.out.println(list);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void save() {
		List<Person> list = new ArrayList<>();
		for (char i = 'a'; i <= 'z'; i++) {
			Person p = new Person(i + "" + i, (int) i, randomDate(), i + "" + i + "@hundsun.com");
			list.add(p);
		}
		personReposi.save(list);
	}

	public static Date randomDate() {
		Calendar calendar = Calendar.getInstance();
		// 注意月份要减去1
		calendar.set(1990, 11, 31);
		calendar.getTime().getTime();
		// 根据需求，这里要将时分秒设置为0
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		long min = calendar.getTime().getTime();

		calendar.set(2016, 11, 30);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.getTime().getTime();
		long max = calendar.getTime().getTime();
		// 得到大于等于min小于max的double值
		double randomDate = Math.random() * (max - min) + min;
		// 将double值舍入为整数，转化成long类型
		calendar.setTimeInMillis(Math.round(randomDate));
		return calendar.getTime();
	}

	@Test
	public void testFindPersonById() {
		Person p = personReposi.findPersonById(1);
		System.out.println(p);

		List<Person> persons = personReposi.findAll();
		System.out.println(persons);
	}

	@Test
	public void testCreatTable() {

	}

	@Test
	public void testDataSource() {
		DataSource dataSource = ac.getBean(DataSource.class);
		System.out.println("dataSource:" + dataSource);
	}

}
