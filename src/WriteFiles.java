
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;


public class WriteFiles {
	
	private String filename;

	public WriteFiles(String filename) {
		super();
		this.filename = filename;
	}
	
	public void writedata(String Header, Map<Double,Double> map) {
		try {
			FileWriter fileWriter = new FileWriter(filename);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println(Header);
			printWriter.println();
			
			for (Entry<Double, Double> entry : map.entrySet()) {
				printWriter.printf(" %d\t%.3f ",(entry.getKey().intValue()),entry.getValue());
				printWriter.println();	
			}
			printWriter.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void writeComposition(String Header, Map<Double,Map<ArrayList<String>,String>> map) {
		try {
			FileWriter fileWriter = new FileWriter(filename);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println(Header);
			printWriter.println();
			
			for (Entry<Double, Map<ArrayList<String>, String>> entry : map.entrySet()) {
				printWriter.printf(entry.getKey().intValue() + "\t\t");
				for (Entry<ArrayList<String>, String> entry2 : entry.getValue().entrySet()) {
					printWriter.printf("%s : %s %s\t",entry2.getKey(), entry2.getValue(),"%");
				}
				//printWriter.printf("%s\t(%s,%s,%s) ",entry.getKey(),(entry.getValue().Nephrin),(entry.getValue().Nck),(entry.getValue().NWASP));
				printWriter.println();	
				printWriter.println();
				
			}
			
			printWriter.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public void writeSteadyStateTimePoints(ArrayList<String> ss_Tps, int numRuns) {
		ArrayList<Double> tp = new ArrayList<Double> ();
		for (String s : ss_Tps) {
			tp.add(1000* Double.valueOf(s));
		}
		try {
			FileWriter fileWriter = new FileWriter(filename);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.printf("Number of Runs : %d" , numRuns);
			printWriter.println();
			printWriter.println();
			printWriter.println("Steady state time points (in ms) :");
			printWriter.println();
			for (double t : tp) {
				printWriter.printf("%.1f \t", t);
				//printWriter.println();
				//printWriter.println();
			}
						
			printWriter.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
