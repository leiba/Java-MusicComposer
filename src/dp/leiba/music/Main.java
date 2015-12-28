package dp.leiba.music;

import dp.leiba.music.tools.Wav;
import dp.leiba.music.tools.WaveForms;

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
        System.out.println("Bit B:");

        Wav wav         = new Wav();
        int amplitude   = 100;

        for(int i = 0; i < 80; i++) {
            wav.setFrames(WaveForms.sine(100, amplitude), amplitude, true);
        }
        wav.save("/var/www/say1.wav");

        System.out.println("Bit B, say bye!");
    }
}
