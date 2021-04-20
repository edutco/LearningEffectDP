import java.util.Random;

public class Files {


	
	
	public static void main(String[] args) {

		Random rand = new Random();
		  
	    // Generate random integers in range 0 to 999
		double []x = new double [1000];
		for( int i=0; i<1000; i++) {
			int rand_int1 = rand.nextInt(100);
			x[i]= rand_int1/100.0;
		}
		double [] y= new double [1000];
		for(int i=0; i<1000; i++) {
			int rand_int2 = rand.nextInt(100);
			y[i]= rand_int2/100.0;
		}
		double [] result= new double [1000];
		for(int i=0; i<1000; i++) {
			if(x[i] >0.5 && y[i]>0.5)
				result[i]=1;
			else
				result[i]=-1;	
		}
		
//		for(int i=0; i<1000; i++) {
//			System.out.println(x[i]+" "+y[i]);
//			
//		}
//		System.out.println("*******************8");
//		for(int i=0; i<1000; i++) {
//			System.out.println(result[i]);
//			
//		}
		
		  // Generate random integers in range 0 to 999
//		1/2  <= x**2 + y**2 <= 3/4
				double []x1 = new double [1000];
				for( int i=0; i<1000; i++) {
					int rand_int1 = rand.nextInt(100);
					x1[i]= rand_int1/100.0;
				}
				double [] y1= new double [1000];
				for(int i=0; i<1000; i++) {
					int rand_int2 = rand.nextInt(100);
					y1[i]= rand_int2/100.0;
				}
				int [] result1= new int [1000];
				for(int i=0; i<1000; i++) {
					double num= x1[i]*x1[i]+y1[i]*y1[i];
					if( 0.5<= num && num <= 0.75)
						result1[i]=1;
					else
						result1[i]=-1;	
				}
				for(int i=0; i<1000; i++) {
				System.out.println(x1[i]+" "+y1[i]);
				
			}
			System.out.println("*******************");
			for(int i=0; i<1000; i++) {
				System.out.println(result1[i]);
				
			}

	}
}


