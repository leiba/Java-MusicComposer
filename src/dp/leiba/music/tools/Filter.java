package dp.leiba.music.tools;

/**
 * Filter.
 */
public class Filter
{
	
	public float value;

	private float c, a1, a2, a3, b1, b2;

	private float[] inputHistory 	= new float[2];
	private float[] outputHistory 	= new float[3];

	/**
	 * Constructor.
	 * 
	 * @param frequency  Frequency.
	 * @param sampleRate Sample rate.
	 * @param passType   Pass type.
	 * @param resonance  Resonance.
	 */
	public Filter(float frequency, int sampleRate, PassType passType, float resonance)
	{
	    switch (passType)
	    {
	        case Lowpass:
	            c 	= 1.0f / (float)Math.tan(Math.PI * frequency / sampleRate);
	            a1 	= 1.0f / (1.0f + resonance * c + c * c);
	            a2 	= 2f * a1;
	            a3 	= a1;
	            b1 	= 2.0f * (1.0f - c * c) * a1;
	            b2 	= (1.0f - resonance * c + c * c) * a1;
	            break;
	            
	        case Highpass:
	            c 	= (float)Math.tan(Math.PI * frequency / sampleRate);
	            a1 	= 1.0f / (1.0f + resonance * c + c * c);
	            a2 	= -2f * a1;
	            a3 	= a1;
	            b1 	= 2.0f * (c * c - 1.0f) * a1;
	            b2	= (1.0f - resonance * c + c * c) * a1;
	            break;
	    }
	}

	public enum PassType
	{
	    Highpass,
	    Lowpass,
	}

	/**
	 * Update point.
	 * 
	 * @param newInput New input.
	 */
	public void update(float newInput)
	{
	    float newOutput = a1 * newInput + a2 * this.inputHistory[0] + a3 * this.inputHistory[1] - b1 * this.outputHistory[0] - b2 * this.outputHistory[1];

	    this.inputHistory[1] = this.inputHistory[0];
	    this.inputHistory[0] = newInput;

	    this.outputHistory[2] = this.outputHistory[1];
	    this.outputHistory[1] = this.outputHistory[0];
	    this.outputHistory[0] = newOutput;
	}

	/**
	 * Get last history.
	 * 
	 * @return Last history.
	 */
	public float getValue()
	{
	    return this.outputHistory[0];
	}
}
