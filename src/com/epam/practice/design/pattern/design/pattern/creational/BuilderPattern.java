package com.epam.practice.design.pattern.design.pattern.creational;

/**
 * Builder pattern is a design pattern that allows for the step-by-step construction of complex objects. 
 * It separates the construction of an object from its representation, allowing the same construction process to create different representations. 
 * The Builder pattern is particularly useful when an object needs to be created with many optional parameters or when the construction process involves multiple steps.
 */
public class BuilderPattern {

	public static void main(String[] args) {
		Request request = new Request.Builder()
				.setUrl("https://example.com/api/data")
				.setMethod("POST")
				.setBody("{\"key\":\"value\"}")
				.setHeaders("Content-Type: application/json")
				.build();
		
		System.out.println(request);

	}

}


/**
 * 
 */
class Request {
	
	private String url;
	private String method;
	private String body;
	private String headers;
	
	public String toString() {
		return "Request [url=" + url + ", method=" + method + ", body=" + body + ", headers=" + headers + "]";
	}
	
	public Request(String url, String method, String body, String headers) {
		this.url = url;
		this.method = method;
		this.body = body;
		this.headers = headers;
	}
	
	public static class Builder {
		
		private String url;
		private String method;
		private String body;
		private String headers;
		
		public Builder setUrl(String url) {
			this.url = url;
			return this;
		}
		
		public Builder setMethod(String method) {
			this.method = method;
			return this;
		}
		
		public Builder setBody(String body) {
			this.body = body;
			return this;
		}
		
		public Builder setHeaders(String headers) {
			this.headers = headers;
			return this;
		}
		
		public Request build() {
			return new Request(url, method, body, headers);
		}
		
	}
}
