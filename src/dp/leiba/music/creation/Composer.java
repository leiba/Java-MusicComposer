package dp.leiba.music.creation;

import dp.leiba.music.tools.ArrayTool;
import dp.leiba.music.tools.Wav;
import dp.leiba.music.tools.WaveForms;

/**
 * Composer.
 */
public class Composer
{
    public static final String  CONFIG_PATH         = "/var/www/bit_b.wav";
    public static final int     CONFIG_AMPLITUDE    = 100;

    private Wav         _cWav   = new Wav();
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
        _cSizeBar   = _cSizeSec;
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

        ArrayTool.fillSum(_cWave, fill(_cBass), 0);
        ArrayTool.fillSum(_cWave, fill(_cLead), 0);
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
     * Get notes.
     *
     * @return Harmony notes.
     */
    public String[] getNotes()
    {
        int i           = 0;
        String[] notes  = new String[_cNotes.length];

        for (; i < notes.length; i++) {
            notes[i] = Theory.getNoteName(_cNotes[i]) + "(" + _cNotes[i] + ")";
        }

        return notes;
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
    public double[] fill(int[] notes)
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
                    ArrayTool.fill(beat, WaveForms.sine(points, CONFIG_AMPLITUDE), j);
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
     * Save wave.
     */
    public void save()
    {
        int amplitude = (int) ArrayTool.maxAbs(_cWave);

        _cWav.setFrames(_cWave, amplitude, false);
        _cWav.save(CONFIG_PATH);
    }
}
