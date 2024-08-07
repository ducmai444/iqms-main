package vn.com.itechcorp.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SoundUtil {

    @Value("${sound.path}")
    private String soundPath;

    private final static Logger logger = LoggerFactory.getLogger(SoundUtil.class);

    private final static String WAV_RESOURCE = "wav/";

    private byte[] XIN_MOI_BENH_NHAN = null;

    private byte[] VAO_PHONG = null;

    private Map<Character, byte[]> DIGIT_SOUNDS = new HashMap<>();

    public final byte[] getXinMoiBenhNhan() {
        if (XIN_MOI_BENH_NHAN != null) return XIN_MOI_BENH_NHAN;
        try {
            XIN_MOI_BENH_NHAN = IOUtils.toByteArray(new FileInputStream(soundPath + "/xin_moi_benh_nhan.wav"));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return XIN_MOI_BENH_NHAN;
    }

    public final byte[] getVaoPhong() {
        if (VAO_PHONG != null) return VAO_PHONG;

        try {
            VAO_PHONG = IOUtils.toByteArray(new FileInputStream(soundPath + "/vao_phong.wav"));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return VAO_PHONG;
    }

    public final byte[] getDigit(Character d) {
        byte[] bytes = DIGIT_SOUNDS.get(d);
        if (bytes != null) return bytes;

        try {
            bytes = IOUtils.toByteArray(new FileInputStream(soundPath + "/" + d + ".wav"));
            DIGIT_SOUNDS.put(d, bytes);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return bytes;
    }

    public byte[] speak(String number, byte[] tail) throws Exception {
        try (AudioInputStream audio = AudioSystem.getAudioInputStream(new ByteArrayInputStream(getXinMoiBenhNhan()))) {
            List<AudioInputStream> aus = new ArrayList<>();

            for (int i = 0; i < number.length(); ++i) {
                byte[] digit = getDigit(number.charAt(i));
                if (digit == null) continue;

                try (AudioInputStream digitAudio = AudioSystem.getAudioInputStream(new ByteArrayInputStream(digit))) {
                    aus.add(digitAudio);
                }
            }

            if (tail != null) {
                try (AudioInputStream tailAudio = AudioSystem.getAudioInputStream(new ByteArrayInputStream(tail))) {
                    aus.add(tailAudio);
                }
            }

            AudioInputStream res = appendAudioStream(audio, aus);
            return createByteArrayWithWaveHeader(res);
        }
    }

    private static AudioInputStream appendAudioStream(AudioInputStream audio, List<AudioInputStream> tail) {
        if (tail == null || tail.isEmpty()) return audio;
        for (AudioInputStream t : tail) {
            audio = new AudioInputStream(
                    new SequenceInputStream(audio, t),
                    audio.getFormat(),
                    audio.getFrameLength() + audio.getFrameLength());
        }

        return audio;
    }

    private static byte[] createByteArrayWithWaveHeader(AudioInputStream audioIn) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            AudioSystem.write(audioIn, AudioFileFormat.Type.WAVE, baos);
            byte[] arrayWithHeader = baos.toByteArray();
            return arrayWithHeader;
        }
    }

}
