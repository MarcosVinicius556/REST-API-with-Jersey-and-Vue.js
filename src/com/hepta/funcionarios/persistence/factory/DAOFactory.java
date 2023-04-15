package com.hepta.funcionarios.persistence.factory;

import com.hepta.funcionarios.persistence.impl.FuncionarioDAOImpl;
import com.hepta.funcionarios.persistence.impl.SetorDAOImpl;


/**
 * 
 * @author marcos
 *
 */
public class DAOFactory {

	public static FuncionarioDAOImpl createFuncionarioDAO() {
		return new FuncionarioDAOImpl();
	}
	
	public static SetorDAOImpl createSetorDAOImpl() {
		return new SetorDAOImpl();
	}
	
	
}
