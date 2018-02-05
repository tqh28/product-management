package com.product.management.test;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import com.product.management.ProductManagementApplicationTests;

public class GetProductListTest extends ProductManagementApplicationTests {
	
	@SuppressWarnings("unchecked")
	@Test
	@WithMockUser(authorities = "ROLE_USER")
	public void testGetProductList() throws Exception {
		MvcResult result = mockMvc.perform(
				get("/get-product-list?name=&lowestPrice=&highestPrice=&length=10&start=0")).andReturn();
		String content = result.getResponse().getContentAsString();
		HashMap<String, Object> resutlMap = mapper.readValue(content, HashMap.class);
		List<?> productList = (List<?>) resutlMap.get("data");
		
		assertTrue(productList.size() > 0);
	}
	
	
}
