package br.gov.daos;


import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.gov.models.DuplicataModel;

@ApplicationScoped
public class DuplicataDAO extends GenericDAO<Long, DuplicataModel> {

    public DuplicataModel listarDuplicatasDaNotaFiscal(Long idNotaFiscal) throws NoResultException {
        StringBuilder jpql = new StringBuilder("select d from DuplicataModel d LEFT JOIN d.notaFiscal where ");
        jpql.append("n.notaFiscal.id = :id");
       
        TypedQuery<DuplicataModel> query = getEntityManager().createQuery(jpql.toString(), DuplicataModel.class).setParameter("id", idNotaFiscal);

        return query.getSingleResult();
    }

}
