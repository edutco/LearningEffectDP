package three_dim_algo;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * this class solves the rounded learning effect on 2 machines when the learning function
 *  for mission j in the k place is upperVal(p_j*(k^a))
 * by DP algorithm and 3D matrix
 * m[j][r][p]=x
 * s.t. if we set M1 to get r jobs with makespan <= p, M2 gets j-r jobs with makespan x (the minimum that is possible)
 *
 */

public class RoundLE3dim {

	//static int missionsNum=3;
	//static int  price[]= new int[missionsNum+1];
	//
	static boolean Round_Flag=false;




	public static double IntMat(int [] times1, double a) {
		int [] times=new int [times1.length];
		for (int i = 0; i < times.length; i++) {
			times[i]=times1[i]*(times1.length-1);
		}
		Arrays.sort(times);
		if(a>0) {
			int n=times.length;
			for(int i=1; i<(n+1)/2; i++) {
				int temp= times[i];
				times[i]=times[n-i];
				times[n-i]=temp;
			} 
		}
		int missionsNum=times.length-1;
		int  price[]= new int[missionsNum+1];
		price[0]=0;
		for(int i=1; i<price.length; i++){
			price[i]=upperVal(times[i]*Math.pow(i,a))+price[i-1];
		}
		int f= (int) (price[missionsNum])+1; //upper bound for answer
		IS m[][][]= new IS [missionsNum+1][missionsNum+1][f+1];
		for(int j=0; j<missionsNum+1; j++) {
			for(int r=0; r<missionsNum+1; r++) {
				for(int p=0; p<=f; p++) {	
					m[j][r][p]=new IS();
//					m[j][r][p].setNum(Integer.MAX_VALUE);
//					m[j][r][p].setS("");
					if(r>j)
						continue;
					if(j==r) { //M1 should take j first jobs if possible
						if(p>=price[j]) {
							m[j][r][p].setNum(0);
							m[j][r][p].setS(oneToJ(j));
						}
						continue;
					}
					if(r==0) {//M2 should take j first jobs if possible
						m[j][r][p].setNum(price[j]);
						continue;
					}
					if(p==0 && r!=0) {  // M1 is out of time and no out of jobs
						continue;
					}
					boolean M1=(p-calc(j,r,times, a)>=0); //p can pay for job j in place (r)
					IS ansM1=new IS();
					ansM1.setNum(Integer.MAX_VALUE);
					int newP=0;
					if(M1) {
						newP=upperVal(p-times[j]*Math.pow(r,a));//calc(j,r, times, a)); //after paying for job j how much time is left for M1
						ansM1.setNum(m[j-1][r-1][newP].getNum());
						ansM1.setS(m[j-1][r-1][newP].getS()+j+" ");
					}
					IS ansM2= new IS();
					ansM2.setNum(m[j-1][r][p].getNum());
					ansM2.setS(m[j-1][r][p].getS());
					if(ansM2.getNum()!=Integer.MAX_VALUE) {
						ansM2.setNum((int) (ansM2.getNum()+times[j]*Math.pow(j-r,a)));//upperVal(calc(j,j-r,times, a)));					
					}
					if(ansM1.getNum() < ansM2.getNum()) {
						m[j][r][p]=ansM1;
					}
					else {
						m[j][r][p]=ansM2;
					}
				}
			}
		}

		IS ansCell = null;
		int ans=Integer.MAX_VALUE;
		for(int i=1; i<=f ; i++) {
			for(int r=0; r<=missionsNum  ; r++) {
				if(ans > Integer.max(m[missionsNum][r][i].getNum(), i) ) {
					ans=Integer.max(m[missionsNum][r][i].getNum(), i); 
					ansCell=m[missionsNum][r][i];
				}
			}
		}
		return divide(ansCell.getS(), missionsNum, times, a);
//		for (int i = 0; i < m.length; i++) {
//			System.out.println("\n\n************** i="+i+" *************");
//			print2Dmatrix(m[i]);
//		}
	}

	private static String oneToJ(int j) {
		String s="1 ";
		for(int i=2;i<=j; i++) {
			s+=i+" ";
		}
		return s;
	}

	private static double divide(String s,  int missionsNum, int [] times, double a) {
		if(s.length()==0)
			//return "empty string";
			s=oneToJ(missionsNum);	
		String[] M1ListString=s.split(" ");
		ArrayList<Integer> M1List=new ArrayList<Integer>();
		for (int i = 0; i < M1ListString.length; i++) {
			M1List.add(Integer.parseInt(M1ListString[i]));
		}
		ArrayList<Integer> M2List=new ArrayList<Integer>();
		for (int i = 1; i <= missionsNum; i++) {
			if(!M1List.contains(i))
				M2List.add(i);
		}
		for (int i = 0; i < M1List.size(); i++) {
			M1List.set(i,(times[M1List.get(i)]/missionsNum));
		}
		for (int i = 0; i < M2List.size(); i++) {
			M2List.set(i,(times[M2List.get(i)]/missionsNum));
		}
		String [] M1All=calcPrices(M1List, a);
		String [] M2All=calcPrices(M2List, a);
//		System.out.println("makespans are ("+M1All[1]+","+M2All[1]+
//				")\n"
//				+ 
//				"C_max="+
//				Double.max(Double.parseDouble(M1All[1]),Double.parseDouble(M2All[1]))
//				+ "\nM1 List is ="+M1List.toString()+"\ncosts are: "+M1All[0]
//				+"\nM2 List is "+M2List.toString()+"\ncosts are: "+M2All[0]
//				);
		return //"makespans are ("+M1All[1]+","+M2All[1]
				//+
				//")\n"
				//+ 
				//"C_max="+
				Double.max(Double.parseDouble(M1All[1]),Double.parseDouble(M2All[1]))
				//+ "\nM1 List is ="+M1List.toString()+"\ncosts are: "+M1All[0]
				//+"\nM2 List is "+M2List.toString()+"\ncosts are: "+M2All[0]
				;
	}


	private static String [] calcPrices(ArrayList<Integer> mList, double a) {
		String s="";
		double sum=0;
		for (int i = 0; i < mList.size(); i++) {
			s+=calcByCost(mList.get(i), i+1, a)+" ";
			sum+=calcByCost(mList.get(i), i+1, a);
		}
		String [] ans= {s, sum+""};
		return ans;
	}

	public static double calc(int missionNum, int loc, int [] times, double a) {
		if(Round_Flag)
			return upperVal(times[missionNum]*Math.pow(loc,a));
		return times[missionNum]*Math.pow(loc,a);
	}

	public static double calcByCost(int missionCost, int loc, double a) {
		if(Round_Flag)
			return upperVal(missionCost*Math.pow(loc,a));
		return missionCost*Math.pow(loc,a);
	}

	public static int upperVal(double d) {
		if(d==(int)(d))
			return (int) d;
		else return (int)( d+1);
	}
	public static void print2Dmatrix(IS [] [] mat) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				if(mat[i][j].getNum()==Integer.MAX_VALUE)
					System.out.print("inf  ");

				else {
					String toPrint=mat[i][j].toString();
					System.out.print(toPrint);
					for(int k=toPrint.length(); k<5; k++){
						System.out.print(" ");
					}
				}
			}
			System.out.println();

		}
	}

	public static void main(String[] args) {
		int  times[]= {0,1,4,8,12};//20,21,22,24,24,12,10,5,7,12};
		double a=-0.32;
		//int [] Reducted_times=new
		long st_dp= System.currentTimeMillis();
		System.out.println(IntMat(times, a));
		long et_dp= System.currentTimeMillis();
		//System.out.println("time= "+(et_dp-st_dp));


	}
}




