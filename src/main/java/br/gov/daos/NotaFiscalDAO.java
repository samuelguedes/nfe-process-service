package br.gov.daos;


import javax.enterprise.context.ApplicationScoped;

import br.gov.models.NotaFiscalModel;

@ApplicationScoped
public class NotaFiscalDAO extends GenericDAO<Long, NotaFiscalModel>{

    public NotaFiscalModel consultarNotaFiscalPelaChave(String chave){
        return getEntityManager()
                .createNamedQuery("NotaFiscalModel.consultarNotaFiscalPorNumero", NotaFiscalModel.class)
                .setParameter("chave", chave)
                .getSingleResult();
    }
}
