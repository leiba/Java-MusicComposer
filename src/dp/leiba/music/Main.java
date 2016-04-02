package dp.leiba.music;

import dp.leiba.music.creation.Composer;
import dp.leiba.music.creation.Theory;
import dp.leiba.music.person.Person;
import dp.leiba.music.tools.ToolFFT;
import dp.leiba.music.tools.ToolSpectrum;
import dp.leiba.music.tools.Wav;
import dp.leiba.music.tools.WaveInstruments;

/**
 * Main.
 */
public class Main
{

    /**
     * Start point.
     *
     * @param args Input arguments.
     */
    public static void main(String[] args)
    {
    	Person.say("Hello");
    	
    	if (false) {
            Wav w = new Wav();
            double[] wave = WaveInstruments.ride(1);
            w.setFrames(wave,  Wav.AMPLITUDE, false);
            w.save("/var/www/bit_test.wav");

            new ToolSpectrum(ToolFFT.fft(wave));


        } else {
            Composer composer = new Composer();
            composer.save();

            Person.debug("BPM",    composer.getBPM());
            Person.debug("Major",  composer.getIsMajor());
            Person.debug("Note",   composer.getNote());
            Person.debug("Notes",  composer.getNotes());
            Person.debug("Chords", composer.getChords());
            Person.debug("Lead",   composer.getLead());
            Person.debug("Bass",   composer.getBass());
            Person.debug("SubBass",composer.getSubBass());

            Person.say("Bye");
        }
    }
}
