package com.nrifintech.medico.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.nrifintech.medico.entity.AbstractBaseEntity;

public interface IAbstractBaseService<T extends AbstractBaseEntity, ID extends Serializable>{
    public abstract T save(T entity);
    public abstract List<T> findAll(); // you might want a generic Collection if u prefer

    public abstract Optional<T> findById(ID entityId);
    public abstract T update(T entity);
    public abstract T updateById(T entity, ID entityId);   
    public abstract void delete(T entity);
    public abstract void deleteById(ID entityId);
    // other methods u might need to be generic
    public abstract T getCurrentEntity(String username);
    
}