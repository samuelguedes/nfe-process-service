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

import br.gov.daos.DuplicataDAO;
import br.gov.dtos.DuplicataDTO;
import br.gov.models.DuplicataModel;

@RequestScoped
public class DuplicataService extends GenericService {

    @Inject
    Logger log;

    @Inject
    DuplicataDAO duplicataDAO;

    public DuplicataService() {
    }

    public DuplicataDTO consultarDuplicataPorId(Long id) throws BadRequestException, NotFoundException {
        DuplicataModel duplicataModel = buscarPorId(id);
        verificaSeObjetoFoiEncontrado(duplicataModel);
        return new DuplicataDTO(duplicataModel);
    }

    public List<DuplicataDTO> listarDuplicatasDaNotaFiscal(Long id) throws BadRequestException, NotFoundException {
        List<DuplicataModel> listarDuplicas = duplicataDAO.listarDuplicatasDaNotaFiscal(id);
        verificaSeObjetoFoiEncontrado(listarDuplicas);
        return listarDuplicas.stream().map(DuplicataDTO::new).collect(Collectors.toList());
    }

    public List<DuplicataDTO> listarDuplicatas() throws BadRequestException, NotFoundException {
        List<DuplicataModel> listarDuplicas = duplicataDAO.listarDuplicatas();
        verificaSeObjetoFoiEncontrado(listarDuplicas);
        return listarDuplicas.stream().map(DuplicataDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public DuplicataDTO inserir(DuplicataDTO duplicataDTO) throws BadRequestException {
        DuplicataModel duplicataModel = new DuplicataModel(duplicataDTO);
        if(!verificarExistenciaDuplicata(duplicataModel)) {
            duplicataDAO.inserir(duplicataModel);
        } else {
            throw new BadRequestException("A duplicata já existe.");
        }
        return new DuplicataDTO(duplicataModel);
    }

    @Transactional
    public DuplicataDTO atualizar(DuplicataDTO duplicataDTO) {
        DuplicataModel duplicataModel = new DuplicataModel(duplicataDTO);
        if(verificarExistenciaDuplicata(duplicataModel)) {
            duplicataDAO.atualizar(duplicataModel);
        } else {
            throw new BadRequestException("Duplicata inexistente.");
        }
        return new DuplicataDTO(duplicataModel);
    }

    @Transactional
    public void remover(Long id) throws BadRequestException, NotFoundException {
        verificaParametro(id);
        DuplicataModel duplicataModelBusca = buscarPorId(id);
        verificaSeObjetoFoiEncontrado(duplicataModelBusca);
        duplicataDAO.remover(duplicataModelBusca);
    }

    private DuplicataModel buscarPorId(Long id) throws BadRequestException, NotFoundException {
        verificaParametro(id);
        DuplicataModel duplicataModel = duplicataDAO.consultarPorId(id);
        verificaSeObjetoFoiEncontrado(duplicataModel);
        return duplicataModel;
    }

    private boolean verificarExistenciaDuplicata(DuplicataModel duplicataModel) {
        try {
            DuplicataModel duplicModel = duplicataDAO.consultarPorId(duplicataModel.getId());
            if(!Objects.isNull(duplicModel) 
                && !Objects.isNull(duplicataModel.getId()) && !duplicataModel.getId().equals(duplicModel.getId())
                || Objects.isNull(duplicataModel.getId()) ) {
                return true;
            }
        } catch (NoResultException nre) {
            log.debug(nre.getMessage(), nre);
        }
        return false;
    }

}