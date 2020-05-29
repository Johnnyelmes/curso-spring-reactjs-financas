package com.john.minhasfinancas.service;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.john.minhasfinancas.exception.RegraNegocioException;
import com.john.minhasfinancas.model.entity.Lancamento;
import com.john.minhasfinancas.model.enums.StatusLancamento;
import com.john.minhasfinancas.model.repository.LancamentoRepository;
import com.john.minhasfinancas.model.repository.LancamentoRepositoryTest;
import com.john.minhasfinancas.service.impl.LancamentoServiceImp;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {

	@SpyBean
	LancamentoServiceImp service;

	@MockBean
	LancamentoRepository repository;

	@Test
	public void deveSalvarUmLancamento() {
		// cenario
		Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarlancamento();
		Mockito.doNothing().when(service).validar(lancamentoASalvar);

		Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarlancamento();
		lancamentoSalvo.setId(1l);
		lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);
		Mockito.when(repository.save(lancamentoASalvar)).thenReturn(lancamentoSalvo);

		// execucao

		Lancamento lancamento = service.salvar(lancamentoASalvar);

		// verificacao
		Assertions.assertEquals(lancamento.getId(), lancamentoSalvo.getId());
		Assertions.assertEquals(lancamento.getStatus(), StatusLancamento.PENDENTE);
	}

	@Test
	public void naoDeveSalvarUmLancamentoQuandoHouverUmErroDeValidacao() {
		// cenario
		Lancamento lancamento = LancamentoRepositoryTest.criarlancamento();
		Mockito.doThrow(RegraNegocioException.class).when(service).validar(lancamento);

		// acao e verificacao
		Assertions.assertThrows(RegraNegocioException.class, () -> service.salvar(lancamento));
		Mockito.verify(repository, Mockito.never()).save(lancamento);
	}

	@Test
	public void deveAtualizarUmLancamento() {
		// cenario
		Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarlancamento();
		lancamentoSalvo.setId(1l);
		lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);

		Mockito.doNothing().when(service).validar(lancamentoSalvo);

		Mockito.when(repository.save(lancamentoSalvo)).thenReturn(lancamentoSalvo);

		// execucao

		service.atualizar(lancamentoSalvo);

		// verificacao

		Mockito.verify(repository, Mockito.times(1)).save(lancamentoSalvo);

	}
	
	@Test
	public void deveLancarErroAoTentarAtualizarUmLancamentoQueAindaNaoFoiSalvo() {
		// cenario
		Lancamento lancamento = LancamentoRepositoryTest.criarlancamento();

		// acao e verificacao
		Assertions.assertThrows(NullPointerException.class, () -> service.atualizar(lancamento));
		Mockito.verify(repository, Mockito.never()).save(lancamento);
	}

	@Test
	public void deveDeletarUmLancamento() {
		//cenario
		Lancamento lancamento = LancamentoRepositoryTest.criarlancamento();
		lancamento.setId(1l);
		
		//acao
		service.deletar(lancamento);
		
		//verificacao
		Mockito.verify(repository).delete(lancamento);
		
	}
	
	@Test
	public void deveLancarErroAoTentarDeletarUmLancamentoQueAindaNaoFoiSalvo() {
		//cenario
		Lancamento lancamento = LancamentoRepositoryTest.criarlancamento();
		
		//acao
		Assertions.assertThrows(NullPointerException.class, () -> service.deletar(lancamento));
		
		//verificacao
		Mockito.verify(repository, Mockito.never()).delete(lancamento);
	}
	
	@Test
	public void deveFiltrarLancamentos() {
		//cenario
		Lancamento lancamento = LancamentoRepositoryTest.criarlancamento();
		lancamento.setId(1l);
		
		List<Lancamento> lista = new ArrayList<Lancamento>();
		lista.add(lancamento);	
		
		Mockito.when(repository.findAll(Mockito.any(Example.class))).thenReturn(lista);
		
		//acao
		List<Lancamento> resultado = service.buscar(lancamento);
		
		//verificacao
		Assertions.assertNotNull(resultado);
	}
	
	@Test
	public void deveAtualizarOStatusDeUmLancamento() {
		//cenario
		Lancamento lancamento = LancamentoRepositoryTest.criarlancamento();
		lancamento.setId(1l);
		lancamento.setStatus(StatusLancamento.PENDENTE);
		
		StatusLancamento novoStatus = StatusLancamento.EFETIVADO;
		Mockito.doReturn(lancamento).when(service).atualizar(lancamento);
		
		// acao
		service.atuaizarStatus(lancamento, novoStatus);
		
		//verificacao
		Assertions.assertEquals(lancamento.getStatus(), novoStatus);
		Mockito.verify(service).atualizar(lancamento);
	}
	
	
}
