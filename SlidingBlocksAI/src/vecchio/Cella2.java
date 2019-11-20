package vecchio;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("Cella2")
public class Cella2 {
	@Param(0)
private int x;
	@Param(1)
private int y;
	@Param(2)
private int id;
	@Param(3)
private int n;

public Cella2(int x, int y, int id,int n) {
	this.x = x;
	this.y = y;
	this.id = id;
	this.n=n;
}
public int getX() {
	return x;
}
public void setX(int x) {
	this.x = x;
}
public int getY() {
	return y;
}
public void setY(int y) {
	this.y = y;
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

@Override
public String toString() {
	return "SLD [x=" + x + ", y=" + y + ", id=" + id + ", n=" + n + "]";
}
public void setN(int n) {
	this.n = n;
}
}
