package com.salesflow.sales_service;

import com.salesflow.sales_service.infrastructure.gateway.PersonGateway;
import com.salesflow.sales_service.infrastructure.gateway.PlanGateway;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

//@SpringBootTest
//class SalesServiceApplicationTests {
//
//	@MockBean
//	private PlanGateway personClient;
//
//	@MockBean
//	private PersonGateway planClient;
//
//	@Test
//	void contextLoads() {
//		// contexto carrega sem tentar instanciar Feign real
//	}
//}
