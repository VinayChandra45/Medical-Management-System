package com.nrifintech.medico.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.nrifintech.medico.entity.AbstractBaseEntity;


@NoRepositoryBean
public interface IAbstractBaseRepository<T extends AbstractBaseEntity, ID extends Serializable>
extends JpaRepository<T, ID>{
	T findByUsername(String Username);
}