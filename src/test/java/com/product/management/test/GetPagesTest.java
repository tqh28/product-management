package com.product.management.test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;

import com.product.management.ProductManagementApplicationTests;

public class GetPagesTest extends ProductManagementApplicationTests {
	
	@Test
	public void testGetLoginPage() throws Exception {
		mockMvc.perform(get("/login")).andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(authorities = "ROLE_USER")
	public void testHome() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk());
	}
	
	
	@Test()
	@WithMockUser(authorities="ROLE_ADMIN")
	public void testNewProductPage() throws Exception {
		mockMvc.perform(get("/admin/create-product")).andExpect(status().isOk());
	}
	
	
	@Test
	public void testGetLoginErrorPage() throws Exception {
		mockMvc.perform(get("/login-error").with(csrf())).andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(authorities = "ROLE_USER")
	public void testGetAccessDeniedPage() throws Exception {
		mockMvc.perform(get("/403")).andExpect(status().isOk());
	}
	
	
	@Test
	@WithMockUser(authorities = "ROLE_ADMIN")
	public void testGetUnlockAccountPage() throws Exception {
		mockMvc.perform(get("/admin/unlock-account")).andExpect(status().isOk());
	}
	
	
	@Test
	public void testGetCreateAccountPage() throws Exception {
		mockMvc.perform(get("/create-account")).andExpect(status().isOk());
	}
	
}
