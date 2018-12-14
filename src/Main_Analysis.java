

import java.io.IOException;

public class Main_Analysis {

	public static void main(String[] args) throws IOException {
		
		String inpath = "Z:\\Ephrin_eph_system\\MODELS\\Dimerization_test\\dimer_phos_test_SIMULATIONS\\Dimeric_phospho_test_SIM_FOLDER";
		//String outpath = "Z:\\JAVA\\Cluster_Analysis_Results\\new_version";										
		double SS_ti = 0.04;
		double SS_dt = 0.02;
		double VS_dt = 0.02;
		
		Cluster_Statistics cs1 = new Cluster_Statistics (inpath, SS_ti, SS_dt, VS_dt);
		
		final long startTime = System.nanoTime();
		
		cs1.execute_analysis();
		
		final long endTime = System.nanoTime();
		
		double time_factor = Math.pow(10,-9);
		
		double execTime = time_factor * (endTime - startTime);
		
		if(execTime > 60) {
			execTime = execTime/60.0;
			System.out.printf("Execution time : %.3f mins" , execTime);
		}
		else {
			System.out.printf("Execution time : %.3f seconds" , execTime);
		}
							
	}

}



