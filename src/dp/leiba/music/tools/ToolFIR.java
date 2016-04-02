package dp.leiba.music.tools;

/**
 * ToolFIR.
 */
public class ToolFIR
{
    public static double[] filterLow(double[] points)
    {
        int i, j;
        int N = 20;
        double Fd = Wav.FREQUENCY;
        double Fs = 20;
        double Fx = 50;
        double[] out = new double[points.length];

        double[] H = new double[N];
        double[] H_id = new double[N];
        double[] W = new double[N];

        double Fc = (Fs + Fx) / (2 * Fd);

        for (i = 0; i < N; i++) {
            if (i == 0) {
                H_id[i] = 2*Math.PI*Fc;
            } else {
                H_id[i] = Math.sin(2 * Math.PI * Fc * i)/(Math.PI*i);
            }

            // Blackman
            W [i] = 0.42 - 0.5 * Math.cos((2 * Math.PI * i) / (N - 1)) + 0.08 * Math.cos((4 * Math.PI * i) / (N - 1));
            H [i] = H_id[i] * W[i];
        }

        double SUM=0;
        for (i=0; i<N; i++) SUM +=H[i];
        for (i=0; i<N; i++) H[i]/=SUM;

        for (i=0; i<points.length; i++)
        {
            out[i]=0.;
            for (j=0; j<N-1; j++)// та самая формула фильтра
                out[i]+= H[j]*points[i-j];
        }

        return out;

    }

    int N = 100; // Poradok
    double h[] = new double[N];
    double x[] = new double[N];
    double y[] = new double[N];

    /**
     * http://dmilvdv.narod.ru/SpeechSynthesis/fir.html
     *
     * @param point
     * @return
     */
    public double filter(double point)
    {
        double result = 0;

        for ( int i = N - 2 ; i >= 0 ; i--) {
            x[i + 1] = x[i];
            y[i + 1] = y[i];
        }

        x[0] = point;

        for (int k = 0; k < N; k++) {
            result = result + x[k] * h[k];
        }

        y[0] = result;
        return result;
    }
}
