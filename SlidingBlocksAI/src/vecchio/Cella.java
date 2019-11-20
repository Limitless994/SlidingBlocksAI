package vecchio;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("Cella")
public class Cella {
	@Param(0)
	int riga;
	
	@Param(1)
	int colonna;

	@Param(2)
	int value;

	public Cella() {}
	
	public Cella(int riga, int colonna, int value) {
		this.riga = riga;
		this.colonna = colonna;
		this.value = value;
	}

	public int getriga() {
		return riga;
	}

	public void setriga(int riga) {
		this.riga = riga;
	}

	public int getcolonna() {
		return colonna;
	}

	public void setcolonna(int colonna) {
		this.colonna = colonna;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Cella [riga=" + riga + ", colonna=" + colonna + ", valore=" + value + "]";
	}
	
	
}
