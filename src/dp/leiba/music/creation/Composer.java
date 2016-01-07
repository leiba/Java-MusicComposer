package dp.leiba.music.creation;

import dp.leiba.music.tools.ArrayTool;
import dp.leiba.music.tools.Wav;
import dp.leiba.music.tools.WaveEffect;
import dp.leiba.music.tools.WaveForms;
import dp.leiba.music.tools.WaveInstruments;

/**
 * Composer.
 */
public class Composer
{
    public static final String  CONFIG_PATH         = "D:\\bit_b.wav";
    public static final int     CONFIG_AMPLITUDE    = 100;
    public static final double  CONFIG_SECONDS      = 60.0;

    private Wav         _cWav   = new Wav();
    private int 		_cBPM	= Rhythm.getBPM();
    private int         _cSizeSec;
    private int         _cSizeBar;
    private int         _cSizeBeat;
    private double[]    _cWave;

    private int         _cBars  = 4;
    private int         _cBeats = 4;

    private int[]       _cRhythm;
    private int         _cNote;
    private boolean     _cIsMajor;
    private int[][]     _cChords;
    private int[]       _cNotes;

    private int[]       _cLead;
    private int[][]     _cPluck;
    private int[]       _cBass;
    private int[][]     _cDrums;

    public Composer()
    {
        _cSizeSec   = _cWav.getBytesPerSecond();
        _cSizeBar   = (int) (_cSizeSec * (_cBPM / CONFIG_SECONDS));
        _cSizeBeat  = _cSizeBar / _cBeats;
        _cWave      = new double[_cSizeBar * _cBars];

        _cRhythm    = Rhythm.getRhythmMelody(_cBars, _cBeats);
        _cNote      = Melody.getNote();
        _cIsMajor   = Melody.getIsMajor();
        _cChords    = Melody.getChords(_cBars, _cNote, _cIsMajor);
        _cNotes     = Theory.getHarmony(_cNote, _cIsMajor, 3);

        _cLead      = Melody.getLead(_cBars, _cBeats, _cRhythm, _cChords, _cNotes);
        _cPluck     = Melody.getPluck(_cBars, _cBeats, _cRhythm, _cChords);
        _cBass      = Melody.getBass(_cBars, _cBeats, _cRhythm, _cChords);
        _cDrums     = Rhythm.getRhythmDrums(_cBars, _cBeats);

        ArrayTool.fillSum(_cWave, getWaveDrums(_cDrums), 0);
        ArrayTool.fillSum(_cWave, WaveEffect.sideChain(fill(_cBass, WaveForms.WAVE_SQUARE), CONFIG_AMPLITUDE, _cSizeBeat), 0);
        // ArrayTool.fillSum(_cWave, fill(_cLead, WaveForms.WAVE_SAW), 0);
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
     * Get chords.
     *
     * @return Chords.
     */
    public int[][] getChords()
    {
        return _cChords;
    }

    /**
     * Get lead.
     *
     * @return Lead.
     */
    public int[] getLead()
    {
        return _cLead;
    }

    /**
     * Fill.
     *
     * @param notes Notes.
     *
     * @return Wave.
     */
    public double[] fill(int[] notes, int form)
    {
        int points, i, j, rest;
        double[] beat;
        double[] wave   = new double[_cSizeBar * _cBars];

        for (i = 0; i< notes.length; i++) {
            beat = new double[_cSizeBeat];

            if (notes[i] == Rhythm.MELODY_REST) {
                beat = WaveForms.rest(beat.length);
            } else {
                points  = (int) (_cSizeSec / Theory.getNoteFreq(notes[i]));

                for (j = 0; j < beat.length; j += points) {
                    ArrayTool.fill(beat, WaveForms.factory(points, CONFIG_AMPLITUDE, form), j);
                }

                if (_cRhythm[i] == Rhythm.MELODY_RELEASE || true) {
                    rest = points / 3;
                    ArrayTool.fillZero(beat, j + (points - rest), j + points);
                }
            }

            ArrayTool.fill(wave, beat, i * _cSizeBeat);
        }

        return wave;
    }
    
    /**
     * Get wave.
     * 
     * @param notes Notes.
     * 
     * @return Wave.
     */
    private double[] getWave(int[][] notes)
    {
    	return new double[0];
    }
    
    /**
     * Get wave drums.
     * 
     * @param drums Drums.
     * 
     * @return Wave.
     */
    private double[] getWaveDrums(int[][] drums)
    {
    	int i, j;
    	double[] part;
    	double[] wave 	= new double[_cSizeBar * _cBars];
    	
    	for (i = 0; i < drums.length; i++) {
    		for (j = 0; j < drums[i].length; j++) {
				part = WaveInstruments.factory(_cWav.getBytesPerSecond(), CONFIG_AMPLITUDE, drums[i][j]);				
				
				if (drums[i][j] == Rhythm.DRUMS_HAT) {
					part = _getWaveHat(part, 2);
				}
				
				ArrayTool.fillSum(wave, part, i * _cSizeBeat);
    		}
    	}
    	
    	return wave;
    }

    /**
     * Save wave.
     */
    public void save()
    {
        int amplitude = (int) ArrayTool.maxAbs(_cWave);

        _cWav.setFrames(_cWave, amplitude, false);
        _cWav.save(CONFIG_PATH);
    }
    
    /**
     * Wave hat.
     * 
     * @param hat   Hat.
     * @param times Times.
     * 
     * @return Wave.
     */
    private double[] _getWaveHat(double[] hat, int times)
    {
    	int i 			= 0;
    	int quarter		= _cSizeBeat / times;
    	double[] wave 	= new double[_cSizeBeat]; 
    	
    	for (; i < times; i++) {
    		ArrayTool.fillSum(wave, hat, i * quarter);
    	}
    	
    	return wave;
    }
}
