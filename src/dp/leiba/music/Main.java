package dp.leiba.music;

import dp.leiba.music.creation.Composer;
import dp.leiba.music.person.Person;
import dp.leiba.music.tools.ArrayTool;
import dp.leiba.music.tools.Wav;
import dp.leiba.music.tools.WaveFilters;
import dp.leiba.music.tools.WaveForms;
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
    	
    	Wav wav1 = new Wav();
    	double[] wave = new double[0];

    	for (int i = 0; i < 10; i++) wave = ArrayTool.concat(wave, WaveForms.sine(10000, 100));
    	wav1.setFrames(wave, 100, false);
    	wav1.save("D:\\komm.wav");
    	Person.say("End");
    	System.exit(0);
    	
    	Wav wav = new Wav();
    	wav.setFrames(WaveInstruments.click(wav.getBytesPerSecond(), 100), 100, false);
    	wav.save("D:\\bit_b2.wav");
    	System.exit(0);

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
