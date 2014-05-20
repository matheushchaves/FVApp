package com.forcadevenda.uteis;

public class MensagemAlerta {
	 
	private CharSequence title;
	private CharSequence body;
	private CharSequence subTitle;
 
	public MensagemAlerta(CharSequence title, CharSequence body,
			CharSequence subTitle) {
 
		this.title = title;
		this.body = body;
		this.subTitle = subTitle;
	}
 
	public CharSequence getTitle() {
		return title;
	}
 
	public void setTitle(CharSequence title) {
		this.title = title;
	}
 
	public CharSequence getBody() {
		return body;
	}
 
	public void setBody(CharSequence body) {
		this.body = body;
	}
 
	public CharSequence getSubTitle() {
		return subTitle;
	}
 
	public void setSubTitle(CharSequence subTitle) {
		this.subTitle = subTitle;
	}
 
}