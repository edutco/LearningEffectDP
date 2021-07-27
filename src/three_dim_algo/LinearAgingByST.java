package three_dim_algo;
import java.util.Arrays;

public class LinearAgingByST//ST= starting time
{

	public static int upperVal(double d) {
		if(d==(int)(d))
			return (int) d;
		else return (int)( d+1);
	}
	public static int lowerVal(double d) {
		if(d==(int)(d))
			return (int) d;
		else return (int)(d);
	}

	static int [] b= {0,1,2,3,4,5,6,7,8,9,21};
	static double [] a= {0,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1};

	public static String printCalculation(String M1IndexList) {
		if(M1IndexList==null) return "no solution";
		double M2=0;
		double M1=0.0;
		String M1List="";
		String M2List="";
		String[] splitted=M1IndexList.split(" ");
		for (int i = 1; i < a.length; i++) {
			if(Arrays.asList(splitted).contains(String.valueOf(i))) {
				M1=M1+M1*a[i]+b[i];
				M1List=M1List+b[i]+" ";
			}
			else {
				M2=M2+M2*a[i]+b[i];
				M2List=M2List+b[i]+" ";
			}
		}
		return "M1= "+M1+" M1 List is: "+M1List+"\nM2= "+M2+" M2 list is: "+M2List;

	}
	static void DPmat() {
		double sum=0;
		//Object times;
		double times [] = new double [a.length];
		for (int i = 1; i < a.length; i++) {
			times[i]=a[i]*times[i-1]+b[i];
			//System.out.println("P_"+i+" on one machine = "+ times[i]);
		}
		for (int i = 0; i < times.length; i++) {
			sum+=times[i];
		}
		//System.out.println("total makespan= "+sum);

		String mat [][][]= new String [b.length][upperVal(sum+1)][upperVal(sum+1)];
		int ans=Integer.MAX_VALUE;
		String ansString="?";
		for (int i = 0; i < mat.length; i++) {
			for (int t = 0; t < mat[0].length; t++) {
				for (int k = 0; k < mat[0][0].length; k++) {
					if( i==6 && t==6 && k==18  )
						System.out.println();
					boolean flag=false;
					if(i==0) {
						mat[i][t][k]="";
						flag=true;
						continue;
					}
					double t0=(t-b[i])/(1+a[i]); //if we assign mission n to M1
					double k0=(k-b[i])/(1+a[i]); //if we assign mission n to M2	

					if(!flag && t0>=0 && k0<0 && mat[i-1][lowerVal(t0)][k]!=null) { //M2 can't take mission n
						mat[i][t][k]=mat[i-1][lowerVal(t0)][k]+i+" ";
						flag=true;
						//continue;
					}
					if(!flag && t0<0 && k0>=0) {//M1 can't take mission n
						mat[i][t][k]=mat[i-1][t][lowerVal(k0)]; 
						flag=true;
						//continue;
					}

					if (!flag &&t0>=0 && k0>=0) { //M1 and M2 can take mission n
						//mat[i][t][k]=mat[i-1][t][(int) (k0+0.5)] || mat[i-1][(int) (t0+0.5)][k];
						if(mat[i-1][lowerVal(t0)][k]!=null)
							mat[i][t][k]=mat[i-1][lowerVal(t0)][k]+i+" ";
						else
							mat[i][t][k]=mat[i-1][t][lowerVal(k0)];
						flag=true;
						//continue;
					}
					if(!flag ) //both can't take mission n
						mat[i][t][k]=null;//mat[i][t][k]=false;
					if(i==b.length-1 && mat[i][t][k]!=null) {
						if(Integer.max(t,k)<ans) {
							ans=Integer.max(t,k);
							ansString=mat[i][t][k];
							//	System.out.println(t+" , "+ k+" -> "+mat[i][t][k]);
						}
						//System.out.println(t+" , "+ k);
					}
				}

			}

		}
		System.out.println("\nDP solution:");
		if(ansString==null) return;
		System.out.println(printCalculation(ansString));
		System.out.println("makespan= "+ans);
	}

	static boolean recurFunc(int n, double t, double k) {
		if(n==0) //no more missions to divide
			return true;
		double t0=(t-b[n])/(1+a[n]); //if we assign mission n to M1
		double k0=(k-b[n])/(1+a[n]); //if we assign mission n to M2		
		if(t0<0 && k0>=0) //M1 can't take mission n
			return recurFunc(n-1, t, k0); //
		if(t0>=0 && k0<0) //M2 can't take mission n
			return recurFunc(n-1, t0, k);
		if (t0>=0 && k0>=0) //M1 and M2 can take mission n
			return recurFunc(n-1, t, k0) || recurFunc(n-1, t0, k) ;
		return false; //both can't take mission n
	}
	static String recurFuncString(int n, double t, double k) {
		if(n==0) //no more missions to divide
			return "";
		double t0=(t-b[n])/(1+a[n]); //if we assign mission n to M1
		double k0=(k-b[n])/(1+a[n]); //if we assign mission n to M2		
		String M1Ans=recurFuncString(n-1, t0, k);
		if(t0>=0 && k0<0 && M1Ans!=null) //M2 can't take mission n
			return recurFuncString(n-1, t0, k)+n+" ";
		if(t0<0 && k0>=0) //M1 can't take mission n
			return recurFuncString(n-1, t, k0); //
		if (t0>=0 && k0>=0) { //M1 and M2 can take mission n
			if(M1Ans!=null)
				return recurFuncString(n-1, t0, k)+n+" " ;
			else
				return recurFuncString(n-1, t, k0);
		}
		return null; //both can't take mission n
	}

	public static void main(String[] args) {
		//System.out.println(lowerVal(1.8));
//		System.out.println("recursive solution:"
//				+ "\n"//M1 gets: "
//				+printCalculation(recurFuncString(10,3720, 3710))
//				+"\nmakespan = 37.2");
		DPmat();
	}
}
