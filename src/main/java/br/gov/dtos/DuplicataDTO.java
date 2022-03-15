package br.gov.dtos;

import br.gov.models.DuplicataModel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import lombok.*;

@Data
public class DuplicataDTO implements Serializable {

    private Long id;

    @NotNull
    private Long numeroParcela;

    @NotNull
    private Double valorParcela;

    @NotNull
    private Date dataVencimento;

    @NotNull
    private NotaFiscalDTO notaFiscalDTO;


    public DuplicataDTO() {
    }

    public DuplicataDTO(DuplicataModel duplicataModel) {
        this.id = duplicataModel.getId();
        this.numeroParcela = duplicataModel.getNumeroParcela();
        this.valorParcela = duplicataModel.getValorParcela();
        this.dataVencimento = duplicataModel.getDataVencimento();
        this.notaFiscalDTO = new NotaFiscalDTO(duplicataModel.getNotaFiscal());
    }

}
