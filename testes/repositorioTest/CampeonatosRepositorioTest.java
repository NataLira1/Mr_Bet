package repositorioTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import repositorio.CampeonatosRepositorio;
import repositorio.TimesRepositorio;

class CampeonatosRepositorioTest {

	CampeonatosRepositorio campeonatosRepositorio;
	TimesRepositorio timesRepositorio;
	
	
	@BeforeEach
	void setUp() throws Exception {
		campeonatosRepositorio = new CampeonatosRepositorio(timesRepositorio = new TimesRepositorio());
		timesRepositorio.incluirTime("250_PB", "NACIONAL de Patos", "Canário");
		timesRepositorio.incluirTime("252_PB", "Sport Lagoa Seca", "Carneiro");
		timesRepositorio.incluirTime("002_RJ", "Clube de Regatas do Flamengo", "Urubu");
		timesRepositorio.incluirTime("105_PB", "Sociedade Recreativa de Monteiro (SOCREMO)", "Gavião");
	}

	@AfterEach
	void tearDown() throws Exception {
		campeonatosRepositorio = null;
	}

	@Test
	@DisplayName("Quando vou adicionar um campeonato com entradas inválidas")
	void adicionarCampeonatoComEntradasInvalidas() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			campeonatosRepositorio.adicionarCampeonato("", 10);
			campeonatosRepositorio.adicionarCampeonato("Copa do Nordeste", -2);
		});
			
		String experada = "ENTRADAS INVALIDAS";
		String atual = exception.getMessage();
		
		assertEquals(experada, atual);
	}

	@Test
	@DisplayName("Cadastrar campeonato sem restrição")
	void cadastrarCampeonato() {
		assertEquals(0 , campeonatosRepositorio.getCampeonatos().size());
		assertEquals("INCLUSÃO REALIZADA!",campeonatosRepositorio.adicionarCampeonato("Brasileirão série A 2023", 5));
		assertEquals(1 ,campeonatosRepositorio.getCampeonatos().size());
	}
	
	@Test
	@DisplayName("Quando preciso cadastrar um campeonato que já existe")
	void quandoPrecisoCadastrarCampeonato() {
		assertEquals(0 , campeonatosRepositorio.getCampeonatos().size());
		assertEquals("INCLUSÃO REALIZADA!", campeonatosRepositorio.adicionarCampeonato("Brasileirão série A 2023", 5));
		assertEquals(1 ,campeonatosRepositorio.getCampeonatos().size());
		assertEquals("CAMPEONATO JÁ EXISTE!", campeonatosRepositorio.adicionarCampeonato("Brasileirão série A 2023", 3));
		assertEquals(1 , campeonatosRepositorio.getCampeonatos().size());
		
	}
	
	@Test
	@DisplayName("Quando preciso incluir time em um campeonato")
	void incluindoTimeEmCampeonato() {
		timesRepositorio.incluirTime("250_PB", "NACIONAL de Patos", "Canário");
		assertEquals(0 , campeonatosRepositorio.getCampeonatos().size());
		assertEquals("INCLUSÃO REALIZADA!", campeonatosRepositorio.adicionarCampeonato("Brasileirão série A 2023", 5));
		assertEquals(1 , campeonatosRepositorio.getCampeonatos().size());
		assertEquals(0, campeonatosRepositorio.getTimeCampeonato("Brasileirão série A 2023").size());
		assertEquals("TIME INCLUIDO NO CAMPEONATO!",campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023", "250_PB"));
		assertEquals("TIME INCLUIDO NO CAMPEONATO!", campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023", "252_PB"));
		assertEquals(2, campeonatosRepositorio.getTimeCampeonato("Brasileirão série A 2023").size());
	}
	
	@Test
	@DisplayName("Incluir times em um campeonato até a quantidade máxima de times")
	void incluindoTimesCampeonatoAoLimite() {
		
		assertEquals(0, campeonatosRepositorio.getTimeCampeonato("Brasileirão série A 2023").size());
		campeonatosRepositorio.adicionarCampeonato("Brasileirão série A 2023", 4);
		campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023", "250_PB");
		campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023", "252_PB");
		campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023", "002_RJ");
		campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023", "105_PB");
		assertEquals(4, campeonatosRepositorio.getTimeCampeonato("Brasileirão série A 2023").size());
		
	}
	
	@Test
	@DisplayName("Incluir time em um campeonato em que ele já foi incluído")
	void incluindoTimeEmCampeonatoRepetido() {
		
		campeonatosRepositorio.adicionarCampeonato("Brasileirão série A 2023", 5);
		
		assertEquals(0, campeonatosRepositorio.getTimeCampeonato("Brasileirão série A 2023").size());
		assertEquals("TIME INCLUIDO NO CAMPEONATO!", campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023","250_PB"));
		assertEquals("TIME INCLUIDO NO CAMPEONATO!", campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023","252_PB"));
		assertEquals("TIME INCLUIDO NO CAMPEONATO!", campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023","252_PB"));
		assertEquals(2, campeonatosRepositorio.getTimeCampeonato("Brasileirão série A 2023").size());
	}
	
	@Test
	@DisplayName("Incluir time em um campeonato quando o time não foi cadastrado")
	void incluirTimeEmCampeonatoComTimeNaoCadastrado() {
		
		campeonatosRepositorio.adicionarCampeonato("Brasileirão série A 2023", 5);
		assertEquals(0, campeonatosRepositorio.getTimeCampeonato("Brasileirão série A 2023").size());
		assertEquals("TIME NÃO EXISTE!", campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023", "005_PB"));
		assertEquals(0, campeonatosRepositorio.getTimeCampeonato("Brasileirão série A 2023").size());
	}
	
	@Test
	@DisplayName("Incluir um time em um campeonato quando o campeonato não existe")
	void incluirTimeEmCampeonatoNaoExistente() {
		
		assertEquals(0, campeonatosRepositorio.getCampeonatos().size());
		assertEquals("CAMPEONATO NÃO EXISTE!", campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023", "252_PB"));
		assertEquals(0,campeonatosRepositorio.getCampeonatos().size());
	}
	
	@Test
	@DisplayName("Incluir time excedendo a quantidade de participantes")
	void incluirTimeExcedendoQuantDePaticipantes() {
		campeonatosRepositorio.adicionarCampeonato("Brasileirão série A 2023", 1);
		assertEquals(0, campeonatosRepositorio.getTimeCampeonato("Brasileirão série A 2023").size());
		assertEquals("TIME INCLUIDO NO CAMPEONATO!", campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023", "252_PB"));
		assertEquals("TODOS OS TIMES DESSE CAMPEONATO JÁ FORAM INCLUÍDOS!", campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023", "250_PB"));
		assertEquals(1, campeonatosRepositorio.getTimeCampeonato("Brasileirão série A 2023").size());
	}
	
	@Test
	@DisplayName("Verificar se um time pertence a um campeonato")
	void verificarSeTimePertenceAoCampeonato() {
		campeonatosRepositorio.adicionarCampeonato("Copa do Nordeste 2023", 10);
		campeonatosRepositorio.incluirTimeCampeonato("Copa do Nordeste 2023", "250_PB");
		
		assertTrue(campeonatosRepositorio.verificarTimeCampeonato("250_PB", "Copa do Nordeste 2023").equals("O TIME ESTÁ NO CAMPEONATO!"));
		assertTrue(campeonatosRepositorio.verificarTimeCampeonato("252_PB", "Copa do Nordeste 2023").equals("O TIME NÃO ESTÁ NO CAMPEONATO!"));
		assertEquals(1, campeonatosRepositorio.getTimeCampeonato("Copa do Nordeste 2023").size());
	}
	
	@Test
	@DisplayName(" Verificar se um time pertence a um campeonato que não foi cadastrado")
	void verificarSeTimePertenceACampeonatoQueNaoExiste() {
		
		assertEquals("O CAMPEONATO NÃO EXISTE!", campeonatosRepositorio.verificarTimeCampeonato("252_PB", "Brasileirão série D 2023"));
		assertEquals(0, campeonatosRepositorio.getCampeonatos().size());
	}
	
	@Test
	@DisplayName("Verificar se o time não cadastrado pertence a um campeonato")
	void verificarSeUmTimeNaoCadastradoPertenceAUmCampeonato() {
		campeonatosRepositorio.adicionarCampeonato("Copa do Nordeste 2023", 10);
		assertEquals(4, timesRepositorio.getTimes().size());
		assertEquals("O TIME NÃO EXISTE", campeonatosRepositorio.verificarTimeCampeonato("005_PB","Copa do Nordeste 2023"));
	}
	
	@Test
	@DisplayName("Exibir Campeonatos do time com o time não cadastrado")
	void exibirCampeonatosDoTimeSemCadastrarTime() {
		
		assertEquals(4, timesRepositorio.getTimes().size());
		assertEquals("TIME NÃO CADASTRADO NO SISTEMA", campeonatosRepositorio.exibirCampeonatosTime("251_PB"));
	}
	
	@Test
	@DisplayName("Exibir campeonatos dos times sem nenhuma restrição")
	void exibirCompeonatosTimes() {
		String resultado = "";
		String resultado1 = "";
		
		campeonatosRepositorio.adicionarCampeonato("Paraibano", 5);
		campeonatosRepositorio.adicionarCampeonato("Copa do Nordeste", 10);
		
		campeonatosRepositorio.incluirTimeCampeonato("Paraibano", "250_PB");
		campeonatosRepositorio.incluirTimeCampeonato("Copa do Nordeste", "250_PB");
		campeonatosRepositorio.incluirTimeCampeonato("Paraibano", "252_PB");
		
		resultado = "Campeonatos do NACIONAL de Patos:\n* Copa do Nordeste - 1/10\n* Paraibano - 2/5\n";
		resultado1 = "Campeonatos do Sport Lagoa Seca:\n* Paraibano - 2/5\n";
		assertEquals(resultado, campeonatosRepositorio.exibirCampeonatosTime("250_PB"));
		assertEquals(resultado1, campeonatosRepositorio.exibirCampeonatosTime("252_PB"));
	}
	
	@Test
	@DisplayName("Exibir os campeonatos sem o time estar incluido em nenhum campeonato")
	void exibirCampeonatosDoTimeSemEstarIncluidoNoCampeonato() {
		String resultado = "Campeonatos do Sport Lagoa Seca:\n\nO TIME NÃO ESTÁ INCLUIDO EM NENHUM CAMPEONATO!";
		
		campeonatosRepositorio.adicionarCampeonato("Paraibano", 5);
		assertEquals(resultado, campeonatosRepositorio.exibirCampeonatosTime("252_PB"));
	}
	
	@Test
	@DisplayName("Verificar se o campeonato existe")
	void verificarSeOCampeonatoExiste() {
		campeonatosRepositorio.adicionarCampeonato("Paraibano", 5);
		assertTrue(!campeonatosRepositorio.existeCampeonato("Paraibano"));
		assertFalse(!campeonatosRepositorio.existeCampeonato("Libertadores"));
	}
	
	@Test
	@DisplayName("Quando vou verificar a quantidade máxima de times de um campeonato")
	void quantidadeMaximaDeTime() {
		campeonatosRepositorio.adicionarCampeonato("Paraibano", 5);
		assertEquals(5, campeonatosRepositorio.quantMaxCampeonato("Paraibano"));
		
	}
	
	@Test
	@DisplayName("Quando vou contar a quantidade de times cadastrados em um campeonato")
	void contarTimesEmCampeonato() {
		
		campeonatosRepositorio.adicionarCampeonato("Paraibano", 5);
		campeonatosRepositorio.adicionarCampeonato("Copa do Nordeste", 10);
		
		campeonatosRepositorio.incluirTimeCampeonato("Paraibano", "250_PB");
		campeonatosRepositorio.incluirTimeCampeonato("Copa do Nordeste", "250_PB");
		campeonatosRepositorio.incluirTimeCampeonato("Paraibano", "252_PB");
		
		assertEquals(2, campeonatosRepositorio.contaTimesPorCampeonato("Paraibano"));
		assertEquals(1, campeonatosRepositorio.contaTimesPorCampeonato("Copa do Nordeste"));
	}
	
	@Test
	@DisplayName("Verificar se time pertence a campeonato")
	void verificarSeTimePertenceCampeonato() {
		campeonatosRepositorio.adicionarCampeonato("Paraibano", 5);
		campeonatosRepositorio.incluirTimeCampeonato("Paraibano", "250_PB");
		assertTrue(campeonatosRepositorio.campeonatoContemTime("250_PB","Paraibano"));
		assertFalse(campeonatosRepositorio.campeonatoContemTime("105_PB","Paraibano"));
	}
}
