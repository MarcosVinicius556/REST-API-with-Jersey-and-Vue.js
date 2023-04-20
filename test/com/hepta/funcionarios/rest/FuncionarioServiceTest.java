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
class FuncionarioServiceTest {

	
	private static FuncionarioService funcionarioService;
	private static SetorService setorService;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		funcionarioService = new FuncionarioService();
		setorService = new SetorService();
	}
	
	@AfterAll
	static void setAfterTests() {
		//Removendo tudo que foi inserido
		boolean removeAll = true; //Passar para true quando quiser que remova todas os novos objetos após o teste
		
		if(removeAll) {
			EntityManager em = HibernateUtil.getEntityManager();
			try {
				em.getTransaction().begin();
				em.createQuery("DELETE FROM Funcionario").executeUpdate();
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
	void deveAdicionarUmFuncionario() {
		Setor setor = new Setor("Teste 1");
		setorService.setorCreate(setor);
		
		Funcionario funcionario = new Funcionario("Teste", setor, 2000.0, "teste@gmail.com", 21);

		List<Funcionario> lstBefore = (List<Funcionario>) funcionarioService.funcionarioRead().getEntity();
		int quantInicial = lstBefore.size();
		
		funcionarioService.funcionarioCreate(funcionario);
		
		List<Funcionario> lstAfter = (List<Funcionario>) funcionarioService.funcionarioRead().getEntity();
		int quantFinal = lstAfter.size();
		
		Assertions.assertEquals(quantInicial + 1, quantFinal, "Inseriu mais de um, ou nenhum");
	}
	
	@Test
	void deveRemoverUmFuncionario() {
		Setor setor = new Setor("Teste 2");
		setorService.setorCreate(setor);
		
		Funcionario funcionario = new Funcionario("Teste", setor, 2000.0, "teste@gmail.com", 21);
		funcionarioService.funcionarioCreate(funcionario);
		
		List<Funcionario> lstBefore = (List<Funcionario>) funcionarioService.funcionarioRead().getEntity();
		int quantInicial = lstBefore.size();
		
		funcionarioService.FuncionarioDelete(funcionario.getId());
		
		List<Funcionario> lstAfter = (List<Funcionario>) funcionarioService.funcionarioRead().getEntity();
		int quantFinal = lstAfter.size();
		
		Assertions.assertEquals(quantInicial - 1, quantFinal, "Removeu mais de um, ou nenhum");		
	}
	
	@Test
	void deveRetornarUmFuncionario() {
		Setor setor = new Setor("Teste 3");
		setorService.setorCreate(setor);
		
		Funcionario f1 = new Funcionario("Teste", setor, 2000.0, "teste@gmail.com", 21);
		funcionarioService.funcionarioCreate(f1);
		
		Funcionario fTest = (Funcionario) funcionarioService.funcionarioFindById(f1.getId()).getEntity();
		
		Assertions.assertEquals(f1, fTest, "O funcionário retornado não foi o mesmo inserido");
	}
	
	
	@Test
	void deveRetornarUmaLista() {
		Setor setor = new Setor("Teste 4");
		setorService.setorCreate(setor);
		
		Funcionario f1 = new Funcionario("Teste 1", setor, 1000.0, "teste_1@gmail.com", 21); 
		Funcionario f2 = new Funcionario("Teste 2", setor, 2000.0, "teste_2@gmail.com", 22);
		Funcionario f3 = new Funcionario("Teste 3", setor, 3000.0, "teste_3@gmail.com", 23);
		Funcionario f4 = new Funcionario("Teste 4", setor, 4000.0, "teste_4@gmail.com", 24);
		Funcionario f5 = new Funcionario("Teste 5", setor, 5000.0, "teste_5@gmail.com", 25);
		
		List<Funcionario> lstBefore = (List<Funcionario>) funcionarioService.funcionarioRead().getEntity();
		
		funcionarioService.funcionarioCreate(f1);
		lstBefore.add(f1);
		
		funcionarioService.funcionarioCreate(f2);
		lstBefore.add(f2);
		
		funcionarioService.funcionarioCreate(f3);
		lstBefore.add(f3);
		
		funcionarioService.funcionarioCreate(f4);
		lstBefore.add(f4);
		
		funcionarioService.funcionarioCreate(f5);
		lstBefore.add(f5);
		
		List<Funcionario> lstAfter = (List<Funcionario>) funcionarioService.funcionarioRead().getEntity();

		Assertions.assertEquals(lstAfter.size(), lstBefore.size(), "A lista retornada difere da esperada");
		
	}
	
	@Test
	void deveRetornarCodigoDeNaoEncontradoAoTentarDeletar() {
		Assertions.assertEquals(funcionarioService.FuncionarioDelete(10000).getStatus(), Status.NOT_FOUND.getStatusCode());
	}
	
	@Test
	void deveRetornarNotFoundAoBuscarAlgoQueNaoExiste() {
		Assertions.assertEquals(funcionarioService.funcionarioFindById(93018).getStatus(), Status.NOT_FOUND.getStatusCode());
	}
	
	@Test
	void deveRetornarNotFoundAoAtualizarAlgoQueNaoExiste() {
		Funcionario f1 = new Funcionario("Teste 1", null, 1000.0, "teste_1@gmail.com", 21);
		f1.setId(9438234);
		Assertions.assertEquals(funcionarioService.funcionarioUpdate(f1).getStatus(), Status.NOT_FOUND.getStatusCode());
	}
	
	/**
	 * Testes unitários
	 */
	@Test
	void testFuncionarioCreate() {
		Funcionario f1 = new Funcionario("Teste Unitario", null, 2000.0, "teste@gmail.com", 21);
		boolean inserido = false;
		try {
			funcionarioService.funcionarioCreate(f1);
			inserido = true;
		} catch (Exception e) {
			inserido = false;
		}
		Assertions.assertEquals(inserido, true, "O funcionário não foi inserido");
	}
	
	@Test
	void testFuncionarioRead() {
		Funcionario f1 = new Funcionario("Teste Unitario", null, 2000.0, "teste@gmail.com", 21);
		funcionarioService.funcionarioCreate(f1);
		List<Funcionario> lst = (List<Funcionario>) funcionarioService.funcionarioRead().getEntity();
		
		Assertions.assertEquals(lst != null && lst.size() > 0, true, "Não foi retornado nenhum funcionário");
	}
	
	
	@Test
	void testFuncionarioUpdate() {
		Funcionario f1 = new Funcionario("Teste Unitario", null, 2000.0, "teste@gmail.com", 21);
		funcionarioService.funcionarioCreate(f1);
		
		f1.setNome("Teste Unitário Update");
		funcionarioService.funcionarioUpdate(f1);
		
		f1 = (Funcionario) funcionarioService.funcionarioFindById(f1.getId()).getEntity();
		Assertions.assertEquals(f1.getNome(), "Teste Unitário Update", "O funcionário não foi atualizado");
	}
	
	@Test
	void testFuncionarioDelete() {
		Funcionario f1 = new Funcionario("Teste Unitario", null, 2000.0, "teste@gmail.com", 21);
		funcionarioService.funcionarioCreate(f1);
		boolean error = false;
		try {
			funcionarioService.FuncionarioDelete(f1.getId());
		} catch (Exception e) {
			error = true;
		}
		
		Assertions.assertEquals(error, false, "O funcionário não foi removido");
	}

}
