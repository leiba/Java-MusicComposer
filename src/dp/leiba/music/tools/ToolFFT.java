package dp.leiba.music.tools;

/**
 * Tool FFT.
 * Arg - phase.
 * Mod - amplitude.
 * http://algs4.cs.princeton.edu/99scientific/FFT.java
 * http://stackoverflow.com/questions/7674877/how-to-get-frequency-from-fft-result
 */
public class ToolFFT
{
    private static final String EXCEPTION = "N is not a power of 2";
	
	/**
	 * FFTFilter.
	 */
	public static class FFTFilter
	{
		protected int _frequency;
		protected int _width;
		
		/**
		 * Instance.
		 * 
		 * @param frequency Frequency.
		 * @param width	    Width.
		 * 
		 * @return FFTFilter.
		 */
		public FFTFilter instance(int frequency, int width)
		{
            FFTFilter instance  = new FFTFilter();
			_frequency 		    = frequency;
			_width 			    = width;
			
			return instance;			
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
			if (i < points.length) {
				complex[i] = new Complex(points[i], 0);
			} else {
				complex[i] = new Complex(0, 0);
			}			
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
		return fft(x, new FFTFilter[0]);
	}
	
	/**
	 * FFT.
	 * 
	 * @param x       Complex points.
	 * @param filters Filters.
	 * 
	 * @return FFT.
	 */
	public static Complex[] fft(Complex[] x, FFTFilter[] filters)
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
}
