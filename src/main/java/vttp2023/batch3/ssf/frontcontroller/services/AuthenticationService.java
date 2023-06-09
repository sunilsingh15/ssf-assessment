package vttp2023.batch3.ssf.frontcontroller.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vttp2023.batch3.ssf.frontcontroller.model.User;
import vttp2023.batch3.ssf.frontcontroller.respositories.AuthenticationRepository;

@Service
public class AuthenticationService {

	@Autowired
	AuthenticationRepository repository;

	String apiURL = "https://authservice-production-e8b2.up.railway.app/api/authenticate";

	// TODO: Task 2
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write the authentication method in here
	public void authenticate(String username, String password) throws Exception {

		User user = new User(username, password);

		RequestEntity<String> request = RequestEntity
											.post(apiURL)
											.contentType(MediaType.APPLICATION_JSON)
											.header("Accept", MediaType.APPLICATION_JSON_VALUE)
											.body(user.toJSON().toString(), String.class);

		RestTemplate template = new RestTemplate();
		ResponseEntity<String> response = template.exchange(request, String.class);
		
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Successfully authenticated.");
		}
		else if (response.getStatusCode().is4xxClientError()) {
			throw new Exception("Invalid login details!");
		}

	}

	// TODO: Task 3
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to disable a user account for 30 mins
	public void disableUser(String username) {
		repository.save(username);

	}

	// TODO: Task 5
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to check if a given user's login has been disabled
	public boolean isLocked(String username) {
		return false;
	}

	public String generateCaptcha() {

		StringBuilder captcha = new StringBuilder();
		String[] expressions = {"+", "*"};  
		Random r = new Random();

		int num1 = r.nextInt(1, 50);
		int num2 = r.nextInt(1, 50);

		captcha.append(num1);
		captcha.append(" ");
		captcha.append(expressions[r.nextInt(1)]);
		captcha.append(" ");
		captcha.append(num2);

		return captcha.toString();
	}

	public String checkCaptcha(String captcha) {

		String[] captchaToArray = captcha.split(" ");
		String expression = captchaToArray[1];
        int num1 = Integer.parseInt(captchaToArray[0]);
		int num2 = Integer.parseInt(captchaToArray[2]);
		int answer = 0;

            switch (expression) {
                case "+":
                    answer = num1 + num2;
                    break;
                case "-":
                    answer = num1 - num2;
                    break;
                case "*":
                    answer = num1 * num2;
                    break;
                case "/":
                    answer = num1 / num2;
                    break;
            }

		return String.valueOf(answer);

	}
}
