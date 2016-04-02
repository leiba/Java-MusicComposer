package dp.leiba.music.creation;

import dp.leiba.music.tools.ToolMath;
import dp.leiba.music.tools.WaveInstruments;

/**
 * Music rhythm.
 */
public class Rhythm
{
    public static final int RELEASE = -1;

    /**
     * Get BPM.
     * 
     * @return BPM.
     */
    public static int getBPM()
    {
    	return ToolMath.random(120, 128);
    }
    
    /**
     * Get melody rhythm.
     *
     * @param bars  Bars.
     * @param beats Beats.
     *
     * @return Rhythm.
     */
    public static int[] getRhythmMelody(int bars, int beats)
    {
        int i           = 0;
        int[] rhythm    = new int[bars * beats];

        for (; i < rhythm.length; i++) {
            if (i == 0 || i % beats == 0) {
                rhythm[i] = RELEASE;
            } else {
                if (ToolMath.is()) {
                    rhythm[i] = ToolMath.is() ? RELEASE : RELEASE;
                } else {
                    rhythm[i] = RELEASE;
                }
            }
        }

        return rhythm;
    }
    
    /**
     * Get kick rhythm.
     *
     * @param bars  Bars.
     * @param beats Beats.
     *
     * @return Rhythm.
     */
    public static int[] getRhythmKick(int bars, int beats)
    {
        int i           = 0;
        int[] rhythm    = new int[bars * beats];
        
        for (; i < rhythm.length; i++) {
            rhythm[i] = WaveInstruments.TYPE_KICK;
        }
        
        return rhythm;
    }

    /**
     * Get ride rhythm.
     *
     * @param bars  Bars.
     * @param beats Beats.
     *
     * @return Rhythm.
     */
    public static int[] getRhythmRide(int bars, int beats)
    {
        int i           = 0;
        int[] rhythm    = new int[bars * beats];

        for (; i < rhythm.length; i++) {
            rhythm[i] = WaveInstruments.TYPE_RIDE;
        }

        return rhythm;
    }
    
    /**
     * Get bass rhythm.
     * 
     * @param bars  Bars.
     * @param beats Beats.
     * 
     * @return Rhythm.
     */
    public static int[] getRhythmBass(int bars, int beats)
    {
        int i           = 0;
        int[] rhythm    = new int[bars * beats];
        
        for (; i < rhythm.length; i++) {
            rhythm[i] = WaveInstruments.TYPE_BASS;
        }
        
        return rhythm;
    }
}
