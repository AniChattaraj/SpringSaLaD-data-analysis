
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Cluster_Statistics {
	double TM ;
	int numRuns = 5;
	//boolean copySimFile = true;
	String inpath;
	//String outpath;
	double ti = 0;
	double tf = 0.05;
	double dt;
	double SS_ti;
	double SS_dt;
	double VS_dt;
	ArrayList<String> components;
	
	public Cluster_Statistics(String inpath,double sS_ti, double sS_dt, double vs_dt) {
		// This constructor only requires input and output locations along with steady state time points 	  
		super();						
		this.inpath = inpath;
		//this.outpath = outpath;						
		SS_ti = sS_ti;
		SS_dt = sS_dt;	
		VS_dt = vs_dt;
	}
		

	public void execute_analysis () throws IOException {
		FileDirectory fd = new FileDirectory(inpath);
		Path simpath = fd.getDefaultLocation();
		String simfile = fd.getSimFile();
		System.out.println(simpath);
		ReadModelFile rmf = new ReadModelFile (inpath + "\\" + simfile);
		model_specs spec = rmf.getModelSpecifications();
		TM = spec.Total_molecules;
		numRuns = spec.numberOfRuns;
		this.tf = spec.Total_time;
		this.dt = spec.timeStep;
		components = spec.Components;
		//int numMolecules = components.size();
			
		System.out.println();
		System.out.println("System : " + simfile);
		System.out.println("NumRuns : " + numRuns + "\nTotal_molecules : " + Double.valueOf(TM).intValue());
		System.out.println("Total_time : " + 1000*tf + " ms; \t dt_data : " + 1000*dt + " ms");
		System.out.println("Molecules : " + components);
		System.out.println();
		fd.CopySimFile();
		//System.out.println("Reading the data .....");
		System.out.println();
							
		Map<Double, Double> ACS_timeCourse = new LinkedHashMap <Double, Double> ();
		Map<Double, Double> ACO_timeCourse = new LinkedHashMap <Double, Double> ();
		Map<Double,Double> SS_Cluster_Occupancy = new TreeMap <Double, Double> ();
		Map<Double,Double> SS_Cluster_Sizes = new TreeMap <Double, Double> ();
		
		
		ArrayList <String> SS_Clusters = new ArrayList <String> ();
		ArrayList <Double> d_SS_Clusters = new ArrayList <Double> ();
		
		
		ArrayList <String> Oligomers_mtmr = new ArrayList <String> ();
		ArrayList<ArrayList<String>> compositions_mtmr = new ArrayList<ArrayList<String>>();
		
		MyUtility mu = new MyUtility ();
		
		ArrayList <String> timepoints = mu.getTimePoints (ti,tf,dt);
		ArrayList <String> visTime = mu.getTimePoints(ti,tf,VS_dt);
		ArrayList <String> ssTime = mu.getTimePoints(SS_ti,tf,SS_dt);
		//ArrayList <String> ssTime = mu.getTimePoints(ti,tf,dt);
		double SS_Tps = 0;
		ArrayList <String> SS_TP = new ArrayList <String> () ;

		for (String tp : timepoints) {
			
			if(visTime.contains(tp)) {
				double td_ms =  1000 * Double.valueOf(tp);
				System.out.printf("%d ms calc is in process",Double.valueOf(td_ms).intValue());
				System.out.println();
			}
			if (ssTime.contains(tp)) {
				SS_Tps += 1;
				SS_TP.add(tp);
							
				for (int run=0; run< numRuns; run++) {
					String file = inpath + "\\data\\Run" + run + "\\Clusters_Time_" + tp + ".csv";
					//System.out.println(file);
					ReadFiles f = new ReadFiles (file);
					Return data = f.readData( components);
					//System.out.println(data.Compositions);
					ArrayList<String> Clusters = data.getClusters();
					ArrayList<String> oligomers = data.getOligomers();
					ArrayList<ArrayList<String>> components_list = data.getCompositions();
					
					//System.out.println(components_list);
					//System.out.print(Clusters.size());
					
					ArrayList<ArrayList<String>> comp_lists = new ArrayList<ArrayList<String>>();
					
					int size = components_list.get(0).size(); // sizes of molecular array or oligomeric array , all would be same
					int kk = 0;
					while (kk<size) {
						ArrayList<String> list = new ArrayList<String>();
						for (int ii=0; ii<components_list.size();ii++) {
							list.add(components_list.get(ii).get(kk));
						}
						comp_lists.add(list);
						kk++;
					}
					//System.out.println("Oligos : " + oligomers);
					//System.out.println("Compositions : " + comp_lists);
					//System.out.println();
					
					
					
					//ArrayList<String> neph = data.Nephrin;
					//ArrayList<String> nck = data.Nck;
					//ArrayList<String> nwasp = data.NWASP;
															
					/*for (int i=0; i < oligomers.size(); i++) {
						cluster_composition comp = new cluster_composition (neph.get(i),nck.get(i),nwasp.get(i));
						compList.add(comp);
					} */
					SS_Clusters.addAll(Clusters);	
					Oligomers_mtmr.addAll(oligomers);
					compositions_mtmr.addAll(comp_lists);
				}
				
			}
			
			ArrayList <Double> acs_stmr = new ArrayList <Double> ();
			ArrayList <Double> aco_stmr = new ArrayList <Double> ();
			double acs = 0;
			double aco = 0;
	
			for (int run = 0; run < numRuns; run++) {
				//System.out.println(" tp : " + tp + "  Run : " + run );
				String file = inpath + "\\data\\Run" + run + "\\Clusters_Time_" + tp + ".csv";
				ReadFiles csvfile = new ReadFiles (file);
				Return data = csvfile.readData(components);
				
				ArrayList<String> Clusters = data.getClusters();
				ArrayList<Double> dC = new ArrayList<Double>();
				
				for (String c : Clusters) {
					dC.add(Double.parseDouble(c));
				}
				
				double acs_stsr = mu.getAverage(dC);
				double aco_stsr = mu.getACO(dC, TM);						
				
				acs_stmr.add(acs_stsr);
				aco_stmr.add(aco_stsr);	
			}
			
			acs = mu.getAverage(acs_stmr);
			aco = mu.getAverage(aco_stmr);
			
			double t = Double.valueOf(tp);
			ACS_timeCourse.put(t*1000, acs);
			ACO_timeCourse.put(t*1000, aco);			
		}
		
		//System.out.println(Oligomers_mtmr);
		//System.out.println(compositions_mtmr);
		//System.out.println();
		
		//Set <ArrayList<String>> set = new HashSet<ArrayList<String>> (compositions_mtmr);
		
		//System.out.println(set);
		
		for (String s : SS_Clusters) {
			d_SS_Clusters.add(Double.parseDouble(s));
		}
		
		SS_Cluster_Occupancy = mu.getClusterOccupancy(d_SS_Clusters, SS_Tps, (double) numRuns, TM);
		SS_Cluster_Sizes = mu.getClusterSizes(d_SS_Clusters);
				
		ArrayList<Double> d_oligomers = new ArrayList<Double> ();
		for (String s : Oligomers_mtmr) {
			d_oligomers.add(Double.valueOf(s));
		}
		
		Set <Double> OligoSet = new HashSet <Double> (d_oligomers);
		
		Map<Double, Map<ArrayList<String>, String>> Compositions = new TreeMap <Double, Map<ArrayList<String>, String>> ();
		
		for (double oligo : OligoSet) {
			ArrayList<ArrayList<String>> comp0 = new ArrayList <ArrayList<String>> ();
			for (ArrayList<String> arr : compositions_mtmr) {
				if(mu.ValidComposition(arr, oligo)) {
					comp0.add(arr);
				}								
			}
			
			Map<ArrayList<String>, String> map1 = mu.getCompositionCount(comp0);
			Compositions .put(oligo, map1);
			//System.out.println(map1);							
		}
		
		//System.out.println(Compositions );
		
		/*
		for (double oligo : OligoSet) {
			ArrayList<cluster_composition> compo = new ArrayList<cluster_composition> ();
			for (cluster_composition comp : compList) {
				if (mu.IsValidComposition(comp, oligo)){
					compo.add(comp);
				}
			}
			
			Map<cluster_composition, String> compMap = mu.countCompositions(compo);
			compositions.put(oligo,compMap);
		} */
		
		String filename_acs = simpath + "\\ACS_dynamics.txt";
		String filename_aco = simpath +  "\\ACO_dynamics.txt";
		String SS_CO_dist = simpath +  "\\SteadyState_Cluster_Occupancy.txt";
		String SS_CS_dist = simpath +  "\\SteadyState_Cluster_Sizes.txt";
		String filename_compo = simpath +  "\\SteadyState_Cluster_Compositions.txt";
		String SS_tp = simpath +  "\\SteadyState_TimePoints.txt";
		
		String acs_Header = "Time(ms)  ACS";
		String aco_Header = "Time(ms)  ACO";
		String ss_co_Header = "Clusters  foTM";
		String comp_Header = "Clusters \t\t" + components +" : frequency";
		String ss_cs_Header = "Clusters  frequency";
		
		WriteFiles wf1 = new WriteFiles(filename_acs);
		WriteFiles wf2 = new WriteFiles(filename_aco);
		WriteFiles wf3 = new WriteFiles(SS_CO_dist);
		WriteFiles wf4 = new WriteFiles(filename_compo);
		WriteFiles wf5 = new WriteFiles(SS_CS_dist);
		WriteFiles wf6 = new WriteFiles(SS_tp);
		
		wf1.writedata(acs_Header,ACS_timeCourse);
		wf2.writedata(aco_Header, ACO_timeCourse);
		wf3.writedata(ss_co_Header, SS_Cluster_Occupancy);
		wf4.writeComposition(comp_Header, Compositions);
		wf5.writedata(ss_cs_Header, SS_Cluster_Sizes);
		wf6.writeSteadyStateTimePoints(SS_TP,numRuns);
		
		System.out.println("... Writing done ! ");		
		
	}
		
}
