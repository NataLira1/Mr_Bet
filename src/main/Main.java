package main;

import java.util.Scanner;

import controlador.Mr_bet;

public class Main {
	
	public static void main(String[] args) {
		Mr_bet mrbet = new Mr_bet();
		
		Scanner sc = new Scanner(System.in);
		String comando = "";
		while(true) {
			comando = menu(sc);
			if(comando.equals("B")) {
				comando = submenu(sc);
			}
			if(comando.equals("T")) {
				comando = submenuAposta(sc);
			}
			opcao(comando, sc, mrbet);
		}
	}
	
	private static String menu(Scanner sc) {
		System.out.println("\n----------MENU--------\n"
				+"(M)Minha inclusão de times\n"
				+ "(R)Recuperar time\n"
				+ "(.)Adicionar campeonato\n"
				+ "(B)Bora incluir time em campeonato e Verificar se time está em campeonato\n"
				+ "(E)Exibir campeonatos que o time participa\n"
				+ "(T)Tentar a sorte e status\n"
				+ "(!)Já pode fechar o programa!\n"
				+ System.lineSeparator()
				+ "Opcão: ");
		
		return 	sc.next().toUpperCase();
	}
	
	private static String submenu(Scanner sc) {
		System.out.println("(I) Incluir time em campeonato ou (V) Verificar se time está em campeonato?\n");
		return sc.next().toUpperCase();
	}
	
	private static String submenuAposta(Scanner sc) {
		System.out.println("(A)Apostar ou (S)Status das Apostas?");
		return sc.next().toUpperCase();
	}
	
	private static void opcao(String comando, Scanner sc, Mr_bet mrbet) {
		switch(comando) {
		case "M":
			incluirTime(mrbet, sc);
			break;
		case "R":
			recuperarTime(mrbet, sc);
			break;
		case ".":
			adicionarCampeonato(mrbet, sc);
			break;
		case "I":
			incluirTimeCampeonato(mrbet, sc);
			break;
		case "V":
			verificarTimeCampeonato(mrbet, sc);
			break;
		case "E":
			exibirCampeonatosTime(mrbet, sc);
			break;
		case "A":
			cadastrarAposta(mrbet, sc);
			break;
		case "S":
			System.out.println(mrbet.statusApostas());
			break;
		case "!":
			sair();
			break;
		default: 
			System.out.println("Opção Inválida");
		}
	}
	
	private static void incluirTime(Mr_bet mrbet, Scanner sc) {
		String resultado = "";
		sc.nextLine();
		System.out.println("Código: ");
		String codigo = sc.nextLine();
		System.out.println("Nome: ");
		String nome = sc.nextLine();
		System.out.println("Mascote: ");
		String mascote = sc.nextLine();
		
		try {
			resultado = mrbet.incluirTime(codigo, nome, mascote);
		}catch(IllegalArgumentException erro) {
			System.out.println("ENTRADAS INVALIDAS");
			System.exit(0);
		}
		System.out.print(resultado);
	}
	
	private static void recuperarTime(Mr_bet mrbet, Scanner sc) {
		System.out.println("Código: ");
		String codigo = sc.next();
		//sc.nextLine();
		System.out.println(mrbet.recuperarTime(codigo));

	}
	
	private static void adicionarCampeonato(Mr_bet mrbet, Scanner sc) {
		String resultado = "";
		sc.nextLine();
		System.out.println("Campeonato: ");
		String campeonato = sc.nextLine();
		System.out.println("Participantes: ");
		int participantes = sc.nextInt();
		
		try {
			resultado = mrbet.adicionarCampeonato(campeonato, participantes);
		}catch(IllegalArgumentException erro) {
			System.out.println("ENTRADAS INVALIDAS");
			System.exit(0);
		}
		System.out.println(resultado);
		
	}
	
	private static void incluirTimeCampeonato(Mr_bet mrbet, Scanner sc) {
		sc.nextLine();
		System.out.println("Código: ");
		String codigo = sc.nextLine();
		System.out.println("Campeonato: ");
		String campeonato = sc.nextLine();
		System.out.println(mrbet.incluirTimeCampeonato(campeonato, codigo));
	}
	private static void verificarTimeCampeonato(Mr_bet mrbet, Scanner sc){
		sc.nextLine();
		System.out.println("Código: ");
		String codigo = sc.nextLine();
		System.out.println("Campeonato: ");
		String campeonato = sc.nextLine();
		System.out.println(mrbet.verificarTimeCampeonato(codigo, campeonato));
	}
	
	private static void exibirCampeonatosTime(Mr_bet mrbet, Scanner sc) {
		sc.nextLine();
		System.out.println("Código: ");
		String codigo = sc.nextLine();
		System.out.println(mrbet.exibirCampeonatosTime(codigo));
	}
	
	private static void cadastrarAposta(Mr_bet mrbet, Scanner sc) {
		String resultado = "";
		sc.nextLine();
		System.out.println("Código: ");
		String codigo = sc.nextLine();
		System.out.println("Campeonato: ");
		String campeonato = sc.nextLine();
		System.out.println("Colocação: ");
		int colocacao = sc.nextInt();
		System.out.println("Valor da Aposta: R$");
		double valor = sc.nextDouble();
		try {
			resultado = mrbet.criarAposta(codigo, campeonato, colocacao, valor);
		}catch(IllegalArgumentException erro) {
			System.out.println("ENTRADAS INVALIDAS");
			System.exit(0);
		}
		System.out.println(resultado);
	}

	
	private static void sair() {
		System.out.println("Por hoje é só pessoal!");
		System.exit(0);
	}
}
