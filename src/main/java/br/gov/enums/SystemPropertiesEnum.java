package br.gov.enums;

import lombok.Getter;

public enum SystemPropertiesEnum {
	
	INPUT("nfe-process-service.diretorio.input"),
	OUTPUT("nfe-process-service.diretorio.output"),
	ERRO("nfe-process-service.diretorio.erro");

	@Getter
	private final String propriedade;

	SystemPropertiesEnum(String propriedade) {
		this.propriedade = propriedade;
	}

}
