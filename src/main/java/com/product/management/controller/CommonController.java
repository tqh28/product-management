package com.product.management.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.management.common.ReplaceVietnameseCharacter;
import com.product.management.dto.NewInputAccount;
import com.product.management.entity.Product;
import com.product.management.entity.UserAccount;
import com.product.management.repository.ProductRepository;
import com.product.management.repository.UserAccountRepository;

@Controller
public class CommonController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private static final Logger logger = LogManager.getLogger(CommonController.class);

	@GetMapping(path = { "/", "home" })
	public String home(Model model) {
		return "home";
	}

	@GetMapping(path = "/get-product-list")
	@ResponseBody
	public Map<String, Object> getProductList(@RequestParam("name") String name,
			@RequestParam("lowestPrice") Double lowestPrice, @RequestParam("highestPrice") Double highestPrice,
			@RequestParam("length") Integer length, // pageSize
			@RequestParam("start") Integer start /* = pageNumber * length */) {

		// convert params
		if (highestPrice == null) {
			highestPrice = Double.MAX_VALUE;
		}
		if (lowestPrice == null) {
			lowestPrice = 0D;
		}

		Page<Product> result = productRepository
				.findBySearchNameContainingAndPriceLessThanEqualAndPriceGreaterThanEqual(
						ReplaceVietnameseCharacter.replace(name), highestPrice, lowestPrice,
						new PageRequest(start / length, length));

		// convert Page to Map for jquery datatable
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", result.getContent());
		map.put("recordsTotal", result.getTotalElements());
		map.put("recordsFiltered", result.getTotalElements());

		return map;
	}

	@GetMapping(path = { "/login" })
	public String login() {
		return "login";
	}

	// Login form with error
	@RequestMapping(path = "/login-error")
	public String loginError(Model model, HttpServletRequest request) {
		Exception e = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		model.addAttribute("error", e != null ? e.getMessage() : "");

		return "login";
	}

	@GetMapping(path = "/create-account")
	public String getCreateAccountPage(Model model) {
		return "create-account";
	}

	@PostMapping(path = "/create-account-action")
	public String createAccountAction(@Valid @ModelAttribute NewInputAccount inputAccount, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			model.addAttribute("error", "Mapping error");
			return "error";
		} else if (!inputAccount.getPassword().equals(inputAccount.getConfirmPassword())) {
			model.addAttribute("error", "Passwords don't match.");
			return "error";
		} else if (userAccountRepository.findByUsername(inputAccount.getUsername()) != null) {
			model.addAttribute("error", "Username: " + inputAccount.getUsername() + " is existed!");
			return "error";
		}

		try {
			UserAccount newAccount = new UserAccount();
			newAccount.setUsername(inputAccount.getUsername());
			newAccount.setPassword(bCryptPasswordEncoder.encode(inputAccount.getPassword()));
			newAccount.setRole(2); // ROLE_USER
			newAccount.setFailAttempts(0);
			newAccount.setIsLocked(false);

			userAccountRepository.saveAndFlush(newAccount);

			model.addAttribute("success", "Account: " + inputAccount.getUsername() + " is created success!");
			return "success";

		} catch (Exception e) {
			model.addAttribute("error", e.toString());
			logger.error(e.toString());
			return "error";
		}
	}

	@RequestMapping(path = "/403")
	public String accessDenied() {
		return "access-denied";
	}
}