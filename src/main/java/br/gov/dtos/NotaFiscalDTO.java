package br.gov.dtos;

import br.gov.models.NotaFiscalModel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.*;

@Data
public class NotaFiscalDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomeArquivo;

    @NotNull
    private String chave;

    @NotNull
    private Date dataHoraRegistro;

    @NotNull
    private String nomeEmitente;

    @NotNull
    private String nomeDestinatario;

    @NotNull
    private Double valor;

    @NotNull
    private String descricaoStatus;

    @NotNull
    private List<DuplicataDTO> duplicatas = new ArrayList<>();

    public NotaFiscalDTO() {
    }

    public NotaFiscalDTO(NotaFiscalModel notaFiscalModel) {
        this.id = notaFiscalModel.getId();
        this.nomeArquivo = notaFiscalModel.getNomeArquivo();
        this.chave = notaFiscalModel.getChave();
        this.dataHoraRegistro = notaFiscalModel.getDataHoraRegistro();
        this.nomeEmitente = notaFiscalModel.getNomeEmitente();
        this.nomeDestinatario = notaFiscalModel.getNomeDestinatario();
        this.valor = notaFiscalModel.getValor();
        this.descricaoStatus = notaFiscalModel.getStatus().getDescricao();
    }

}
