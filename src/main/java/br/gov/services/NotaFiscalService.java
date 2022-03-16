package br.gov.services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.jboss.logging.Logger;

import br.gov.daos.NotaFiscalDAO;
import br.gov.dtos.NotaFiscalDTO;
import br.gov.enums.StatusProcessamentoEnum;
import br.gov.models.NotaFiscalModel;

@RequestScoped
public class NotaFiscalService extends GenericService {

    @Inject
    Logger log;

    @Inject
    NotaFiscalDAO notaFiscalDAO;

    public NotaFiscalService() {
    }

    public NotaFiscalDTO consultarNotaFiscalPorId(Long id) throws BadRequestException, NotFoundException {
        NotaFiscalModel notaFiscalModel = buscarPorId(id);
        verificaSeObjetoFoiEncontrado(notaFiscalModel);
        return new NotaFiscalDTO(notaFiscalModel);
    }

    public List<NotaFiscalDTO> listarNotasFiscais() throws BadRequestException, NotFoundException {
        List<NotaFiscalModel> listNotasFiscais = notaFiscalDAO.listarNotaFiscal();
        verificaSeObjetoFoiEncontrado(listNotasFiscais);
        return listNotasFiscais.stream().map(NotaFiscalDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public NotaFiscalModel inserir(NotaFiscalDTO notaFiscalDTO) throws BadRequestException {
        NotaFiscalModel notaFiscalModel = new NotaFiscalModel(notaFiscalDTO);
        if(!verificarExistenciaNota(notaFiscalModel)) {
            notaFiscalModel.setStatus(StatusProcessamentoEnum.PROCESSADA);
            notaFiscalDAO.inserir(notaFiscalModel);
        } else {
            throw new BadRequestException("A nota fiscal já existe.");
        }
        return notaFiscalModel;
    }

    @Transactional
    public NotaFiscalDTO atualizar(NotaFiscalDTO notaFiscalDTO) {
        NotaFiscalModel notaFiscalModel = new NotaFiscalModel(notaFiscalDTO);
        if(verificarExistenciaNota(notaFiscalModel)) {
            notaFiscalDAO.atualizar(notaFiscalModel);
        } else {
            throw new BadRequestException("Nota fiscal inexistente.");
        }
        return new NotaFiscalDTO(notaFiscalModel);
    }

    @Transactional
    public void remover(Long id) throws BadRequestException, NotFoundException {
        NotaFiscalModel notaFiscalModel = buscarPorId(id);
        verificaSeObjetoFoiEncontrado(notaFiscalModel);
        notaFiscalDAO.remover(notaFiscalModel);
    }

    private NotaFiscalModel buscarPorId(Long id) throws BadRequestException, NotFoundException {
        verificaParametro(id);
        NotaFiscalModel notaFiscalModel = notaFiscalDAO.consultarPorId(id);
        verificaSeObjetoFoiEncontrado(notaFiscalModel);
        return notaFiscalModel;
    }

    private boolean verificarExistenciaNota(NotaFiscalModel notaFiscalModel) {
        try {
            NotaFiscalModel noFiscalModel = notaFiscalDAO.consultarNotaFiscalPelaChave(notaFiscalModel.getChave());
            if(!Objects.isNull(noFiscalModel) 
                && !Objects.isNull(notaFiscalModel.getId()) && !notaFiscalModel.getId().equals(noFiscalModel.getId())
                || Objects.isNull(notaFiscalModel.getId()) ) {
                return true;
            }
        } catch (NoResultException nre) {
            log.debug(nre.getMessage(), nre);
        }
        return false;
    }

}