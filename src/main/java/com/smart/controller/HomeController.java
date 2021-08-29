package com.smart.controller;

import javax.sound.midi.Soundbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entity.User;

@Controller
public class HomeController {
	@Autowired
	private UserRepository repository;
	@GetMapping("/")
	public String home(Model model)
	{	
		model.addAttribute("title", "Home - Smart Contact Manager");
		return "home";
	}
	
	@GetMapping("/about")
	public String about(Model model)
	{	
		model.addAttribute("title", "About - Smart Contact Manager");
		return "about";
	}
	@GetMapping("/signup")
	public String signup(Model model)
	{	
		model.addAttribute("title", "Signup - Smart Contact Manager");
		model.addAttribute("user", new User());
		return "signup";
	}
	
	// Handler for Registering user
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user, @RequestParam(value = "agreement",defaultValue = "false") boolean agreement, Model model)
	{
		if (!agreement) {
				System.out.println("You hava not agreed the Terms and Conditions");
		}
		user.setRole("ROLE_USER");
		user.setEnable(true);
		System.out.println("Agreement : " + agreement);
		System.out.println("USER : " + user);
		User result =  this.repository.save(user);
		model.addAttribute("user", result);
		
		return "signup";
	}
}
