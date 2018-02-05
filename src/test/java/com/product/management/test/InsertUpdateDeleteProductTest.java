package com.product.management.test;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.product.management.ProductManagementApplicationTests;
import com.product.management.entity.Product;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InsertUpdateDeleteProductTest extends ProductManagementApplicationTests {
	
	private static Integer insertedId;
	
	
	@Test
	@WithMockUser(authorities="ROLE_ADMIN")
	public void test0NewProductActionFail() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = post("/admin/create-product-action")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.content(new String("name=&code=B&price=C&quantity=D")).with(csrf());
		String content = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
		assertTrue(content.contains("Mapping error"));
	}
	
	
	@Test
	@WithMockUser(authorities="ROLE_ADMIN")
	public void test1NewProductActionSuccess() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = post("/admin/create-product-action")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.content(new String("name=UnitTestName&code=UnitTestCode&price=28.08&quantity=1")).with(csrf());
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}
	
	
	@Test
	public void test2GetProductByCode() {
		Product p = productRepository.findByCode("UnitTestCode");
		if (p != null) {
			insertedId = p.getId();
		}
	}
	
	
	@Test
	@WithMockUser(authorities="ROLE_ADMIN")
	public void test3EditProductPageSuccess() throws Exception {
		mockMvc.perform(get("/admin/edit-product?id=" + insertedId))
				.andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(authorities="ROLE_ADMIN")
	public void test3EditProductPageError() throws Exception {
		mockMvc.perform(get("/admin/edit-product?id=-1"))
				.andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(authorities="ROLE_ADMIN")
	public void test4EditProductActionFail() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = post("/admin/edit-product-action")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.content(new String("id=" + insertedId
						+ "&name=A&code=B&price=C&quantity=D"))
				.with(csrf());
		String content = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
		assertTrue(content.contains("Mapping error"));
	}
	
	
	@Test
	@WithMockUser(authorities="ROLE_ADMIN")
	public void test5EditProductActionError() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = post("/admin/edit-product-action")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.content(new String("id=-1&name=UnitTestNameEdited&code=UnitTestCode&price=28.08&quantity=1"))
				.with(csrf());
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(authorities="ROLE_ADMIN")
	public void test5EditProductActionSuccess() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = post("/admin/edit-product-action")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.content(new String("id=" + insertedId
						+ "&name=UnitTestNameEdited&code=UnitTestCode&price=28.08&quantity=1"))
				.with(csrf());
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(authorities="ROLE_USER")
	public void test6ProductDetailPageSuccess() throws Exception {
		mockMvc.perform(get("/product-detail?id=" + insertedId))
				.andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(authorities="ROLE_USER")
	public void test6ProductDetailPageError() throws Exception {
		mockMvc.perform(get("/product-detail?id=-1"))
				.andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(authorities = "ROLE_ADMIN")
	public void test7DeleteProductError() throws Exception {
		mockMvc.perform(delete("/admin/remove-product?id=-1")
				.with(csrf())).andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(authorities = "ROLE_ADMIN")
	public void test7DeleteProductSuccess() throws Exception {
		mockMvc.perform(delete("/admin/remove-product?id=" + insertedId)
				.with(csrf())).andExpect(status().isOk());
	}

}
