package four_dim_algo;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
public class BasicAlgoNewVersion {
	/**
	 * this class is the basic most generic algorithm for learning & aging effect on 2 machines
	 *  when the function for mission j in the k place is (p_j*(k^a))
	 *  the main change is
	 *  m[j][r][p][e] means M1 takes j jobs and M2 r jobs.
	 *  solution is where j+r=n
	 * by DP algorithm
	 *
	 */
	static int missionsNum;//=11;
	static ArrayList<Double>  price= new ArrayList<Double>() ;
	static ArrayList<Integer>  times= new ArrayList<Integer>() ;//= {0,1,2,4,3,4,5,6,7,4,8,9};
	static double a= -0.32;






	public static int numaricMat() {
		int f= (int) (price.get(missionsNum)+1); //upper bound for answer
		couple m[][][][]= new couple [missionsNum+1][missionsNum+1][f+1][f+1];
		for(int j=0; j<=missionsNum; j++) {
			for(int r=0 ;r<=missionsNum; r++) {
				if(r+j> missionsNum)
					continue;
				for(int p=0; p<=f; p++) {
					for( int e=0; e<=f; e++) {
//						if(j==1 && r==2 && p==2 && e==6 )
//							System.out.println();
						if(r+j==0) {
							m[j][r][p][e]= new couple(0,0);
							continue;
						}
						if(r==0 ) { //M1 should take 1-j first jobs if possible
							if(p>=price.get(r+j)) {
								String oneToj="";
								for(int i=1; i<=r+j; i++)
									oneToj+=" "+i;
								m[j][r][p][e]= new couple(price.get(r+j),0, oneToj); //all jobs go to M1
							}
							else 
								m[j][r][p][e]= new couple();
							continue;
						}
						if(j==0 ) {//M2 should take r first jobs if possible
							if(e>=price.get(r+j))
								m[j][r][p][e]= new couple(0,price.get(r+j)); // all jobs go to M2
							else m[j][r][p][e]= new couple();
							continue;
						}
						if((e==0 && r!=0) || (p==0 && j!=0)) {  // M1 or M2 is out of time and no out of jobs
							m[j][r][p][e]=new couple();
							continue;
						}
						boolean M1=(p-calc(r+j,j)>=0); //p can pay for job r+j in place (j)
						couple M1Ans=new couple();
						int newP=0;
						if(M1) {
							newP=p-upperVal(calc(r+j,j)); //after paying for job j+r how much time is left for M1
							couple M1origin=new couple(m[j-1][r][newP][e]);
							if(M1origin.getX()!=-1)
								M1origin.addX(calc(r+j,j));
							couple M1originTry;//= new couple();
							if(newP<f && m[j-1][r][newP+1][e].getX()+calc(r+j,j)<= p) { //another check to avoid delay
								M1originTry=new couple(m[j-1][r][newP+1][e]);
								if(M1originTry.getX()!=-1)
									M1originTry.addX(calc(r+j,j));
								M1origin=couple.min(M1origin, M1originTry);
							}
//							if(e<f && m[j-1][r-1][newP][e+1].getX()+calc(j,r)<= p && m[j-1][r-1][newP][e+1].getY()<= e) { //another check to avoid delay
//								M1originTry=new couple(m[j-1][r-1][newP][e+1]);
//								if(M1originTry.getX()!=-1)
//									M1originTry.addX(calc(j,r));
//
//								M1origin=couple.min(M1origin, M1originTry);
//							}
//							if(e<f && newP<f && m[j-1][r-1][newP+1][e+1].getX()+calc(j,r)<= p && m[j-1][r-1][newP+1][e+1].getY()<= e) { //another check to avoid delay
//								M1originTry=new couple(m[j-1][r-1][newP+1][e+1]);
//								if(M1originTry.getX()!=-1)
//									M1originTry.addX(calc(j,r));
//
//								M1origin=couple.min(M1origin, M1originTry);
//							}
							if(M1origin.getX()!=-1 )
								M1Ans= new couple(M1origin.getX(), M1origin.getY(), M1origin.s+" "+(j+r));
						}

						couple M2Ans=new couple();	
						int newE=0;
						boolean M2=(e-calc(j+r,r)>=0);//e can pay for job j+r in place (r)
						if(M2) {
							newE=e-upperVal(calc(j+r,r)); //after paying for job j how much time is left for M2
							couple M2origin=new couple(m[j][r-1][p][newE]);
							if(M2origin.getX()!=-1)
								M2origin.addY(calc(j+r,r));
							couple M2originTry;
							if(newE<f && m[j][r-1][p][newE+1].getY()+calc(j+r,r)<= e) { //another check to avoid delay
								M2originTry=new couple(m[j][r-1][p][newE+1]);
								if(M2originTry.getX()!=-1)
									M2originTry.addY(calc(j+r,r));
								M2origin=couple.min(M2origin, M2originTry);
							}
//							if(p<f && m[j-1][r][p+1][newE].getY()+calc(j,j-r)<= e && m[j-1][r][p+1][newE].getX()<=p) { //another check to avoid delay
//								M2originTry=new couple(m[j-1][r][p+1][newE]);
//								if(M2originTry.getX()!=-1)
//									M2originTry.addY(calc(j,j-r));
//								M2origin=couple.min(M2origin, M2originTry);
//							}
//							if(newE<f && p<f && m[j-1][r][p+1][newE+1].getY()+calc(j,j-r)<= e && m[j-1][r][p+1][newE+1].getX()<=p) { //another check to avoid delay
//								M2originTry=new couple(m[j-1][r][p+1][newE+1]);
//								if(M2originTry.getX()!=-1)
//									M2originTry.addY(calc(j,j-r));
//								M2origin=couple.min(M2origin, M2originTry);
//							}
							if(M2origin.getX()!=-1 ) 
								M2Ans= new couple(M2origin.getX(), M2origin.getY(), M2origin.s);
						}
						couple ans=couple.min(M1Ans, M2Ans); //choose better option
						m[j][r][p][e]=ans;
					}
				}
			}
		}
//		ArrayList<String> lis=new ArrayList<String>();
//				couple cur;int l=0;
//				for(int j=0; j<missionsNum+1; j++) {
//					for(int r=missionsNum-j; r>=0; r--) {
//						for(int p=0; p<=f; p++) {
//							for( int e=0; e<=f; e++) {
//								cur=m[j][r][p][e];
//								if(cur.getX()!=-1 && upperVal(cur.getX())<=f && upperVal(cur.getY())<=f ) {
//									couple c=m[j][r][upperVal(cur.getX())][upperVal(cur.getY()) ];
//									if(c==null || c.getX()==-1 ) {
//										String s=  j+" "+r+" "+cur.getX()+" "+cur.getY()+" "+cur.s+"\n";
//										if(!lis.contains(s)) {
//											l++;
//											lis.add(s);
//										}
//									}
//		
//								}
//							}
//						}
//					}
//		
//				}
//				System.out.println("l="+l);
//				if(l>0)
//					System.out.println(lis);
		couple ans=new couple(Double.MAX_VALUE, Double.MAX_VALUE);
		for(int i=1; i<=f ; i++) {
			for(int r=0; r<=missionsNum  ; r++) {
				if(m[missionsNum-r][r][i][i]!=null && m[missionsNum-r][r][i][i].getX()!=-1 && m[missionsNum-r][r][i][i].getY()!=-1) {
					if(m[missionsNum-r][r][i][i].max() < ans.max())
						ans=m[missionsNum-r][r][i][i]; 
				}
			}
		}
		System.out.println(ans+"\nC_max= "+ ans.max()+"\n"+ divide(ans.s));
		return upperVal(ans.max());
	}


