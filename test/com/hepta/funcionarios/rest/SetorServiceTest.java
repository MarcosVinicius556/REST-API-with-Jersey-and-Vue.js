package com.hepta.funcionarios.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.hepta.funcionarios.entity.Funcionario;
import com.hepta.funcionarios.entity.Setor;
import com.hepta.funcionarios.persistence.singleton.HibernateUtil;

@SuppressWarnings("unchecked")
class SetorServiceTest {

	private static SetorService setorService;
	private static FuncionarioService funcionarioService;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		setorService = new SetorService();
		funcionarioService = new FuncionarioService();
	}
	
	@AfterAll
	static void setAfterTests() {
		//Removendo tudo que foi inserido
		boolean removeAll = false; //Passar para true quando quiser que remova todas os novos objetos após o teste
		
		if(removeAll) {
			EntityManager em = HibernateUtil.getEntityManager();
			try {
				em.getTransaction().begin();
				em.createQuery("DELETE FROM Setor").executeUpdate();
				em.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				HibernateUtil.shutdown();
			}
		}
	}
	
	/**
	 * Testes de integração
	 */
	
	@Test
	void deveAdicionarUmSetor() {
		Setor setor = new Setor("Teste 1");
		
		List<Setor> lstBefore = (List<Setor>) setorService.setorRead().getEntity();
		int quantInicial = lstBefore.size();
		
		setorService.setorCreate(setor);
		
		List<Setor> lstAfter = (List<Setor>) setorService.setorRead().getEntity();
		int quantFinal = lstAfter.size();
		
		Assertions.assertEquals(quantInicial + 1, quantFinal, "Inseriu mais de um, ou nenhum");
	}
	
	@Test
	void deveRemoverUmSetor() {
		Setor setor = new Setor("Teste 2");
		setorService.setorCreate(setor);
		
		List<Setor> lstBefore = (List<Setor>) setorService.setorRead().getEntity();
		int quantInicial = lstBefore.size();
		
		
		setorService.setorDelete(setor.getId());
		
		List<Setor> lstAfter = (List<Setor>) setorService.setorRead().getEntity();
		int quantFinal = lstAfter.size();
		
		Assertions.assertEquals(quantInicial - 1, quantFinal, "Removeu mais de um, ou nenhum");		
	}
	
	@Test
	void deveRetornarUmSetor() {
		Setor setor = new Setor("Teste 3");
		setorService.setorCreate(setor);
		
		Setor setorTest = (Setor) setorService.setorFindById(setor.getId()).getEntity();
		
		Assertions.assertEquals(setor, setorTest, "O setor retornado não foi o mesmo inserido");
	}
	
	
	@Test
	void deveRetornarUmaListaDeSetor() {
		Setor setor_1 = new Setor("Teste 1");
		Setor setor_2 = new Setor("Teste 2");
		Setor setor_3 = new Setor("Teste 3");
		Setor setor_4 = new Setor("Teste 4");
		Setor setor_5 = new Setor("Teste 5");
		
		List<Setor> lstBefore = (List<Setor>) setorService.setorRead().getEntity();

		setorService.setorCreate(setor_1);
		setorService.setorCreate(setor_2);
		setorService.setorCreate(setor_3);
		setorService.setorCreate(setor_4);
		setorService.setorCreate(setor_5);
		
		lstBefore.add(setor_1);
		lstBefore.add(setor_2);
		lstBefore.add(setor_3);
		lstBefore.add(setor_4);
		lstBefore.add(setor_5);
		
		List<Setor> lstAfter = (List<Setor>) setorService.setorRead().getEntity();

		Assertions.assertEquals(lstAfter.size(), lstBefore.size(), "A lista retornada difere da esperada");
		
	}
	
	@Test
	void deveRetornarCodigoDeNaoEncontradoAoTentarDeletar() {
		Assertions.assertEquals(setorService.setorDelete(10000).getStatus(), Status.NOT_FOUND.getStatusCode(), "Não obteve o retorno esperado");
	}
	
	@Test
	void deveRetornarNotFoundAoBuscarAlgoQueNaoExiste() {
		Assertions.assertEquals(setorService.setorFindById(93018).getStatus(), Status.NOT_FOUND.getStatusCode(), "Não obteve o retorno esperado");
	}
	
	@Test
	void deveRetornarNotFoundAoAtualizarAlgoQueNaoExiste() {
		Setor setor = new Setor("Teste 1");
		setor.setId(9121212);
		Assertions.assertEquals(setorService.setorUpdate(setor).getStatus(), Status.NOT_FOUND.getStatusCode(), "Não obteve o retorno esperado");
	}
	
	@Test
	void deveRetornarExcecaoAoDeletarSetorComFuncVinculado() {
		Setor setor = new Setor("Teste 1");
		Funcionario f1 = new Funcionario("Teste 1", setor, 1000.0, "teste_1@gmail.com", 21);
		
		setorService.setorCreate(setor);
		funcionarioService.funcionarioCreate(f1);
		
		Assertions.assertEquals(setorService.setorDelete(setor.getId()).getStatus(), Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Não obteve o retorno esperado");
	}
	 
	/**
	 * Testes unitários
	 */
	@Test
	void testSetorCreate() {
		Setor setor = new Setor("Teste");
		boolean inserido = false;
		try {
			setorService.setorCreate(setor);
			inserido = true;
		} catch (Exception e) {
			inserido = false;
		}
		Assertions.assertEquals(inserido, true, "O setor não foi inserido");
	}
	
	@Test
	void testSetorRead() {
		Setor setor = new Setor("Teste");
		setorService.setorCreate(setor);
		List<Setor> lst = (List<Setor>) setorService.setorRead().getEntity();
		
		Assertions.assertEquals(lst != null && lst.size() > 0, true, "Não foi retornado nenhum setor");
	}
	
	
	@Test
	void testSetorUpdate() {
		Setor setor = new Setor("Teste");
		setorService.setorCreate(setor);
		
		setor.setNome("Teste Unitário Update");
		setorService.setorUpdate(setor);
		
		setor = (Setor) setorService.setorFindById(setor.getId()).getEntity();
		Assertions.assertEquals(setor.getNome(), "Teste Unitário Update", "O setor não foi atualizado");
	}
	
	@Test
	void testSetorDelete() {
		Setor setor = new Setor("Teste");
		setorService.setorCreate(setor);
		boolean error = false;
		try {
			setorService.setorDelete(setor.getId());
		} catch (Exception e) {
			error = true;
		}
		
		Assertions.assertEquals(error, false, "O setor não foi removido");
	}

}
