package br.gov.services;

import br.gov.daos.NotaFiscalDAO;
import br.gov.dtos.NotaFiscalDTO;
import br.gov.models.NotaFiscalModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class NotaFiscalService {

    @Inject
    NotaFiscalDAO notaFiscalDAO;

    public NotaFiscalService() {
    }

    public NotaFiscalDTO consultarNotaFiscalPorId(Long id) {
        NotaFiscalModel notaFiscalModel = notaFiscalDAO.consultarPorId(id);
        return new NotaFiscalDTO(notaFiscalModel);
    }

    public NotaFiscalDTO consultarNotaFiscalPelaChave(String chave) {
        NotaFiscalModel notaFiscalModel = notaFiscalDAO.consultarNotaFiscalPelaChave(chave);
        return new NotaFiscalDTO(notaFiscalModel);
    }

    public List<NotaFiscalDTO> listarNotasFiscais() {
        List<NotaFiscalModel> listPessoas = notaFiscalDAO.listar();
        return listPessoas.stream().map(NotaFiscalDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public NotaFiscalModel save(NotaFiscalDTO pessoaDTO) {
        NotaFiscalModel notaFiscalModel = new NotaFiscalModel(pessoaDTO);
        notaFiscalDAO.inserir(notaFiscalModel);
        return notaFiscalModel;
    }

    @Transactional
    public NotaFiscalDTO atualizar(NotaFiscalDTO pessoaDTO) {
        NotaFiscalModel notaFiscalModel = new NotaFiscalModel(pessoaDTO);
        notaFiscalDAO.atualizar(notaFiscalModel);
        return new NotaFiscalDTO(notaFiscalModel);
    }

    @Transactional
    public void remover(Long id) {
        NotaFiscalModel notaFiscalModel = notaFiscalDAO.consultarPorId(id);
        notaFiscalDAO.remover(notaFiscalModel);
    }
}
