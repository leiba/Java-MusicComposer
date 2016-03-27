package dp.leiba.music;

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
    	
    	int i;
    	int p = Wav.FREQUENCY / 10000;
    	double[] wave = new double[0];
    	
    	for (i = 0; i < 10000; i++) {
    		wave = ToolArray.concat(wave, WaveForms.sine(p, Wav.AMPLITUDE));
    		//System.out.println((i * 44100 / 1024) + " : " + c[i].mod());
    		//System.out.println(c[i].arg());
    	}
    	
    	wave = WaveInstruments.ride(Wav.FREQUENCY, Wav.AMPLITUDE);
//    	wave = ToolFFT.fftFilter(wave, new ToolFFT.FFTFilter[] {
//    		//ToolFFT.filter(ToolFFT.FILTER_LOW, 13000, 0)
//    	});
    	Complex[] c = ToolFFT.fft(wave);
    	System.out.println(c.length);
    	
    	new ToolSpectrum(c);
    	Wav w = new Wav();
    	w.setFrames(wave,  Wav.AMPLITUDE, false);
    	w.save("D:\\bit_ride.wav");
    	//System.exit(0);
    	
    	/*
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
        */
    }
}
