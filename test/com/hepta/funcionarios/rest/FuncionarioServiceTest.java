package com.hepta.funcionarios.rest;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.hepta.funcionarios.entity.Funcionario;
import com.hepta.funcionarios.entity.Setor;

class FuncionarioServiceTest {

	
	private static FuncionarioService funcionarioService;
	private static SetorService setorService;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		funcionarioService = new FuncionarioService();
		setorService = new SetorService();
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
		
		funcionarioService.FuncionarioDelete(funcionario.getId());
		setorService.setorDelete(setor.getId());
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
		
		funcionarioService.FuncionarioDelete(funcionario.getId());
		setorService.setorDelete(setor.getId());
	}
	
	@Test
	void deveRetornarUmFuncionario() {
		Setor setor = new Setor("Teste 3");
		setorService.setorCreate(setor);
		
		Funcionario f1 = new Funcionario("Teste", setor, 2000.0, "teste@gmail.com", 21);
		funcionarioService.funcionarioCreate(f1);
		
		Funcionario fTest = (Funcionario) funcionarioService.funcionarioFindById(f1.getId()).getEntity();
		
		Assertions.assertEquals(f1, fTest, "O funcionário retornado não foi o mesmo inserido");
		
		funcionarioService.FuncionarioDelete(f1.getId());
		setorService.setorDelete(setor.getId());
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
		
		funcionarioService.FuncionarioDelete(f1.getId());
		funcionarioService.FuncionarioDelete(f2.getId());
		funcionarioService.FuncionarioDelete(f3.getId());
		funcionarioService.FuncionarioDelete(f4.getId());
		funcionarioService.FuncionarioDelete(f5.getId());
		setorService.setorDelete(setor.getId());
	}
	
	@Test
	void deveRetornarCodigoDeNaoEncontradoAoTentarDeletar() {
		Assertions.assertEquals(funcionarioService.FuncionarioDelete(10000).getStatus(), Status.NOT_FOUND);
	}
	
	@Test
	void deveRetornarNotFoundAoBuscarAlgoQueNaoExiste() {
		Assertions.assertEquals(funcionarioService.funcionarioFindById(93018).getStatus(), Status.NOT_FOUND);
	}
	
	@Test
	void deveRetornarNotFoundAoAtualizarAlgoQueNaoExiste() {
//		Funcionario 
//		Assertions.assertEquals(funcionarioService.funcionarioFindById(93018).getStatus(), Status.NOT_FOUND);
	}
	
	
	/**
	 * Testes unitários
	 */
	@Test
	void testFuncionarioCreate() {
		Setor setor = new Setor("Teste Unitario Inserção");
		Funcionario f1 = new Funcionario("Teste Unitario", setor, 2000.0, "teste@gmail.com", 21);
		boolean inserido = false;
		try {
			funcionarioService.funcionarioCreate(f1);
			inserido = true;
		} catch (Exception e) {
			inserido = false;
		}
		Assertions.assertEquals(inserido, true, "O funcionário não foi inserido");
		
		funcionarioService.FuncionarioDelete(f1.getId());
		setorService.setorDelete(setor.getId());
	}
	
	@Test
	void testFuncionarioRead() {
		Funcionario f1 = new Funcionario("Teste Unitario", null, 2000.0, "teste@gmail.com", 21);
		funcionarioService.funcionarioCreate(f1);
		List<Funcionario> lst = (List<Funcionario>) funcionarioService.funcionarioRead().getEntity();
		
		Assertions.assertEquals(lst != null && lst.size() > 0, true, "Não foi retornado nenhum funcionário");
		
		funcionarioService.FuncionarioDelete(f1.getId());
	}
	
	
	@Test
	void testFuncionarioUpdate() {
		Funcionario f1 = new Funcionario("Teste Unitario", null, 2000.0, "teste@gmail.com", 21);
		funcionarioService.funcionarioCreate(f1);
		
		f1.setNome("Teste Unitário Update");
		funcionarioService.funcionarioUpdate(f1);
		
		f1 = (Funcionario) funcionarioService.funcionarioFindById(f1.getId()).getEntity();
		Assertions.assertEquals(f1.getNome(), "Teste Unitário Update", "O funcionário não foi atualizado");
		
		funcionarioService.FuncionarioDelete(f1.getId());
	}
	
	@Test
	void testFuncionarioDelete() {
		Funcionario f1 = new Funcionario("Teste Unitario", null, 2000.0, "teste@gmail.com", 21);
		funcionarioService.funcionarioCreate(f1);
		
		funcionarioService.FuncionarioDelete(f1.getId());
		
		f1 = (Funcionario) funcionarioService.funcionarioFindById(f1.getId()).getEntity();
		Assertions.assertEquals(f1, null, "O funcionário não foi removido");
	}

}
