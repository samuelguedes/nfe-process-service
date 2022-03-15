package br.gov.daos;


import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.gov.models.NotaFiscalModel;

@ApplicationScoped
public class NotaFiscalDAO extends GenericDAO<Long, NotaFiscalModel>{

    public NotaFiscalModel consultarNotaFiscalPelaChave(String chave) throws NoResultException {
        StringBuilder jpql = new StringBuilder("select n from NotaFiscalModel n LEFT JOIN n.duplicatas where ");
        jpql.append("n.chave = :chave");
       
        TypedQuery<NotaFiscalModel> query = getEntityManager().createQuery(jpql.toString(), NotaFiscalModel.class).setParameter("chave", chave);

        return query.getSingleResult();
    }

    public List<NotaFiscalModel> listarNotaFiscal(){
        StringBuilder jpql = new StringBuilder("select n from NotaFiscalModel n LEFT JOIN n.duplicatas ");
       
        TypedQuery<NotaFiscalModel> query = getEntityManager().createQuery(jpql.toString(), NotaFiscalModel.class);
        List<NotaFiscalModel> notasFiacais = (List<NotaFiscalModel>) query.getResultList();

        return notasFiacais;
    }

    
}
