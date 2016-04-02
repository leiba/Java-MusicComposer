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
            double[] magnitude  = _getMagnitude();
            
            for (i = 0; i < J_WIDTH; i++) {
            	g.setColor(Color.BLACK);
            	g.drawLine(i, getWidth(), i, getHeight() - (int) magnitude[i]);

            	if (i % 50 == 0) {
            		g.setColor(Color.BLUE);
            		g.drawString("" + (int) (Wav.FREQUENCY / 2 / 100.0 * (i * 100 / J_WIDTH)), i, 10);
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
            double max          = _fft.length / 2.0;
        	double freq 		= 0;
    		double freqStep 	= max / Wav.FREQUENCY;
    		double[] prepare 	= new double[J_WIDTH];
    		
    		for (i = 0; i < max; i++) {
    			freq 	+= freqStep;
    			index	= (int) Math.floor(J_WIDTH / 100.0 * (i * 100.0 / max));
    			
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
