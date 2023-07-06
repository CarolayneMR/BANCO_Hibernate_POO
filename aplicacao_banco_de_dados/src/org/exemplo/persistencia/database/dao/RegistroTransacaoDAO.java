package org.exemplo.persistencia.database.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.exemplo.persistencia.database.db.IConnection;
import org.exemplo.persistencia.database.model.Cliente;
import org.exemplo.persistencia.database.model.RegistroTransacao;
import org.hibernate.Session;

public class RegistroTransacaoDAO implements IEntityDAO<RegistroTransacao>{
	
	private IConnection conn;
	
	
	public RegistroTransacaoDAO (IConnection conn) {
		this.conn = conn; 
	}
	
	
	@Override
	public void save(RegistroTransacao t) {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		session.beginTransaction();
		session.persist(t);
		session.getTransaction().commit();
		session.close();
		
	}

	@Override
	public RegistroTransacao findById(Integer id) {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		return session.find(RegistroTransacao.class, id);
	}

	@Override
	public List<RegistroTransacao> findAll() {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<RegistroTransacao> query = builder.createQuery(RegistroTransacao.class);
        Root<RegistroTransacao> root = query.from(RegistroTransacao.class);
        query.select(root);
        return session.createQuery(query).getResultList();
	}

	@Override
	public void update(RegistroTransacao t) {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		session.beginTransaction();
		session.merge(t);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void delete(RegistroTransacao t) {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(t);
		session.getTransaction().commit();
		session.close();
	}

}
