package dp.leiba.music;

import dp.leiba.music.creation.Composer;
import dp.leiba.music.person.Person;
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
    	
    	Wav w = new Wav();
    	w.setFrames(WaveInstruments.ride(w.getBytesPerSecond(), Wav.AMPLITUDE),  Wav.AMPLITUDE, false);
    	w.save("D:\\bit_ride.wav");
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
