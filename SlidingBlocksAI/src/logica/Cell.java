package logica;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("cell")
public class Cell {
	@Param(0)
	int riga;
	
	@Param(1)
	int colonna;

	@Param(2)
	int value;
	
//	@Param(3)
//	int gipf;

	public Cell() {}
	
	public Cell(int riga, int colonna, int value) {
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
		return "Cell [riga=" + riga + ", colonna=" + colonna + ", value=" + value + "]";
	}
	
	
}
