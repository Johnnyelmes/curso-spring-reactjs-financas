package com.john.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.john.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
