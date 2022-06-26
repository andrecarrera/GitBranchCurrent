import java.io.*;
import java.util.Properties;

public class GitBranchCurrent {

	public static void main(String[] args) {
		String confFile;
		if (args.length == 0){
			confFile = "src/main/resources/gitbranch.properties";			
		}else
		{
			confFile = args[0];
		}
		Properties prop = loadProperties(confFile);
     	GitBranchThread t = new GitBranchThread(prop);
        t.start();
	}
	
	private static Properties loadProperties(String pConfig) {
        Properties prop = new Properties();

		try (InputStream input = new FileInputStream(pConfig)) {
            // load a properties file
            prop.load(input);

            return prop;
        } catch (IOException ex) {
        	prop.setProperty("monitor", "0");
        	prop.setProperty("pastas", "c:\\dev;c:\\devslt");
            return null;
        }
		
	}

}
