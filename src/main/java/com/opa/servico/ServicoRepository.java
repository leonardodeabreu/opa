package com.opa.servico;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServicoRepository extends JpaRepository<Servico, String> {

	@Query("FROM Servico s order by s.dataCadastro DESC")
	List<Servico> getAllServicos(Pageable paginacao);
}
