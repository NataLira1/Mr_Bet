package controlador;


import java.util.ArrayList;
import java.util.HashSet;

import repositorio.ApostaRepositorio;
import repositorio.CampeonatosRepositorio;
import repositorio.TimesRepositorio;
/**
 * A classe Mr_bet foi criada para controlar o sistema como um todo tendo acesso as classes do repositorio
 * 
 * @author Natã Cavalcante
 *
 */
public class Mr_bet {
	
	/**
	 * Instância para ter acesso aos métodos do repositório de times
	 */
	private TimesRepositorio times;
	
	/**
	 * Instância para ter acesso aos métodos do repositório de campeonatos
	 */
	private CampeonatosRepositorio campeonatosRepositorio;
	
	/**
	 * Instância para ter acesso aos métodos do repositório de apostas
	 */
	private ApostaRepositorio apostas;
	
	/**
	 * Constroi o controlador que responsávavel por controlar todos os métodos necessários para o sistema
	 */
	public Mr_bet() {
		this.times = new TimesRepositorio();
		this.campeonatosRepositorio = new CampeonatosRepositorio(this.times);
		this.apostas = new ApostaRepositorio(this.campeonatosRepositorio, this.times);
	}
	
	/**
	 * Método responsável por pegar o conjunto dos times cadastrados
	 * @return retorna o conjunto de times
	 */
	public HashSet<metodo.Time> getTimes(){
		return times.getTimes();
	}
	
	/**
	 * Método responsável por pegar o cojunto dos campeonatos cadastrados
	 * @return retorna o conjuntos dos campeonatos
	 */
	public HashSet<metodo.Campeonato> getCampeonatos(){
		return campeonatosRepositorio.getCampeonatos();
	}
	
	/**
	 * Método responsável por pegar o conjunto de times que foram cadastrados em um campeonato específico, por isso é passado como parâmetro
	 * o nome do campeonato que é um identificador para filtrar o campeonato desejado entre todos os campeonatos
	 * @param nomeCampeonato, O nome do campeonato
	 * @return retorna o conjunto de times cadastrados em um campeonato específico
	 */
	public HashSet<String> getTimesCampeonato(String nomeCampeonato){
		return campeonatosRepositorio.getTimeCampeonato(nomeCampeonato);
	}
	
	/**
	 * Método responsável por pegar o ArrayList das apostas já cadastradas
	 * @return retorna o conjunto de apostas já cadastradas
	 */
	public ArrayList<metodo.Aposta> getAposta(){
		return apostas.getApostas();
	}
	
	/**
	 * Método responsável por incluir um time no HashSet de times
	 * 
	 * @param codigo, código indentificador do time
	 * @param nome, nome do time
	 * @param mascote, mascote do time
	 * @return retorna uma String identificado a situação da inclusão
	 */
	public String incluirTime(String codigo, String nome, String mascote) {
		
		return times.incluirTime(codigo, nome, mascote);
	}
	
	/**
	 * Método responsável por apresentar os dados formatados de um time
	 * @param codigo, O código identificador do time
	 * @return retorna os dados do time formatados
	 */
	public String recuperarTime(String codigo) {
		
		return times.recuperarTime(codigo);
	}
	
	/**
	 * Método responsável por adicionar um Campeonato no HashSet do campeonatoRepositório
	 * 
	 * @param nomeCampeonato, nome do campeonato
	 * @param numParticipantes, o número máximo suportado por um campeonato
	 * @return retorna a situação da inclusão de um campeonato
	 */
	public String adicionarCampeonato(String nomeCampeonato, int numParticipantes) {
		
		return  campeonatosRepositorio.adicionarCampeonato(nomeCampeonato, numParticipantes);
	}
	
	/**
	 * Método responsável por incluir o código de um time em um campeonato, podendo ser mais de um time em um campeonato
	 * 
	 * @param nomeCampeonato, o nome do campeonato 
	 * @param codigoTime, o código do time a ser adicionado
	 * @return retorna a situação da inclusão de um time em um campeonato
	 */
	public String incluirTimeCampeonato(String nomeCampeonato, String codigoTime) {
		
		return  campeonatosRepositorio.incluirTimeCampeonato(nomeCampeonato, codigoTime);
	}
	
	/**
	 * Método responsável por verificar a situação de um time em um campeonato, ou seja, se o time está incluido no time ou não
	 * 
	 * @param codigoTime, representa o código do time
	 * @param nomeCampeonato, nome do campeonato
	 * @return retorna a situação da verificação do time no campeonato
	 */
	public String verificarTimeCampeonato(String codigoTime, String nomeCampeonato) {
		
		return  campeonatosRepositorio.verificarTimeCampeonato(codigoTime, nomeCampeonato);
	}
	
	/**
	 * Método responsável por exibir todos os campeonatos que apenas um time participa, podendo ser mais de um campeonato ou nenhum. Através,
	 * do código do time que é o identificador do time é possível filtrar todos os campeonatos que um time participa
	 * 
	 * @param codigoTime, O código do time
	 * @return retorna uma String que representa os campeonatos que um time participa
	 */
	public String exibirCampeonatosTime(String codigoTime) {
		
		return  campeonatosRepositorio.exibirCampeonatosTime(codigoTime);
	}
	
	/**
	 * Método responsável por criar uma aposta, utlizando  como dados o código do time, nome do campeonato, colocação e valor a ser apostado
	 * 
	 * @param codigoTime, O código do time
	 * @param nomeCampeonato, O nome do campeonato
	 * @param colocacao, A colocação do time no campeonato
	 * @param valor, O valor da aposta
	 * @return retorna a situação da criação da aposta
	 */
	public String criarAposta(String codigoTime, String nomeCampeonato, int colocacao, double valor) {
		
		return apostas.criarAposta(codigoTime, nomeCampeonato, colocacao, valor);
		
	}
	
	/**
	 * Método responsável por apresentar todas as apostas feitas até o momento, sem nenhuma execessão
	 * 
	 * @return retorna uma String com todas as apostas
	 */
	public String statusApostas() {
		
		return apostas.statusApostas();
	}
	
}