	private static String divide(String s) {
		System.out.println("s is= "+s+"\n");
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
			M1List.set(i,times.get(M1List.get(i)));
		}
		for (int i = 0; i < M2List.size(); i++) {
			M2List.set(i,times.get(M2List.get(i)));
		}
		return "M1 List is ="+M1List.toString()+"\nM2 List is "+M2List.toString();
	}


	public static double calc(int missionNum, int loc) {
		if (loc==0) return 0;
		return times.get(missionNum)* Math.pow(loc, a);
	}


	public static int upperVal(double d) {
		if(d==(int)(d))
			return (int) d;
		else return (int)( d+1);
	}


	public static void main(String[] args) {
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		System.out.println("Enter number of jobs\n");
	    missionsNum = Integer.parseInt(myObj.nextLine());  // Read user input
	    System.out.println("Enter jobs processing times\n");
	    String StringTimes = myObj.nextLine();  // Read user input
	    String [] Array =StringTimes.split(" ");
	    times.ensureCapacity(missionsNum+1);
	    times.add(0);
	    for(int i=0; i< Array.length; i++) {
	    	times.add(Integer.parseInt(Array[i]));
	    }
	    
	    for (int i = 0; i < times.size(); i++) {
			System.out.println(times.get(i));
		}
	    System.out.println("Enter your k\n");

	    int k = myObj.nextInt();  // Read user input
	    myObj.close();
	    Collections.sort(times);
	    if(a>0)
	    	Collections.reverse(times);
	
		
		price.add(0, 0.0);
		for(int i=1; i<missionsNum+1; i++){
			price.add( times.get(i)*Math.pow(i,a)+price.get(i-1));
		}
		if(numaricMat()<=k )
			System.out.println("\n"+k+" is enough!!");
		else 
			System.out.println("\n"+k+" is too small");


	}
}
