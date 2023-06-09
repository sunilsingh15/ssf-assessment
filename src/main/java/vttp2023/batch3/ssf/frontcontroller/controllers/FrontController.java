package vttp2023.batch3.ssf.frontcontroller.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import vttp2023.batch3.ssf.frontcontroller.model.User;

@Controller
@RequestMapping
public class FrontController {

	// TODO: Task 2, Task 3, Task 4, Task 6

	@GetMapping
	public String landingPage(Model model) {

		model.addAttribute("user", new User());
		return "view0";
		
	}

	@PostMapping(consumes = "application/x-www-form-urlencoded", produces = "text/html", path = "/login")
	public String loginPage(@Valid User user, BindingResult result) {

		if (result.hasErrors()) {
			return "view0";
		}


		return "view1";
	}
	
}
