package metodo;
/**
 * Classe desenvolvida para o objeto aposta
 * 
 * @author Natã Cavalcante
 *
 */
public class Aposta {
	
	/**
	 * O código do Time, responsável por identificar o time no qual será feita a aposta
	 */
	private String codigoTime;
	
	/**
	 * O nome do campeonato em que será feita a aposta
	 */
	private String nomeCampeonato;
	
	/**
	 * A colocação em que o time irá ficar no campeonato
	 */
	private int colocacao;
	
	/**
	 * O valor da aposta
	 */
	private double valorAposta;
	
	
	/**
	 * Contrói uma aposta
	 * 
	 * @param codigoTime, O código do time
	 * @param nomeCampeonato, O nome do Campeonato
	 * @param colocacao, A colocação do time no campeonato
	 * @param valorAposta, O valor da aposta
	 */
	public Aposta(String codigoTime, String nomeCampeonato, int colocacao, double valorAposta) {
		this.codigoTime = codigoTime;
		this.nomeCampeonato = nomeCampeonato;
		this.colocacao = colocacao;
		this.valorAposta = valorAposta;
	}

	
	/**
	 * Método responsável por pegar o código do time da aposta
	 * 
	 * @return O código do time
	 */
	public String getCodigoTime() {
		return codigoTime;
	}

	/**
	 * Método responsável por pegar o nome do campeonato da aposta
	 * @return O nome do campeonato
	 */
	public String getNomeCampeonato() {
		return nomeCampeonato;
	}

	
	/**
	 * Método responsável por pegar a colação do time no campeonato, isso numa aposta
	 * @return retorna a colocação do time
	 */
	public int getColocacao() {
		return colocacao;
	}

	
	/**
	 * Método responsável por pegar o valor da aposta
	 * @return retorna o valor da aposta
	 */
	public double getValorAposta() {
		return valorAposta;
	}

}
