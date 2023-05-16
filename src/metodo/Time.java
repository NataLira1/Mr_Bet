package metodo;


import java.util.Objects;

/**
 * Classe desenvolvida para o objeto Time
 * 
 * @author Natã Cavalcante
 *
 */
public class Time {
	
	/**
	 * O código do time, responsável por identificar o time sendo que esse código não pode repetir
	 */
	private String identificador;
	
	/**
	 * O nome do time
	 */
	private String nome;
	
	/**
	 * O mascote do time
	 */
	private String mascote;
	
	/**
	 * Constrói um time
	 * 
	 * @param identificador, O código de um time
	 * @param nome, O nome de um time
	 * @param mascote, O mascote de um time
	 */
	public Time(String identificador, String nome, String mascote) {
		
		this.identificador = identificador;
		this.nome = nome;
		this.mascote = mascote;
		
	}
	
	/**
	 * Gera uma identificação de uma variável a partir da geração de um código de inteiro
	 */
	@Override
	public int hashCode() {
		return Objects.hash(identificador);
	}

	/**
	 * Método responsável por pegar o código identificador de um time
	 * @return retorna o código identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Método responsável por pegar o nome do time
	 * @return retorna o nome do time
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método responsável por pegar o mascote do time
	 * @return retorna o mascote do time
	 */
	public String getMascote() {
		return mascote;
	}

	/**
	 * Método responsável por apresentar o time
	 */
	public String toString() {
		return "[" + identificador + "] " + nome + " / " + mascote;
	}
	
}
