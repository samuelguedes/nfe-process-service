package br.gov.models;

import br.gov.dtos.NotaFiscalDTO;
import br.gov.enums.StatusProcessamentoEnum;

import javax.persistence.*;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.*;

@Data
@Entity
@Table(name = "tb_nota_fiscal")
public class NotaFiscalModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_nota_fiscal_id_seq")
    @SequenceGenerator(name = "tb_nota_fiscal_seq_gen", sequenceName = "tb_nota_fiscal_id_seq")
    private Long id;

    @Column(name = "nm_arquivo", nullable = false)
    private String nomeArquivo;

    @Column(name = "nu_nota_fiscal", length = 100, nullable = false)
    private String chave;

    @Column(name = "dh_registro", nullable = false)
    private Date dataHoraRegistro;

    @Column(name = "nm_emitente", length = 100, nullable = false)
    private String nomeEmitente;

    @Column(name = "nm_destinatario", length = 100, nullable = false)
    private String nomeDestinatario;

    @Column(name = "vl_nota_fiscal", nullable = false)
    private Double valor;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "id_st_processamento", nullable = false)
    private StatusProcessamentoEnum status;


    @OneToMany(mappedBy = "notaFiscal", cascade = { javax.persistence.CascadeType.ALL }, orphanRemoval = true)
    private List<DuplicataModel> duplicatas = new ArrayList<>();

    public NotaFiscalModel() {
    }

    public NotaFiscalModel(NotaFiscalDTO notaFiscalDTO) {
        this.id = notaFiscalDTO.getId();
        this.nomeArquivo = notaFiscalDTO.getNomeArquivo();
        this.chave = notaFiscalDTO.getChave();
        this.dataHoraRegistro = notaFiscalDTO.getDataHoraRegistro();
        this.nomeEmitente = notaFiscalDTO.getNomeEmitente();
        this.nomeDestinatario = notaFiscalDTO.getNomeDestinatario();
        this.valor = notaFiscalDTO.getValor();
        this.status = StatusProcessamentoEnum.obterPelaDescricao(notaFiscalDTO.getDescricaoStatus());
        this.duplicatas = notaFiscalDTO.getDuplicatas().stream().map(DuplicataModel::new).collect(Collectors.toList());
    }
    
}
