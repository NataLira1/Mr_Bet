package metodo;

public class TimeCampeonato {

	private String codigoTime;
	private String nomeCampeonato;
	
	
	public TimeCampeonato( String nomeCampeonato, String codigoTime) {
		this.nomeCampeonato = nomeCampeonato;
		this.codigoTime = codigoTime;
	}
	

	public String getCodigoTime() {
		return codigoTime;
	}

	public String getNomeCampeonato() {
		return nomeCampeonato;
	}

	@Override
	public String toString() {
		return "TimeCampeonato [codigoTime=" + codigoTime + ", nomeCampeonato=" + nomeCampeonato + "]";
	}
	
	
}
