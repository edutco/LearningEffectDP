package three_dim_algo;
//object that represents int and string
public class IS {

	private int num;
	private String s;
	public IS() {
		num=Integer.MAX_VALUE;
		s="";
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	public String toString() {
		return ""+num;
	}
}
