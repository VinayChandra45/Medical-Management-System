package com.nrifintech.medico.request;

public class AdminRequest {
	
	private String inlineRadioOptions;
	private String year;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getInlineRadioOptions() {
		return inlineRadioOptions;
	}

	public void setInlineRadioOptions(String inlineRadioOptions) {
		this.inlineRadioOptions = inlineRadioOptions;
	}

	public AdminRequest(String inlineRadioOptions, String year) {
		super();
		this.inlineRadioOptions = inlineRadioOptions;
		this.year = year;
	}

	@Override
	public String toString() {
		return "AdminRequest [inlineRadioOptions=" + inlineRadioOptions + ", year=" + year + "]";
	}

	
	
}