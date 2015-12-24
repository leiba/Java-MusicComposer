package dp.leiba.music.tools;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Wav file.
 */
public class Wav
{
    public static final int I_CHANNEL_MONO     = 1;
    public static final int I_CHANNEL_STEREO   = 2;

    protected String  _iChunkId       = "RIFF";   // 4b
    protected int     _iChunkSize     = 0;        // 4b
    protected String  _iFormat        = "WAVE";   // 4b
    protected String  _iSubChunk1Id   = "fmt ";   // 4b
    protected int     _iSubChunk1Size = 16;       // 4b
    protected short   _iAudioFormat   = 1;        // 2b
    protected short   _iNumChannels   = 1;        // 2b 1/2
    protected int     _iSampleRate    = 8000;     // 4b 8000/44100
    protected int     _iByteRate      = 0;        // 4b Bytes per second
    protected short   _iBlockAlign    = 0;        // 2b Bytes in sample for all channels
    protected short   _iBitsPerSample = 0;        // 2b Bits in sample
    protected String  _iSubChunk2Id   = "data";   // 4b
    protected int     _iSubChunk2Size = 16;       // 4b Bytes in data part

    protected ArrayList<byte[]> _iFramesLeft  = new ArrayList<byte[]>();
    protected ArrayList<byte[]> _iFramesRight = new ArrayList<byte[]>();

    /**
     * Wav.
     */
    public Wav()
    {

    }

