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
    	
    	private int _frequency;
    	private Complex[] _fft;
    	
    	/**
    	 * Constructor.
    	 * 
    	 * @param fft FFT.
    	 */
    	public Panel(Complex[] fft)
    	{
    		super();
    		
    		
    		_fft = fft;
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
            		g.drawString("" + (_frequency / 100 * (i * 100 / magnitude.length)), i, 10);
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
        	int i, index;
        	double freq 		= 0;
    		double freqStep 	= Wav.FREQUENCY / (_fft.length * 1.0);
    		double[] prepare 	= new double[J_WIDTH];    		
    		_frequency 			= _fft.length / 2;
    		
    		for (i = 0; i < _frequency; i++) {   
    			freq 	+= freqStep;
    			index	= (int) Math.floor(freq * J_WIDTH / _frequency); 
    			
    			if (index >= J_WIDTH) {
    				index = J_WIDTH - 1;
    			}
    			
    			if (_fft[i].mod() > prepare[index]) {
    				prepare[index] = _fft[i].mod();
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
