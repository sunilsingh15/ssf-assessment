package vttp2023.batch3.ssf.frontcontroller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp2023.batch3.ssf.frontcontroller.model.User;
import vttp2023.batch3.ssf.frontcontroller.services.AuthenticationService;

@Controller
@RequestMapping
public class FrontController {

	// TODO: Task 2, Task 3, Task 4, Task 6

	@Autowired
	AuthenticationService service;

	private int loginAttempts = 0;

	@GetMapping
	public String landingPage(Model model) {

		model.addAttribute("user", new User());
		return "view0";

	}

	@PostMapping(consumes = "application/x-www-form-urlencoded", produces = "text/html", path = "/login")
	public String loginPage(@Valid User user, BindingResult result, HttpSession session, Model model,
			@RequestParam(value = "captcha", required = false) String captcha,
			@RequestParam(value = "captchaanswer", required = false) String answer) throws Exception {

		if (result.hasErrors()) {
			return "view0";
		}

		if (service.isLocked(user.getUsername())) {
			model.addAttribute("locked", "You have been locked out for 30 minutes. Try again later.");
			return "view0";
		}

		if (answer != null && answer.equals(service.checkCaptcha((String) session.getAttribute("captcha")))) {

			try {
				service.authenticate(user.getUsername(), user.getPassword());
				return "view1";
			} catch (Exception ex) {
				loginAttempts++;
				model.addAttribute("exception", "Invalid login details!");
				model.addAttribute("loginAttempts", loginAttempts);
				return "view0";
			}

		}

		try {
			service.authenticate(user.getUsername(), user.getPassword());
			return "view1";
		} catch (Exception ex) {
			loginAttempts++;
			model.addAttribute("exception", "Invalid login details!");
			model.addAttribute("loginAttempts", loginAttempts);

			String generatedCaptcha = service.generateCaptcha();
			session.setAttribute("captcha", generatedCaptcha);
			model.addAttribute("captcha", generatedCaptcha);
			
			if (loginAttempts == 3) {
				service.disableUser(user.getUsername());
				model.addAttribute("username", user.getUsername());
				return "view2";
			}

			return "view0";
			
		}

	}

}
