package org.exemplo.persistencia.database.application;
 
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import org.exemplo.persistencia.database.dao.ClienteDAO;
import org.exemplo.persistencia.database.dao.ContaDAO;
import org.exemplo.persistencia.database.dao.IEntityDAO;
import org.exemplo.persistencia.database.db.ConexaoBancoHibernate;
import org.exemplo.persistencia.database.model.Cliente;
import org.exemplo.persistencia.database.model.Conta;
import org.exemplo.persistencia.database.model.TipoConta;
 
public class Application {
 
	public static void main(String[] args) {	
		
		IEntityDAO<Cliente> daocliente = new ClienteDAO(new ConexaoBancoHibernate());
		IEntityDAO<Conta> daoconta = new ContaDAO(new ConexaoBancoHibernate());
		
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\nBem-vindo ao banco! O que você gostaria de fazer?\n");
			System.out.println("1. Cadastrar novo cliente");
			System.out.println("2. Selecionar cliente existente");
			System.out.println("3. Listar clientes");
			System.out.println("4. Excluir conta");
			System.out.println("5. Sair");

			int opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
            case 1: //CADASTRAR CLIENTE - OK
                System.out.println("Digite o nome do cliente:");
                String nome = scanner.nextLine();
                System.out.println("Digite o CPF do cliente:");
                String cpf = scanner.nextLine();

                
                Cliente cliente = new Cliente(nome, cpf);
                daocliente.save(cliente);
                break;
                
            case 2: //SELECIONAR CLIENTES - OK
            	System.out.println("Digite o ID:");
            	int id = scanner.nextInt();
            	Cliente c = daocliente.findById(id);
            	
				if (c == null) {
					System.out.println("Cliente não encontrado");
            	} else {
					System.out.println("Cliente selecionado: " + c.getNome());
					System.out.println("O que você gostaria de fazer?\n");
					System.out.println("1. Criar nova conta");
					System.out.println("2. Ver informações das contas");
					System.out.println("3. Realizar Deposito");
					System.out.println("4. Realizar Saque");
					System.out.println("5. Realizar Transferencia");
					System.out.println("6. Imprimir extrato");
					System.out.println("7. Remover conta");
					System.out.println("8. Balanço entre contas");
            	
					opcao = scanner.nextInt();
					scanner.nextLine();
            	
					switch (opcao) {
					case 1: //CRIAR O TIPO DE CONTA
						System.out.println("Que tipo de conta deseja criar?");
						System.out.println("1. Conta Poupança");
						System.out.println("2. Conta Corrente");
						
						opcao = scanner.nextInt();
						scanner.nextLine();
						switch (opcao) {
						case 1: // CRIAR A CONTA POUPANÇA
							try {
								Conta conta = new Conta();
								conta.setSaldo(BigDecimal.ZERO);
								conta.setDataAbertura(LocalDateTime.now());
								conta.setStatus(true);
								conta.setTipoConta(TipoConta.POUPANCA);

								conta.setCliente(c);
								daoconta.save(conta);
								daocliente.update(c);
								
								System.out.println("Conta Corrente criada com sucesso!");

							} catch (Exception e) {
								System.out.println("Ocorreu um erro ao criar a conta:" + e.getMessage());
								e.printStackTrace();
							}
						break;
						
						case 2: //CRIAR CONTA CORRENTE
							try {
								Conta conta = new Conta();
								conta.setSaldo(BigDecimal.ZERO);
								conta.setDataAbertura(LocalDateTime.now());
								conta.setStatus(true);
								conta.setTipoConta(TipoConta.CORRENTE);
				
								conta.setCliente(c);
								daoconta.save(conta);
								daocliente.update(c);
								
								System.out.println("Conta Corrente criada com sucesso!");

							} catch (Exception e) {
								System.out.println("Ocorreu um erro ao criar a conta:" + e.getMessage());
								e.printStackTrace();
							}
						break;
						
						default: //MENSAGEM DE ERRO CASO ESCOLHAM OUTRA OPÇÃO
							System.out.println("Opção inválida");
							break;
						}
						
					case 2: //VER INFORMAÇÕES DE CONTA
						try {							
							if (c.getContas().isEmpty()) {
								System.out.println("Cliente não tem contas");
							} else {
								System.out.println("Contas do cliente:");
								for (Conta conta : c.getContas()) {
									System.out.println("Nº da Conta: " + conta.getNumeroconta());
									System.out.println("Saldo: " + conta.getSaldo());
									System.out.println("Tipo da Conta: " + conta.getTipoConta());
									System.out.println("Status: " + conta.isStatus());
									System.out.println("D. de Abertura: " + conta.getDataAbertura());
								}	
							}
						} catch (Exception e) {
							System.out.println("Erros: " + e.getMessage());
							e.printStackTrace();
						}
						
					case 3: //REALIZAR DEPOSITO
						
					}
				}
          	
                break;


				case 3: //LISTAGEM DE CLIENTES - OK
				    List<Cliente> clientes = daocliente.findAll();
				    
				    System.out.println("- Clientes existentes:");
				    if (clientes.isEmpty()) {
				        System.out.println("Não há contas cadastradas.\n");
				    } else {
				        for (Cliente cliente1 : clientes) {
				            System.out.println("[ ID: " + cliente1.getId() + " | Nome: " + cliente1.getNome() + " | CPF: " + cliente1.getCpf() + " ]");
				        }
				    }
					break;
					
					
				case 4: //REMOVER CLIENTE - OK
				    List<Cliente> clientesR = daocliente.findAll(); //
				    
				    System.out.println("Clientes existentes:");
				    for (Cliente cliente1 : clientesR) {
				        System.out.println("[ ID: " + cliente1.getId() + " | Nome: " + cliente1.getNome() + " | CPF: " + cliente1.getCpf() + " ]");
				    } 
					
					System.out.println("Insira o ID do cliente:");
					int idremove = scanner.nextInt();
					System.out.println();
					
					Cliente cremove = daocliente.findById(idremove);
					if (cremove == null) {
						System.err.println("Cliente não encontrado");
					} else {
						daocliente.delete(cremove);
						System.err.println("Cliente excluido com Sucesso!");
					}
					break;		
					
				case 5: //SAIR - OK
					System.out.println("Até logo!");
					System.exit(0);

				default:
					System.out.println("Opção inválida");
					break;
			}
		}
	}
}