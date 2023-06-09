package vttp2023.batch3.ssf.frontcontroller.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/protected")
public class ProtectedController {

	// TODO Task 5
	// Write a controller to protect resources rooted under /protected

	@GetMapping
	public String protectedPage() {

		

		return "view2";
	}
}
