package com.john.minhasfinancas.model.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.john.minhasfinancas.model.entity.Usuario;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;

	@Test
	public void deveVerificarAExixtenciaDeUmEmail() {
		//cenario
		Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").build();
		entityManager.persist(usuario);
		
		//ação/execucao
		boolean result = repository.existsByEmail("usuario@email.com");
		
		//verificacao
		Assertions.assertTrue(result);
	}
	
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
		//cenario
		
		//ação
		boolean result = repository.existsByEmail("usuario@email.com");
		
		//verificacao
		Assertions.assertFalse(result);
		
	}
}
