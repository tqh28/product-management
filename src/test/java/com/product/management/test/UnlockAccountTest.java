package com.product.management.test;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class UnlockAccountTest extends ProductManagementApplicationTests {

	@Test
	@WithMockUser(authorities = "ROLE_ADMIN")
	public void test0GetListLockedAccount() throws Exception {
		mockMvc.perform(get("/admin/get-locked-user?length=10&start=0")).andExpect(status().isOk());
	}
	
	
	@Test
	public void test1LoginErrorToInactiveByNormalUser() throws Exception {
		for (int i = 0; i < 5; i++) {
			MockHttpServletRequestBuilder requestBuilder = post("/login")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
					.content(new String("username=huy&password=huy1")).with(csrf());
			mockMvc.perform(requestBuilder).andExpect(status().is3xxRedirection());
		}
	}
	
	
	@Test
	@WithMockUser(authorities = "ROLE_ADMIN")
	public void test2UnlockAccountActionSuccess() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = 
				put("/admin/unlock-account-action?username=huy").with(csrf());
		String content = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
		assertTrue(content.contains("Unlock account done"));
	}
	
	
	@Test
	@WithMockUser(authorities = "ROLE_ADMIN")
	public void test2UnlockAccountActionError() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = 
				put("/admin/unlock-account-action?username=q1!w2@e3#r4$t5%y6^").with(csrf());
		String content = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
		assertTrue(content.contains("0"));
	}
}
