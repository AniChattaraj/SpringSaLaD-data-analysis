

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileDirectory {
	
	private String inpath;
	private String outpath;
	private boolean IsOutpathGiven;
	
	public FileDirectory(String...paths) {
		super();
		
		if(paths.length==2) {
			IsOutpathGiven = true;
			this.inpath = paths[0];
			this.outpath = paths[1];
		} 
		else {
			IsOutpathGiven = false;
			this.inpath = paths[0];
		}
	}
	
	/*public FileDirectory(String inpath) {
		this.inpath = inpath;
	} */
	
	private String getSimulationName() {
		String [] dirs = inpath.split("\\\\");   // Escape regex '\\' by putting another '\\' 
		int last_index = dirs.length - 1;
		String name = dirs[last_index];
		return name;
	}
	
	public Path getDefaultLocation() {
		Path outputPath = null;
		
		String name = getSimulationName();		
		int l = name.length();
		String sim_name = name.substring(0, l-11);  // "_SIM_FOLDER" contains 11 character
		
		if(!IsOutpathGiven) {
			outputPath = Paths.get(this.inpath + "\\Cluster_statistics");
			if(!Files.exists(outputPath)) {
			    try {
			      Files.createDirectories(outputPath);
			    } catch (IOException e) {
			      e.printStackTrace();
			    }
			}
		} else {
			outputPath= Paths.get(outpath + "\\"+ sim_name);
			if(!Files.exists(outputPath)) {
			    try {
			      Files.createDirectories(outputPath);
			    } catch (IOException e) {
			      e.printStackTrace();
			    }
			}			
		}					
		return outputPath;		
	}
	
	public Path getOutputPath() {
		//String [] dirs = inpath.split("\\\\");   // Escape regex '\\' by putting another '\\' 
		//int last_index = dirs.length - 1;
		
		String name = getSimulationName();		
		int l = name.length();
		String sim_name = name.substring(0, l-11);  // "_SIM_FOLDER" contains 11 character
		
		Path path = Paths.get(outpath + "\\"+ sim_name);
		if(!Files.exists(path)) {
		    try {
		      Files.createDirectories(path);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		}
		return path;
	}
	
	public String getSimFile() throws IOException {
		//String [] dirs = inpath.split("\\\\");   // Escape regex '\\' by putting another '\\' 
		//int last_index = dirs.length - 1;
		String name = getSimulationName();	
		int l = name.length();
		String sim_name = name.substring(0, l-7);  // "_FOLDER" contains 7 character
		String txtfile = sim_name + ".txt";
		return txtfile;	
	}
	
	public void CopySimFile() throws IOException {
		//String [] dirs = inpath.split("\\\\");   // Escape regex '\\' by putting another '\\' 
		//int last_index = dirs.length - 1;
		
		if(IsOutpathGiven) {
			String name = getSimulationName();	
			int l = name.length();
			String sim_name = name.substring(0, l-7);  // "_FOLDER" contains 7 character
			String txtfile = sim_name + ".txt";
			
			FileSystem fileSys = FileSystems.getDefault();
			java.nio.file.Path srcPath = fileSys.getPath(inpath + "\\" + txtfile);
			java.nio.file.Path destPath = fileSys.getPath(outpath + "\\" + txtfile);
			try {		 
			      Files.copy(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
			      System.out.println("Succesfully Copied the simFile.");
			      		 
			    } catch (IOException ioe) {
			      ioe.printStackTrace();
			    }
		}
		else {
			System.out.println("Default simulation location already has the simFile.");
		}
			
	}
	

}
