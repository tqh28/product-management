package com.product.management.test;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.product.management.ProductManagementApplicationTests;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginTest extends ProductManagementApplicationTests {
	
	
	@Test
	@WithMockUser(authorities = "ROLE_ADMIN")
	public void test0UnlockAccountAction() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = 
				put("/admin/unlock-account-action?username=huy").with(csrf());
		String content = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
		assertTrue(content.contains("Unlock account done"));
	}
	
	
	@Test
	public void test1LoginErrorByAdmin() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = post("/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.content(new String("username=admin&password=adminn")).with(csrf());
		mockMvc.perform(requestBuilder).andExpect(status().is3xxRedirection());
	}
	
	
	@Test
	public void test2LoginSuccess() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = post("/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.content(new String("username=huy&password=huy")).with(csrf());
		mockMvc.perform(requestBuilder).andExpect(status().is3xxRedirection());
	}
	
	
	@Test
	public void test3LoginErrorByNotExistedUser() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = post("/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.content(new String("username=qwerty123&password=qwerty123")).with(csrf());
		mockMvc.perform(requestBuilder).andExpect(status().is3xxRedirection());
	}
	
	
	@Test
	public void test4LoginErrorByNormalUser() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = post("/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.content(new String("username=huy&password=huy1")).with(csrf());
		mockMvc.perform(requestBuilder).andExpect(status().is3xxRedirection());
	}
	
	
	@Test
	public void test5LoginErrorToInactiveByNormalUser() throws Exception {
		for (int i = 0; i < 5; i++) {
			MockHttpServletRequestBuilder requestBuilder = post("/login")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
					.content(new String("username=huy&password=huy1")).with(csrf());
			mockMvc.perform(requestBuilder).andExpect(status().is3xxRedirection());
		}
	}
	
	
	@Test
	public void test6LoginErrorByLockedAccount() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = post("/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.content(new String("username=huy&password=huy")).with(csrf());
		mockMvc.perform(requestBuilder).andExpect(status().is3xxRedirection());
	}
}
