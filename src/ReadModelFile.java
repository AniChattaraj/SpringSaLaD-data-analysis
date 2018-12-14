

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadModelFile {
	
	private String txtfile;

	public ReadModelFile(String txtfile) {
		super();
		this.txtfile = txtfile;
	}
	
	public model_specs getModelSpecifications () {
		
		//String csvFile = this.txtfile;
		ArrayList <String> components = new ArrayList<String> ();
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ":";
		double TM = 0;
		int numRuns = 0;
		double Tf = 0;
		double dt = 0;
		
		ArrayList<Double> num_mols = new ArrayList <Double> ();
		
		try {
            br = new BufferedReader(new FileReader(txtfile));
            while ((line = br.readLine()) != null) {  
            	if (!line.equals("")) {
            		ArrayList <String> arr = new ArrayList<String> ();
            		String[] lines = line.split(cvsSplitBy);
            		
            		for (String s : lines) {
            			arr.add(s);
            		}
            		
            		// count the number of molecules with the MOLECULE tag; for pdb source file in Springsalad v2, length > 2 condition; empty file : MOLECULE { molecule null}
            		if(lines[0].equals("MOLECULE")) {
            			String mol_details = lines[1];
            			String [] mol_arr = mol_details.split(" ");
            			if(mol_arr.length > 3) {
            				//System.out.println(arr);
            				String mol_name = mol_arr[1];
            				String name = mol_name.replaceAll("^\"|\"$", "");
            				//System.out.println(mol_name);
            				//System.out.println(name);
            				components.add(name);
            				num_mols.add(Double.valueOf(mol_arr[4]));
            			}            			            			        	
            		}
            		
            		// Get the number of runs used in the simulation
            		if (lines[0].equals("Runs")) {
            			String runs = lines[1];
            			numRuns = (Double.valueOf(runs)).intValue();            			         
            		}
            		
            		// Get the time informations : total_time and dt_data
            		if (lines[0].equals("Total time")){
            			Tf = Double.valueOf(lines[1]);
            		}
            		if (lines[0].equals("dt_data")){
            			dt = Double.valueOf(lines[1]);
            		}                           				           		           		           		           		           		                      		           		 
            	}                                                     
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }	
		
		
		//System.out.println(num_mols);
		for (double nums : num_mols) {
			TM += nums;
		}
		return new model_specs(components,TM,numRuns,Tf,dt);				
	}
}

class model_specs  {
	ArrayList <String> Components;
	double Total_molecules;
	int numberOfRuns;
	double Total_time;
	double timeStep;
	
	model_specs (ArrayList<String> components, double tm, int NR, double tf, double dt){
		Components = components;
		Total_molecules = tm;
		numberOfRuns = NR;
		Total_time = tf;
		timeStep = dt;
	}
	
	
}


