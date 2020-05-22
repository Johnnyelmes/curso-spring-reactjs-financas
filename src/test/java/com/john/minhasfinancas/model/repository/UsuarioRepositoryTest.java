package com.john.minhasfinancas.model.repository;

import java.util.Optional;

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
	public void deveVerificarAExistenciaDeUmEmail() {
		// cenario
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);

		// ação/execucao
		boolean result = repository.existsByEmail("usuario@email.com");

		// verificacao
		Assertions.assertTrue(result);
	}

	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
		// ação
		boolean result = repository.existsByEmail("usuario@email.com");

		// verificacao
		Assertions.assertFalse(result);

	}

	@Test
	public void devePersistirUmUsuarioNaBasedeDados() {
		// cenario
		Usuario usuario = criarUsuario();
		// ação
		Usuario usuarioSalvo = repository.save(usuario);
		// verificacao
		Assertions.assertNotNull(usuarioSalvo.getId());
	}

	@Test
	public void deveBuscarUmUsuarioPorEmail() {
		// cenario
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		// Verificacao
		
		Optional<Usuario> result =repository.findByEmail("usuario@email.com");
		Assertions.assertTrue(result.isPresent());
	}
	
	@Test
	public void deveRetornarVazioAoBuscarUmUsuarioPorEmailQuandoNaoExisteNaBase() {
		// Verificacao
		Optional<Usuario> result =repository.findByEmail("usuario@email.com");
		Assertions.assertFalse(result.isPresent());
	}
	
	public static Usuario criarUsuario() {
		return Usuario.builder().nome("usuario").senha("senha").email("usuario@email.com").build(); 
	}
}
