package dp.leiba.music;

import java.util.Arrays;

import dp.leiba.music.creation.Composer;
import dp.leiba.music.creation.Theory;
import dp.leiba.music.person.Person;
import dp.leiba.music.tools.*;

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
    	
    	double[] wave1 = new double[0];
    	double[] wave2 = new double[0];
    	
    	for (int i = 0; i < 10; i++) {
    		wave1 = ToolArray.concat(wave1, WaveForms.sine(4000, Wav.AMPLITUDE / 2.5));
    		
    		if (i % 2 == 0) {
    			for (int j = 0; j < 100; j++)
    			wave2 = ToolArray.concat(wave2, WaveForms.sine(40, Wav.AMPLITUDE / 2.5));
    		} else {
    			wave2 = ToolArray.concat(wave2, Arrays.copyOfRange(new double[0], 0, 4000));
    		}
    	}
    	
    	wave2 = Wave.mix(new double[][] {wave1, wave2});
    	
    	Wav wv = new Wav();
    	wv.setFrames(Wave.limit(wave2),  Wav.AMPLITUDE, false);
    	wv.save("D:\\bit_ex.wav");
    	System.exit(0);
    	
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
