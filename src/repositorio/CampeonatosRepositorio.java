package repositorio;

import java.util.HashSet;

import metodo.Campeonato;
import metodo.Time;

/**
 * A classe CampeonatosRepositorio é responsável por armazenar os dados de um campeonato e também realizar manipulações com esses dados, como
 * adicionar um campeonato, incluir time em um campeonato, verifica se o time está contido no campeonato e por último exibir os campeonatos
 * de um time
 * 
 * @author Natã Cavalcante
 *
 */
public class CampeonatosRepositorio {

	/**
	 * HashSet responsavel por armazenar os campeonatos do tipo campeonato
	 */
	private HashSet<Campeonato> campeonatos;
	
	
	/**
	 * Instância utilizada para acessar os métodos da classe TimesRepositorio
	 */
	private TimesRepositorio times;
	
	
	/**
	 * Construtor no qual cria o Conjunto de campeonatos, e também atribuimos o parâmetro a instância times
	 * 
	 * @param times, intância da classe TimesRepositorio
	 */
	public CampeonatosRepositorio(TimesRepositorio times) {
		campeonatos = new HashSet<>();
		this.times = times;
	}
	
	
	/**
	 * Método responsável por retornar o conjunto de campeonatos
	 * 
	 * @return HashSet campeonatos
	 */
	public HashSet<Campeonato> getCampeonatos() {
		return campeonatos;
	}
	
	
	/**
	 * O método getTimeCampeonato, tem como função pegar o conjunto de times que particapam de um determinado campeonato 
	 * 
	 * @param nomeCampeonato, O nome do campeonato é utilizado para filtrar qual campeonato vai puxar o conjunto
	 */
	public HashSet<String> getTimeCampeonato(String nomeCampeonato){
		HashSet<String> timeCampeonato = new HashSet<>();
		
		for(Campeonato campeonato: campeonatos) {
			if(campeonato.getNomeCampeonato().toLowerCase().equals(nomeCampeonato.toLowerCase())) {
				timeCampeonato = campeonato.getTimesCampeonato();
			}
		}
		return timeCampeonato;
	}
	
	
	/**
	 * O método adicionarCampeonato é responsável por adicionar um campeonato ao conjunto de campeonatos, no entanto antes de adicionar o 
	 * campeonato são feitas algumas validações como, verificar se as entradas estão de acordo com o padrão exigido pelo sistema caso não 
	 * é lançado um erro (IllegalArgumentException), também é feita a verificação se o campeonato já existe caso já exista é retornado 
	 * ("CAMPEONATO JÁ EXISTE!"). Depois dessas validações é permitido adicionar o campeonato ao conjunto de campeonatos, se o campeonato, 
	 * for adicionado com sucesso é retornada a mensagem ("CAMPEONATO ADICIONADO!")
	 * 
	 * @param nomeCampeonato, O nome do campeonato dentro dos padrões estabelecidos
	 * @param numParticipantes, A quantidade de time(participantes) que compõem esse campeonato
	 * @return retorna a string "CAMPEONATO JÁ EXISTE!", se o campeonato já existir
	 * @return retorna a string "CAMPEONATO ADICIONADO!", se o campeonato foi adicionado com sucesso
	 * @throws IllegalArgumentException, se as entradas não estiverem de acordo com as necessidades do sistema
	 */
	public String adicionarCampeonato(String nomeCampeonato, int numParticipantes) {
		
		if(validarEntradas(nomeCampeonato, numParticipantes)) {
			throw new IllegalArgumentException("ENTRADAS INVALIDAS");
		}
		
		if(!(existeCampeonato(nomeCampeonato))) {
			return "CAMPEONATO JÁ EXISTE!";
		}
		
		campeonatos.add(new Campeonato(nomeCampeonato, numParticipantes));
		return "INCLUSÃO REALIZADA!";
	}
	
	
	/**
	 * O método validarEntradas é responsável por validar as entradas passadas pelo usuario para o sistema, checando se elas estão de acordo
	 * com os padrões estabelecidos no sistema
	 * 
	 * @param nomeCampeonato, O nome do campeonato
	 * @param numParticipantes, O número de participantes
	 * @return true, se as entradas não estão seguindo os padrões impostos pelo sistema
	 * @return false, se as entradas estão seguindo os padrões impostos pelo sistema
	 */
	private boolean validarEntradas(String nomeCampeonato, int numParticipantes) {
		if(nomeCampeonato.isBlank() || numParticipantes < 0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * O método incluirTimeCampeonato tem como responsabilidade incluir um time no conjunto timesCampeonato de seu respectivo campeonato, no
	 * entanto para realizar essa inclusão são feitas algumas verificações como, a existência do time e do campeonato, outra verificação seria
	 * se o campeonato ainda suporta a inclusão de um novo time, e também pe feita a verificação se o campeonato já contém o time. Para por fim
	 * realizar o incremento no conjunto timesCampeonato do respectivo campeonato
	 *  
	 * @param nomeCampeonato, O nome do campeonato
	 * @param codigoTime, O código do time forma de identificar o time
	 * @return "TIME NÃO EXISTE!", se o time não existir
	 * @return "CAMPEONATO NÃO EXISTE!", se o campeonato não existir
	 * @return "TODOS OS TIMES DESSE CAMPEONATO JÁ FORAM INCLUÍDOS!", se a quantidade máxima de times do campeonato for atingida
	 * @return "TIME INCLUIDO NO CAMPEONATO!", para quando o time já existe no conjunto timesCampeonato
	 * @return "TIME INCLUIDO NO CAMPEONATO!", para quando o time foi inserido com sucesso no conjunto timesCampeonato
	 */
	public String incluirTimeCampeonato(String nomeCampeonato, String codigoTime) {
		
		if(times.existeTime(codigoTime)) {
			return  "TIME NÃO EXISTE!";
		}
		if(existeCampeonato(nomeCampeonato)) {
			return "CAMPEONATO NÃO EXISTE!";
		}
		
		if(contaTimesPorCampeonato(nomeCampeonato) >= quantMaxCampeonato(nomeCampeonato)) {
			return "TODOS OS TIMES DESSE CAMPEONATO JÁ FORAM INCLUÍDOS!";
		}
		
		if(campeonatoContemTime(codigoTime, nomeCampeonato)) {
			return "TIME INCLUIDO NO CAMPEONATO!";
		}
		
		for(Campeonato campeonato: campeonatos) {
			if(campeonato.getNomeCampeonato().toLowerCase().equals(nomeCampeonato.toLowerCase())) {
				campeonato.adicionarTimeAoCampeonato(codigoTime);
			}
		}
		
		return "TIME INCLUIDO NO CAMPEONATO!";
		
	}
	/**
	 * O método existeCampeonato é responsável por realizar a verificação se o Campeonato existe no conjunto de campeonatos
	 * 
	 * @param nomeCampeonato, O nome do campeonato
	 * @return false, se o campeonato existir no conjunto de campeonatos
	 * @return true, se o campeonato não existir no conjunto de campeonatos
	 */
	public boolean existeCampeonato(String nomeCampeonato) {
		for(Campeonato oCampeonato : campeonatos) {
			if(oCampeonato.getNomeCampeonato().toLowerCase().equals(nomeCampeonato.toLowerCase())) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * O método contaTimesPorCampeonato é responsável por cantar quantos times estão previamente cadastrados/inclusos no conjunto de timesCampeonatos
	 * do seu respectivo campeonato
	 * 
	 * @param nomeCampeonato, O nome do campeonato usado para filtrar o campeonato
	 * @return retorna um inteiro, que se refere a quantidade já cadastrados em um campeonato
	 */
	public int contaTimesPorCampeonato(String nomeCampeonato) {
		int qtdeTimesPorCampeonato = 0;
		for(Campeonato campeonato : campeonatos) {
			if(campeonato.getNomeCampeonato().toLowerCase().equals(nomeCampeonato.toLowerCase())) {
				qtdeTimesPorCampeonato = campeonato.getTimesCampeonato().size();
			}
		}
		return qtdeTimesPorCampeonato;
	}
	
	
	/**
	 * O método quantMaxCampeonato é responsável por obter a quantidade máxima suportada de times em um campeonato
	 * 
	 * @param nomeCampeonato, O nome do campeonato utilizado para filtrar qual o campeonato desejado
	 * @return retorna um inteiro referente a quantidade máxima de times de um campeonato
	 */
	public int quantMaxCampeonato(String nomeCampeonato) {
		int numMaxTimes = 0;
		for(Campeonato campeonato: campeonatos) {
			if(campeonato.getNomeCampeonato().toLowerCase().equals(nomeCampeonato.toLowerCase())) {
				numMaxTimes = campeonato.getNumTimes();
			}
		}
		return numMaxTimes;
	}
	
	
	/**
	 * O método campeonatoContemTime é responsável por realizar a verificação se determinado time está em determinado campeonato. Através
	 * do código do time e o nome do campeonato que serão os parâmetros utilizados para filtrar 
	 * 
	 * @param codigoTime, O código do time, mais precisamente o identificador do time
	 * @param nomeCampeonato, O nome do campeonato
	 * @return false, se o campeonato não contém o time
	 * @return true, se o campeonato contém to time
	 */
	public boolean campeonatoContemTime(String codigoTime, String nomeCampeonato) {
		boolean resultado = false;
		
		for(Campeonato campeonato: campeonatos) {
			if(campeonato.getNomeCampeonato().toLowerCase().equals(nomeCampeonato.toLowerCase())) {
				if(campeonato.getTimesCampeonato().contains(codigoTime.toUpperCase())) {
					resultado = true;
				}
			}
		}
		return resultado;
	}
	
	
	/**
	 * O método verificarTimeCampeonato é responsável por verificar se o time está contido ou não no conjunto timesCampeonato, por meio do
	 * código do time, e também pelo nome do campeonato. No entanto, são feitas algumas verificações como se o time e o campeonato existe,
	 * para posteriormente verificar se o time está contido no conjunto timesCampeonato
	 *  
	 * @param codigoTime, é o código do time
	 * @param nomeCampeonato, nome do campeonato
	 * @return "O TIME NÃO EXISTE",se o time não existir
	 * @return "O CAMPEONATO NÃO EXISTE!", se o campeonato não existir
	 * @return "O TIME ESTÁ NO CAMPEONATO!", se o time estiver contido no conjunto timesCampeonato
	 * @return "O TIME NÃO ESTÁ NO CAMPEONATO!", se o time não estiver contido no conjunto timesCampeonato
	 */
	public String verificarTimeCampeonato(String codigoTime, String nomeCampeonato) {
		
		if(times.existeTime(codigoTime)) {
			return "O TIME NÃO EXISTE";
		}
		if(existeCampeonato(nomeCampeonato)) {
			return "O CAMPEONATO NÃO EXISTE!";
		}
		if(campeonatoContemTime(codigoTime, nomeCampeonato)) {
			return "O TIME ESTÁ NO CAMPEONATO!";
		}else {
			return "O TIME NÃO ESTÁ NO CAMPEONATO!";
		}
		
	}
	
	
	/**
	 * O método exibirCampeonatosTime é responsável po exibir os campeonatos que um time participa, podendo um time participar em mais de um
	 * campeonato. No entanto, é feita a verificação se o time existe, caso não e retornado uma String, se o time existir é realizado um filtro 
	 * em todos os campeonatos no qual o time já está cadastrado. Logo, retornando um resultado com os campeonatos que um time participa, utilizando 
	 * como chave para filtro o código do time
	 *  
	 * @param codigoTime, O código do time que é utilizado como o identificador do time
	 * @return "TIME NÃO CADASTRADO NO SISTEMA", se o time não estiver cadastrado no sistema
	 * @return "O TIME NÃO ESTÁ INCLUIDO EM NENHUM CAMPEONATO!", se o time não estiver cadastrado em nenhum campeonato
	 * @return retorna uma String formatada com todas as informações necessárias dos campeonatos.No qual, representa todos os campeonatos que um 
	 * time participa
	 */
	public String exibirCampeonatosTime(String codigoTime) {
		String resultado = "";
		
		if(times.existeTime(codigoTime.toUpperCase())) {
			return "TIME NÃO CADASTRADO NO SISTEMA";
		}
		
		for(Time oTime : times.getTimes()) {
			if(oTime.getIdentificador().toUpperCase().equals(codigoTime.toUpperCase())) {
				resultado = "Campeonatos do " + oTime.getNome() + ":\n";
			}
		}
		
		//preciso incluir um campeonato de qualquer formar
		for(Campeonato campeonato: campeonatos) {
			if(campeonato.getTimesCampeonato().contains(codigoTime.toUpperCase())) {
				resultado += formataExibir(campeonato);
			} else if(campeonato.getTimesCampeonato().size() == 0){
				resultado += "\nO TIME NÃO ESTÁ INCLUIDO EM NENHUM CAMPEONATO!";
			}
		}
		
		return resultado;
	}
	
	
	/**
	 * O método formataExibir é responsável por formatar uma String, incluindo como informações o nome do campeonato, a quantidade de times 
	 * já cadastradas no campeonato e por fim a quantidade máxima de times de um campeonato, sendo utilizado no método exibirCampeonatosTime
	 * 
	 * @param campeonato, Um objeto do tipo campeonato
	 * @return retorna as informações necessária já formatadas
	 */
	private String formataExibir(Campeonato campeonato) {
		return "* " + campeonato.getNomeCampeonato()+
				" - " + contaTimesPorCampeonato(campeonato.getNomeCampeonato()) +
				"/" + quantMaxCampeonato(campeonato.getNomeCampeonato()) +"\n";
	}
}
