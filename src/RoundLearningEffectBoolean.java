import java.util.ArrayList;
import java.util.Arrays;
/**
 * this class solves the rounded learning effect on 2 machines when the learning function
 *  for mission j in the k place is upperVal(p_j*(k^a))
 * by DP algorithm and boolean matrix
 *
 */

public class RoundLearningEffectBoolean {

	static int missionsNum=13;
	static int  price[]= new int[missionsNum+1];
	static int  times[]= {0,4,7,3,5,6,9,5,2,3,4,5,6,1};
	static double a=0.32;





	public static void BoolMat() {
		int f= (int) (price[missionsNum])+1; //upper bound for answer
		Boolean m[][][][]= new Boolean [missionsNum+1][missionsNum+1][f+1][f+1];
		for(int j=0; j<missionsNum+1; j++) {
			for(int r=0; r<=j; r++) {
				for(int p=0; p<=f; p++) {
					for( int e=0; e<=f; e++) {
						m[j][r][p][e]= false;
						if(j==r ) { //M1 should take j first jobs if possible
							if(p>=price[j]) {
								m[j][r][p][e]=true;//.copy(price[j],0, oneToJ(j)); //all jobs go to M1
							}
							continue;
						}
						if(r==0 ) {//M2 should take j first jobs if possible
							if(e>=price[j])
								m[j][r][p][e]=true;//.copy(0,price[j],""); // all jobs go to M2
							continue;
						}
						if((e==0 && j!=r) || (p==0 && r!=0)) {  // M1 or M2 is out of time and no out of jobs
							continue;
						}
						boolean M1=(p-calc(j,r)>=0); //p can pay for job j in place (r)
						Boolean ansM1=false;
						int newP=0;
						if(M1) {
							newP=p-calc(j,r); //after paying for job j how much time is left for M1
							ansM1=m[j-1][r-1][newP][e];
							}
						Boolean ansM2=false;
						int newE=0;
						boolean M2=(e-calc(j,j-r)>=0);//e can pay for job j in place (j-r)
						if(M2) {
							newE=e-calc(j,j-r); //after paying for job j how much time is left for M2
							ansM2=(m[j-1][r][p][newE]);
						}
						
						m[j][r][p][e]=(ansM1 || ansM2);
					}
				}
			}
		}
		int ans=Integer.MAX_VALUE;
		for(int i=1; i<=f ; i++) {
			for(int r=0; r<=missionsNum  ; r++) {
				if(m[missionsNum][r][i][i] ) {
					if(i < ans)
						ans=i; 
				}
			}
		}
		System.out.println(ans+"\nC_max= "+ ans+"\n");//+ divide(ans.s));
	}

//	private static String oneToJ(int j) {
//		String s="1 ";
//		for(int i=2;i<=j; i++) {
//			s+=i+" ";
//		}
//		return s;
//	}
//	
//	private static String divide(String s) {
//		if(s.length()==0)
//			return "empty string";
//		String[] M1ListString=s.split(" ");
//		ArrayList<Integer> M1List=new ArrayList<Integer>();
//		for (int i = 0; i < M1ListString.length; i++) {
//			M1List.add(Integer.parseInt(M1ListString[i]));
//		}
//		ArrayList<Integer> M2List=new ArrayList<Integer>();
//		for (int i = 1; i <= missionsNum; i++) {
//			if(!M1List.contains(i))
//				M2List.add(i);
//		}
//		for (int i = 0; i < M1List.size(); i++) {
//			M1List.set(i,times[M1List.get(i)]);
//		}
//		for (int i = 0; i < M2List.size(); i++) {
//			M2List.set(i,times[M2List.get(i)]);
//		}
//		return "M1 List is ="+M1List.toString()+"\nM2 List is "+M2List.toString();
//	}


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
		BoolMat();


	}
}




