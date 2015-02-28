package br.com.cadocruz.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

	@RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HelloMessage> hello(Principal principal) {
		return new ResponseEntity<HelloMessage>(new HelloMessage("Hello, "
				+ principal.getName() + "!"), HttpStatus.OK);
	}

	class HelloMessage {
		private String message;

		public HelloMessage(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}
}