    /**
     * Wav.
     *
     * @param path File path.
     */
    public Wav(String path)
    {
        try {
            byte[]  data = Files.readAllBytes(Paths.get(path));
            byte[]  sample;
            int     sampleHalf;

            _iChunkId       = _byteToString(data, 0, 4);
            _iChunkSize     = _byteToInt(data, 4, 8);
            _iFormat        = _byteToString(data, 8, 12);
            _iSubChunk1Id   = _byteToString(data, 12, 16);
            _iSubChunk1Size = _byteToInt(data, 16, 20);
            _iAudioFormat   = _byteToShort(data, 20, 22);
            _iNumChannels   = _byteToShort(data, 22, 24);
            _iSampleRate    = _byteToInt(data, 24, 28);
            _iByteRate      = _byteToInt(data, 28, 32);
            _iBlockAlign    = _byteToShort(data, 32, 34);
            _iBitsPerSample = _byteToShort(data, 34, 36);
            _iSubChunk2Id   = _byteToString(data, 36, 40);
            _iSubChunk2Size = _byteToInt(data, 40, 44);

            for (int i = 44; i < data.length - _iBlockAlign; i+= _iBlockAlign) {
                sample      = Arrays.copyOfRange(data, i, i + _iBlockAlign);
                sampleHalf  = sample.length / 2;

                if (_iNumChannels == I_CHANNEL_MONO) {
                    _iFramesLeft.add(sample);
                } else {
                    _iFramesLeft.add(Arrays.copyOfRange(sample, 0, sampleHalf));
                    _iFramesRight.add(Arrays.copyOfRange(sample, sampleHalf, sampleHalf));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get channel frames.
     *
     * @param channel Channel.
     *
     * @return Frames.
     */
    public ArrayList<byte[]> getFrames(int channel) {
        return channel == I_CHANNEL_MONO ? _iFramesLeft : _iFramesRight;
    }

    /**
     * Get channel amplify.
     *
     * @param channel Channel number.
     *
     * @return Amplify
     */
    public int[] getAmplify(int channel)
    {
        ArrayList<byte[]>   frames  = getFrames(channel);
        int[]               amplify = new int[frames.size()];
        byte[]              frame;

        for (int i = 0; i < frames.size(); i++) {
            frame = frames.get(i);
            amplify[i] = frame.length == 2 ? _byteToShort(frame, 0, frame.length)
                    : _byteToInt(frame, 0, frame.length);
        }

        return amplify;
    }

    /**
     * Set frames for channel.
     *
     * @param frames  Frames.
     * @param channel Channel.
     */
    public void setFrames(ArrayList<byte[]> frames, int channel)
    {
        if (channel == I_CHANNEL_MONO) {
            _iFramesLeft.clear();
            _iFramesLeft.addAll(frames);
        } else {
            _iFramesRight.clear();
            _iFramesRight.addAll(frames);
        }
    }

    /**
     * Fill random amplify.
     *
     * @param size Number of frames.
     */
    public void fillRandom(int size)
    {
        ArrayList<byte[]>   frames = new ArrayList<byte[]>();
        Random              random = new Random();

        for (int i = 0; i < size; i++) {
            frames.add(ByteBuffer.allocate(2).putShort((short) random.nextInt(250)).array());
        }

        setFrames(frames, I_CHANNEL_MONO);

        if (_iNumChannels == I_CHANNEL_STEREO) {
            setFrames(frames, I_CHANNEL_STEREO);
        }
    }

    /**
     * Save sound to file.
     *
     * @param path Path.
     */
    public void save(String path)
    {
        try {
            byte[]              data    = new byte[0];
            FileOutputStream    stream  = new FileOutputStream(path);

            data = _byteAppend(data, _iChunkId.getBytes());
            data = _byteAppend(data, _byteFromInt(_iChunkSize));
            data = _byteAppend(data, _iFormat.getBytes());
            data = _byteAppend(data, _iSubChunk1Id.getBytes());
            data = _byteAppend(data, _byteFromInt(_iSubChunk1Size));
            data = _byteAppend(data, _byteFromShort(_iAudioFormat));
            data = _byteAppend(data, _byteFromShort(_iNumChannels));
            data = _byteAppend(data, _byteFromInt(_iSampleRate));
            data = _byteAppend(data, _byteFromInt(_iByteRate));
            data = _byteAppend(data, _byteFromShort(_iBlockAlign));
            data = _byteAppend(data, _byteFromShort(_iBitsPerSample));
            data = _byteAppend(data, _iSubChunk2Id.getBytes());
            data = _byteAppend(data, _byteFromInt(_iSubChunk2Size));

            for (int i = 0; i < _iFramesLeft.size(); i++) {
                data = _byteAppend(data, _iFramesLeft.get(i));

                if (_iNumChannels == I_CHANNEL_STEREO) {
                    data = _byteAppend(data, _iFramesRight.get(i));
                }
            }

            stream.write(data);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Convert byte array to string.
     *
     * @param data Byte data.
     *
     * @return String.
     */
    private String _byteToString(byte[] data, int from, int to)
    {
        return new String(Arrays.copyOfRange(data, from, to));
    }

    /**
     * Convert byte array to short.
     *
     * @param data Byte data.
     *
     * @return Short.
     */
    private short _byteToShort(byte[] data, int from, int to)
    {
        return ByteBuffer.wrap(Arrays.copyOfRange(data, from, to))
                .order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    /**
     * Convert byte array to int.
     *
     * @param data Byte data.
     *
     * @return Int.
     */
    private int _byteToInt(byte[] data, int from, int to)
    {
        return ByteBuffer.wrap(Arrays.copyOfRange(data, from, to))
                .order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    /**
     * Short to byte array.
     *
     * @param value Value.
     *
     * @return Short array.
     */
    public byte[] _byteFromShort(short value)
    {
        byte[] data =  ByteBuffer.allocate(2).putShort(value).array();
        _byteReverse(data);

        return data;
    }

    /**
     * Int to byte array.
     *
     * @param value Value.
     *
     * @return Byte array.
     */
    public byte[] _byteFromInt(int value)
    {
        byte[] data = ByteBuffer.allocate(4).putInt(value).array();
        _byteReverse(data);

        return data;
    }

    /**
     * Merge byte arrays.
     *
     * @param data1 Array one.
     * @param data2 Array two.
     *
     * @return Merged array.
     */
    private byte[] _byteAppend(byte[] data1, byte[] data2)
    {
        byte[] total = new byte[data1.length + data2.length];

        for (int i = 0; i < total.length; i++) {
            total[i] = i < data1.length ? data1[i] : data2[i - data1.length];
        }

        return total;
    }

    /**
     * Reverse byte array.
     *
     * @param data Array.
     */
    private void _byteReverse(byte[] data) {
        int left = 0;
        int right = data.length - 1;

        while( left < right ) {
            byte temp = data[left];
            data[left] = data[right];
            data[right] = temp;

            left++;
            right--;
        }
    }
}
