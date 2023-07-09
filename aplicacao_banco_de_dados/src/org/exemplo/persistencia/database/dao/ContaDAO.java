package org.exemplo.persistencia.database.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.exemplo.persistencia.database.db.IConnection;
import org.exemplo.persistencia.database.model.Conta;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ContaDAO implements IEntityDAO<Conta>{
	
private IConnection conn;
	
	public ContaDAO (IConnection conn) {
		this.conn = conn; 
	}

	@Override
	public void save(Conta t) {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		session.beginTransaction();
		session.persist(t);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public Conta findById(Integer id) {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		Conta c = session.find(Conta.class, id);
		session.close();
		return c;
	}
	
	public Conta findByNConta(Integer numeroconta) {
		Session session = conn.getSessionFactory().openSession();
		String hql = "FROM Conta WHERE numeroconta = :numero_conta";
		Query<Conta> query = session.createQuery(hql, Conta.class);
		query.setParameter("numero_conta", numeroconta);
		Conta c = query.uniqueResult(); 
		session.close();
		return c;
	}

	@Override
	public List<Conta> findAll() {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Conta> query = builder.createQuery(Conta.class);
        Root<Conta> root = query.from(Conta.class);
        query.select(root);
        return session.createQuery(query).getResultList();
	}

	@Override
	public void update(Conta t) {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		session.beginTransaction();
		session.merge(t);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void delete(Conta t) {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(t);
		session.getTransaction().commit();
		session.close();
	}

}
