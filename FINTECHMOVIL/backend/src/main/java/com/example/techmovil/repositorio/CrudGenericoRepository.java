package com.example.techmovil.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CrudGenericoRepository<T, K> extends JpaRepository<T, K> {
}
