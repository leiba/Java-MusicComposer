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
    	
//    	Wav wav = new Wav();
//    	wav.setFrames(WaveInstruments.stick(wav.getBytesPerSecond(), 100), 100, false);
//    	wav.save("D:\\kickb.wav");
//    	System.exit(0);

        Composer composer = new Composer();
        composer.save();
        
        Person.debug("BPM",    composer.getBPM());
        Person.debug("Major",  composer.getIsMajor());
        Person.debug("Note",   composer.getNote());
        Person.debug("Notes",  composer.getNotes());
        Person.debug("Chords", composer.getChords());
        Person.debug("Lead",   composer.getLead());
        
        Person.say("Bye");
    }
}
