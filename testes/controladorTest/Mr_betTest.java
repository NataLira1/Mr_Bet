package controladorTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import controlador.Mr_bet;
import repositorio.ApostaRepositorio;
import repositorio.CampeonatosRepositorio;
import repositorio.TimesRepositorio;

class Mr_betTest {
	
	Mr_bet mrbet;
	CampeonatosRepositorio campeonatosRepositorio;
	TimesRepositorio timesRepositorio;
	ApostaRepositorio apostaRepositorio;
	
	@BeforeEach
	void setUp() {
		mrbet = new Mr_bet();
		mrbet.incluirTime("250_PB", "NACIONAL de Patos", "Canário");
		mrbet.incluirTime("252_PB", "Sport Lagoa Seca", "Carneiro");
		mrbet.incluirTime("002_RJ", "Clube de Regatas do Flamengo", "Urubu");
		mrbet.incluirTime("105_PB", "Sociedade Recreativa de Monteiro (SOCREMO)", "Gavião");
	} 
	
	@AfterEach
	void tearDown() {
		mrbet = null;
		
	}
	
	@Test
	@DisplayName("Quando vou cadastrar time já cadastrado")
	void cadastrarTimeJaCadastrado() {
		
		assertEquals(4, mrbet.getTimes().size());
		assertEquals("TIME JÁ EXISTE", mrbet.incluirTime("002_RJ", "Clube de Regatas do Flamengo", "Urubu"));
		assertEquals(4, mrbet.getTimes().size());
	}
	
