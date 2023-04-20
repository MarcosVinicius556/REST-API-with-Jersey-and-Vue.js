package com.hepta.funcionarios.rest;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.hepta.funcionarios.dto.FuncionarioDTO;
import com.hepta.funcionarios.dto.SetorDTO;
import com.hepta.funcionarios.entity.Funcionario;
import com.hepta.funcionarios.entity.Setor;
import com.hepta.funcionarios.persistence.SetorDAO;
import com.hepta.funcionarios.persistence.factory.DAOFactory;

@SuppressWarnings("unchecked")
class SetorServiceTest {

	private static SetorService setorService;
	private static FuncionarioService funcionarioService;
	private static SetorDAO setorDAO;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		setorService = new SetorService();
		funcionarioService = new FuncionarioService();
		setorDAO = DAOFactory.createSetorDAO();
	}
	
	@AfterAll
	static void setAfterTests() {
	
	}
	
	/**
	 * Testes de integração
	 */
	
	@Test
	void deveAdicionarUmSetor() {
		Setor setor = new Setor("Recursos Humanos");
		
		List<SetorDTO> lstBefore = (List<SetorDTO>) setorService.setorRead().getEntity();
		int quantInicial = lstBefore.size();
		
		setorService.setorCreate(new SetorDTO(setor));
		
		List<SetorDTO> lstAfter = (List<SetorDTO>) setorService.setorRead().getEntity();
		int quantFinal = lstAfter.size();
		
		Assertions.assertEquals(quantInicial + 1, quantFinal, "Inseriu mais de um, ou nenhum");
	}
	
	@Test
	void deveRemoverUmSetor() {
		Setor setor = new Setor("Desenvolvimentos");
		boolean error = false;
		int quantInicial = 0;
		int quantFinal = 0;
		try {
			setorDAO.save(setor);
			
			List<Setor> lstBefore = (List<Setor>) setorService.setorRead().getEntity();
			quantInicial = lstBefore.size();
			
			setorService.setorDelete(setor.getId());
			
			List<Setor> lstAfter = (List<Setor>) setorService.setorRead().getEntity();
			quantFinal = lstAfter.size();
		} catch (Exception e) {
			e.printStackTrace();
			error = true;
		};
		
		
		Assertions.assertEquals(quantInicial - 1, quantFinal, "Removeu mais de um, ou nenhum");
		Assertions.assertEquals(error, false, "Ocorreu um erro na exclusão do setor");
	}
	
	@Test
	void deveRetornarUmSetor() {
		Setor setor = new Setor("Qualidade");
		Setor setorTest = null;
		boolean error = false;
		try {
			setorDAO.save(setor);
		
			SetorDTO dto = (SetorDTO) setorService.setorFindById(setor.getId()).getEntity();
			setorTest = dto.fromDTO();
		
		} catch (Exception e) { 
			e.printStackTrace();
			error = true;
		}
		Assertions.assertEquals(setor, setorTest, "O setor retornado não foi o mesmo inserido");
		Assertions.assertEquals(error, false, "Ocorreu um erro na inserção do setor");
	}
	
	
	@Test
	void deveRetornarUmaListaDeSetor() {
		Setor setor_1 = new Setor("Adm");
		Setor setor_2 = new Setor("Segurança");
		Setor setor_3 = new Setor("Limpeza");
		Setor setor_4 = new Setor("Marketing");
		Setor setor_5 = new Setor("Análise");
		
		List<Setor> lstBefore = (List<Setor>) setorService.setorRead().getEntity();

		setorService.setorCreate(new SetorDTO(setor_1));
		setorService.setorCreate(new SetorDTO(setor_2));
		setorService.setorCreate(new SetorDTO(setor_3));
		setorService.setorCreate(new SetorDTO(setor_4));
		setorService.setorCreate(new SetorDTO(setor_5));
		
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
		Assertions.assertEquals(setorService.setorUpdate(new SetorDTO(setor)).getStatus(), Status.NOT_FOUND.getStatusCode(), "Não obteve o retorno esperado");
	}
	
	@Test
	void deveRetornarExcecaoAoDeletarSetorComFuncVinculado() {
		Setor setor = new Setor("Teste 1");
		Funcionario f1 = new Funcionario("Teste 1", setor, 1000.0, "teste_1@gmail.com", 21);
		boolean error = false;
		int responseStatus = 0;
		try {
			setorDAO.save(setor);
			funcionarioService.funcionarioCreate(new FuncionarioDTO(f1));
			
			responseStatus = setorService.setorDelete(setor.getId()).getStatus();
		} catch (Exception e) {
			e.printStackTrace();
			error = true;
		}
		
		Assertions.assertEquals(responseStatus, Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Não obteve o retorno esperado");
		Assertions.assertEquals(error, false, "Ocorreu um erro na exclusão");
	}
	 
	/**
	 * Testes unitários
	 */
	@Test
	void testSetorCreate() {
		Setor setor = new Setor("Recrutamento");
		boolean inserido = false;
		try {
			setorService.setorCreate(new SetorDTO(setor));
			inserido = true;
		} catch (Exception e) {
			inserido = false;
		}
		Assertions.assertEquals(inserido, true, "O setor não foi inserido");
	}
	
	@Test
	void testSetorRead() {
		Setor setor = new Setor("Comercial");
		setorService.setorCreate(new SetorDTO(setor));
		List<Setor> lst = (List<Setor>) setorService.setorRead().getEntity();
		
		Assertions.assertEquals(lst != null && lst.size() > 0, true, "Não foi retornado nenhum setor");
	}
	
	
	@Test
	void testSetorUpdate() {
		Setor setor = new Setor("Vendas");
		boolean error = false;
		try {
			setorDAO.save(setor);
			setor.setNome("Venda II");
			setorService.setorUpdate(new SetorDTO(setor));
		} catch (Exception e) {
			e.printStackTrace();
			error = true;
		}
		
		SetorDTO dto = (SetorDTO) setorService.setorFindById(setor.getId()).getEntity();
		
		Assertions.assertEquals(dto.getNome(), "Venda II", "O setor não foi atualizado");
		Assertions.assertEquals(error, false, "Ocorreu um erro na atualizacao do setor");
	}
	 
	@Test
	void testSetorDelete() {
		Setor setor = new Setor("Teste");
		setorService.setorCreate(new SetorDTO(setor));
		boolean error = false;
		try {
			setorService.setorDelete(setor.getId());
		} catch (Exception e) {
			error = true;
		}
		
		Assertions.assertEquals(error, false, "O setor não foi removido");
	}

}
