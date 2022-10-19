import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.io.*;
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 80);
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        
        JPanel panel = new JPanel();            // Creating a JPanel
        panel.setLayout(new GridLayout(0, 2));  // Grid n linhas, 2 colunas

        JLabel labelT = new JLabel("Branchs em USO"); 
        JLabel labelT2 = new JLabel(""); 
        panel.add(labelT); 
        panel.add(labelT2);
        
        String pastasMonitoradas[] = prop.getProperty("pastas").split(";"); //Pastas configuradas

        //Primeira pasta monitorada
        JLabel labelB1 = new JLabel(""); 
        JLabel labelB1U = new JLabel("x"); 
        labelB1.setText(pastasMonitoradas[0] + " =>");
        panel.add(labelB1); 
        panel.add(labelB1U);

        JLabel labelB1W = new JLabel("");     
        JLabel labelB1WU = new JLabel("");   //LABEL Alerta par compilar WKS
        labelB1W.setForeground(Color.blue);        
        labelB1WU.setForeground(Color.red);        
        panel.add(labelB1W); 
        panel.add(labelB1WU);
        
        //Segunda pasta monitorada (se houver)
        JLabel labelB2 = new JLabel(""); 
        JLabel labelB2U = new JLabel("x"); 
        if (pastasMonitoradas.length > 1) {
            labelB2.setText(pastasMonitoradas[1] + " =>");
            panel.add(labelB2); 
            panel.add(labelB2U); 
        }

        frame.add(panel); // Adding panel to frame
        frame.setAlwaysOnTop(true);
        
        frame.setOpacity(0.85f);//50% opaque
        
        frame.setVisible(true); //Displaying the frame
        
        Integer monitor = Integer.parseInt(prop.getProperty("monitor")); 
        showOnScreen(monitor, frame);
        
    	
        while (true) {
        	labelB1U.setText(ObtemBranch(pastasMonitoradas[0]));

        	labelB1W.setText(VerificarPUSH(pastasMonitoradas[0]));
        	labelB1WU.setText(VerificarOUTPUT(pastasMonitoradas[0]));
       	
            if (pastasMonitoradas.length > 1) {
                labelB2U.setText(ObtemBranch(pastasMonitoradas[1]));
            }
            try {
                //Aguarda 10 segundos
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
    }
    
    public String ObtemBranch(String pLocal) {
    	
        try {
            String cmdSet[] = {"cmd", "/c /k", "c: & cd\\" + pLocal.substring(3) + " & git branch --show-current"};

            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(cmdSet).getInputStream()));
            return bufferedreader.readLine();
        } catch (Exception ex) {
        	return "(não obtido)";
        }
    }

    public String VerificarOUTPUT(String pLocal) {
    	if (VerificarAlertas(pLocal, "OUTPUT")) {
			return "*Compilar WKS*";
    	} else {
    		return "";
    	}
    }

    public String VerificarPUSH(String pLocal) {
    	if (VerificarAlertas(pLocal, "PUSH")) {
			return "!!!FAZER PUSH!!!";
    	} else {
    		return "";
    	}
    }
    
    public Boolean VerificarAlertas(String pLocal, String pTipo) {
        try {
            String cmdSet[] = {"cmd", "/c /k", "c: & cd\\" + pLocal.substring(3) + " & git status"};

            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(cmdSet).getInputStream()));
            
            Boolean encontrouTexto = false; 
    		while (true){
    			if (pTipo.equals("OUTPUT")) {
		            if (bufferedreader.readLine().contains("/output/") ) {
		            	encontrouTexto = true;
		            	break;
		            }
    			}
    			
    			if (pTipo.equals("PUSH")) {
		            if (bufferedreader.readLine().contains("\"git push\"") ) {
		            	encontrouTexto = true;
		            	break;
		            }
    			}
    		}
            
            return encontrouTexto;
        } catch (Exception ex) {
        	return false;
        }
    }
    
    
	public void showOnScreen(int screen, JFrame frame ) {
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] gd = ge.getScreenDevices();
	    int width = 0, height = 0;
	    if( screen > -1 && screen < gd.length ) {
	        width = gd[screen].getDefaultConfiguration().getBounds().width;
	        height = gd[screen].getDefaultConfiguration().getBounds().height;

	        //frame.setLocation(
	        //    ((width / 2) - (frame.getSize().width / 2)) + gd[screen].getDefaultConfiguration().getBounds().x, 
	        //    ((height / 2) - (frame.getSize().height / 2)) + gd[screen].getDefaultConfiguration().getBounds().y
	        //);

	        String lado = prop.getProperty("lado").toUpperCase();
	        String local = prop.getProperty("local").toUpperCase();
	       
	        Integer posicaoX = 0;
	        Integer posicaoY = 0;
	        
	        if (lado.contains("DIR")) {
	        	posicaoX = (width - frame.getSize().width) + gd[screen].getDefaultConfiguration().getBounds().x; 
	        } else {
	            posicaoX = gd[screen].getDefaultConfiguration().getBounds().x; 
	        }
	        
	        if (local.contains("INF")) {
	            posicaoY = (height - frame.getSize().height - 40) + gd[screen].getDefaultConfiguration().getBounds().y;
	        } else {
	            posicaoY = gd[screen].getDefaultConfiguration().getBounds().y;
	        }
	        frame.setLocation(posicaoX, posicaoY);	        
	        
	        //frame.setLocation(
		    //         (width - frame.getSize().width) + gd[screen].getDefaultConfiguration().getBounds().x, 
		    //         (height - frame.getSize().height - 40) + gd[screen].getDefaultConfiguration().getBounds().y
		    // );

	        frame.setVisible(true);
	    } else {
	        throw new RuntimeException( "No Screens Found" );
	    }
	}	
    
    
}


