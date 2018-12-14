

import java.io.IOException;
import java.util.ArrayList;

public class Main_multiple {

	public static void main(String[] args) throws IOException {
		
		String inpath1 = "Z:\\Nephrin_Nck_NWASP\\model and results\\model_slow_diff\\final_version\\CONTROL_system\\Final_version_test_SIMULATIONS\\Final_version_test_SIM_FOLDER";
		String inpath2 = "Z:\\Nephrin_Nck_NWASP\\model and results\\model_slow_diff\\final_version\\CONTROL_system\\Final_version_1nm_longer_SIMULATIONS\\final_version_1nm_longer_SIM_FOLDER";
		String inpath3 = "Z:\\Nephrin_Nck_NWASP\\model and results\\model_slow_diff\\final_version\\CONTROL_system\\Final_version_2nm_longer_SIMULATIONS\\Final_version_2nm_longer_SIM_FOLDER";
		
		double TM = 36;
		int numRuns = 50;
		boolean copySimFile = true;
		double ti = 0.0;
		double tf = 0.5;
		double dt = 0.002;
		double SS_ti = 0;
		double SS_dt = 0.1;
		double VS_dt = 0.1;
		
		String outpath = "Z:\\JAVA\\Cluster_Analysis_Results";
		
		ArrayList <Cluster_Statistics> CS = new ArrayList<Cluster_Statistics> ();
		
		Cluster_Statistics cs1 = new Cluster_Statistics (inpath1, SS_ti,  SS_dt,  VS_dt);
		CS.add(cs1);
		Cluster_Statistics cs2 = new Cluster_Statistics ( inpath2,  SS_ti,  SS_dt,  VS_dt);
		CS.add(cs2);
		Cluster_Statistics cs3 = new Cluster_Statistics ( inpath3, SS_ti,  SS_dt,  VS_dt);
		CS.add(cs3);
		
		for (Cluster_Statistics cs : CS ) {
			final long startTime = System.nanoTime();
			cs.execute_analysis();
			final long endTime = System.nanoTime();
			double f = Math.pow(10,-9);
			double time = (endTime - startTime) * f;
			if (time < 60) {
				System.out.printf("Execution Time : %.3f seconds", time);
			} else {
				time = time/60.0;
				System.out.printf("Execution Time : %.3f mins", time);
			}
			System.out.println();
			System.out.println();
			System.out.println("**********");
		}
		

	}

}
