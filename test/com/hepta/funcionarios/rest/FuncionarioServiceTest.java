package com.hepta.funcionarios.rest;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.hepta.funcionarios.dto.FuncionarioDTO;
import com.hepta.funcionarios.entity.Funcionario;
import com.hepta.funcionarios.entity.Setor;
import com.hepta.funcionarios.persistence.FuncionarioDAO;
import com.hepta.funcionarios.persistence.SetorDAO;
import com.hepta.funcionarios.persistence.factory.DAOFactory;

@SuppressWarnings("unchecked")
class FuncionarioServiceTest {

	
	private static FuncionarioService funcionarioService;
	private static FuncionarioDAO funcionarioDAO;
	private static SetorDAO setorDAO;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		funcionarioService = new FuncionarioService();
		funcionarioDAO = DAOFactory.createFuncionarioDAO();
		setorDAO = DAOFactory.createSetorDAO();
	}
	
	@AfterAll
	static void setAfterTests() {
		
	}
	
	/**
	 * Testes de integração
	 */
	
	@Test
	void deveAdicionarUmFuncionario() {
		Setor setor = new Setor("Dev 1");
		Funcionario funcionario = new Funcionario("MarcosVinicius Angeli", setor, 3000.0, "marcos@gmail.com", 21);
		List<FuncionarioDTO> lstBefore = null;
		List<FuncionarioDTO> lstAfter = null;
		int quantInicial = 0;
		int quantFinal = 0;
		boolean error = false;
		try {
			setorDAO.save(setor);
			lstBefore = (List<FuncionarioDTO>) funcionarioService.funcionarioRead().getEntity();
			quantInicial = lstBefore.size();
			
			funcionarioService.funcionarioCreate(new FuncionarioDTO(funcionario));

			lstAfter = (List<FuncionarioDTO>) funcionarioService.funcionarioRead().getEntity();
			
			quantFinal = lstAfter.size();
		} catch (Exception e) {
			e.printStackTrace();
			error = true;
		}
		
		Assertions.assertEquals(quantInicial + 1, quantFinal, "Inseriu mais de um, ou nenhum");
		Assertions.assertEquals(error, false, "Ocorreu um erro na inserção");
	}
	
	@Test
	void deveRemoverUmFuncionario() {
		Setor setor = new Setor("Qualidade 2");
		Funcionario funcionario = new Funcionario("Ellen", setor, 4000.0, "ellen@gmail.com", 21);
		List<FuncionarioDTO> lstBefore = null;
		List<FuncionarioDTO> lstAfter = null;
		int quantInicial = 0;
		int quantFinal = 0;
		boolean error = false;
		try {
			setorDAO.save(setor);
			
			funcionarioDAO.save(funcionario);
			
			lstBefore = (List<FuncionarioDTO>) funcionarioService.funcionarioRead().getEntity();
			quantInicial = lstBefore.size();
			
			funcionarioService.FuncionarioDelete(funcionario.getId());
			
			lstAfter = (List<FuncionarioDTO>) funcionarioService.funcionarioRead().getEntity();
			quantFinal = lstAfter.size();
			
		} catch (Exception e) {
			e.printStackTrace();
			error = true;
		}
		
		
		Assertions.assertEquals(quantInicial - 1, quantFinal, "Removeu mais de um, ou nenhum");
		Assertions.assertEquals(error, false, "Erro na exclusão");
	}
	
	@Test
	void deveRetornarUmFuncionario() {
		Setor setor = new Setor("Adm 3");
		Funcionario f1 = new Funcionario("Rosa Maria", setor, 6000.0, "rosa@gmail.com", 21);
		boolean error = false;
		try {
			setorDAO.save(setor);
			funcionarioDAO.save(f1);
		} catch (Exception e) {
			e.printStackTrace();
			error = true;
		}
		
		FuncionarioDTO fTest = (FuncionarioDTO) funcionarioService.funcionarioFindById(f1.getId()).getEntity();
		
		Assertions.assertEquals(f1, fTest.fromDTO(), "O funcionário retornado não foi o mesmo inserido");
		Assertions.assertEquals(error, false, "Ocorreu um erro na busca");
	}
	
	
	@Test
	void deveRetornarUmaLista() {
		Setor setor = new Setor("Teste 4");
		Funcionario f1 = new Funcionario("Marco Antonio", setor, 4500.0, "marco_antonio@gmail.com", 21); 
		Funcionario f2 = new Funcionario("Marcos Antonio Angeli", setor, 2000.0, "marcos_antonio@gmail.com", 22);
		Funcionario f3 = new Funcionario("Lucas", setor, 3500.0, "lucas@gmail.com", 23);
		Funcionario f4 = new Funcionario("Camilla", setor, 4000.0, "camilla@gmail.com", 24);
		Funcionario f5 = new Funcionario("João", setor, 1800.0, "joao@gmail.com", 25);
		List<FuncionarioDTO> lstBefore = null;
		List<FuncionarioDTO> lstAfter = null;
		boolean error = false;
		try {
			setorDAO.save(setor);
			
			lstBefore = (List<FuncionarioDTO>) funcionarioService.funcionarioRead().getEntity();
			
			funcionarioService.funcionarioCreate(new FuncionarioDTO(f1));
			lstBefore.add(new FuncionarioDTO(f1));
			
			funcionarioService.funcionarioCreate(new FuncionarioDTO(f2));
			lstBefore.add(new FuncionarioDTO(f2));
			
			funcionarioService.funcionarioCreate(new FuncionarioDTO(f3));
			lstBefore.add(new FuncionarioDTO(f3));
			
			funcionarioService.funcionarioCreate(new FuncionarioDTO(f4));
			lstBefore.add(new FuncionarioDTO(f4));
			
			funcionarioService.funcionarioCreate(new FuncionarioDTO(f5));
			lstBefore.add(new FuncionarioDTO(f5));
			
		} catch (Exception e) {
			e.printStackTrace();
			error = true;
		}
		
		lstAfter = (List<FuncionarioDTO>) funcionarioService.funcionarioRead().getEntity();

		Assertions.assertEquals(lstAfter.size(), lstBefore.size(), "A lista retornada difere da esperada");
		Assertions.assertEquals(error, false, "Erro na inserção do funcionário");
		
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
		Assertions.assertEquals(funcionarioService.funcionarioUpdate(new FuncionarioDTO(f1)).getStatus(), Status.NOT_FOUND.getStatusCode());
	}
	
	/**
	 * Testes unitários
	 */
	@Test
	void testFuncionarioCreate() {
		Funcionario f1 = new Funcionario("Pamela", null, 2000.0, "teste@gmail.com", 21);
		boolean inserido = false;
		try {
			funcionarioService.funcionarioCreate(new FuncionarioDTO(f1));
			inserido = true;
		} catch (Exception e) {
			inserido = false;
		}
		Assertions.assertEquals(inserido, true, "O funcionário não foi inserido");
	}
	
	@Test
	void testFuncionarioRead() {
		Funcionario f1 = new Funcionario("Roberta Silva", null, 2000.0, "teste@gmail.com", 21);
		funcionarioService.funcionarioCreate(new FuncionarioDTO(f1));
		List<FuncionarioDTO> lst = (List<FuncionarioDTO>) funcionarioService.funcionarioRead().getEntity();
		
		Assertions.assertEquals(lst != null && lst.size() > 0, true, "Não foi retornado nenhum funcionário");
	}
	
	
	@Test
	void testFuncionarioUpdate() {
		Funcionario f1 = new Funcionario("Marcia Cosssta", null, 2000.0, "teste@gmail.com", 21);
		FuncionarioDTO f1_DTO = null;
		boolean error = false;
		try {
			funcionarioDAO.save(f1);
			
			f1_DTO = new FuncionarioDTO(f1);
			
			f1_DTO.setNome("Marcia Costa");
			funcionarioService.funcionarioUpdate(f1_DTO);
			
		} catch (Exception e) {
			e.printStackTrace();
			error = true;
		}
		
		f1_DTO = (FuncionarioDTO) funcionarioService.funcionarioFindById(f1_DTO.getId()).getEntity();
		Assertions.assertEquals(f1_DTO.getNome(), "Marcia Costa", "O funcionário não foi atualizado");
		Assertions.assertEquals(error, false, "Erro na atualização");
	}
	
	@Test
	void testFuncionarioDelete() {
		Funcionario f1 = new Funcionario("Teste Unitario", null, 2000.0, "teste@gmail.com", 21);
		boolean error = false;
		try {
			funcionarioDAO.save(f1);
			funcionarioService.FuncionarioDelete(f1.getId());
		} catch (Exception e) {
			e.printStackTrace();
			error = true;
		}
		
		Assertions.assertEquals(error, false, "O funcionário não foi removido");
	}

}
