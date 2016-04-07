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
    	
    	/*
    	int i;
    	double[] wave = new double[0];
    	
    	for (i = 0; i < 100; i++) {
    		wave = ToolArray.concat(wave, WaveForms.sine(220, Wav.AMPLITUDE));
    	}
    	
    	for (i = 0; i < 100; i++) {
    		wave = ToolArray.concat(wave, WaveForms.sine(220, Wav.AMPLITUDE * 0.6));
    	}
    	
    	for (i = 0; i < 100; i++) {
    		wave = ToolArray.concat(wave, WaveForms.sine(220, Wav.AMPLITUDE * 0.3));
    	}
    	
    	wave = Wave.limit(Wave.compress(wave, 0.3, 60, 0, 11000));
    	
    	Wav wv = new Wav();
    	wv.setFrames(wave,  Wav.AMPLITUDE, false);
    	wv.save("D:\\bit_ex.wav");
    	System.exit(0);
    	*/
    	
        Composer composer = new Composer();
        composer.save();

        Person.debug("BPM",    composer.getBPM());
        Person.debug("Major",  composer.getIsMajor());
        Person.debug("Note",   composer.getNote());
        Person.debug("Notes",  composer.getNotes());
        Person.debug("Chords", composer.getChords());
        Person.debug("Lead",   composer.getLead());
        Person.debug("Bass",   composer.getBass());
        //Person.debug("SubBass",composer.getSubBass());

        Person.say("Bye");
    }
}
