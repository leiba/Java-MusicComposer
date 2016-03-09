package dp.leiba.music.tools;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Wav file.
 */
public class Wav
{
    public static final int AMPLITUDE = 1;

    protected String  _iChunkId       = "RIFF";   // 4b
    protected int     _iChunkSize     = 58;       // 4b
    protected String  _iFormat        = "WAVE";   // 4b
    protected String  _iSubChunk1Id   = "fmt ";   // 4b
    protected int     _iSubChunk1Size = 16;       // 4b
    protected short   _iAudioFormat   = 3;        // 2b
    protected short   _iNumChannels   = 1;        // 2b 1/2
    protected int     _iSampleRate    = 44100;    // 4b 8000/44100
    protected int     _iByteRate      = 44100 * 4;   // 4b Bytes per second
    protected short   _iBlockAlign    = 4;        // 2b Bytes in sample for all channels
    protected short   _iBitsPerSample = 32;       // 2b Bits in sample
    protected String  _iSubChunk2Id   = "data";   // 4b
    protected int     _iSubChunk2Size = 3;        // 4b Bytes in data part

    protected byte[] _iFrames = new byte[0];

    /**
     * Wav.
     */
    public Wav()
    {
        // Empty wave file.
    }

    /**
     * Bytes per second.
     *
     * @return Count.
     */
    public int getBytesPerSecond()
    {
        return _iSampleRate;
    }

    /**
     * Set frames with convert.
     *
     * @param frames    Frames.
     * @param amplitude Amplitude.
     * @param isAppend  Is append.
     */
    public void setFrames(double[] frames, int amplitude, boolean isAppend)
    {
        int i, from, to, shift;
        byte[] iFrames = new byte[frames.length * _iBlockAlign], chunk;

        for (i = 0; i < frames.length; i++) {
            chunk =  _byteFromFloat((float) frames[i], false);
            from 	= i * _iBlockAlign;
            to		= from + _iBlockAlign;            
            
            for (shift = 0; from < to; from++) {
            	iFrames[from] = chunk[shift++]; 
            }
        }

        if (isAppend) {
            _iFrames = _byteAppend(_iFrames, iFrames);
        } else {
            _iFrames = iFrames;
        }
    }

    /**
     * Set frames for channel.
     *
     * @param frames Frames.
     */
    public void setFrames(byte[] frames)
    {
        _iFrames = frames;
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
            data = _byteAppend(data, _byteFromInt(_iFrames.length));
            data = _byteAppend(data, _iFrames);

            stream.write(data);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return _byteFromInt(value, true);
    }
    
    /**
     * Int to byte array.
     *
     * @param value Value.
     *
     * @return Byte array.
     */
    public byte[] _byteFromInt(int value, boolean reverse)
    {
        byte[] data = ByteBuffer.allocate(4).putInt(value).array();
        
        if (reverse) {
        	_byteReverse(data);
        }

        return data;
    }
    
    /**
     * Int to byte array.
     *
     * @param value Value.
     *
     * @return Byte array.
     */
    public byte[] _byteFromFloat(float value, boolean reverse)
    {
        byte[] data = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(value).array();
        
        if (reverse) {
        	_byteReverse(data);
        }

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
        int i           = 0;
        byte[] total    = new byte[data1.length + data2.length];

        for (; i < total.length; i++) {
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
        int left    = 0;
        int right   = data.length - 1;

        while(left < right) {
            byte temp   = data[left];
            data[left]  = data[right];
            data[right] = temp;

            left++;
            right--;
        }
    }
}