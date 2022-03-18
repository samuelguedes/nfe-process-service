package br.gov.models;

import br.gov.dtos.DuplicataDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import lombok.*;

@Data
@Entity
@Table(name = "tb_duplicata")
public class DuplicataModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_duplicata_id_seq")
    @SequenceGenerator(name = "tb_duplicata_seq_gen", sequenceName = "tb_duplicata_id_seq")
    private Long id;

    @Column(name = "nr_parcela", nullable = false)
    private Long numeroParcela;

    @Column(name = "vr_parcela", nullable = false)
    private Double valorParcela;

    @Column(name = "dt_vencimento", nullable = false)
    private Date dataVencimento;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "id_nota_fiscal")
    private NotaFiscalModel notaFiscal = new NotaFiscalModel();


    public DuplicataModel() {
    }

    public DuplicataModel(DuplicataDTO duplicataDTO) {
        this.id = duplicataDTO.getId();
        this.numeroParcela = duplicataDTO.getNumeroParcela();
        this.valorParcela = duplicataDTO.getValorParcela();
        this.dataVencimento = duplicataDTO.getDataVencimento();
    }
    
}
