package com.hepta.funcionarios.persistence.factory;

import com.hepta.funcionarios.persistence.impl.FuncionarioDAOImpl;
import com.hepta.funcionarios.persistence.impl.SetorDAOImpl;
import com.hepta.funcionarios.persistence.interfaces.FuncionarioDAO;
import com.hepta.funcionarios.persistence.interfaces.SetorDAO;


/**
 * 
 * @author marcos
 * 
 * @apiNote Retorna a instancia de um DAO,
 * 			ficando assim dependente da abstração 
 * 			e não da implementação;
 *
 */
public class DAOFactory {

	public static FuncionarioDAO createFuncionarioDAO() {
		return new FuncionarioDAOImpl();
	}
	
	public static SetorDAO createSetorDAOImpl() {
		return new SetorDAOImpl();
	}
	
}
