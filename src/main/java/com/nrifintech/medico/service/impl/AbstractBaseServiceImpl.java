package com.nrifintech.medico.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nrifintech.medico.entity.AbstractBaseEntity;
import com.nrifintech.medico.repository.IAbstractBaseRepository;
import com.nrifintech.medico.service.IAbstractBaseService;

@Service
@Transactional
public abstract class AbstractBaseServiceImpl<T extends AbstractBaseEntity, ID extends Serializable>
        implements IAbstractBaseService<T, ID>{
    
    private IAbstractBaseRepository<T, ID> abstractBaseRepository;
    
    @Autowired
    public AbstractBaseServiceImpl(IAbstractBaseRepository<T, ID> abstractBaseRepository) {
        this.abstractBaseRepository = abstractBaseRepository;
    }
    
    @Override
    public T save(T entity) {
        return (T) abstractBaseRepository.save(entity);
    }

    @Override
    public List<T> findAll() {
        return abstractBaseRepository.findAll();
    }

    @Override
    public Optional<T> findById(ID entityId) {
        return abstractBaseRepository.findById(entityId);
    }

    @Override
    public T update(T entity) {
        return (T) abstractBaseRepository.save(entity);
    }

    @Override
    public T updateById(T entity, ID entityId) {
        Optional<T> optional = abstractBaseRepository.findById(entityId);
        if(optional.isPresent()){
            return (T) abstractBaseRepository.save(entity);
        }else{
            return null;
        }
    }

    @Override
    public void delete(T entity) {
        abstractBaseRepository.delete(entity);
    }

    @Override
    public void deleteById(ID entityId) {
        abstractBaseRepository.deleteById(entityId);
    }
    
    @Override
    public T getCurrentEntity(String username) {
    	return abstractBaseRepository.findByUsername(username);
    }
}