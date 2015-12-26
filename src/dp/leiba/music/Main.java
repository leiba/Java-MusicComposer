package dp.leiba.music;

import dp.leiba.music.creation.Theory;
import dp.leiba.music.tools.Wav;
import dp.leiba.music.tools.WaveForms;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

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
        //Wav wav = new Wav("/var/www/example.wav");
//        Wav wavw = new Wav();
//        wavw.save("/var/www/say2.wav");


        ArrayList<byte[]> data = new ArrayList<byte[]>();
        for(int i =0; i < 50; i++) {
            double[] part = WaveForms.sine(10, 200);
            for(int j = 0; j < part.length; j++) {
                data.add(ByteBuffer.allocate(2).putShort((short) Math.abs(part[j])).array());
            }
        }

        Wav wav = new Wav();
        wav.setFrames(data);
        wav.save("/var/www/say1.wav");

        System.out.println("Bit B, say bye!");
    }
}
