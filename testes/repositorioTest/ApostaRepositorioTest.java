package repositorioTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import repositorio.ApostaRepositorio;
import repositorio.CampeonatosRepositorio;
import repositorio.TimesRepositorio;

class ApostaRepositorioTest {

	ApostaRepositorio apostaRepositorio;
	TimesRepositorio timesRepositorio;
	CampeonatosRepositorio campeonatosRepositorio;
	
	@BeforeEach
	void setUp(){
		apostaRepositorio = new ApostaRepositorio(campeonatosRepositorio = new CampeonatosRepositorio(timesRepositorio = new TimesRepositorio()), timesRepositorio);
		timesRepositorio.incluirTime("250_PB", "NACIONAL de Patos", "Canário");
		timesRepositorio.incluirTime("252_PB", "Sport Lagoa Seca", "Carneiro");
		timesRepositorio.incluirTime("002_RJ", "Clube de Regatas do Flamengo", "Urubu");
		timesRepositorio.incluirTime("105_PB", "Sociedade Recreativa de Monteiro (SOCREMO)", "Gavião");
	}

	@AfterEach
	void tearDown(){
		apostaRepositorio = null;
	}

	@Test
	@DisplayName("Fazer aposta com entradas inválidas")
	void fazerApostaComEntradasInvalidas() {
		campeonatosRepositorio.adicionarCampeonato("Paraibano", 10);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			apostaRepositorio.criarAposta("", "Paraibano", 1, 1000);
			apostaRepositorio.criarAposta("250_PB", "", 1, 1000);
			apostaRepositorio.criarAposta("250_PB", "Paraibano", -2, 1000);
			apostaRepositorio.criarAposta("250_PB", "Paraibano", 1, -200);
		});
			
		String experada = "ENTRADAS INVALIDAS";
		String atual = exception.getMessage();
		
		assertEquals(experada, atual);
	}

	@Test
	@DisplayName("Fazer uma aposta com um time que não existe")
	void fazerApostaComTimeQueNaoExiste() {
		
		assertEquals(0, apostaRepositorio.getApostas().size());
		campeonatosRepositorio.adicionarCampeonato("Paraibano", 10);
		assertEquals(4, timesRepositorio.getTimes().size()); //são os times já cadastrados
		assertEquals("O TIME NÃO EXISTE", apostaRepositorio.criarAposta("251_PB", "Paraibano", 5, 76.00));
		assertEquals(0,apostaRepositorio.getApostas().size());
	}
	
	@Test
	@DisplayName("Fazer aposta com campeonato que não existe")
	void fazerApostaComCampeonatoNaoExistente() {
		
		assertEquals("O CAMPEONATO NÃO EXISTE!", apostaRepositorio.criarAposta("250_PB", "Copa do Nordeste 2023", 1, 1000.0));
		assertEquals(0, apostaRepositorio.getApostas().size());
		assertEquals(0, campeonatosRepositorio.getCampeonatos().size());
	}
	
	@Test
	@DisplayName("Fazer aposta sem o time está cadastrado no campeonato")
	void fazerApostaSemOTimeEstaCadastradoNoCampeonato() {
		
		campeonatosRepositorio.adicionarCampeonato("Libertadores", 50);
		assertEquals("O TIME NÃO ESTÁ CADASTRADO NO CAMPEONATO", apostaRepositorio.criarAposta("250_PB", "Libertadores", 6, 700.0));
		assertEquals(0, apostaRepositorio.getApostas().size());
		assertEquals(0, campeonatosRepositorio.getTimeCampeonato("Libertadores").size());
	}
	
	@Test
	@DisplayName("Fazer Aposta com a colocação excedendo a quantidade máxima de times")
	void fazerApostaExcedendoAColocacao() {
		
		campeonatosRepositorio.adicionarCampeonato("Brasileirão série A 2023", 25);
		assertEquals(0, campeonatosRepositorio.getTimeCampeonato("Brasileirão série A 2023").size());
		campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023", "250_PB");
		assertEquals(1, campeonatosRepositorio.getTimeCampeonato("Brasileirão série A 2023").size());
		assertEquals(0, apostaRepositorio.getApostas().size());
		assertEquals("APOSTA NÃO REGISTRADA!", apostaRepositorio.criarAposta("250_PB", "Brasileirão série A 2023", 30, 80.0));
		assertEquals(0, apostaRepositorio.getApostas().size());
	}
	
	@Test
	@DisplayName("Fazer Aposta com repetição")
	void fazerApostaComRepeticao() {
		
		campeonatosRepositorio.adicionarCampeonato("Paraibano", 3);
		campeonatosRepositorio.incluirTimeCampeonato("Paraibano", "250_PB");
		
		assertEquals(0, apostaRepositorio.getApostas().size());
		assertEquals("APOSTA REGISTRADA", apostaRepositorio.criarAposta("250_Pb", "Paraibano", 1, 50.0));
		assertEquals("APOSTA REGISTRADA",apostaRepositorio.criarAposta("250_PB", "Paraibano", 2, 100.0));
		assertEquals("APOSTA REGISTRADA", apostaRepositorio.criarAposta("250_PB", "Paraibano", 2, 100.0));
		assertEquals(3, apostaRepositorio.getApostas().size());
	}
	
	@Test
	@DisplayName("Fazer Aposta sem restrições")
	void fazerAposta() {
	
		campeonatosRepositorio.adicionarCampeonato("Brasileirão série A 2023", 4);
		campeonatosRepositorio.adicionarCampeonato("Libertadores", 6);
		campeonatosRepositorio.adicionarCampeonato("Paraibano", 8);
		
		campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023", "250_PB");
		campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023", "252_PB");
		campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023", "002_RJ");
		campeonatosRepositorio.incluirTimeCampeonato("Paraibano", "105_PB");
		campeonatosRepositorio.incluirTimeCampeonato("Libertadores", "252_PB");
		campeonatosRepositorio.incluirTimeCampeonato("Libertadores", "250_PB");
		
		assertEquals(0, apostaRepositorio.getApostas().size());
		assertEquals("APOSTA REGISTRADA", apostaRepositorio.criarAposta("250_PB", "Brasileirão série A 2023", 3, 50.0));
		assertEquals("APOSTA REGISTRADA", apostaRepositorio.criarAposta("252_PB", "Libertadores", 1, 250.0));
		assertEquals("APOSTA REGISTRADA", apostaRepositorio.criarAposta("250_PB", "Libertadores", 5, 80.0));
		assertEquals("APOSTA REGISTRADA", apostaRepositorio.criarAposta("250_PB", "Brasileirão série A 2023", 3, 50.0));
		assertEquals("APOSTA REGISTRADA", apostaRepositorio.criarAposta("002_RJ", "Brasileirão série A 2023", 1, 1000.0));
		assertEquals("APOSTA REGISTRADA", apostaRepositorio.criarAposta("105_PB", "Paraibano", 4, 47.60));
		assertEquals(6, apostaRepositorio.getApostas().size());
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
		
		campeonatosRepositorio.adicionarCampeonato("Brasileirão série A 2023", 4);
		campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023", "250_PB");
		campeonatosRepositorio.incluirTimeCampeonato("Brasileirão série A 2023", "002_RJ");
		assertEquals("APOSTA REGISTRADA",  apostaRepositorio.criarAposta("250_PB", "Brasileirão série A 2023", 3, 50.0));
		assertEquals("APOSTA REGISTRADA",  apostaRepositorio.criarAposta("002_RJ", "Brasileirão série A 2023", 1, 1000.0));
		
		assertEquals(resultado, apostaRepositorio.statusApostas());
	}

}
