package logica;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("direzione")
public class Direzione {
	
	@Param(0)
	int direzione;
	
	public Direzione() {
		direzione = 0;
	}
	
	public Direzione(int direzione2) {
		direzione = direzione2;
	}

	public int getDirezione() {
		return direzione;
	}

	public void setDirezione(int direzione) {
		this.direzione = direzione;
	}

	@Override
	public String toString() {
		return "Direzione [direzione=" + direzione + "]";
	}
	

}
