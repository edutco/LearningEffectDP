package three_dim_algo;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class DP_vs_Naive {

	public static void WriteToFile(String file_Name, String content) {

		try {
			FileWriter myWriter = new FileWriter(file_Name+".txt",true);
			myWriter.write(content);
			myWriter.close();
			//System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		Random random = new Random();
		long [] DPTimeAvrage =new long [26]; 
		//long DPTimeAvrageTemp=0;
		long [] NaiveTimeAvrage =new long [26]; 
		
		double [] DifferenceAvrage =new double [26]; 
		
		for(int i=3; i<=20; i++) {
			long DPTimeAvrageTemp=0;
			long NaiveTimeAvrageTemp=0;
			double differAvrageTemp=0;
			for (int k = 0; k < 100; k++) {
				int  times[]=new int[i+1]; //{0,4,5,7,8,12,4,10,45,11,15,23,25,19};
				times[0]=0;
				for(int j=1;j<i ; j++)
				{
					times[j] = random.nextInt(30);
				}
				Arrays.sort(times);
				//times={0, 1,2 ,46,14,6,13,23,11,12,23};
				double a=-0.32;
				//System.out.println("DP solution is");
				long st_dp= System.currentTimeMillis();
				double dp=RoundLE3dim.IntMat(times, a);
				long et_dp= System.currentTimeMillis();
				DPTimeAvrageTemp+=(et_dp-st_dp);
				//System.out.println("\n\nNaive solution is");
				long st_naive= System.currentTimeMillis();
				double naive=NaiveRoundAlgorithm.recursion(times, a);
				long et_naive= System.currentTimeMillis();
				NaiveTimeAvrageTemp+=(et_naive-st_naive);
				differAvrageTemp+=dp-naive;
				{
//				String results=(times.length-1)+
//						"\t"+Double.parseDouble(String.format("%.3f", naive))+
//						"\t"+(et_naive-st_naive)+
//						"\t"+Double.parseDouble(String.format("%.3f", dp))+
//						"\t"+(et_dp-st_dp)+
//						"\t"+Double.parseDouble(String.format("%.3f", (-naive+dp)))+"\n";
//				WriteToFile("results", results);
//				System.out.println(results);
				}
			}
			DPTimeAvrage[i]= DPTimeAvrageTemp/100;
			NaiveTimeAvrage[i]= NaiveTimeAvrageTemp/100;
			DifferenceAvrage[i]= differAvrageTemp/100.0;
		
		String results=i+"\t"+DPTimeAvrage[i]+
				"\t"+NaiveTimeAvrage[i]+
				"\t"+String.format("%.3f", (DifferenceAvrage[i]))+"\n";
		WriteToFile("results", results);
		//System.out.println(results);
	}
		}
	
	
}
