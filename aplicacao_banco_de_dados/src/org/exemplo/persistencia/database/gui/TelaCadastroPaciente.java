package org.exemplo.persistencia.database.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaCadastroPaciente extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel formComponents;
	private JLabel lblNome;
	private JLabel lblAltura;
	private JLabel lblPeso;
	private JLabel lblId;
	
	private JTextField txfNome;
	private JTextField txfAltura;
	private JTextField txfPeso;
	
	private JPanel buttonComponents;
	private JButton btnSalvar;
	private JButton btnLimpar;
	private JButton btnFechar;
	
	
	public TelaCadastroPaciente() {
		setTitle("Tela Cadastro Paciente - Sistema de Cadastro");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		setSize(480, 320);
		setResizable(false);
		setLayout(new BorderLayout());
		formComponents = new JPanel();
		formComponents.setLayout(new FlowLayout());
		getContentPane().add(formComponents, BorderLayout.CENTER);
		loadFormComponents();
		setVisible(true);
	}
	
	private void loadFormComponents() {
	
		lblNome = new JLabel("Nome: ");
		formComponents.add(lblNome);
		txfNome = new JTextField(30);
		formComponents.add(txfNome);
		lblAltura = new JLabel("Altura: ");
		formComponents.add(lblAltura);
		txfAltura = new JTextField(15);
		formComponents.add(txfAltura);
		lblPeso = new JLabel("Peso: ");
		formComponents.add(lblPeso);
		txfPeso = new JTextField(15);
		formComponents.add(txfPeso);
		
	}
	

}
