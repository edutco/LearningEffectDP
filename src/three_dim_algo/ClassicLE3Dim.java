package three_dim_algo;
import java.util.ArrayList;
import java.util.Arrays;

import four_dim_algo.couple;
/**
 * this class solves the rounded learning effect on 2 machines when the learning function
 *  for mission j in the k place is p_j*(k^a)
 * by DP algorithm and 3D matrix
 * m[j][r][p]=(x,y)
 * s.t. if we set M1 to get r jobs with makespan=x <= p, M2 gets j-r jobs with makespan y (the minimum that is possible)
 *
 */

public class ClassicLE3Dim {

	static int missionsNum=3;
	static double  price[]= new double[missionsNum+1];
	static int  times[]= {0,1,1,6};
	static double a=-0.5;





	public static void IntMat() {
		int f= (int) (price[missionsNum])+1; //upper bound for answer
		couple m[][][]= new couple [missionsNum+1][missionsNum+1][f+1];
		for(int j=0; j<missionsNum+1; j++) {
			for(int r=0; r<missionsNum+1; r++) {
				for(int p=0; p<=f; p++) {	
					m[j][r][p]=new couple();
					if(r>j)
						continue;
					if(j==r) { //M1 should take j first jobs if possible
						if(p>=price[j]) {
							m[j][r][p]= new couple(price[j],0, oneToJ(j));
						}
						continue;
					}
					if(r==0) {//M2 should take j first jobs if possible
						m[j][r][p]= new couple(0,price[j],"");
						continue;
					}
					if(p==0 && r!=0) {  // M1 is out of time and no out of jobs
						continue;
					}

					boolean M1=(p-calc(j,r)>=0); //p can pay for job j in place (r)
					couple M1Ans=new couple();
					int newP=0;
					if(M1) {
						newP=p-upperVal(calc(j,r)); //after paying for job j how much time is left for M1
						couple M1origin=new couple(m[j-1][r-1][newP]);
						if(M1origin.getX()!=-1) {
							M1origin.addX(calc(j,r));
							M1Ans= new couple(M1origin.getX(), M1origin.getY(), M1origin.s+j+" ");
						}
					}

					couple M2Ans=new couple();	
					couple M2origin=new couple(m[j-1][r][p]);
					if(M2origin.getX()!=-1) {
						M2origin.addY(calc(j,j-r));
						M2Ans= new couple(M2origin.getX(), M2origin.getY(), M2origin.s);
					}
					couple ans=couple.min(M1Ans, M2Ans); //choose better option
					m[j][r][p]=ans;
				}

			}
		}
	

			couple ansCell = null;
			double ans=Double.MAX_VALUE;
			for(int i=1; i<=f ; i++) {
				for(int r=0; r<=missionsNum  ; r++) {
					if(ans > m[missionsNum][r][i].max())  {
						//System.out.println("good");
						ans=m[missionsNum][r][i].max(); 
						ansCell=m[missionsNum][r][i];
					}
				}
			}
			System.out.println(ans+"\nC_max= "+ ans+"\n"+ divide(ansCell.s));
//			for (int i = 0; i < m.length; i++) {
//				System.out.println("\n\n************** i="+i+" *************");
//				print2Dmatrix(m[i]);
//			}
//		}
	}
	private static String oneToJ(int j) {
		String s="1 ";
		for(int i=2;i<=j; i++) {
			s+=i+" ";
		}
		return s;
	}

	private static String divide(String s) {
		if(s.length()==0)
			return "empty string";
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
			M1List.set(i,times[M1List.get(i)]);
		}
		for (int i = 0; i < M2List.size(); i++) {
			M2List.set(i,times[M2List.get(i)]);
		}
		return "M1 List is ="+M1List.toString()+"\ncosts are: "+calcPrices(M1List)
		+"\nM2 List is "+M2List.toString()+"\ncosts are: "+calcPrices(M2List)
		;
	}


	private static String calcPrices(ArrayList<Integer> mList) {
		String s="";
		for (int i = 0; i < mList.size(); i++) {
			s+=calcByCost(mList.get(i), i+1)+" ";

		}
		return s;
	}

	public static double calc(int missionNum, int loc) {
		return times[missionNum]*Math.pow(loc,a);
	}

	public static double calcByCost(int missionCost, int loc) {
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
		Arrays.sort(times);
		if(a>0) {
			int n=times.length;
			for(int i=1; i<(n+1)/2; i++) {
				int temp= times[i];
				times[i]=times[n-i];
				times[n-i]=temp;
			} 
		}

		price[0]=0;
		for(int i=1; i<price.length; i++){
			price[i]=(times[i]*Math.pow(i,a))+price[i-1];
		//	System.out.println(price[i]);
		}
		IntMat();


	}
}




