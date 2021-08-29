package com.smart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CONTACT")
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cId;
	private String name;
	private String nickName;
	private String work;
	@Column(unique = true)
	private String email;
	private String phone;
	private String imageURL;
	@Column(length = 500)
	private String description;
	@ManyToOne
	private User user;

	/**
	 * 
	 */
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cId
	 * @param name
	 * @param nickName
	 * @param work
	 * @param email
	 * @param phone
	 * @param imageURL
	 * @param description
	 */
	public Contact(Integer cId, String name, String nickName, String work, String email, String phone, String imageURL,
			String description) {
		super();
		this.cId = cId;
		this.name = name;
		this.nickName = nickName;
		this.work = work;
		this.email = email;
		this.phone = phone;
		this.imageURL = imageURL;
		this.description = description;
	}

	/**
	 * @return the cId
	 */
	public Integer getcId() {
		return cId;
	}

	/**
	 * @param cId the cId to set
	 */
	public void setcId(Integer cId) {
		this.cId = cId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the work
	 */
	public String getWork() {
		return work;
	}

	/**
	 * @param work the work to set
	 */
	public void setWork(String work) {
		this.work = work;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Contact [cId=" + cId + ", name=" + name + ", nickName=" + nickName + ", work=" + work + ", email="
				+ email + ", phone=" + phone + ", imageURL=" + imageURL + ", description=" + description + "]";
	}

}
