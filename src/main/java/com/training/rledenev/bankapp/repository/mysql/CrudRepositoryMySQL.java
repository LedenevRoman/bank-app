package com.training.rledenev.bankapp.repository.mysql;

import com.training.rledenev.bankapp.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public abstract class CrudRepositoryMySQL<T> implements CrudRepository<T> {
    private Class<T> clazz;

    @PersistenceContext
    private EntityManager entityManager;

    public void setClazz(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    @Override
    public Optional<T> findOne(long id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("from " + clazz.getName(), clazz)
                .getResultList();
    }

    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(long entityId) {
        Optional<T> entity = findOne(entityId);
        entity.ifPresent(this::delete);
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
