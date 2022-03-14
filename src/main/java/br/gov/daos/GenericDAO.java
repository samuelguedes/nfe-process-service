package br.gov.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class GenericDAO<P, E extends Serializable> {

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    private Class<E> clazz;

    @SuppressWarnings("unchecked")
    public GenericDAO() {
        Type genericSuperClass = getClass().getGenericSuperclass();

        ParameterizedType parametrizedType = null;

        while (parametrizedType == null) {
            if ((genericSuperClass instanceof ParameterizedType)) {
                parametrizedType = (ParameterizedType) genericSuperClass;
            } else {
                genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
            }
        }

        this.clazz = (Class<E>) parametrizedType.getActualTypeArguments()[1];
    }

    public List<E> listar() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<E> query = builder.createQuery(clazz);
        query.from(clazz);
        List<E> resultList = em.createQuery(query).getResultList();
        return resultList;
    }

    public E consultarPorId(P p) {
        return em.find(clazz, p);
    }

    public void inserir(E e) {
        em.persist(e);
    }

    public E atualizar(E e) {
        return em.merge(e);
    }

    public void remover(E e) {
        em.remove(e);
    }

    public EntityManager getEntityManager() {
        return em;
    }

}
