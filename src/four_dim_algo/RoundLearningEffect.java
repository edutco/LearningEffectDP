package four_dim_algo;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * this class solves the rounded learning effect on 2 machines when the learning function
 *  for mission j in the k place is upperVal(p_j*(k^a))
 * by DP algorithm
 *
 */

public class RoundLearningEffect {

	static int missionsNum=4;
	static int  price[]= new int[missionsNum+1];
	static int  times[]= {0,10,10,10,100};
	static double a=-0.5;

	 // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE




	public static void numaricMat() {
		int f= (int) (price[missionsNum])+1; //upper bound for answer
		couple m[][][][]= new couple [missionsNum+1][missionsNum+1][f+1][f+1];
		for(int j=0; j<missionsNum+1; j++) {
			for(int r=0; r<=j; r++) {
				for(int p=0; p<=f; p++) {
					for( int e=0; e<=f; e++) {
						m[j][r][p][e]= new couple();
						if(j==r ) { //M1 should take j first jobs if possible
							if(p>=price[j]) {
								m[j][r][p][e].copy(price[j],0, oneToJ(j)); //all jobs go to M1
							}
							continue;
						}
						if(r==0 ) {//M2 should take j first jobs if possible
							if(e>=price[j])
								m[j][r][p][e].copy(0,price[j],""); // all jobs go to M2
							continue;
						}
						if((e==0 && j!=r) || (p==0 && r!=0)) {  // M1 or M2 is out of time and no out of jobs
							continue;
						}
						boolean M1=(p-calc(j,r)>=0); //p can pay for job j in place (r)
						couple M1Ans=new couple();
						int newP=0;
						if(M1) {
							newP=p-calc(j,r); //after paying for job j how much time is left for M1
							couple M1origin=new couple(m[j-1][r-1][newP][e]);
							if(M1origin.getX()!=-1) {
								M1origin.addX(calc(j,r));
								M1Ans= new couple(M1origin.getX(), M1origin.getY(), M1origin.s+j+" ");
							}
						}
						couple M2Ans=new couple();	
						int newE=0;
						boolean M2=(e-calc(j,j-r)>=0);//e can pay for job j in place (j-r)
						if(M2) {
							newE=e-calc(j,j-r); //after paying for job j how much time is left for M2
							couple M2origin=new couple(m[j-1][r][p][newE]);
							if(M2origin.getX()!=-1) {
								M2origin.addY(calc(j,j-r));
								M2Ans= new couple(M2origin.getX(), M2origin.getY(), M2origin.s);
							}
						}
						couple ans=couple.min(M1Ans, M2Ans); 
						m[j][r][p][e]=ans;
					}
				}
			}
		}
		couple ans=new couple(Double.MAX_VALUE, Double.MAX_VALUE);
		for(int i=1; i<=f ; i++) {
			for(int r=0; r<=missionsNum  ; r++) {
				if(m[missionsNum][r][i][i]!=null && m[missionsNum][r][i][i].getX()!=-1 && m[missionsNum][r][i][i].getY()!=-1) {
					if(m[missionsNum][r][i][i].max() < ans.max())
						ans=m[missionsNum][r][i][i]; 
				}
			}
		}
		System.out.println(ans+"\nC_max= "+ ans.max()+"\n"+ divide(ans.s));
//		for(int j=0; j<missionsNum+1; j++) {
//			for(int r=0; r<=j; r++) {
//				System.out.println("\n*********************\n\nj= "+j+" r= "+r);
//				System.out.print("p\\e ");
//				for (int i = 0; i < 11; i++) {
//					System.out.print(" "+i+" ");
//				}
//				System.out.println();
//				for(int p=0; p<=f; p++) {
//					if(p!=10)
//						System.out.print(p+":  ");
//					else
//						System.out.print(p+": ");
//					for( int e=0; e<=f; e++) {
//						if(m[j][r][p][e].getX()!=-1)
//							System.out.print(" T ");
//						else
//							System.out.print(" F ");
//						
//					}
//					System.out.println();
//					}
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
		return "M1 List is ="+M1List.toString()+"\nM2 List is "+M2List.toString();
	}


	public static int calc(int missionNum, int loc) {
		return upperVal(times[missionNum]*Math.pow(loc,a));
	}

	public static int upperVal(double d) {
		if(d==(int)(d))
			return (int) d;
		else return (int)( d+1);
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
			price[i]=upperVal(times[i]*Math.pow(i,a))+price[i-1];

		}
		numaricMat();
	
	}
}




