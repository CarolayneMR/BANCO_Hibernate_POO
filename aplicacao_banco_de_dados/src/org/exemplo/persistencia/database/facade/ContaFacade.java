package org.exemplo.persistencia.database.facade;

import java.util.List;

import org.exemplo.persistencia.database.dao.ContaDAO;
import org.exemplo.persistencia.database.dao.IEntityDAO;
import org.exemplo.persistencia.database.db.ConexaoBancoHibernate;
import org.exemplo.persistencia.database.model.Conta;

public class ContaFacade {

	private IEntityDAO<Conta> contadao;
	private static ContaFacade instance;

	private ContaFacade() {
		contadao = new ContaDAO(new ConexaoBancoHibernate());
	}

	public static ContaFacade getInstance() {

		if (instance != null)
			return instance;
		else {
			instance = new ContaFacade();
			return instance;
		}
	}

	public void save() {
		contadao.save(new Conta());
	}

	public void delete(Integer id) {
		contadao.delete(new Conta(id));
	}

	public void update() {
		contadao.update(new Conta());

	}

	public Conta findById(Integer id) {
		return contadao.findById(id);
	}

	public List<Conta> findAll() {
		return contadao.findAll();
	}
}
