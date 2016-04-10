package dp.leiba.music.creation;

import java.util.Arrays;

import dp.leiba.music.tools.ToolMath;
import dp.leiba.music.tools.WaveInstruments;

/**
 * Music rhythm.
 */
public class Rhythm
{
    public static final int I_INSTR     = 0;
    public static final int I_LENGTH    = 1;
    public static final int I_NOTE      = 2;

    public static final int RELEASE = -1;
    public static final int SCALE   = 4;

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
                rhythm[i] = SCALE;
            } else {
                if (ToolMath.is()) {
                    rhythm[i] = ToolMath.is() ? SCALE : SCALE;
                } else {
                    rhythm[i] = SCALE;
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
     * @param root  Root.
     *
     * @return Rhythm.
     */
    public static int[][] getRhythmKick(int bars, int beats, int root)
    {
        int i           = 0;
        int[][] rhythm  = new int[bars * beats][];
        
        for (; i < rhythm.length; i++) {
            rhythm[i] = new int[] {
                WaveInstruments.TYPE_KICK,
                SCALE,
                root
            };
        }
        
        return rhythm;
    }

    /**
     * Get ride rhythm.
     *
     * @param bars  Bars.
     * @param beats Beats.
     * @param root  Root.
     *
     * @return Rhythm.
     */
    public static int[][] getRhythmRide(int bars, int beats, int root)
    {
        int i           = 0;
        int[][] rhythm  = new int[bars * beats][];

        for (; i < rhythm.length; i++) {
            rhythm[i] = new int[] {
                WaveInstruments.TYPE_RIDE,
                SCALE,
                root
            };
        }

        return rhythm;
    }
    
    /**
     * Get bass rhythm.
     * 
     * @param bars  Bars.
     * @param beats Beats.
     * @param root  Root.
     *
     * @return Rhythm.
     */
    public static int[][] getRhythmSubBass(int bars, int beats, int root)
    {
        int i           = 0;
        int[][] rhythm  = new int[bars * beats][];

        for (; i < rhythm.length; i++) {
            rhythm[i] = new int[] {
                WaveInstruments.TYPE_SUB_BASS,
                SCALE,
                root
            };
        }

        return rhythm;
    }
    
    /**
     * Get bass rhythm.
     * 
     * @param bars  Bars.
     * @param beats Beats.
     * @param root  Root.
     *
     * @return Rhythm.
     */
    public static int[][] getRhythmLead(int bars, int beats, int[] harmony)
    {
        int length, type, i	= 0;
        int[][] rhythm  	= new int[0][];

        for (; i < bars * beats * SCALE;) {
        	rhythm = Arrays.copyOfRange(rhythm, 0, rhythm.length + 1);
        	
        	if (i % SCALE == 0) {
        		length 	= 1;
        		type 	= WaveInstruments.TYPE_LEAD;
        	} else {
        		length 	= ToolMath.random(1, SCALE - (i % SCALE));
        		type 	= ToolMath.is() ? WaveInstruments.TYPE_LEAD : RELEASE;
        	}        	
        	
            rhythm[rhythm.length - 1] = new int[] {
                type,
                length,
                harmony[ToolMath.random(0, harmony.length - 1)]
            };
            
            i += length;
        }

        return rhythm;
    }
}
