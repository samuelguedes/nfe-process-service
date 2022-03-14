package br.gov.enums;

import lombok.Getter;
import lombok.Setter;

public enum StatusProcessamentoEnum {

    EM_PROCESSAMENTO(0L, "Em processamento"),
    PROCESSADA(1L, "Processada"),
    PROCESSADA_COM_ERRO(2L, "Processada com erro");

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String descricao;

    StatusProcessamentoEnum(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
    
    public static StatusProcessamentoEnum obterPelaDescricao(String descricao) {
        for (StatusProcessamentoEnum status : values()) {
            if(status.descricao.equals(descricao)){
                return status;
            }
        }
        return null;
    }

}
