
import java.util.ArrayList;

public class Return {
	private ArrayList <String> Clusters;
	private ArrayList <String> Oligomers;
	private ArrayList<ArrayList<String>> Compositions;
	//ArrayList <String> Nephrin;
	//ArrayList <String> Nck;
	//ArrayList <String> NWASP;
	
	public Return(ArrayList<String> clusters, ArrayList<String> oligomers, ArrayList<ArrayList<String>> lists){
			 
		super();
		Clusters = clusters;
		Oligomers = oligomers;
		Compositions = lists;		
		
		//Nephrin = nephrin;
		//Nck = nck;
		//NWASP = nWASP;
	}

	public ArrayList<String> getClusters() {
		return Clusters;
	}

	public ArrayList<String> getOligomers() {
		return Oligomers;
	}

	public ArrayList<ArrayList<String>> getCompositions() {
		return Compositions;
	}
	

}
