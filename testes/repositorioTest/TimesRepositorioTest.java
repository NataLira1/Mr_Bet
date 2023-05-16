package repositorioTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import repositorio.TimesRepositorio;

class TimesRepositorioTest {

	TimesRepositorio timesRepositorio;
	
	@BeforeEach
	void setUp() throws Exception {
		timesRepositorio = new TimesRepositorio();
		timesRepositorio.incluirTime("250_PB", "Campinense", "Raposa");
	}

	@AfterEach
	void tearDown() throws Exception {
		timesRepositorio = null;
	}

	@Test
	@DisplayName("Quando vou inserir um time sem nenhuma restrição")
	void quandoVouInserirTimeSemRestricao() {
		assertEquals(1, timesRepositorio.getTimes().size());
		assertEquals("INCLUSÃO REALIZADA", timesRepositorio.incluirTime("251_PB", "Campinense", "Raposa"));
		assertEquals(2, timesRepositorio.getTimes().size());
	}
	
	@Test
	@DisplayName("Quando vou cadastrar time já cadastrado")
	void cadastrarTimeJaCadastrado() {
		
		assertEquals(1, timesRepositorio.getTimes().size());
		assertEquals("INCLUSÃO REALIZADA", timesRepositorio.incluirTime("002_RJ", "Clube de Regatas do Flamengo", "Urubu"));
		assertEquals("TIME JÁ EXISTE", timesRepositorio.incluirTime("002_RJ", "Clube de Regatas do Flamengo", "Urubu"));
		assertEquals(2, timesRepositorio.getTimes().size());
	}
	
	@Test
	@DisplayName("Quando vou cadastar um time e os dados estão inválidos")
	void cadastarComEntradasInvalididas() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			timesRepositorio.incluirTime("", "Campinense", "Raposa");
			timesRepositorio.incluirTime("251_PB", "", "Raposa");
			timesRepositorio.incluirTime("251_PB", "Campinense", " ");
		});
			
		String experada = "ENTRADAS INVALIDAS";
		String atual = exception.getMessage();
		
		assertEquals(experada, atual);
	}
	
	@Test
	@DisplayName("Quando vou verificar se o time existe")
	void quandoVouVerificarSeOTimeExiste() {
		assertEquals("INCLUSÃO REALIZADA", timesRepositorio.incluirTime("002_RJ", "Clube de Regatas do Flamengo", "Urubu"));
		assertTrue(!timesRepositorio.existeTime("002_RJ"));
		assertFalse(!timesRepositorio.existeTime("101_RJ"));
	}
	
	@Test
	@DisplayName("Quando exibir dados de um time")
	void exibirDadosDeUmTime() {
		assertEquals("[250_PB] Campinense / Raposa", timesRepositorio.recuperarTime("250_PB"));
		assertEquals("TIME NÃO EXISTE!", timesRepositorio.recuperarTime("280_SC"));
	}

}
