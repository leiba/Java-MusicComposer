package dp.leiba.music.tools;

import be.tarsos.dsp.util.Complex;

/**
 * Tool FFT.
 * Arg - phase.
 * Mod - amplitude.
 * http://algs4.cs.princeton.edu/99scientific/FFT.java
 * http://stackoverflow.com/questions/7674877/how-to-get-frequency-from-fft-result
 */
public class ToolFFT
{
	
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
	
	public static Complex[] fft(Complex[] x) {
        int N = x.length;

        // base case
        if (N == 1) {
        	return new Complex[] { x[0] };
        }

        if (N % 2 != 0) {
            throw new IllegalArgumentException("N is not a power of 2");
        }

        // fft of even terms
        Complex[] even = new Complex[N/2];
        for (int k = 0; k < N/2; k++) {
            even[k] = x[2*k];
        }
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd  = even;  // reuse the array
        for (int k = 0; k < N/2; k++) {
            odd[k] = x[2*k + 1];
        }
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[N];
        for (int k = 0; k < N/2; k++) {
            double kth = -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k]       = q[k].plus(wk.times(r[k]));
            y[k + N/2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }

}
