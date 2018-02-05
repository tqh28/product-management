package com.product.management.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.management.common.Constant;
import com.product.management.common.ReplaceVietnameseCharacter;
import com.product.management.common.ReturnMessage;
import com.product.management.common.UserAndRoleManagement;
import com.product.management.dto.UserAccountUsernameInactiveDate;
import com.product.management.entity.Product;
import com.product.management.repository.ProductRepository;
import com.product.management.repository.UserAccountRepository;

@Controller
public class AdminController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private UserAndRoleManagement userAndRoleManagement;

	private static final Logger logger = LogManager.getLogger(AdminController.class);

	@GetMapping(path = "/admin/create-product")
	public String newProductPage(Model model) {
		return "create-product";
	}

	@GetMapping(path = "/admin/edit-product")
	public String editProductPage(@RequestParam("id") Integer id, Model model) {
		try {
			Product product = productRepository.findOne(id);

			model.addAttribute("id", product.getId());
			model.addAttribute("name", product.getName());
			model.addAttribute("code", product.getCode());
			model.addAttribute("price", product.getPrice());

			return "edit-product";
		} catch (Exception e) {
			model.addAttribute("error", e.toString());
			logger.error(e.toString());
			return "error";
		}
	}

	@GetMapping(path = "/admin/unlock-account")
	public String getUnlockPage(Model model) {
		return "unlock-account";
	}

	@GetMapping(path = "/admin/get-locked-user")
	@ResponseBody
	public Map<String, Object> getListLockedAccount(@RequestParam("length") Integer length, // pageSize
			@RequestParam("start") Integer start /* = pageNumber * length */) {

		Page<UserAccountUsernameInactiveDate> result = userAccountRepository.findByIsLocked(true,
				new PageRequest(start / length, length));

		// convert Page to Map for jquery datatable
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", result.getContent());
		map.put("recordsTotal", result.getTotalElements());
		map.put("recordsFiltered", result.getTotalElements());

		return map;
	}

	@PostMapping(path = "/admin/create-product-action")
	public String newProductAction(@Valid @ModelAttribute Product newProduct, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("error", "Mapping error");
			return "error";
		}

		try {
			newProduct.setCreatedBy(userAndRoleManagement.getCurrentUserName());
			newProduct.setSearchName(ReplaceVietnameseCharacter.replace(newProduct.getName()));
			newProduct = productRepository.saveAndFlush(newProduct);

			model.addAttribute("name", newProduct.getName());
			model.addAttribute("code", newProduct.getCode());
			model.addAttribute("price", newProduct.getPrice());
			model.addAttribute("showSuccessText", true);

			return "product-detail";
		} catch (Exception e) {
			model.addAttribute("error", e.toString());
			logger.error(e.toString());
			return "error";
		}
	}

	@PostMapping(path = "/admin/edit-product-action")
	public String editProductAction(@Valid @ModelAttribute Product updateProduct, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("error", "Mapping error");
			return "error";
		}

		try {
			Product existedProduct = productRepository.findOne(updateProduct.getId());
			existedProduct.setName(updateProduct.getName());
			existedProduct.setSearchName(ReplaceVietnameseCharacter.replace(updateProduct.getName()));
			existedProduct.setCode(updateProduct.getCode());
			existedProduct.setPrice(updateProduct.getPrice());
			existedProduct.setUpdatedBy(userAndRoleManagement.getCurrentUserName());

			existedProduct = productRepository.saveAndFlush(existedProduct);

			model.addAttribute("name", updateProduct.getName());
			model.addAttribute("code", updateProduct.getCode());
			model.addAttribute("price", updateProduct.getPrice());
			model.addAttribute("showSuccessText", true);

			return "product-detail";
		} catch (Exception e) {
			model.addAttribute("error", e.toString());
			logger.error(e.toString());
			return "error";
		}
	}

	@PutMapping(path = "/admin/unlock-account-action")
	@ResponseBody
	public ReturnMessage unlockAccountAction(@RequestParam("username") String username) {
		try {
			userAndRoleManagement.resetFailAttempts(userAccountRepository, username);
			return new ReturnMessage(Constant.SUCCESS_CODE, Constant.UNLOCK_ACCOUNT_SUCCESS_TEXT);
		} catch (Exception e) {
			logger.error(e.toString());
			return new ReturnMessage(Constant.FAIL_CODE, e.toString());
		}
	}

	@DeleteMapping(path = "/admin/remove-product")
	@ResponseBody
	public ReturnMessage removeProduct(@RequestParam("id") Integer id) {
		try {
			productRepository.delete(id);
			return new ReturnMessage(Constant.SUCCESS_CODE, Constant.DELETE_SUCCESS_TEXT);
		} catch (Exception e) {
			logger.error(e.toString());
			return new ReturnMessage(Constant.FAIL_CODE, e.toString());
		}
	}
}
