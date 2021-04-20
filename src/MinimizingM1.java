import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
public class MinimizingM1 {
	/**
	 * this class is the basic most generic algorithm for learning & aging effect on 2 machines when the function for mission j in the k place is (p_j*(k^a))
	 * by DP algorithm
	 * here every spot in the matrix  
		 * m[j][r][p][e] is 
		 * j- number of missions in total
		 * r- number of missions for M1
		 * p- total time of all missions times that are M1 responsibility after calculate function 
		 * e- total time of all missions times that are M2 responsibility after calculate function
		 
	 */
	static int missionsNum=15;
	static double  price[]= new double[missionsNum+1];
	static int  times[]= {0,1,1,1,2,4,5,5,5,6,6,8,8,9,9,12};
	static double a= -0.32;






	public static void numaricMat() {
		int f= (int) (price[missionsNum])+1; //upper bound for answer
		couple m[][][][]= new couple [missionsNum+1][missionsNum+1][f+1][f+1];
		for(int j=0; j<missionsNum+1; j++) {
			for(int r=0; r<=j; r++) {
				for(int p=0; p<=f; p++) {
					for( int e=0; e<=f; e++) {
						if(j==r ) { //M1 should take 1-j first jobs if possible
							if(p>=price[j]) {
								String oneToj="";
								for(int i=1; i<=j; i++)
									oneToj+=" "+i;
								m[j][r][p][e]= new couple(price[j],0, oneToj); //all jobs go to M1
							}
							else m[j][r][p][e]= new couple();
							continue;
						}
						if(r==0 ) {//M2 should take 1-j first jobs if possible
							if(e>=price[j])
								m[j][r][p][e]= new couple(0,price[j]); // all jobs go to M2
							else m[j][r][p][e]= new couple();
							continue;
						}
						if((e==0 && j!=r) || (p==0 && r!=0)) {  // M1 or M2 is out of time and no out of jobs
							m[j][r][p][e]=new couple();
							continue;
						}
						boolean M1=(p-calc(j,r)>=0); //p can pay for job j in place (r)
						couple M1Ans=new couple();
						int newP=0;
						if(M1) {
							newP=p-upperVal(calc(j,r)); //after paying for job j how much time is left for M1
							couple M1origin=new couple(m[j-1][r-1][newP][e]);
							if(M1origin.getX()!=-1)
								M1origin.addX(calc(j,r));
							couple M1originTry;//= new couple();
							if(newP<f && m[j-1][r-1][newP+1][e].getX()+calc(j,r)<= p) { //another check to avoid delay
								M1originTry=new couple(m[j-1][r-1][newP+1][e]);
								if(M1originTry.getX()!=-1)
									M1originTry.addX(calc(j,r));
								M1origin=couple.minM1(M1origin, M1originTry);
							}
							if(e<f && m[j-1][r-1][newP][e+1].getX()+calc(j,r)<= p && m[j-1][r-1][newP][e+1].getY()<= e) { //another check to avoid delay
								M1originTry=new couple(m[j-1][r-1][newP][e+1]);
								if(M1originTry.getX()!=-1)
									M1originTry.addX(calc(j,r));

								M1origin=couple.minM1(M1origin, M1originTry);
							}
							if(e<f && newP<f && m[j-1][r-1][newP+1][e+1].getX()+calc(j,r)<= p && m[j-1][r-1][newP+1][e+1].getY()<= e) { //another check to avoid delay
								M1originTry=new couple(m[j-1][r-1][newP+1][e+1]);
								if(M1originTry.getX()!=-1)
									M1originTry.addX(calc(j,r));

								M1origin=couple.minM1(M1origin, M1originTry);
							}
							if(M1origin.getX()!=-1 )
								M1Ans= new couple(M1origin.getX(), M1origin.getY(), M1origin.s+" "+j);
						}

						couple M2Ans=new couple();	
						int newE=0;
						boolean M2=(e-calc(j,j-r)>=0);//e can pay for job j in place (j-r)
						if(M2) {
							newE=e-upperVal(calc(j,j-r)); //after paying for job j how much time is left for M2
							couple M2origin=new couple(m[j-1][r][p][newE]);
							if(M2origin.getX()!=-1)
								M2origin.addY(calc(j,j-r));
							couple M2originTry;
							if(newE<f && m[j-1][r][p][newE+1].getY()+calc(j,j-r)<= e) { //another check to avoid delay
								M2originTry=new couple(m[j-1][r][p][newE+1]);
								if(M2originTry.getX()!=-1)
									M2originTry.addY(calc(j,j-r));
								M2origin=couple.minM1(M2origin, M2originTry);
							}
							if(p<f && m[j-1][r][p+1][newE].getY()+calc(j,j-r)<= e && m[j-1][r][p+1][newE].getX()<=p) { //another check to avoid delay
								M2originTry=new couple(m[j-1][r][p+1][newE]);
								if(M2originTry.getX()!=-1)
									M2originTry.addY(calc(j,j-r));
								M2origin=couple.minM1(M2origin, M2originTry);
							}
							if(newE<f && p<f && m[j-1][r][p+1][newE+1].getY()+calc(j,j-r)<= e && m[j-1][r][p+1][newE+1].getX()<=p) { //another check to avoid delay
								M2originTry=new couple(m[j-1][r][p+1][newE+1]);
								if(M2originTry.getX()!=-1)
									M2originTry.addY(calc(j,j-r));
								M2origin=couple.minM1(M2origin, M2originTry);
							}
							if(M2origin.getX()!=-1 ) 
								M2Ans= new couple(M2origin.getX(), M2origin.getY(), M2origin.s);
						}
						couple ans=couple.minM1(M1Ans, M2Ans); //choose better option
						m[j][r][p][e]=ans;
					}
				}
			}
		}
		ArrayList<String> lis=new ArrayList<String>();
				couple cur;int l=0;
				for(int j=1; j<missionsNum+1; j++) {
					for(int r=0; r<=j; r++) {
						for(int p=0; p<=f; p++) {
							for( int e=0; e<=f; e++) {
								cur=m[j][r][p][e];
								if(cur.getX()!=-1 && upperVal(cur.getX())<=f && upperVal(cur.getY())<=f ) {
									couple c=m[j][r][upperVal(cur.getX())][upperVal(cur.getY()) ];
									if(c==null || c.getX()==-1 ) {
										String s=  j+" "+r+" "+cur.getX()+" "+cur.getY()+" "+cur.s+"\n";
										if(!lis.contains(s)) {
											l++;
											lis.add(s);
										}
									}
		
								}
							}
						}
					}
		
				}
				System.out.println("l="+l);
				if(l>0)
					System.out.println(lis);
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




