package com.example.accessingdatajpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class AccessingDataJpaApplicationTests {

	@Autowired
	private CustomerRepository customerRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	@Rollback(value = true)
	void testCreatedDateNotUpdateAble(){
		// 1. save a new customer
		LocalDateTime now = LocalDateTime.now();
		customerRepository.save(Customer.builder()
			.firstName("jungha")
			.lastName("kim")
			.createBy("SYSTEM")
			.createdDate(now)
			.lastModifiedBy("SYSTEM")
			.lastModifiedDate(now)
			.build());

		// 2. DB 반영
		entityManager.flush();
		entityManager.clear();

		Customer savedCustomer = customerRepository.findById(1L);
		log.info("----- 최초 고객 정보 확인 -----");
		System.out.println(savedCustomer.toString());

		//3. 생성 시간 , 수정 시간 변경
		savedCustomer.updatePlusDay(2);

		entityManager.flush();
		entityManager.clear();

		// 4. 결과 확인
		log.info("----- 고객 정보 생성 시간 확인 -----");
		Customer checkedCustomer = customerRepository.findById(1L);
		System.out.println(checkedCustomer.toString());

		assertThat(checkedCustomer.getCreatedDate().truncatedTo(ChronoUnit.DAYS))
			.isEqualTo(now.truncatedTo(ChronoUnit.DAYS));
	}


	@Test
	@DisplayName("auditing 기능 적용한 후 테스트")
	@Transactional
	@Rollback(value = false)
	void testAuditing() throws InterruptedException {
		// 1. save a new customer
		LocalDateTime now = LocalDateTime.now();
		customerRepository.save(Customer.builder()
			.firstName("haJung")
			.lastName("kim")
			.build());
	}

	@Test
	@DisplayName("auditing 기능 적용한 후 테스트")
	@Transactional
	@Rollback(value = false)
	void testAuditing1() throws InterruptedException {
		Customer customer = customerRepository.findById(1L);
		customer.updateName("HaHa");
	}


}
