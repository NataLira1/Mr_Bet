package repositorio;

import java.util.ArrayList;
import metodo.Aposta;
import metodo.Time;

/**
 * A classe desenvovida armazenar as apostas e também para a realização de tratamentos
 * 
 * @author Natã Cavalcante
 *
 */
public class ApostaRepositorio {

	/**
	 * Instância utilizada para acessar os métodos da classe TimesRepositorio
	 */
	private TimesRepositorio times;
	
	
	/**
	 * Instância utilizada para acessar os métodos da classe CampeonatosRepositorio
	 */
	private CampeonatosRepositorio campeonatos;
	
	
	/**
	 * Colections responsável por armazenar as apostas
	 */
	private ArrayList<Aposta> apostas;
	
	
	/**
	 * Constroi uma aposta, no entanto para contruir uma aposta é necessário ter acesso aos métodos das classes dos TimesRepositorio e também 
	 * do CampeonatoRepositorio
	 * 
	 * @param campeonatos, instância da classe CampeonatosRepositorio
	 * @param times, instância da classe TimesRepositorio
	 */
	public ApostaRepositorio(CampeonatosRepositorio campeonatos, TimesRepositorio times) {
		apostas = new ArrayList<>();
		this.campeonatos = campeonatos;
		this.times = times;
	}

	
	/**
	 * Método responsável por conseguir pegar o ArrayList das apostas
	 * 
	 * @return ArrayList Aposta
	 */
	public ArrayList<Aposta> getApostas() {
		return apostas;
	}
	
	
	/**
	 * O método criarAposta é responsável por criar uma aposta no ArrayList. No entanto, para criar uma aposta são feitas algumas verificações
	 * são elas a verificação de entradas, pois se as entradas não estiver de acordo com o padrão aceito pelo sistema é lançada um erro, também
	 * é feita a verificação se o time e o campeonato exite, também é verificado se o time está incluso no campeonato e por fim só é possível
	 * realizar uma aposta se a colocação estiver abaixo da quantidade máxima de times do campeonato. Portanto, são feitas essas verificações
	 * para realizar uma aposta
	 * 
	 * @param codigoTime, O código do time
	 * @param nomeCampeonato, O nome do campeonato
	 * @param colocacao, A colocação na qual o time irá ficar na sua aposta
	 * @param valor, O valor da aposta
	 * @return IllegalArgumentException, se as entradas estiverem erradas
	 * @return "O TIME NÃO EXISTE", se o time não existir
	 * @return "O CAMPEONATO NÃO EXISTE!", se o campeonato não existir
	 * @return "O TIME NÃO ESTÁ CADASTRADO NO CAMPEONATO", se o time não estiver contido no campeonato
	 * @return "APOSTA NÃO REGISTRADA!", se a colocação for maior que a quantidade máxima de times de um campeonato
	 * @return "APOSTA REGISTRADA", se a aposta foi registrada com sucesso
	 */
	public String criarAposta(String codigoTime, String nomeCampeonato, int colocacao, double valor) {
		
		if(validarEntradas(codigoTime, nomeCampeonato, colocacao, valor)) {
			throw new IllegalArgumentException("ENTRADAS INVALIDAS");
		}
		
		if(times.existeTime(codigoTime)) {
			return "O TIME NÃO EXISTE";
		}
		
		if(campeonatos.existeCampeonato(nomeCampeonato)) {
			return "O CAMPEONATO NÃO EXISTE!";
		}
		
		
		if(!(campeonatos.campeonatoContemTime(codigoTime, nomeCampeonato))) {
			return "O TIME NÃO ESTÁ CADASTRADO NO CAMPEONATO";
		}
		
		
		if(colocacao > campeonatos.quantMaxCampeonato(nomeCampeonato)) {
			return "APOSTA NÃO REGISTRADA!";
		}
		
		apostas.add(new Aposta(codigoTime, nomeCampeonato, colocacao, valor));
		return"APOSTA REGISTRADA";
	}
	
	
	/**
	 * Responsável por validar as entradas inseridas pelo o usuário, retornando se tais entradas então corretas ou não. O padrão exigido são
	 * variaveis não vazias, e colocação positivas 
	 * 
	 * @param codigoTime, O código do time
	 * @param nomeCampeonato, O nome do campeonato
	 * @param colocacao, A colocação do time desejada pelo usuário
	 * @param valor, O valor da aposta
	 * @return true, se as entradas não estão de acordo com o padrão
	 * @return false, se as entradas estão de acordo com o padrão
	 */
	private boolean validarEntradas(String codigoTime, String nomeCampeonato, int colocacao, double valor) {
		if(codigoTime.isBlank() || nomeCampeonato.isBlank() || colocacao < 0 || valor < 0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * O método statusApostas é responsável por apresentar todas as apostas realizadas até o momento, idependente do campeonato ou até mesmo do
	 * time
	 * 
	 * @return retorna uma String formatada com todas as apostas feitas
	 */
	public String statusApostas() {
		String resultado = "Apostas:\n";
		int posicao = 1; 
		
		for(Aposta aposta: apostas) {
			for(Time time: times.getTimes()) {
				if(aposta.getCodigoTime().toUpperCase().equals(time.getIdentificador().toUpperCase())) {
					resultado += posicao + formataStatusAposta(aposta, time);
				}
			}
			posicao++;
		}
		return resultado;
	}
	
	
	/**
	 * O método formataStatusAposta é responsável por formatar as apostas
	 * 
	 * @param aposta, do tipo Aposta utilizado para ter acesso aos métodos da classe Aposta
	 * @param time, do tipo Time utilizado para ter acesso aos métodos da classe Time
	 * @return retorna uma String com todas as apostas realizadas até o momento
	 */
	private String formataStatusAposta(Aposta aposta, Time time) {
		return ". [" + time.getIdentificador() + "] "+ time.getNome() + " / " + time.getMascote() + "\n" +
				aposta.getNomeCampeonato() + "\n" +
				aposta.getColocacao() + "/" + campeonatos.quantMaxCampeonato(aposta.getNomeCampeonato()) + "\n"+
				"R$ " + aposta.getValorAposta() + "\n";
	}
}
