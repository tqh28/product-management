package com.product.management.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.product.management.entity.Product;
import com.product.management.repository.ProductRepository;

@Controller
public class UserController {

	@Autowired
	private ProductRepository productRepository;
	
	private static final Logger logger = LogManager.getLogger(UserController.class);

	@GetMapping(path = "/product-detail")
	public String productDetail(@RequestParam("id") Integer id, Model model) {
		try {
			Product product = productRepository.findOne(id);

			model.addAttribute("id", product.getId());
			model.addAttribute("name", product.getName());
			model.addAttribute("code", product.getCode());
			model.addAttribute("price", product.getPrice());

			return "product-detail";
		} catch (Exception e) {
			model.addAttribute("error", e.toString());
			logger.error(e.toString());
			return "error";
		}
	}
}
