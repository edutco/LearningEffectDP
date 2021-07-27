package four_dim_algo;
import java.util.ArrayList;
import java.util.Arrays;

public class IntegerMatrixMinimizeM1 {
	/**
		 * this class is the basic most generic algorithm for learning & aging effect on 2 machines when the function for mission j in the k place is (p_j*(k^a))
		 * by DP algorithm
		 * the main difference is that here we try to minimize only M1
		 * here every spot in the matrix  
		 * m[j][r][p][e] is 
		 * j- number of missions in total
		 * r- number of missions for M1
		 * p- sum of all origin missions times that are M1 responsibility 
		 * e- sum of all origin missions times that are M2 responsibility
		 */
		static int missionsNum=15;
		static double  price[]= new double[missionsNum+1];
		static int  times[]= {0,1,1,1,2,4,5,5,5,6,6,8,8,9,9,12};
		static double a= -0.32;






		public static void numaricMat() {
			int f= sum(missionsNum,times); //upper bound for answer
			couple m[][][][]= new couple [missionsNum+1][missionsNum+1][f+1][f+1];
			for(int j=0; j<missionsNum+1; j++) {
				for(int r=0; r<=j; r++) {
					for(int p=0; p<=f; p++) {
						for( int e=0; e<=f; e++) {
							
							m[j][r][p][e]= new couple();
							if(j==15 && r==8 &&p==42 &&e==40)
								System.out.println("here");
							if(p+e != sum(j,times)) {
								continue;
							}
							if(j==r ) { //M1 should take 1-j first jobs if possible
								if(p==sum(j,times) ) {
									String oneToj="";
									for(int i=1; i<=j; i++)
										oneToj+=" "+i;
									m[j][r][p][e]= new couple(price[j],0, oneToj); //all jobs go to M1
								}
								continue;
							}
							if(r==0 ) {//M2 should take 1-j first jobs if possible
								if(e==sum(j,times) )
									m[j][r][p][e]= new couple(0,price[j]); // all jobs go to M2
								
								continue;
							}
							if((e==0 && j!=r) || (p==0 && r!=0)) {  // M1 or M2 is out of time and no out of jobs
								
								continue;
							}
							boolean M1=(p-times[j]>=0); //p can pay for job j in place (r)
							couple M1Ans=new couple();
							int newP=0;
							if(M1) {
								newP=p-times[j]; //after paying for job j how much time is left for M1
								couple M1origin=new couple(m[j-1][r-1][newP][e]);
								if(M1origin.getX()!=-1) {
									M1origin.addX(calc(j,r));
									M1Ans= new couple(M1origin.getX(), M1origin.getY(), M1origin.s+" "+j);
								}
							}

							couple M2Ans=new couple();	
							int newE=0;
							boolean M2=(e-times[j]>=0);//e can pay for job j in place (j-r)
							if(M2) {
								newE=e-times[j]; //after paying for job j how much time is left for M2
								couple M2origin=new couple(m[j-1][r][p][newE]);
								if(M2origin.getX()!=-1) {
									M2origin.addY(calc(j,j-r));
									M2Ans= new couple(M2origin.getX(), M2origin.getY(), M2origin.s);
								}
							}
							couple ans=couple.minM1(M1Ans, M2Ans); //choose better option
							m[j][r][p][e]=ans;
						}
					}
				}
			}

			couple ans=new couple(Double.MAX_VALUE, Double.MAX_VALUE);
			for(int i=1; i<=f ; i++) {
				for(int r=0; r<=missionsNum  ; r++) {
					if(m[missionsNum][r][i][sum(missionsNum,times)-i].getX()!=-1) {
						if(m[missionsNum][r][i][sum(missionsNum,times)-i].max() < ans.max())
							ans=m[missionsNum][r][i][sum(missionsNum,times)-i];
					}
				}
			}
			System.out.println(ans+"\nC_max= "+ ans.max()+"\n"+ divide(ans.s));
		}


		private static String divide(String s) {
			s=s.substring(1);
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


		public static double calc(int missionNum, int loc) {
			return times[missionNum]* Math.pow(loc, a);
		}


		public static int upperVal(double d) {
			if(d==(int)(d))
				return (int) d;
			else return (int)( d+1);
		}
		public static int sum(int i, int [] arr) {
			int s=0;
			for (int j = 1; j <= i; j++) {
				s+=arr[j];
			}
			return s;
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
				price[i]=times[i]*Math.pow(i,a)+price[i-1];
				//System.out.println(price[i]);
			}
			numaricMat();
			//booleanMat();

		}
	}
