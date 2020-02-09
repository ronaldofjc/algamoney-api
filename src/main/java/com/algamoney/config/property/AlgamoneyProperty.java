package com.algamoney.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("algamoney")
public class AlgamoneyProperty {
	
	private String originPermited = "http://localhost:8000";
	
	private final Security security = new Security();
	
	public Security getSecurity() {
		return security;
	}
	
	public String getOriginPermited() {
		return originPermited;
	}

	public void setOriginPermited(String originPermited) {
		this.originPermited = originPermited;
	}

	public static class Security {
		
		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
	}
	
}
