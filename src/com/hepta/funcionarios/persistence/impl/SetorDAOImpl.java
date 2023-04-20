package com.hepta.funcionarios.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.hepta.funcionarios.entity.Setor;
import com.hepta.funcionarios.persistence.SetorDAO;
import com.hepta.funcionarios.persistence.exception.ObjectNotFoundException;
import com.hepta.funcionarios.persistence.singleton.HibernateUtil;

/**
 * 
 * @author marcos
 * @apiNote Implementações dos métodos
 * 			definidos na interface DAO do objeto
 *
 */
public class SetorDAOImpl implements SetorDAO {

	public Setor findById(Object id) throws ObjectNotFoundException {
		EntityManager em = HibernateUtil.getEntityManager();
		Setor setor = null;
		try {
			setor = em.find(Setor.class, id);
			if(setor == null)
				throw new ObjectNotFoundException("Setor não encontrado");
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new ObjectNotFoundException("");
		} finally {
			em.close();
		}
		return setor;
	}

	@SuppressWarnings("unchecked")
	public List<Setor> getAll() throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		List<Setor> setores = new ArrayList<>();
		try {
			Query query = em.createQuery("SELECT s FROM Setor s");
			setores = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}
		return setores;
	}
	
}
