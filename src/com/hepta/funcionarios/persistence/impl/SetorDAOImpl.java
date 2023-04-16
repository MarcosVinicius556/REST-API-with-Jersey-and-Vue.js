package com.hepta.funcionarios.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.hepta.funcionarios.entity.Setor;
import com.hepta.funcionarios.persistence.HibernateUtil;
import com.hepta.funcionarios.persistence.interfaces.SetorDAO;

/**
 * 
 * @author marcos
 * @apiNote Implementações dos métodos 
 * 			definidos na interface DAO do objeto
 *
 */
public class SetorDAOImpl implements SetorDAO {

	public void save(Setor setor) throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(setor);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}
	}

	public Setor update(Setor setor) throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		Setor setorAtualizado = null;
		try {
			em.getTransaction().begin();
			setorAtualizado = em.merge(setor);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}
		return setorAtualizado;
	}

	public void delete(Integer id) throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			Setor setor = em.find(Setor.class, id);
			em.remove(setor);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}

	}

	public Setor find(Integer id) throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		Setor setor = null;
		try {
			setor = em.find(Setor.class, id);
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
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
