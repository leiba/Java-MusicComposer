package dp.leiba.music.tools;

import javax.swing.*;
import java.awt.*;

public class ToolSpectrum extends JFrame
{

	private static final int J_WIDTH    = 1000;
	private static final int J_HEIGHT   = 200;
	private static final int J_LEFT     = 0;
	private static final int J_TOP      = 0;

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
            
            int i;
            double[] magnitude = _getMagnitude();
            
            for (i = 0; i < magnitude.length; i++) {
            	g.setColor(Color.BLACK);
            	g.drawLine(i, getWidth(), i, getHeight() - (int) magnitude[i]);
            	
            	if (i % 50 == 0) {
            		g.setColor(Color.BLUE);
            		g.drawString("" + (Wav.FREQUENCY / 100 * (i * 100 / magnitude.length)), i, 10);
            	}
            }
        }

        /**
         * Get magnitude.
         *
         * @return Magnitude.
         */
        private double[] _getMagnitude()
        {
        	int i, frequency, index, step = Wav.FREQUENCY / getWidth();
    		double[] prepare 	= new double[J_WIDTH];
    		
    		for (i = 0; i < _frequency.length; i++) {   
    			frequency 	= i * Wav.FREQUENCY / _frequency.length;    			
    			index		= frequency / step;
    			index		= index >= prepare.length ? prepare.length - 1 : index < 0 ? 0 : index; 
    			
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
        setLocation(J_LEFT, J_TOP);
        setVisible(true);

        add(new Panel(frequency));
    }
}
