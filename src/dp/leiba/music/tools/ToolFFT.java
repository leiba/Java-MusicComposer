package dp.leiba.music.tools;

import java.util.Arrays;

/**
 * Tool FFT.
 * Arg - phase.
 * Mod - amplitude.
 * http://algs4.cs.princeton.edu/99scientific/FFT.java
 * http://stackoverflow.com/questions/7674877/how-to-get-frequency-from-fft-result
 */
public class ToolFFT
{
	public static final int FILTER_LOW 	= 0;
	public static final int FILTER_HIGH	= 1;
	public static final int FILTER_BAND = 2;
	
    private static final String EXCEPTION = "N is not a power of 2";
	
	/**
	 * FFTFilter.
	 */
	public static class FFTFilter
	{
		protected int _frequency;
		protected int _width;
		
		/**
		 * Constructor.
		 * 
		 * @param frequency Frequency.
		 * @param width	    Width.
		 * 
		 * @return FFTFilter.
		 */
		public FFTFilter (int frequency, int width)
		{
            _frequency = frequency;
            _width 	= width;			
		}
		
		/**
		 * Is cut.
		 * 
		 * @param frequency Frequency.
		 * 
		 * @return Is cut.
		 */
		public boolean cut(int frequency)
		{
			return false;
		}		
	}

    /**
     * FFTFilterLow.
     */
    public static class FFTFilterLow extends FFTFilter
    {

    	/**
		 * Constructor.
		 * 
		 * @param frequency Frequency.
		 * @param width	    Width.
		 * 
		 * @return FFTFilter.
		 */
        public FFTFilterLow(int frequency, int width)
        {
			super(frequency, width);
		}

		/**
         * Is cut.
         *
         * @param frequency Frequency.
         *
         * @return Is cut.
         */
        public boolean cut(int frequency)
        {
            return frequency < _frequency;
        }
    }

    /**
     * FFTFilterHigh.
     */
    public static class FFTFilterHigh extends FFTFilter
    {

        public FFTFilterHigh(int frequency, int width) {
			super(frequency, width);
		}

		/**
         * Is cut.
         *
         * @param frequency Frequency.
         *
         * @return Is cut.
         */
        public boolean cut(int frequency)
        {
            return frequency > _frequency;
        }
    }

    /**
     * FFTFilterBand.
     */
    public static class FFTFilterBand extends FFTFilter
    {

    	/**
		 * Constructor.
		 * 
		 * @param frequency Frequency.
		 * @param width	    Width.
		 * 
		 * @return FFTFilter.
		 */
        public FFTFilterBand(int frequency, int width)
        {
			super(frequency, width);
		}

		/**
         * Is cut.
         *
         * @param frequency Frequency.
         *
         * @return Is cut.
         */
        public boolean cut(int frequency)
        {
            return (frequency < (_frequency - _width))
                || (frequency > (_frequency + _width));
        }
    }
    
    public static FFTFilter filter(final int type, int frequency, int width)
    {
    	FFTFilter filter = null;
    	
    	switch (type) {
	    	case FILTER_LOW  :
	    		filter = new FFTFilterLow(frequency, width);
	    		break;
	    		
	    	case FILTER_HIGH :
	    		filter = new FFTFilterHigh(frequency, width);
	    		break;
	    		
	    	case FILTER_BAND :
	    		filter = new FFTFilterBand(frequency, width);
	    		break;
    	}
    	
    	return filter;
    }

    /**
     * FFTFilter.
     * http://stackoverflow.com/questions/24644496/implementing-low-pass-filter-on-frequencies-using-java-by-just-having-frequency
     * http://stackoverflow.com/questions/4026648/how-to-implement-low-pass-filter-using-java
     *
     * @param points  Points.
     * @param filters Filters.
     */
    public static double[] fftFilter(double[] points, FFTFilter[] filters)
    {
    	int i;
    	Complex[] fft	= fft(points);
    	Complex[] ifft  = ifft(fftFilter(fft, filters));
    	
    	for (i = 0; i< points.length; i++) {
    		points[i] = ifft[i].mod();
    	}

    	return points;
    }
    
    /**
     * FFT Filter.
     * 
     * @param fft     FFT.
     * @param filters Filters.
     * 
     * @return Filtered FFT.
     */
    public static Complex[] fftFilter(Complex[] fft, FFTFilter[] filters)
    {
    	int i, j, frequency;
		
		for (i = 0; i < fft.length; i++) {   
			frequency = i * Wav.FREQUENCY / fft.length;			
			
			for (j = 0; j < filters.length; j++) {
				if (filters[j].cut(frequency)) {
					fft[i] = new Complex(0, 0);
					break;
				}
			}
		}
		
    	return fft;
    }
	
	/**
	 * FFT.
	 * 
	 * @param points Points.
	 * 
	 * @return FFT.
	 */
    public static Complex[] fft(double[] points)
    {
        Complex[] complex;
        int i, length = points.length;

        while ((length & -length) != length) {
            length++;
        }

        complex = new Complex[length];

        for (i = 0; i < complex.length; i++) {
        	complex[i] = new Complex(i < points.length ? points[i] : 0, 0);
        }

        return fft(complex);
    }
	
	/**
	 * FFT.
	 * 
	 * @param x Complex points.
	 * 
	 * @return FFT.
	 */
    public static Complex[] fft(Complex[] x)
    {
        int k, N = x.length;
        double kth;
        Complex wk;
        Complex[] even, odd, q, r, y;

        // base case
        if (N == 1) {
            return new Complex[] {
                x[0]
            };
        }

        if (N % 2 != 0) {
            throw new IllegalArgumentException(EXCEPTION);
        }

        // fft of even terms
        even = new Complex[N / 2];
        for (k = 0; k < N / 2; k++) {
            even[k] = x[2 * k];
        }
        q = fft(even);

        // fft of odd terms
        odd  = even;  // reuse the array
        for (k = 0; k < N / 2; k++) {
            odd[k] = x[2 * k + 1];
        }
        r = fft(odd);

        // combine
        y = new Complex[N];
        for (k = 0; k < N / 2; k++) {
            kth         = -2 * k * Math.PI / N;
            wk          = new Complex(Math.cos(kth), Math.sin(kth));
            y[k]        = q[k].plus(wk.times(r[k]));
            y[k + N/2]  = q[k].minus(wk.times(r[k]));
        }

        return y;
    }
    
    /**
     * Inverse FFT.
     *
     * @param  x Complex array.
     * 
     * @return Inverse FFT.
     */
    public static Complex[] ifft(Complex[] x) {
        int N 		= x.length;
        Complex[] y = new Complex[N];

        // take conjugate
        for (int i = 0; i < N; i++) {
            y[i] = x[i].conjugate();
        }

        // compute forward FFT
        y = fft(y);

        // take conjugate again
        for (int i = 0; i < N; i++) {
            y[i] = y[i].conjugate();
        }

        // divide by N
        for (int i = 0; i < N; i++) {
            y[i] = y[i].scale(1.0 / N);
        }

        return y;
    }
}
