package three_dim_algo;
import java.util.ArrayList;
import java.util.Collections;

public class NaiveRoundAlgorithm {
	static boolean RoundFlag=false;
	public static double recursion(int[] times, double a) {
		String s="";
		double min=Integer.MAX_VALUE;
		String division="";
		double temp;
		for(int i=0; i<Math.pow(2, times.length-1); i++) {
			s=convert_to_binary(i, times.length-1);
			//System.out.println(s);
			temp=calculateDiv(s, times, a);
			if(temp<min) {
				//System.out.println(temp);
				min=temp;
				division=s;
			}
		}
//		System.out.println(//division+"\n"+
//		min);
		return min;

	}

	private static double calculateDiv(String s, int[] times, double a) {
		//String[] M1ListString=s.split(" ");
		ArrayList<Integer> M1List=new ArrayList<Integer>();
		ArrayList<Integer> M2List=new ArrayList<Integer>();
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i)=='0')
				M1List.add(i+1);
			else
				M2List.add(i+1);
		}

		for (int i = 0; i < M1List.size(); i++) {
			M1List.set(i,times[M1List.get(i)]);
		}
		for (int i = 0; i < M2List.size(); i++) {
			M2List.set(i,times[M2List.get(i)]);
		}
		Collections.sort(M1List);
		Collections.sort(M2List);  
		double M1Sum=0; double M2Sum=0;
		if(RoundFlag) {
			for (int i = 0; i <M1List.size(); i++) {
				M1Sum+=upperVal(M1List.get(i)*Math.pow(i+1,a));
			}
			for (int i = 0; i <M2List.size(); i++) {
				M2Sum+=upperVal(M2List.get(i)*Math.pow(i+1,a));
			}}
		else {
			for (int i = 0; i <M1List.size(); i++) {
				M1Sum+=M1List.get(i)*Math.pow(i+1,a);
			}
			for (int i = 0; i <M2List.size(); i++) {
				M2Sum+=M2List.get(i)*Math.pow(i+1,a);
			}}
		return Double.max(M1Sum, M2Sum);
	}
	public static int upperVal(double d) {
		if(d==(int)(d))
			return (int) d;
		else return (int)( d+1);
	}
	private static String convert_to_binary(int i, int len) {
		if (len > 0)
		{
			return String.format("%" + len + "s",
					Integer.toBinaryString(i)).replaceAll(" ", "0");
		}
		return null;
	}

	public static void main(String[] args) {
		int  times[]= {0,1,1,10};
		double a=-0.5;
		System.out.println(recursion(times, a));


	}



}
