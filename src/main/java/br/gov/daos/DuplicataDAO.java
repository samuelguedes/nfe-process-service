package br.gov.daos;


import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.gov.models.DuplicataModel;

@ApplicationScoped
public class DuplicataDAO extends GenericDAO<Long, DuplicataModel>{

    public List<DuplicataModel> listarDuplicataPorIdNotaFiscal(Long id){
        return getEntityManager()
                .createNamedQuery("DuplicataModel.consultarDuplicataPorIdNotaFiscal", DuplicataModel.class)
                .setParameter("id", id)
                .getResultList();
    }
}
