package dp.leiba.music.creation;

import dp.leiba.music.tools.ArrayTool;
import dp.leiba.music.tools.Wav;

/**
 * Composer.
 */
public class Composer
{
    public static final String CONFIG_PATH = "/var/www/bit_b.wav";

    private Wav         _cWav   = new Wav();
    private int         _cBarSize;
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
        _cBarSize   = _cWav.getBytesPerSecond() / 2;
        _cWave      = new double[_cBarSize * _cBars];

        _cRhythm    = Rhythm.getRhythmMelody(_cBars, _cBeats);
        _cNote      = Melody.getNote();
        _cIsMajor   = Melody.getIsMajor();
        _cChords    = Melody.getChords(_cBars, _cNote, _cIsMajor);
        _cNotes     = Theory.getHarmony(_cNote, _cIsMajor, 3);

        _cLead      = Melody.getLead(_cBars, _cBeats, _cRhythm, _cChords, _cNotes);
        _cPluck     = Melody.getPluck(_cBars, _cBeats, _cRhythm, _cChords);
        _cBass      = Melody.getBass(_cBars, _cBeats, _cRhythm, _cChords);
        _cDrums     = Rhythm.getRhythmDrums(_cBars, _cBeats);


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
