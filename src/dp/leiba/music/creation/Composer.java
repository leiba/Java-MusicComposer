package dp.leiba.music.creation;

import dp.leiba.music.tools.*;

import java.util.Arrays;

/**
 * Composer.
 */
public class Composer
{
    public static final String  CONFIG_PATH         = "D:\\bit_b.wav";
    public static final double  CONFIG_SECONDS      = 60.0;

    private Wav         _cWav   = new Wav();
    private int 		_cBPM	= Rhythm.getBPM();
    private int         _cSizeBar;
    private int         _cSizeBeat;
    private int         _cSizeStep;
    private double[]    _cWave;

    private int         _cBars  = 4;
    private int         _cBeats = 4;

    private int         _cNote;
    private boolean     _cIsMajor;
    private int[]       _cNotes;

    private int[][]     _cDrumKick;
    private int[][]     _cDrumRide;
    private int[][]     _cSubBass;
    private int[][]     _cLead;   

    public Composer()
    {
        _cSizeBeat  = (int) (Wav.FREQUENCY / (_cBPM / CONFIG_SECONDS));
        _cSizeStep  = _cSizeBeat / Rhythm.SCALE;
        _cSizeBar   = _cSizeBeat * _cBeats;
        _cWave      = new double[_cSizeBar * _cBars];

        _cNote      = Melody.getNote();
        _cIsMajor   = Melody.getIsMajor();
        _cNotes     = Theory.getHarmony(_cNote, _cIsMajor, 3);
        
        _cDrumKick  = Rhythm.getRhythmKick(_cBars, _cBeats, _cNote);
        _cDrumRide  = Rhythm.getRhythmRide(_cBars, _cBeats, _cNote);
        _cSubBass   = Rhythm.getRhythmSubBass(_cBars, _cBeats, _cNote);
        _cLead      = Rhythm.getRhythmLead(_cBars, _cBeats, _cNotes);
        
        double[] wDrum = Wave.limit(Wave.mix(new double[][] {
            getWave(_cDrumKick),
            getWave(_cDrumRide)
        }));

        double[] wMelody = Wave.sideChain(Wave.limit(Wave.mix(new double[][]{
            getWave(_cSubBass),
            getWave(_cLead)
        })), _cSizeBeat);

        _cWave = Wave.limit(Wave.mix(new double[][]{
            wDrum, wMelody
        }));
    }
    
    /**
     * Get BPM.
     * 
     * @return BPM.
     */
    public String getBPM()
    {
    	return String.valueOf(_cBPM);
    }

    /**
     * Get is major.
     *
     * @return Is major.
     */
    public boolean getIsMajor()
    {
        return _cIsMajor;
    }
    
    /**
     * Get main note.
     * 
     * @return Note.
     */
    public int getNote()
    {
    	return _cNote;
    }

    /**
     * Get notes.
     *
     * @return Harmony notes.
     */
    public int[] getNotes()
    {
        return _cNotes;
    }
    
    /**
     * Get lead.
     *
     * @return Lead.
     */
    public int[][] getSubBass()
    {
        return _cSubBass;
    }

    /**
     * Get lead.
     *
     * @return Lead.
     */
    public int[][] getLead()
    {
        return _cLead;
    }

    /**
     * Get wave drums.
     * 
     * @param instrument Instrument.
     *
     * @return Wave.
     */
    private double[] getWave(int[][] instrument)
    {
    	int i;
    	double[] part;
    	double[] wave 	= new double[0];
    	
    	for (i = 0; i < instrument.length; i++) {
            if (instrument[i][Rhythm.I_INSTR] == Rhythm.RELEASE) {
                part = new double[0];
            } else {
                part = WaveInstruments.factory(
                    instrument[i][Rhythm.I_INSTR],
                    instrument[i][Rhythm.I_NOTE],
                    _cSizeStep * instrument[i][Rhythm.I_LENGTH]
                );
            }

            wave = ToolArray.concat(
                wave,
                Arrays.copyOfRange(part, 0, _cSizeStep * instrument[i][Rhythm.I_LENGTH])
            );
    	}
    	
    	return wave;
    }

    /**
     * Save wave.
     */
    public void save()
    {
        int amplitude = (int) ToolArray.maxAbs(_cWave);

        _cWav.setFrames(_cWave, amplitude, false);
        _cWav.save(CONFIG_PATH);
    }
}
