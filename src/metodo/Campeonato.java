package metodo;

import java.util.HashSet;
import java.util.Objects;

/**
 * Classe Campeonato foi desenvolvida para o objeto Campeonato
 * 
 * @author Natã Cavalcante
 *
 */
public class Campeonato {
	
	/**
	 * O número máximo de times em um campeonato
	 */
	private int numTimes;
	
	/**
	 * O nome de um campeonato
	 */
	private String nomeCampeonato;
	
	/**
	 * Um conjunto que armazena o código dos times que foram cadastrados em um campeonato
	 */
	private HashSet<String> timesCampeonato;
	
	
	/**
	 * Constrói um campeonato
	 * 
	 * @param nomeCampeonato, O nome do campeonato
	 * @param numTimes, O número máximo de times em um campeonato
	 */
	public Campeonato( String nomeCampeonato, int numTimes) {
		this.nomeCampeonato = nomeCampeonato;
		this.numTimes = numTimes;
		timesCampeonato = new HashSet<>();
	}
	
	
	/**
	 * Método responsável por adicionar ao conjunto timesCampeonato o código dos times que serão cadastrados no campeonato
	 * 
	 * @param codigoTime, A string que representa o código do time
	 */
	public void adicionarTimeAoCampeonato(String codigoTime) {
		this.timesCampeonato.add(codigoTime.toUpperCase());
	}
	
	
	/**
	 * Método responsável por pegar o conjunto de times cadastrados em um campeonato
	 * @return HashSet timesCampeonato
	 */
	public HashSet<String> getTimesCampeonato() {
		return timesCampeonato;
	}
	
	
	/**
	 * Gera um código de inteiro de uma variável
	 */
	@Override
	public int hashCode() {
		return Objects.hash(nomeCampeonato);
	}
 
	/**
	 * Método responsável por pegar o nome do campeonato
	 * @return retorna o nome do campeonato
	 */
	public String getNomeCampeonato() {
		return nomeCampeonato;
	}

	
	/**
	 * Método responsável por pegar o número máximo de times de um campeonato
	 * @return retorna o máximo de times suportado por um campeonato
	 */
	public int getNumTimes() {
		return numTimes;
	}
}
