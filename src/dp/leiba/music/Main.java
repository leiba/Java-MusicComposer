package dp.leiba.music;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.filters.HighPass;
import be.tarsos.dsp.filters.LowPassFS;
import be.tarsos.dsp.io.TarsosDSPAudioFloatConverter;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.jvm.AudioPlayer;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;
import dp.leiba.music.person.Person;
import dp.leiba.music.tools.*;

import javax.sound.sampled.AudioInputStream;
import java.io.ByteArrayInputStream;

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




     	Complex[] c = ToolFFT.fft(wave);
    	System.out.println(c.length);
    	
    	new ToolSpectrum(c);
    	Wav w = new Wav();
    	w.setFrames(wave,  Wav.AMPLITUDE, false);
    	w.save("/var/www/bit_ride.wav");
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

    private static float[] cd(double[] f)
    {
        int i;
        float[] d = new float[f.length];

        for (i = 0; i < f.length; i++) {
            d[i] = (float) f[i];
        }

        return d;
    }

    private static double[] cf(float[] f)
    {
        int i;
        double[] d = new double[f.length];

        for (i = 0; i < f.length; i++) {
            d[i] = f[i];
        }

        return d;
    }
}
