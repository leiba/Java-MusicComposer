package dp.leiba.music;

import java.util.Arrays;

import dp.leiba.music.creation.Composer;
import dp.leiba.music.person.Person;

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

        Composer composer = new Composer();
        composer.save();
        
        Person.debug("Major",  composer.getIsMajor());
        Person.debug("Note",   composer.getNote());
        Person.debug("Notes",  composer.getNotes());
        Person.debug("Chords", composer.getChords());
        Person.debug("Lead",   composer.getLead());
        
        Person.say("Bye");
    }
}
