

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class ReadFiles {
	
	private String filename;

	public ReadFiles(String filename) {
		
		this.filename = filename;
	}
	public Return readData( ArrayList<String> components) {
		String csvFile = this.filename;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		ArrayList <String> mols = new ArrayList < String> ();
		ArrayList <String> nums = new ArrayList < String> ();
		
		try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {  
            	if (!line.equals("")) {
            		String[] cols = line.split(cvsSplitBy);
            		mols.add(cols[0]);
            		nums.add(cols[1]);  
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
		
		ArrayList<String> oligomers = getCounts(mols,nums,"Size");
		//ArrayList<String> neph = getCounts(mols,nums,"NEPHRIN");
		//ArrayList<String> nck = getCounts(mols,nums,"NCK");
		//ArrayList<String> nwasp = getCounts(mols,nums,"NWASP");
		ArrayList<String> Clusters = getAllClusters(nums.get(0), oligomers);  // First element of "nums" list always contains the total_cluster_count
		
		//System.out.println("Oligos : " + oligomers);
		//System.out.println("clusters : " + Clusters);
		
		ArrayList<ArrayList<String>> compositions = new ArrayList<ArrayList<String>>();
		
		for (int i=0; i<components.size(); i++) {
			//System.out.println(components.get(i));
			ArrayList<String> mol = getCounts(mols,nums,components.get(i));
			//System.out.println(components.get(i) + " : " +  mol);
			compositions.add(mol);
		}
		//System.out.println(compositions);
		//System.out.println();
		
		return new Return (Clusters,oligomers,compositions);
				
	}
	
	private static ArrayList<String> getCounts(ArrayList<String> arr1, ArrayList<String> arr2,String name) {
		int n = arr1.size();
		ArrayList<String> countarray = new ArrayList <String> ();
		for (int i=0; i<n; i++) {
			if(arr1.get(i).equalsIgnoreCase(name)) {
				Object s = arr2.get(i);
				countarray.add((String) s);
			}
		}
		return countarray;
	}
	
	private static ArrayList<String> getAllClusters (String Total_Clusters, ArrayList<String> oligomers){
		ArrayList<String> Clusters = new ArrayList<String>();
		Double TC = Double.parseDouble(Total_Clusters);
		double monomer_count = TC - oligomers.size();
		double [] monomers = new double[(int) monomer_count];
		Arrays.fill(monomers, 1.0);
		for (double one : monomers) {
			String mono = String.valueOf(one);
			Clusters.add(mono);
		}
		Clusters.addAll(oligomers);
		
		return Clusters;
	}
	
}



