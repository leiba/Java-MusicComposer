package dp.leiba.music.tools;

import javax.swing.*;
import java.awt.*;

import be.tarsos.dsp.util.Complex;

public class ToolSpectrum extends JFrame
{

	private static final int J_WIDTH  = 1000;
	private static final int J_HEIGHT = 200;
	
    /**
     * Panel.
     */
    public static class Panel extends JPanel
    {
    	
    	private Complex[] _frequency;
    	
    	/**
    	 * Constructor.
    	 * 
    	 * @param frequency Frequency.
    	 */
    	public Panel(Complex[] frequency)
    	{
    		super();
    		
    		
    		_frequency = frequency;
    	}

        /**
         * Paint component.
         *
         * @param g Graphics.
         */
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            
            int i;
            double[] magnitude = _getMagnitude();
            
            for (i = 0; i < magnitude.length; i++) {
            	g.drawLine(i, 0, i, (int) magnitude[i]); 
            	System.out.println(i + ":" + magnitude[i]);
            }

            try {
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        private double[] _getMagnitude()
        {
        	int i, frequency, index, step = Wav.FREQUENCY / J_WIDTH;
    		double[] prepare 	= new double[J_WIDTH];
    		
    		for (i = 0; i < _frequency.length; i++) {   
    			frequency 	= i * Wav.FREQUENCY / _frequency.length;    			
    			index		= frequency / step;
    			index		= index >= prepare.length ? prepare.length - 1 : index; 
    			
    			if (_frequency[i].mod() > prepare[index]) {
    				prepare[index] = _frequency[i].mod();
    			}
    		}
    		
    		return prepare;
        }
    }

    /**
	 * Constructor.
	 * 
	 * @param frequency Frequency.
	 */
    public ToolSpectrum(Complex[] frequency)
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(J_WIDTH, J_HEIGHT);
        setVisible(true);

        add(new Panel(frequency));
    }
}