	@Test
	@DisplayName("Quando vou cadastar um time e os dados estão inválidos")
	void cadastarComEntradasInvalididas() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			mrbet.incluirTime("", "Campinense", "Raposa");
			mrbet.incluirTime("251_PB", "", "Raposa");
			mrbet.incluirTime("251_PB", "Campinense", " ");
		});
			
		String experada = "ENTRADAS INVALIDAS";
		String atual = exception.getMessage();
		
		assertEquals(experada, atual);
	}
	
	@Test
	@DisplayName("Quando vou adicionar um campeonato com entradas inválidas")
	void adicionarCampeonatoComEntradasInvalidas() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			mrbet.adicionarCampeonato("", 10);
			mrbet.adicionarCampeonato("Copa do Nordeste", -2);
		});
			
		String experada = "ENTRADAS INVALIDAS";
		String atual = exception.getMessage();
		
		assertEquals(experada, atual);
	}
	
	@Test
	@DisplayName("Cadastrar campeonato sem restrição")
	void cadastrarCampeonato() {
		assertEquals(0 , mrbet.getCampeonatos().size());
		assertEquals("INCLUSÃO REALIZADA!", mrbet.adicionarCampeonato("Brasileirão série A 2023", 5));
		assertEquals(1 , mrbet.getCampeonatos().size());
	}
	
	@Test
	@DisplayName("Quando preciso cadastrar um campeonato que já existe")
	void quandoPrecisoCadastrarCampeonato() {
		assertEquals(0 , mrbet.getCampeonatos().size());
		assertEquals("INCLUSÃO REALIZADA!", mrbet.adicionarCampeonato("Brasileirão série A 2023", 5));
		assertEquals(1 , mrbet.getCampeonatos().size());
		assertEquals("CAMPEONATO JÁ EXISTE!", mrbet.adicionarCampeonato("Brasileirão série A 2023", 3));
		assertEquals(1 , mrbet.getCampeonatos().size());
		
	}
	
	@Test
	@DisplayName("Quando exibir dados de um time")
	void exibirDadosDeUmTime() {
		assertEquals("[250_PB] NACIONAL de Patos / Canário", mrbet.recuperarTime("250_PB"));
		assertEquals("TIME NÃO EXISTE!", mrbet.recuperarTime("280_SC"));
	}
	
	@Test
	@DisplayName("Quando preciso incluir time em um campeonato")
	void incluindoTimeEmCampeonato() {
		assertEquals(0 , mrbet.getCampeonatos().size());
		assertEquals("INCLUSÃO REALIZADA!", mrbet.adicionarCampeonato("Brasileirão série A 2023", 5));
		assertEquals(1 , mrbet.getCampeonatos().size());
		assertEquals(0, mrbet.getTimesCampeonato("Brasileirão série A 2023").size());
		assertEquals("TIME INCLUIDO NO CAMPEONATO!", mrbet.incluirTimeCampeonato("Brasileirão série A 2023", "250_PB"));
		assertEquals("TIME INCLUIDO NO CAMPEONATO!", mrbet.incluirTimeCampeonato("Brasileirão série A 2023", "252_PB"));
		assertEquals(2, mrbet.getTimesCampeonato("Brasileirão série A 2023").size());
	}
	
	@Test
	@DisplayName("Incluir times em um campeonato até a quantidade máxima de times")
	void incluindoTimesCampeonatoAoLimite() {
		
		assertEquals(0, mrbet.getTimesCampeonato("Brasileirão série A 2023").size());
		mrbet.adicionarCampeonato("Brasileirão série A 2023", 4);
		mrbet.incluirTimeCampeonato("Brasileirão série A 2023", "250_PB");
		mrbet.incluirTimeCampeonato("Brasileirão série A 2023", "252_PB");
		mrbet.incluirTimeCampeonato("Brasileirão série A 2023", "002_RJ");
		mrbet.incluirTimeCampeonato("Brasileirão série A 2023", "105_PB");
		assertEquals(4, mrbet.getTimesCampeonato("Brasileirão série A 2023").size());
		
	}
	
	@Test
	@DisplayName("Incluir time em um campeonato em que ele já foi incluído")
	void incluindoTimeEmCampeonatoRepetido() {
		
		mrbet.adicionarCampeonato("Brasileirão série A 2023", 5);
		
		assertEquals(0, mrbet.getTimesCampeonato("Brasileirão série A 2023").size());
		assertEquals("TIME INCLUIDO NO CAMPEONATO!", mrbet.incluirTimeCampeonato("Brasileirão série A 2023","250_PB"));
		assertEquals("TIME INCLUIDO NO CAMPEONATO!", mrbet.incluirTimeCampeonato("Brasileirão série A 2023","252_PB"));
		assertEquals("TIME INCLUIDO NO CAMPEONATO!", mrbet.incluirTimeCampeonato("Brasileirão série A 2023","252_PB"));
		assertEquals(2, mrbet.getTimesCampeonato("Brasileirão série A 2023").size());
	}
	
	@Test
	@DisplayName("Incluir time em um campeonato quando o time não foi cadastrado")
	void incluirTimeEmCampeonatoComTimeNaoCadastrado() {
		
		mrbet.adicionarCampeonato("Brasileirão série A 2023", 5);
		assertEquals(0, mrbet.getTimesCampeonato("Brasileirão série A 2023").size());
		assertEquals("TIME NÃO EXISTE!", mrbet.incluirTimeCampeonato("Brasileirão série A 2023", "005_PB"));
		assertEquals(0, mrbet.getTimesCampeonato("Brasileirão série A 2023").size());
	}
	
	@Test
	@DisplayName("Incluir um time em um campeonato quando o campeonato não existe")
	void incluirTimeEmCampeonatoNaoExistente() {
		
		assertEquals(0, mrbet.getCampeonatos().size());
		assertEquals("CAMPEONATO NÃO EXISTE!", mrbet.incluirTimeCampeonato("Brasileirão série A 2023", "252_PB"));
		assertEquals(0, mrbet.getCampeonatos().size());
	}
	
	@Test
	@DisplayName("Incluir time excedendo a quantidade de participantes")
	void incluirTimeExcedendoQuantDePaticipantes() {
		mrbet.adicionarCampeonato("Brasileirão série A 2023", 1);
		assertEquals(0, mrbet.getTimesCampeonato("Brasileirão série A 2023").size());
		assertEquals("TIME INCLUIDO NO CAMPEONATO!", mrbet.incluirTimeCampeonato("Brasileirão série A 2023", "252_PB"));
		assertEquals("TODOS OS TIMES DESSE CAMPEONATO JÁ FORAM INCLUÍDOS!", mrbet.incluirTimeCampeonato("Brasileirão série A 2023", "250_PB"));
		assertEquals(1, mrbet.getTimesCampeonato("Brasileirão série A 2023").size());
	}
	
	@Test
	@DisplayName("Verificar se um time pertence a um campeonato")
	void verificarSeTimePertenceAoCampeonato() {
		mrbet.adicionarCampeonato("Copa do Nordeste 2023", 10);
		mrbet.incluirTimeCampeonato("Copa do Nordeste 2023", "250_PB");
		
		assertTrue(mrbet.verificarTimeCampeonato("250_PB", "Copa do Nordeste 2023").equals("O TIME ESTÁ NO CAMPEONATO!"));
		assertTrue(mrbet.verificarTimeCampeonato("252_PB", "Copa do Nordeste 2023").equals("O TIME NÃO ESTÁ NO CAMPEONATO!"));
		assertEquals(1, mrbet.getTimesCampeonato("Copa do Nordeste 2023").size());
	}
	
	@Test
	@DisplayName(" Verificar se um time pertence a um campeonato que não foi cadastrado")
	void verificarSeTimePertenceACampeonatoQueNaoExiste() {
		
		assertEquals("O CAMPEONATO NÃO EXISTE!", mrbet.verificarTimeCampeonato("252_PB", "Brasileirão série D 2023"));
		assertEquals(0, mrbet.getCampeonatos().size());
	}
	
	@Test
	@DisplayName("Verificar se o time não cadastrado pertence a um campeonato")
	void verificarSeUmTimeNaoCadastradoPertenceAUmCampeonato() {
		mrbet.adicionarCampeonato("Copa do Nordeste 2023", 10);
		assertEquals(4, mrbet.getTimes().size());
		assertEquals("O TIME NÃO EXISTE", mrbet.verificarTimeCampeonato("005_PB","Copa do Nordeste 2023"));
	}
	
	@Test
	@DisplayName("Exibir Campeonatos do time com o time não cadastrado")
	void exibirCampeonatosDoTimeSemCadastrarTime() {
		
		assertEquals(4, mrbet.getTimes().size());
		assertEquals("TIME NÃO CADASTRADO NO SISTEMA", mrbet.exibirCampeonatosTime("251_PB"));
	}
	
	@Test
	@DisplayName("Exibir campeonatos dos times sem nenhuma restrição")
	void exibirCompeonatosTimes() {
		String resultado = "";
		String resultado1 = "";
		
		mrbet.adicionarCampeonato("Paraibano", 5);
		mrbet.adicionarCampeonato("Copa do Nordeste", 10);
		
		mrbet.incluirTimeCampeonato("Paraibano", "250_PB");
		mrbet.incluirTimeCampeonato("Copa do Nordeste", "250_PB");
		mrbet.incluirTimeCampeonato("Paraibano", "252_PB");
		
		resultado = "Campeonatos do NACIONAL de Patos:\n* Copa do Nordeste - 1/10\n* Paraibano - 2/5\n";
		resultado1 = "Campeonatos do Sport Lagoa Seca:\n* Paraibano - 2/5\n";
		assertEquals(resultado, mrbet.exibirCampeonatosTime("250_PB"));
		assertEquals(resultado1, mrbet.exibirCampeonatosTime("252_PB"));
	}
	
	@Test
	@DisplayName("Fazer aposta com entradas inválidas")
	void fazerApostaComEntradasInvalidas() {
		mrbet.adicionarCampeonato("Paraibano", 10);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			mrbet.criarAposta("", "Paraibano", 1, 1000);
			mrbet.criarAposta("250_PB", "", 1, 1000);
			mrbet.criarAposta("250_PB", "Paraibano", -2, 1000);
			mrbet.criarAposta("250_PB", "Paraibano", 1, -200);
		});
			
		String experada = "ENTRADAS INVALIDAS";
		String atual = exception.getMessage();
		
		assertEquals(experada, atual);
	}
	
	@Test
	@DisplayName("Fazer uma aposta com um time que não existe")
	void fazerApostaComTimeQueNaoExiste() {
		
		assertEquals(0, mrbet.getAposta().size());
		mrbet.adicionarCampeonato("Paraibano", 10);
		assertEquals(4, mrbet.getTimes().size()); //são os times já cadastrados
		assertEquals("O TIME NÃO EXISTE", mrbet.criarAposta("251_PB", "Paraibano", 5, 76.00));
		assertEquals(0, mrbet.getAposta().size());
	}
	
	@Test
	@DisplayName("Fazer aposta com campeonato que não existe")
	void fazerApostaComCampeonatoNaoExistente() {
		
		assertEquals("O CAMPEONATO NÃO EXISTE!", mrbet.criarAposta("250_PB", "Copa do Nordeste 2023", 1, 1000.0));
		assertEquals(0, mrbet.getAposta().size());
		assertEquals(0, mrbet.getCampeonatos().size());
	}
	
	@Test
	@DisplayName("Fazer aposta sem o time está cadastrado no campeonato")
	void fazerApostaSemOTimeEstaCadastradoNoCampeonato() {
		
		mrbet.adicionarCampeonato("Libertadores", 50);
		assertEquals("O TIME NÃO ESTÁ CADASTRADO NO CAMPEONATO", mrbet.criarAposta("250_PB", "Libertadores", 6, 700.0));
		assertEquals(0, mrbet.getAposta().size());
		assertEquals(0, mrbet.getTimesCampeonato("Libertadores").size());
	}
	
	@Test
	@DisplayName("Fazer Aposta com a colocação excedendo a quantidade máxima de times")
	void fazerApostaExcedendoAColocacao() {
		
		mrbet.adicionarCampeonato("Brasileirão série A 2023", 25);
		assertEquals(0, mrbet.getTimesCampeonato("Brasileirão série A 2023").size());
		mrbet.incluirTimeCampeonato("Brasileirão série A 2023", "250_PB");
		assertEquals(1, mrbet.getTimesCampeonato("Brasileirão série A 2023").size());
		assertEquals(0, mrbet.getAposta().size());
		assertEquals("APOSTA NÃO REGISTRADA!", mrbet.criarAposta("250_PB", "Brasileirão série A 2023", 30, 80.0));
		assertEquals(0, mrbet.getAposta().size());
	}
	
	@Test
	@DisplayName("Fazer Aposta com repetição")
	void fazerApostaComRepeticao() {
		
		mrbet.adicionarCampeonato("Paraibano", 3);
		mrbet.incluirTimeCampeonato("Paraibano", "250_PB");
		
		assertEquals(0, mrbet.getAposta().size());
		assertEquals("APOSTA REGISTRADA", mrbet.criarAposta("250_Pb", "Paraibano", 1, 50.0));
		assertEquals("APOSTA REGISTRADA", mrbet.criarAposta("250_PB", "Paraibano", 2, 100.0));
		assertEquals("APOSTA REGISTRADA", mrbet.criarAposta("250_PB", "Paraibano", 2, 100.0));
		assertEquals(3, mrbet.getAposta().size());
	}
	
	@Test
	@DisplayName("Fazer Aposta sem restrições")
	void fazerAposta() {
	
		mrbet.adicionarCampeonato("Brasileirão série A 2023", 4);
		mrbet.adicionarCampeonato("Libertadores", 6);
		mrbet.adicionarCampeonato("Paraibano", 8);
		
		mrbet.incluirTimeCampeonato("Brasileirão série A 2023", "250_PB");
		mrbet.incluirTimeCampeonato("Brasileirão série A 2023", "252_PB");
		mrbet.incluirTimeCampeonato("Brasileirão série A 2023", "002_RJ");
		mrbet.incluirTimeCampeonato("Paraibano", "105_PB");
		mrbet.incluirTimeCampeonato("Libertadores", "252_PB");
		mrbet.incluirTimeCampeonato("Libertadores", "250_PB");
		
		assertEquals(0, mrbet.getAposta().size());
		assertEquals("APOSTA REGISTRADA", mrbet.criarAposta("250_PB", "Brasileirão série A 2023", 3, 50.0));
		assertEquals("APOSTA REGISTRADA", mrbet.criarAposta("252_PB", "Libertadores", 1, 250.0));
		assertEquals("APOSTA REGISTRADA", mrbet.criarAposta("250_PB", "Libertadores", 5, 80.0));
		assertEquals("APOSTA REGISTRADA", mrbet.criarAposta("250_PB", "Brasileirão série A 2023", 3, 50.0));
		assertEquals("APOSTA REGISTRADA", mrbet.criarAposta("002_RJ", "Brasileirão série A 2023", 1, 1000.0));
		assertEquals("APOSTA REGISTRADA", mrbet.criarAposta("105_PB", "Paraibano", 4, 47.60));
		assertEquals(6, mrbet.getAposta().size());
	}
	
	@Test
	@DisplayName("Exibir todas as apostas")
	void exibirStatusAposta() {
		String resultado = "Apostas:\n"
				+ "1. [250_PB] NACIONAL de Patos / Canário\n"
				+ "Brasileirão série A 2023\n"
				+ "3/4\n"
				+ "R$ 50.0\n"
				+ "2. [002_RJ] Clube de Regatas do Flamengo / Urubu\n"
				+ "Brasileirão série A 2023\n"
				+ "1/4\n"
				+ "R$ 1000.0\n";
		
		mrbet.adicionarCampeonato("Brasileirão série A 2023", 4);
		mrbet.incluirTimeCampeonato("Brasileirão série A 2023", "250_PB");
		mrbet.incluirTimeCampeonato("Brasileirão série A 2023", "002_RJ");
		assertEquals("APOSTA REGISTRADA", mrbet.criarAposta("250_PB", "Brasileirão série A 2023", 3, 50.0));
		assertEquals("APOSTA REGISTRADA", mrbet.criarAposta("002_RJ", "Brasileirão série A 2023", 1, 1000.0));
		
		assertEquals(resultado, mrbet.statusApostas());
	}
	
	@Test
	@DisplayName("Exibir os campeonatos sem o time estar incluido em nenhum campeonato")
	void exibirCampeonatosDoTimeSemEstarIncluidoNoCampeonato() {
		String resultado = "Campeonatos do Sport Lagoa Seca:\n\nO TIME NÃO ESTÁ INCLUIDO EM NENHUM CAMPEONATO!";
		
		mrbet.adicionarCampeonato("Paraibano", 5);
		assertEquals(resultado, mrbet.exibirCampeonatosTime("252_PB"));
	}
	
}
