
public class cluster_composition {
	private String Nephrin;
	private String Nck;
	private String NWASP;
	
	public cluster_composition(String nephrin, String nck, String nWASP) {
		//super();
		Nephrin = nephrin;
		Nck = nck;
		NWASP = nWASP;
	}
	
	public String getNephrin() {
		return Nephrin;
	}
	public String getNck() {
		return Nck;
	}
	public String getNWASP() {
		return NWASP;
	}
	
	public boolean Isequal(Object o) {
		boolean result = false;
		if(o instanceof cluster_composition) {
			cluster_composition cc = (cluster_composition) o;
			if (getNephrin().equals(cc.getNephrin()) && getNck().equals(cc.getNck()) && getNWASP().equals(cc.getNWASP()) ) {
				result = true;
			}
		}
		return result;
	}

}
