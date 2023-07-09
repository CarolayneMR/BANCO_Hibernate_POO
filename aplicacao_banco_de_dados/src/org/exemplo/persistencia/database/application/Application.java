package org.exemplo.persistencia.database.application;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import org.exemplo.persistencia.database.dao.ClienteDAO;
import org.exemplo.persistencia.database.dao.ContaDAO;
import org.exemplo.persistencia.database.dao.IEntityDAO;
import org.exemplo.persistencia.database.dao.RegistroTransacaoDAO;
import org.exemplo.persistencia.database.db.ConexaoBancoHibernate;
import org.exemplo.persistencia.database.model.Cliente;
import org.exemplo.persistencia.database.model.Conta;
import org.exemplo.persistencia.database.model.RegistroTransacao;
import org.exemplo.persistencia.database.model.TipoConta;
import org.exemplo.persistencia.database.model.TipoTransacao;

public class Application {

	public static void main(String[] args) {

		IEntityDAO<Cliente> daocliente = new ClienteDAO(new ConexaoBancoHibernate());
		ContaDAO daoconta = new ContaDAO(new ConexaoBancoHibernate());

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
			case 1: // CADASTRAR CLIENTE - OK
				System.out.println("Digite o nome do cliente:");
				String nome = scanner.nextLine();
				System.out.println("Digite o CPF do cliente:");
				String cpf = scanner.nextLine();

				Cliente cliente = new Cliente(nome, cpf);
				daocliente.save(cliente);
				break;

			case 2: // SELECIONAR CLIENTES - OK
				System.out.println("Digite o ID do Cliente:");
				int id = scanner.nextInt();
				Cliente c = daocliente.findById(id);

				if (c == null) {
					System.out.println("Cliente não encontrado");
				} else {
					System.out.println("- Cliente selecionado: " + c.getNome());
					System.out.println("\nBem-vindo(a)! O que você gostaria de fazer?\n");
					System.out.println("1. Criar nova conta");
					System.out.println("2. Ver informações das contas");
					System.out.println("3. Realizar Deposito");
					System.out.println("4. Realizar Saque");
					System.out.println("5. Realizar Transferencia");
					System.out.println("6. Imprimir extrato");
					System.out.println("7. Remover conta");
					System.out.println("8. Balanço entre contas");
					System.out.println("9. Logout");

					opcao = scanner.nextInt();
					scanner.nextLine();

					switch (opcao) {
					case 1: // CRIAR O TIPO DE CONTA
						System.out.println("Que tipo de conta deseja criar?");
						System.out.println("1. Conta Poupança \n2. Conta Corrente");

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

								System.out.println("Conta Poupança criada com sucesso!");

							} catch (Exception e) {
								System.out.println("Ocorreu um erro ao criar a conta:" + e.getMessage());
								e.printStackTrace();
							}
							break;

						case 2: // CRIAR CONTA CORRENTE
							try {
								Conta conta = new Conta();
								conta.setSaldo(BigDecimal.ZERO);
								conta.setDataAbertura(LocalDateTime.now());
								conta.setStatus(true);
								conta.setTipoConta(TipoConta.CORRENTE);

								conta.setCliente(c);
								daoconta.save(conta);

								System.out.println("Conta Corrente criada com sucesso!");

							} catch (Exception e) {
								System.out.println("Ocorreu um erro ao criar a conta:" + e.getMessage());
								e.printStackTrace();
							}
							break;

						default: // MENSAGEM DE ERRO CASO ESCOLHAM OUTRA OPÇÃO
							System.out.println("Opção inválida");
							break;
						}
						break;

					case 2: // VER INFORMAÇÕES DE CONTA
						try {
							if (c.getContas().isEmpty()) {
								System.out.println("Cliente não tem contas");
							} else {
								System.out.println("- CONTAS DO CLIENTE -");
								for (Conta conta : c.getContas()) {
									System.out.println("[ Nº da Conta: " + conta.getNumeroconta() + " | Saldo: "
											+ conta.getSaldo() + " | Tipo da Conta: " + conta.getTipoConta()
											+ " | Status: " + conta.isStatus() + " | D. de Abertura: "
											+ conta.getDataAbertura() + " ]");
								}
							}
						} catch (Exception e) {
							System.out.println("Erros: " + e.getMessage());
							e.printStackTrace();
						}
						break;

					case 3: // REALIZAR DEPOSITO
						try {
							if (c.getContas().isEmpty()) {
								System.out.println("Cliente não tem contas");
							} else {
								System.out.println("- CONTAS DO CLIENTE -");
								for (Conta conta : c.getContas()) {
									System.out.println("[ Nº da Conta: " + conta.getNumeroconta() + " | Saldo: "
											+ conta.getSaldo() + " | Tipo da Conta: " + conta.getTipoConta()
											+ " | Status: " + conta.isStatus() + " | D. de Abertura: "
											+ conta.getDataAbertura() + " ]");
								}

								System.out.println("Digite o número da conta que deseja realisar o depósito:");

								int numeroConta = scanner.nextInt();
								scanner.nextLine();

								Conta contaSelecionada = null;
								for (Conta conta : c.getContas()) {
									if (conta.getNumeroconta() == numeroConta) {
										contaSelecionada = conta;
										break;
									}
								}

								if (contaSelecionada != null) {
									System.out.println("Digite o valor do depósito:");
									double quantia = scanner.nextDouble();
									scanner.nextLine();

									contaSelecionada.depositar(new BigDecimal(quantia));

									IEntityDAO<RegistroTransacao> TransacaoDAO = new RegistroTransacaoDAO(
											new ConexaoBancoHibernate());
									RegistroTransacao transacao = new RegistroTransacao(BigDecimal.valueOf(quantia),
											LocalDateTime.now());
									transacao.setConta(contaSelecionada);

									IEntityDAO<Conta> Condao = new ContaDAO(new ConexaoBancoHibernate());
									Condao.update(contaSelecionada);
									TransacaoDAO.save(transacao);
									System.out.println("Depósito realizado com sucesso!");
								} else {
									System.out.println("Conta não encontrada;");
								}
							}
						} catch (Exception e) {
							System.out.println("Erros: " + e.getMessage());
							e.printStackTrace();
						}
						break;

					case 4: // REALIZAR SAQUE
						try {
							if (c.getContas().isEmpty()) {
								System.out.println("Cliente não tem contas");
							} else {
								System.out.println("- CONTAS DO CLIENTE -");
								for (Conta conta : c.getContas()) {
									System.out.println("[ Nº da Conta: " + conta.getNumeroconta() + " | Saldo: "
											+ conta.getSaldo() + " | Tipo da Conta: " + conta.getTipoConta()
											+ " | Status: " + conta.isStatus() + " | D. de Abertura: "
											+ conta.getDataAbertura() + " ]");
								}

								System.out.println("Digite o numero da conta que deseja sacar:");
								int numeroConta = scanner.nextInt();
								scanner.nextLine();

								Conta contaSelecionada = null;
								for (Conta conta : c.getContas()) {
									if (conta.getNumeroconta() == numeroConta) {
										contaSelecionada = conta;
										break;
									}
								}
								if (contaSelecionada != null) {
									System.out.println("Digite o valor do saque:");
									double quantia = scanner.nextDouble();
									scanner.nextLine();
									contaSelecionada.sacar(new BigDecimal(quantia));

									IEntityDAO<RegistroTransacao> TransacaoDAO = new RegistroTransacaoDAO(
											new ConexaoBancoHibernate());
									RegistroTransacao transacao = new RegistroTransacao(BigDecimal.valueOf(quantia),
											LocalDateTime.now());
									transacao.setConta(contaSelecionada);

									IEntityDAO<Conta> Condao = new ContaDAO(new ConexaoBancoHibernate());
									Condao.update(contaSelecionada);
									TransacaoDAO.save(transacao);

									System.out.println("Saque realizado com sucesso");
								} else {
									System.out.println("Conta não encontrada.");
								}
							}
						} catch (Exception e) {
							System.out.println("Ocorreu um erro ao realizar o saque: " + e.getMessage());
							e.printStackTrace();
						}
						break;

					case 5: // REALIZAR TRANSFERENCIA
						try {
							if (c.getContas().isEmpty()) {
								System.out.println("Cliente não tem contas");
							} else {
								System.out.println("- CONTAS DO CLIENTE -");
								for (Conta conta : c.getContas()) {
									System.out.println("[ Nº da Conta: " + conta.getNumeroconta() + " | Saldo: "
											+ conta.getSaldo() + " | Tipo da Conta: " + conta.getTipoConta()
											+ " | Status: " + conta.isStatus() + " | D. de Abertura: "
											+ conta.getDataAbertura() + " ]");
								}

								System.out.println("Digite o número da conta de origem: ");
								int numeroContaOrigem = scanner.nextInt();
								Conta contaOrigem = daoconta.findByNConta(numeroContaOrigem);

								System.out.println("Digite o número da conta de destino: ");
								int numeroContaDestino = scanner.nextInt();
								Conta contaDestino = daoconta.findByNConta(numeroContaDestino);

								if (contaOrigem != null && contaDestino != null) {
									System.out.println("Digite o valor da transferencia:");
									double quantia = scanner.nextDouble();
									scanner.nextLine();

									contaOrigem.transferir(contaDestino, new BigDecimal(quantia));

									IEntityDAO<RegistroTransacao> TransacaoDAO = new RegistroTransacaoDAO(
											new ConexaoBancoHibernate());
									RegistroTransacao transacao = new RegistroTransacao(BigDecimal.valueOf(quantia),
											LocalDateTime.now());
									transacao.setConta(contaOrigem);
									TransacaoDAO.save(transacao);
									RegistroTransacao transacao2 = new RegistroTransacao(BigDecimal.valueOf(quantia),
											LocalDateTime.now());
									transacao2.setConta(contaDestino);
									TransacaoDAO.save(transacao2);

									daoconta.update(contaOrigem);
									daoconta.update(contaDestino);

									System.out.println("Transferencia realizada com sucesso");

								} else {
									System.out.println("Conta Origem ou Destino não encontrada.");
								}
							}
						} catch (Exception e) {
							System.out.println("Ocorreu um erro ao realizar a transferencia: " + e.getMessage());
							e.printStackTrace();
						}
						break;

					case 6: // VER EXTRATO DA CONTA
						try {
							if (c.getContas().isEmpty()) {
								System.out.println("Cliente não tem contas");
							} else {
								System.out.println("- CONTAS DO CLIENTE -");
								for (Conta conta : c.getContas()) {
									System.out.println("[ Nº da Conta: " + conta.getNumeroconta() + " | Saldo: "
											+ conta.getSaldo() + " | Tipo da Conta: " + conta.getTipoConta()
											+ " | Status: " + conta.isStatus() + " | D. de Abertura: "
											+ conta.getDataAbertura() + " ]");
								}
							}

							System.out.println("Digite o numero da conta:");
							int numeroConta = scanner.nextInt();
							scanner.nextLine();

							Conta conta = daoconta.findByNConta(numeroConta);

							if (conta != null) {

								List<RegistroTransacao> transacoes = conta.getTransacoes();
								if (transacoes.isEmpty()) {
									System.out.println("Não há transações registradas para esta conta");
								} else {
									System.out.println("Extrato da conta:");

									for (RegistroTransacao transacao : transacoes) {
										System.out.println("[ ID: " + transacao.getId() + " | Data: "
												+ transacao.getData() + " | Valor: " + transacao.getValor() + " ]");
									}
								}
							} else {
								System.out.println("Conta não encontrada ou não pertence ao cliente");
							}
						} catch (Exception e) {
							System.out.println("Ocorreu um erro ao imprimir o extrato: " + e.getMessage());
							e.printStackTrace();
						}
						break;

					case 7: // REMOVER/DELETAR A CONTA
						try {
							if (c.getContas().isEmpty()) {
								System.out.println("Cliente não tem contas");
							} else {
								System.out.println("- CONTAS DO CLIENTE -");
								for (Conta conta : c.getContas()) {
									System.out.println("[ Nº da Conta: " + conta.getNumeroconta() + " | Saldo: "
											+ conta.getSaldo() + " | Tipo da Conta: " + conta.getTipoConta()
											+ " | Status: " + conta.isStatus() + " | D. de Abertura: "
											+ conta.getDataAbertura() + " ]");
								}
							}
							System.out.println("\nDigite o numero da conta que deseja remover:");
							int numeroConta = scanner.nextInt();
							scanner.nextLine();

							Conta conta = daoconta.findByNConta(numeroConta);

							if (conta != null) {

								daocliente.update(c);
								daoconta.delete(conta);
								System.out.println("Conta removida com sucesso.");
							} else {
								System.out.println("Conta não encontrada ou não pertence ao cliente.");
							}

						} catch (Exception e) {
							System.out.println("Ocorreu um erro ao remover a conta: " + e.getMessage());
							e.printStackTrace();
						}
						break;

					default:
						System.out.println("Opção inválida");
						break;

					case 8: // BALANÇO ENTRE CONTAS
						try {
							List<Conta> contas = c.getContas();
							BigDecimal saldoTotal = BigDecimal.ZERO;

							for (Conta conta : contas) {
								saldoTotal = saldoTotal.add(conta.getSaldo());
							}
							System.out.println("Balaço total entre as contas: " + saldoTotal);
						} catch (Exception e) {
							System.out.println("Ocorreu um erro ao calcular o balanço: " + e.getMessage());
							e.printStackTrace();
						}
						break;

					case 9: // LOGOUT
						System.out.println("Até logo!");
						System.exit(0);
					}
					break;
				}

			case 3: // LISTAGEM DE CLIENTES - OK
				List<Cliente> clientes = daocliente.findAll();

				System.out.println("- Clientes existentes:");
				if (clientes.isEmpty()) {
					System.out.println("Não há contas cadastradas.\n");
				} else {
					for (Cliente cliente1 : clientes) {
						System.out.println("[ ID: " + cliente1.getId() + " | Nome: " + cliente1.getNome() + " | CPF: "
								+ cliente1.getCpf() + " ]");
					}
				}
				break;

			case 4: // REMOVER CLIENTE - OK
				List<Cliente> clientesR = daocliente.findAll(); //

				System.out.println("Clientes existentes:");
				for (Cliente cliente1 : clientesR) {
					System.out.println("[ ID: " + cliente1.getId() + " | Nome: " + cliente1.getNome() + " | CPF: "
							+ cliente1.getCpf() + " ]");
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

			case 5: // SAIR - OK
				System.out.println("Até logo!");
				System.exit(0);

			default:
				System.out.println("Opção inválida");
				break;
			}
		}
	}
}