
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class test_main {

	public static void main(String[] args) {
		
		MyUtility mu = new MyUtility ();
		ArrayList <String> ssTime = mu.getTimePoints(0.02,0.2,0.02);
		ArrayList <String> Time = mu.getTimePoints(0,0.2,0.02);
		
		System.out.println(ssTime);
		System.out.println(Time);
		

	}
	

}
