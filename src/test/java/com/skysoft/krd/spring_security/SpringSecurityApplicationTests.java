package com.skysoft.krd.spring_security;

import com.skysoft.krd.spring_security.entities.User;
import com.skysoft.krd.spring_security.services.JwtServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SpringSecurityApplicationTests {
	@Autowired
	JwtServiceImpl jwtserviceImpl;
	@Test
	void contextLoads() {
		User user =new User(1l,"hama","123","");
		String token= jwtserviceImpl.generateToken(user);
		System.out.println("token generated :- "+token );
		Long id= jwtserviceImpl.getUserIdFromToken(token);
		System.out.println("Generated ID :- "+id);
	}

}
