package org.exemplo.persistencia.database.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.exemplo.persistencia.database.db.IConnection;
import org.exemplo.persistencia.database.model.Cliente;
import org.hibernate.Session;

public class ClienteDAO implements IEntityDAO<Cliente>{

	
	private IConnection conn;
	
	
	public ClienteDAO (IConnection conn) {
		this.conn = conn; 
	}
	
	@Override
	public void save(Cliente t) {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		session.beginTransaction();
		session.persist(t);
		session.getTransaction().commit();
		session.close();
		
	}

	@Override
	public Cliente findById(Integer id) {
		Session session = conn.getSessionFactory().openSession();
		return session.find(Cliente.class, id);
	}
	
	public Cliente findByCpf(String cpf) {
		Session session = conn.getSessionFactory().openSession();
		return session.find(Cliente.class, cpf);
	}

	@Override
	public List<Cliente> findAll() {
		Session session = conn.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
        Root<Cliente> root = query.from(Cliente.class);
        query.select(root);
        return session.createQuery(query).getResultList();
	}

	@Override
	public void update(Cliente t) {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		session.beginTransaction();
		session.merge(t);
		session.getTransaction().commit();
		session.close();	
	}

	@Override
	public void delete(Cliente t) {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(t);
		session.getTransaction().commit();
		session.close();
		
	}
}
