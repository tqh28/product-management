package com.product.management.test;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.product.management.ProductManagementApplicationTests;
import com.product.management.entity.UserAccount;
import com.product.management.repository.UserAccountRepository;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateAccountTest extends ProductManagementApplicationTests {
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	
	@Test
	public void test0CreateAccountActionPasswordsDontMatchError() throws Exception{
		MockHttpServletRequestBuilder requestBuilder = post("/create-account-action")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.content(new String("username=UnitTestAccount&password=Unit&confirmPassword=Test")).with(csrf());
		String response = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
		assertTrue(response.contains("Passwords don&#39;t match."));
	}
	
	
	@Test
	public void test1CreateAccountActionExistedAccountError() throws Exception{
		MockHttpServletRequestBuilder requestBuilder = post("/create-account-action")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.content(new String("username=admin&password=admin&confirmPassword=admin")).with(csrf());
		String response = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
		assertTrue(response.contains("Username: admin is existed!"));
	}
	
	
	@Test
	public void test2CreateAccountActionSuccess() throws Exception{
		MockHttpServletRequestBuilder requestBuilder = post("/create-account-action")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.content(new String("username=UnitTestAccount&password=UnitTestAccount&confirmPassword=UnitTestAccount")).with(csrf());
		String response = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
		assertTrue(response.contains("Account: UnitTestAccount is created success!"));
	}
	
	
	@Test
	public void test3DeleteTestAccount() throws Exception{
		UserAccount account = userAccountRepository.findByUsername("UnitTestAccount");
		userAccountRepository.delete(account);
	}
}
