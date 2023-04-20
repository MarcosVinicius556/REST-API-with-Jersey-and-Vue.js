package com.hepta.funcionarios.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.hepta.funcionarios.entity.Funcionario;
import com.hepta.funcionarios.persistence.FuncionarioDAO;
import com.hepta.funcionarios.persistence.exception.ObjectNotFoundException;
import com.hepta.funcionarios.persistence.singleton.HibernateUtil;

/**
 * 
 * @author marcos
 * @apiNote Implementações dos métodos
 * 			definidos na interface DAO do objeto
 *
 */
public class FuncionarioDAOImpl implements FuncionarioDAO {

	public Funcionario findById(Object id) throws ObjectNotFoundException {
		EntityManager em = HibernateUtil.getEntityManager();
		Funcionario funcionario = null;
		try {
			funcionario = em.find(Funcionario.class, id);
			if(funcionario == null)
				throw new ObjectNotFoundException("Funcionário não encontrado");
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new ObjectNotFoundException("");
		} finally {
			em.close();
		}
		return funcionario;
	}

	@SuppressWarnings("unchecked")
	public List<Funcionario> getAll() throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		List<Funcionario> funcionarios = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Funcionario f join fetch f.setor ");
			funcionarios = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}
		return funcionarios;
	}
	
}
