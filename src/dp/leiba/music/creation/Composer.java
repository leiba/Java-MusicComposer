package dp.leiba.music.creation;

import dp.leiba.music.tools.ToolArray;
import dp.leiba.music.tools.Wav;
import dp.leiba.music.tools.WaveForms;
import dp.leiba.music.tools.WaveInstruments;

import java.util.Arrays;

/**
 * Composer.
 */
public class Composer
{
    public static final String  CONFIG_PATH         = "/var/www/bit_b.wav";
    public static final double  CONFIG_SECONDS      = 60.0;

    private Wav         _cWav   = new Wav();
    private int 		_cBPM	= Rhythm.getBPM();
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
    private int[]       _cSubBass;
    private int[]       _cBass;
    private int[]       _cDrumKick;

    public Composer()
    {
        _cSizeBeat   = (int) (Wav.FREQUENCY / (_cBPM / CONFIG_SECONDS));
        _cSizeBar  = _cSizeBeat * _cBeats;
        _cWave      = new double[_cSizeBar * _cBars];

        _cRhythm    = Rhythm.getRhythmMelody(_cBars, _cBeats);
        _cNote      = Melody.getNote();
        _cIsMajor   = Melody.getIsMajor();
        _cChords    = Melody.getChords(_cBars, _cNote, _cIsMajor);
        _cNotes     = Theory.getHarmony(_cNote, _cIsMajor, 3);

        _cLead      = Melody.getLead(_cBars, _cBeats, _cRhythm, _cChords, _cNotes);
        _cPluck     = Melody.getPluck(_cBars, _cBeats, _cRhythm, _cChords);
        _cSubBass   = Melody.getSubBass(_cBars, _cBeats, _cRhythm, _cChords);
        _cBass      = Melody.getBass(_cBars, _cBeats, _cRhythm, _cChords);
        _cDrumKick  = Rhythm.getRhythmKick(_cBars, _cBeats);

        
        //ArrayTool.fillSum(_cWave, WaveEffect.sideChain(fill(_cSubBass, WaveForms.WAVE_SINE), Wav.AMPLITUDE, _cSizeBeat), 0, false);
        //ArrayTool.fillSum(_cWave, WaveEffect.sideChain(fill(_cBass, WaveForms.WAVE_ATAN), CONFIG_AMPLITUDE, _cSizeBeat), 0, false);
        ToolArray.fillSum(_cWave, getWaveDrum(_cDrumKick), 0, true);
        // ArrayTool.fillSum(_cWave, fill(_cLead, WaveForms.WAVE_TAN), 0);
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
     * Get bass.
     *
     * @return Lead.
     */
    public int[] getBass()
    {
        return _cBass;
    }

    /**
     * Get sub bass.
     *
     * @return Lead.
     */
    public int[] getSubBass()
    {
        return _cSubBass;
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

            if (notes[i] == Rhythm.RELEASE) {
                beat = WaveForms.rest(beat.length);
            } else {
                points  = (int) (Wav.FREQUENCY / Theory.getNoteFreq(notes[i]));

                for (j = 0; j < beat.length; j += points) {
                    ToolArray.fill(beat, WaveForms.factory(points, Wav.AMPLITUDE, form), j);
                }

                if (_cRhythm[i] == Rhythm.RELEASE || true) {
                    rest = points / 3;
                    ToolArray.fillZero(beat, j + (points - rest), j + points);
                }
            }

            ToolArray.fill(wave, beat, i * _cSizeBeat);
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
    private double[] getWaveDrum(int[] drums)
    {
    	int i;
    	double[] part;
    	double[] wave 	= new double[0];
    	
    	for (i = 0; i < drums.length; i++) {
            if (drums[i] != Rhythm.RELEASE) {
                part = WaveInstruments.factory(_cNote, drums[i]);
                wave = Arrays.copyOfRange(wave, 0, i * _cSizeBeat);
                wave = ToolArray.concat(wave, part);
            }
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
    		ToolArray.fillSum(wave, hat, i * quarter, true);
    	}
    	
    	return wave;
    }
}
