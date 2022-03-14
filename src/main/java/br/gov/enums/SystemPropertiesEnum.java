package br.gov.enums;

import br.gov.utils.SystemUtils;
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

	public String getValue() {
		return SystemUtils.get(getPropriedade());
	}

}
