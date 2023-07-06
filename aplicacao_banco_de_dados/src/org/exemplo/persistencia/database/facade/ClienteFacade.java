package org.exemplo.persistencia.database.facade;

import java.util.List;

import org.exemplo.persistencia.database.dao.ClienteDAO;
import org.exemplo.persistencia.database.dao.IEntityDAO;
import org.exemplo.persistencia.database.db.ConexaoBancoHibernate;
import org.exemplo.persistencia.database.model.Cliente;

public class ClienteFacade {

	private IEntityDAO<Cliente> clientedao;
	private static ClienteFacade instance;

	private ClienteFacade() {
		clientedao = new ClienteDAO(new ConexaoBancoHibernate());
	}

	public static ClienteFacade getInstance() {

		if (instance != null)
			return instance;
		else {
			instance = new ClienteFacade();
			return instance;
		}
	}

	public void save( String nome, String cpf) {
		clientedao.save(new Cliente(nome,cpf));
	}

	public void delete(Integer id) {
		clientedao.delete(new Cliente(id));
	}

	public void update(String nome,String cpf) {
		clientedao.update(new Cliente(nome,cpf));
	}

	public Cliente findById(Integer id) {
		return clientedao.findById(id);
	}

	public List<Cliente> findAll() {
		return clientedao.findAll();
	}
}
