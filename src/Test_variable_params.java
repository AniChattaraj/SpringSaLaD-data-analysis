

public class Test_variable_params {
	
	String Cluster;

	public Test_variable_params(String cluster, String...strings) {
		super();
		Cluster = cluster;
		for (String s : strings) {
			System.out.println(s);
		}
	}
	
	
}
