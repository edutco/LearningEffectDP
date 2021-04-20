
public class couple {
	private double x,y;
	public String s;
	public couple() {
		this.x = -1;
		this.y=-1;
		s="";
	}
	public couple(double x, double y) {
		this.x = x;
		this.y=y;
		s="";
	}
	public couple(couple other) {
		this.x = other.x;
		this.y=other.y;
		s=new String(other.s);
	}
	public couple(double x, double y, String _s) {
		this.x = x;
		this.y=y;
		this.s=_s;
	}
	public void copy(double x, double y, String _s) {
		this.x = x;
		this.y=y;
		this.s=_s;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public static couple min(couple a, couple b) {
		double aMax, bMax;
		aMax= Double.max(a.x, a.y);
		bMax= Double.max(b.x, b.y);
		if( aMax !=-1 && bMax ==-1)
			return a;
		if( aMax ==-1 && bMax !=-1)
			return b;
		if(aMax !=-1 && bMax !=-1) {
			if(aMax <= bMax) return a;
			return b;
		}
		return new couple();
			
	}
	public static couple minM1(couple a, couple b) {
		double aMax, bMax;
		aMax= Double.max(a.x, a.y);
		bMax= Double.max(b.x, b.y);
		if( aMax !=-1 && bMax ==-1)
			return a;
		if( aMax ==-1 && bMax !=-1)
			return b;
		if(aMax !=-1 && bMax !=-1) {
			if(a.x < b.x) return a;
			return b;
		}
		return new couple();
			
	}
	
	public String toString() {
		return "("+x+","+y+")";//  s="+s;
	}
	public double max() {
		return Double.max(x,y);
	}
	public void addY(double c) {
		if(y!=-1 && x!=-1)
			y+=c;
		
	}
	public void addX(double c) {
		if(y!=-1 && x!=-1)
			x+=c;
		
	}
}
