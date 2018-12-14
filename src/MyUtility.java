
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;


public class MyUtility {
	/*This Class has all the useful methods to perform mathematical and organizational tasks in Class 'Cluster_Statistics';
	 * Due to diverse method types, this class can be initialized without any argument (empty constructor)
	 */
	
	public MyUtility() {
		
	}
	
	public  ArrayList<String> getTimePoints (double ti, double tf, double dt) {
		// Create the time series 
		ArrayList <Double> timePoints = new ArrayList <Double> ();
		ArrayList <String> timeStrings = new ArrayList <String> (); 
		double tp = ti;
		while (tp < tf+dt) {
			timePoints.add(tp);
			tp += dt;
		}
		for (double td : timePoints) {
			timeStrings.add(String.format("%.3f", td));
		}
		return timeStrings;
	}
	
	public double getACO (ArrayList<Double> list, double TM) {
		// calculate the average cluster occupancy for multiple runs at single time-point
		double aco = 0;
		
		Map<Double,Integer> cluster_counts = elementCounts(list);
		for (Entry<Double, Integer> entry : cluster_counts.entrySet()) {
			double foTM = entry.getValue() * (entry.getKey() / TM);
			double occupancy = foTM * entry.getKey();
			aco += occupancy;
		}
		
		return aco;
	}
	
	public double getAverage (ArrayList<Double> list) {
		// calculate the arithmetic mean of a ArrayList made of doubles
		double acs = 0;
		double sum = 0;
		for (double d : list) {
			sum += d;
		}
		acs = sum / list.size();
		return acs;
	}
	
	public Map <Double, Integer> elementCounts(ArrayList <Double> list) {
		// compute the number of appearance of each unique elements in a ArrayList  
		Set <Double> set = new LinkedHashSet <Double> (list);
		Map <Double,Integer> map = new HashMap <Double, Integer>();
		
		for (double d1 : set) {
			 int count = 0;
			 for (double d2 : list) {
				 if (d2==d1) {
					 count++;
				 }
			 }
			 map.put(d1, count);
		}
		return map;
	}
	
	public  Map<Double,Double> getClusterSizes (ArrayList<Double> list){
		// get the frequency distribution of individual clusters  at steady state
		Map <Double,Double> map = new TreeMap <Double,Double> ();
		Map<Double,Integer> cluster_counts = elementCounts(list);
		double length = list.size();
		double sum = 0;
		for (Entry <Double,Integer> entry : cluster_counts.entrySet()) {
			double size = entry.getKey();
			double f = (double) entry.getValue() / length;
			sum += f;
			map.put(size, f);
		}
		System.out.println();
		System.out.printf("Sum of cluster size frequency : %.3f" , sum);
		System.out.println();
		
		return map;
	}
	
	public Map<Double, Double> getClusterOccupancy (ArrayList<Double> list,double SS_tps, double numRuns, double TM) {
		// get the occupancy distribution of individual clusters at steady state
		Map <Double, Double> map = new TreeMap <Double, Double> ();
		Map<Double,Integer> cluster_counts = elementCounts(list);
		double numRealizations = SS_tps * numRuns;
		double sum = 0;
		for (Entry<Double, Integer> entry : cluster_counts.entrySet()) {
			double fpR = (double) entry.getValue() / numRealizations;    // fpR : frequency per realization
			double foTM = (fpR * entry.getKey()) / TM ;
			sum += foTM;
			map.put(entry.getKey(), foTM);
		}
		System.out.println();
		System.out.printf("Sum of foTM : %.3f" , sum);
		System.out.println();
		return map;
	}
	
	public Map<cluster_composition, String> countCompositions (ArrayList<cluster_composition> compList){
		// count the number of unique compositions (abb:2, bbc:3, abc: 1 etc.)
		
		Map <cluster_composition,String> compMap = new HashMap <cluster_composition, String>();
		
		if( compList.size() != 0) {
			ArrayList<cluster_composition> unique_elems = getUniqueElements(compList);
			int total_count = compList.size();
				
			for(cluster_composition cc1 : unique_elems) {
				int count = 0;
				for (cluster_composition cc2 : compList) {
					if (cc1.Isequal(cc2)) {
						count++;
					}
				}
				double f = ((double)count)/((double)total_count);
				double f100 = f*100;
				String frequency = String.format("%.2f",f100);
				compMap.put(cc1, frequency);	
			}
		}
		else {
			System.out.println("WARNING ! The composition list is empty.");
		}
		return compMap;			
	}
	
	public  ArrayList<cluster_composition> getUniqueElements(ArrayList<cluster_composition> compList){
		// create a set (unique) of compositions 
		ArrayList<cluster_composition> set = new ArrayList<>();
		int i = 0;
		cluster_composition elem = compList.get(i);
        set.add(elem);
		
		while ( i < compList.size()) {
			boolean res = true;
			for( cluster_composition cc : set) {
				if (cc.Isequal(compList.get(i))) {
					res = false;
				}
			}
			while(res) {
				set.add(compList.get(i));
				break;
			} 			
			i++;	
		}
		return set;		
	}
	/*
	public boolean IsValidComposition(cluster_composition comp, double oligomer) {
		// checks if a composition belongs to a cluster size
		boolean valid = false;
		
		double neph_count = Double.valueOf(comp.getNephrin());
		double nck_count = Double.valueOf(comp.getNck());
		double nwasp_count = Double.valueOf(comp.getNWASP());
		
		if (neph_count + nck_count + nwasp_count == oligomer) {
			valid = true;
		}
		return valid;
	} */
	
	public boolean ValidComposition(ArrayList<String> comp, double oligomer) {
		// checks if a composition belongs to a cluster size
		boolean valid = false;
		double sum = 0;
		for (String s : comp) {
			double d  = Double.valueOf(s);
			sum +=d;
		}
		if (sum == oligomer) {
			valid = true;
		}
		return valid;
	}
	
	public Map<ArrayList<String>, String> getCompositionCount(ArrayList<ArrayList<String>> lists) {
		Set <ArrayList<String>> set = new HashSet<ArrayList<String>> (lists);
		Map<ArrayList<String>,String> map = new HashMap<ArrayList<String>,String> ();
		double length = lists.size();
		for (ArrayList<String> arr : set) {
			double count = 0;
			for (ArrayList<String> elem : lists) {
				if(arr.equals(elem)) {
					count++;
				}
			}
			count = count/length;
			double c100 = count * 100;
			String fraction = String.format("%.2f", c100 );
			map.put(arr,fraction);
		}
		
		return map;
	}
	
	
	public ArrayList<Double> Sort(ArrayList<Double> list) {
		// sorting algorithm for a list of numbers ; It hasn't been used in this package ; just for future reference.
		
		int i = 1;
		
		while (i < list.size()) {
			double prev = list.get(i-1);
			double current = list.get(i);
			if(current< prev) {
				
				list.set(i-1,current);
				list.set(i, prev);
			}
			
			i++;					
		}
		return list;
		
	}

}





