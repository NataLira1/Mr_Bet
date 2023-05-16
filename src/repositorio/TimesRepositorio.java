package repositorio;

import java.util.HashSet;

import metodo.Time;

/**
 * A classe TimesRepositorio é responsável por armazenar os dados dos times que vão ser cadastrados e também realizar manipulações como incluir
 * time, recuperar time. Tais dados são do tipo Time
 * 
 * @author Natã Cavalcante
 *
 */
public class TimesRepositorio {
	/**
	 * O HashSet times é responsavel por armazenar os times
	 */
	
	private HashSet<Time> times = new HashSet<>();
	
	/**
	 * Constroi o HashSet times
	 */
	public TimesRepositorio() {
		times = new HashSet<>();
	}
	
	
	/**
	 * getTimes é responsável por retornar o conjunto de times
	 * @return retorna o conjunto de times
	 */
	public HashSet<Time> getTimes() {
		return times;
	}

	/**
	 * O método incluir time tem como principal função inserir os times no conjunto de time, contudo antes de realizar tal função são feitas
	 * algumas verificações como a validação de parametros, caso tenha alguma entrada inválida o sistema lança um erro (IllegalArgumentException),
	 * outra validação é verificar se o time existe, se existir retornará (TIME JÁ EXISTE) logo não será possivel incluir novamente o mesmo time, por fim é possível realizar a 
	 * inclusão
	 * 
	 * @param codigo O codigo do time, que no caso é o identificador do time
	 * @param nome O nome do time, importante pois o nome será apresentado da mesmo forma na função exibir campeonatos
	 * @param mascote O mascote do time
	 * @return retorna "INCLUSÃO REALIZADA", se o time for adicionado ao conjunto de times com sucesso
	 */
	public String incluirTime(String codigo, String nome, String mascote) {
		Time timeTemporario = new Time(codigo.toUpperCase(), nome, mascote);
		if(validarParametros(timeTemporario)) {
			throw new IllegalArgumentException("ENTRADAS INVALIDAS");
		}
		
		if(!(existeTime(codigo))) {
			return "TIME JÁ EXISTE";
		}
		
		times.add(new Time(codigo, nome, mascote));
			return "INCLUSÃO REALIZADA";
	}
	
	/**
	 * Método responsável por retornar um boolean se o time existe ou não no conjunto times
	 * 
	 * @param codigoTime, O codigo do time utilizado como identificador do time
	 * @return false, se o time existir
	 * @return true, se o time não existir
	 */
	
	public boolean existeTime(String codigoTime) {
		for(Time oTime : times) {
			if(oTime.getIdentificador().toUpperCase().equals(codigoTime.toUpperCase())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * O método validarParametros tem como responsabilidade validar se as entradas fornecidas pelo usuário se enquadra nos padrões necessario
	 * para o sistema como um todo funcionar corretamente
	 * 
	 * @param timeTemporario, um objeto do tipo time no qual é possível chegar os seus atributos
	 * @return true, se algum das entradas não está de acordo com padrão
	 * @return false, se todas as entradas estão de acordo com os padrões estabelecidos
	 */
	private boolean validarParametros(Time timeTemporario) {
		if(timeTemporario.getIdentificador().isBlank()|| timeTemporario.getNome().isBlank() || timeTemporario.getMascote().isBlank()) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * O método recuperarTime tem como função apresentar os dados do time no seguinte formato ([codigo do time] nome / mascote)
	 *    
	 * @param codigoTime, o código do time utilizado para identificar o time
	 * @return retorna os dados do time no formato pré-estabelecido
	 */
	public String recuperarTime(String codigoTime) {
		for(Time oTime : times) {
			if(oTime.getIdentificador().toUpperCase().equals(codigoTime.toUpperCase())) {
				return oTime.toString();
			}
		}
		return "TIME NÃO EXISTE!";
	}
}
