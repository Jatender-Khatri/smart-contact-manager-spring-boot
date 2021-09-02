package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entity.Contact;
import com.smart.entity.User;
import com.smart.helper.MessageHelper;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository repository;
	@Autowired
	private ContactRepository contactRepository;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String email = principal.getName();
		System.out.println("Email : " + email);
		User user = this.repository.getUserByUserName(email);
		System.out.println("User : " + user);
		model.addAttribute("user", user);
	}

	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title", "User Dashborad");
		return "normal/user_dashboard";
	}

	// Open add Form Handler
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}

	@PostMapping("/process-contant")
	public String addContact(@ModelAttribute Contact contact, @RequestParam("contact.imageURL") MultipartFile file,
			Principal principal, HttpSession session) {
		try {
			String email = principal.getName();
			User user = this.repository.getUserByUserName(email);
			// Proccessing and Uploading Image
			if (file.isEmpty()) {
				System.out.println("File is Empty");
				contact.setImageURL("contact.png");
			} else {
				contact.setImageURL(file.getOriginalFilename());
				File saveFile = new ClassPathResource("static/image").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Image is Upload");

			}
			contact.setUser(user);
			user.getContacts().add(contact);
			this.repository.save(user);
			System.out.println("Data : " + contact);
			System.out.println("Added To Data base");
			// Message show
			session.setAttribute("message", new MessageHelper("Your contact is added !! Add more..", "success"));
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
			e.printStackTrace();
			// Message show
			session.setAttribute("message", new MessageHelper("Something Went Wrong !! Try again..", "danger"));
		}
		return "normal/add_contact_form";
	}

	@GetMapping("/show_contacts/{page}")
	public String getAllContacts(@PathVariable("page") Integer page, Model model, Principal principal) {
		model.addAttribute("title", "Show User Contacts");
		String userName = principal.getName();
		User user = this.repository.getUserByUserName(userName);
		Pageable pageable = PageRequest.of(page, 6);
		Page<Contact> list = this.contactRepository.findContactsByUser(user.getUserId(), pageable);
		model.addAttribute("contacts", list);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", list.getTotalPages());
		return "normal/show_contacts";
	}

	// Show Particular Contact Details
	@RequestMapping("/{cId}/contact")
	public String showContactDetail(@PathVariable("cId") Integer id, Model model, Principal principal) {
		model.addAttribute("title", "Contact-Detail");
		System.out.println("Contact Id : " + id);
		Optional<Contact> contactOptional = this.contactRepository.findById(id);
		Contact contact = contactOptional.get();
		String userName = principal.getName();
		User user = this.repository.getUserByUserName(userName);

		if (user.getUserId() == contact.getUser().getUserId()) {
			model.addAttribute("contact", contact);
			model.addAttribute("title", contact.getName());
		}

		return "normal/contact_detail";
	}

	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid") Integer cId, Model model, HttpSession session,
			Principal principal) {
		Contact contact = this.contactRepository.findById(cId).get();
		System.out.println("Contact : " + contact.getcId());
		User user = this.repository.getUserByUserName(principal.getName());
		user.getContacts().remove(contact);
		this.repository.save(user);
		System.out.println("Deleted");
		session.setAttribute("message", new MessageHelper("Contact deleted Sucessfully...", "success"));
		return "redirect:/user/show_contacts/0";
	}

	// Open Update Form Handler
	@PostMapping("/update-contact/{cid}")
	public String updateForm(@PathVariable("cid") Integer cId, Model model) {
		model.addAttribute("title", "Update-Contact");

		Contact contact = this.contactRepository.findById(cId).get();
		model.addAttribute("contact", contact);
		return "normal/update_form";
	}

	// Update Contact Handler
	@PostMapping("/process-update")
	public String updateHandler(@ModelAttribute Contact contact, @RequestParam("contact.imageURL") MultipartFile file,
			Model model, HttpSession session, Principal principal) {
		model.addAttribute("title", "Update-Contact");
		try {
			// Old Contact Details
			Contact oldContactDetail = this.contactRepository.findById(contact.getcId()).get();
			// Image
			if (!file.isEmpty()) {
				// Delete Old Photo Form Computer
				File deleteFile = new ClassPathResource("static/image").getFile();
				File file2 = new File(deleteFile,oldContactDetail.getImageURL());
				file2.delete();
				//Update New Photo
				File saveFile = new ClassPathResource("static/image").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				contact.setImageURL(file.getOriginalFilename());
			} else {
				contact.setImageURL(oldContactDetail.getImageURL());
			}
			User user = this.repository.getUserByUserName(principal.getName());
			contact.setUser(user);
			this.contactRepository.save(contact);
			session.setAttribute("message", new MessageHelper("Your Contact is Updated....", "success"));
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/user/" + contact.getcId() + "/contact";
	}
	@GetMapping("/profile")
	public String yourProfile(Model model)
	{
		model.addAttribute("title", "User-Profile");
		return "normal/profile";
	}
}
