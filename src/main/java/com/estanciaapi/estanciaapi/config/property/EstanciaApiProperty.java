package com.estanciaapi.estanciaapi.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("estanciaapi")
public class EstanciaApiProperty {

	private String originPermitida = "https://desa-estancia-ui.herokuapp.com";
	//private String originPermitida = "http://localhost:4200";
	private final Seguranca seguranca = new Seguranca();
	public static class Seguranca{
		private boolean enableHttps; 

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
	}
	public Seguranca getSeguranca() {
		return seguranca;
	}
	public String getOriginPermitida() {
		return originPermitida;
	}
	public void setOriginPermitida(String originPermitida) {
		this.originPermitida = originPermitida;
	}
	
}
