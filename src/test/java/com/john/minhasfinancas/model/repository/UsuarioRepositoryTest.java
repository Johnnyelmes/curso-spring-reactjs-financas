package com.john.minhasfinancas.model.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.john.minhasfinancas.model.entity.Usuario;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repository;

	@Test
	public void deveVerificarAExixtenciaDeUmEmail() {
		//cenario
		Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").build();
		repository.save(usuario);
		
		//ação/execucao
		boolean result = repository.existsByEmail("usuario@email.com");
		
		//verificacao
		Assertions.assertTrue(result);
	}
	
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
		//cenario
		repository.deleteAll();
		
		//ação
		boolean result = repository.existsByEmail("usuario@email.com");
		
		//verificacao
		Assertions.assertFalse(result);
		
	}
}
