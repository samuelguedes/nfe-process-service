package br.gov.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SystemUtils {

	private SystemUtils() {
	}

	public static String get(final String chave) {
		String property = System.getProperty(chave);

		if (property == null || property.isEmpty()) {
			log.warn("Propriedade " + chave + " não configurada!");
			return null;
		}
		return property;
	}

}
