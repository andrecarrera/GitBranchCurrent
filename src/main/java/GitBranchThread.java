import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Properties;

import javax.swing.*;

public class GitBranchThread  extends Thread {
	
    //private int monitor = 0;
	//private int localJanela = 0;
	
	private Properties prop;
	
	public GitBranchThread(Properties pProp) {
		prop = pProp;
		
	}
    public void run() {
    	
    	JFrame frame = new JFrame("JPanel Example");	  
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(100, 200, 300, 300); 
   
        JLabel label = new JLabel("JLabel Title"); //Creating a JLabel with title
        JLabel label2 = new JLabel("JLabel Title2"); //Creating a JLabel with title
        label2.setText(prop.getProperty("pastas"));
        JPanel panel = new JPanel(); // Creating a JPanel
        panel.setLayout(new GridLayout(0, 2));
        
        
        
        JButton button1,button2; // Initializing JButtons
        button1 = new JButton("Hello!"); //Creating a Button1 with a title 
        button2 = new JButton("Welcome to CodeSpeedy!");  //Creating a Button2 with a title
  
        //panel.add(button1); //Adding the button1 to panel
        //panel.add(button2); //Adding the button2 to panel
        panel.add(label); //Adding the leabel to panel
        panel.add(label2); //Adding the leabel to panel
        
        //panel.setBackground(Color.CYAN); //Setting the BG of the panel
        
        frame.add(panel); // Adding panel to frame
        frame.setVisible(true); //Displaying the frame

    	
    	
    	
    	
        while (true) {
            //System.out.println(this.getName() + ": New Thread is running..." + i++);
            
            
            
            try {
                //Aguarda 10 segundos
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
    }
    
    public String ObtemBranch(String pLocal) {
    	
    	
    	return pLocal;
    }
}


