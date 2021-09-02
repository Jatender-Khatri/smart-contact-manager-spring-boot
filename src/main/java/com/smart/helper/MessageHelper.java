package com.smart.helper;

public class MessageHelper {

	private String content;
	private String type;

	/**
	 * 
	 */
	public MessageHelper() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param content
	 * @param type
	 */
	public MessageHelper(String content, String type) {
		super();
		this.content = content;
		this.type = type;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MessageHelper [content=" + content + ", type=" + type + "]";
	}

}
