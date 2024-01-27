package ChatterMod.actions;

import ChatterMod.MainModfile;
import ChatterMod.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class RecordAndPlaybackAction extends AbstractGameAction {
    private TargetDataLine input;
    private final File audioFile = new File("ChatterMod_Chatter.wav");
    private final Consumer<Float> callback;
    public RecordAndPlaybackAction(Consumer<Float> callback) {
        this.duration = this.startDuration = Settings.ACTION_DUR_XLONG;
        this.callback = callback;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            new CaptureThread().start();
        }
        tickDuration();
        if (isDone) {
            if (input != null) {
                input.stop();
                input.close();
            }
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractDungeon.effectsQueue.add(new ShockWaveEffect(Wiz.adp().hb.cX, Wiz.adp().hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC));
                    callback.accept(getAvgVolume(audioFile));
                    play(audioFile);
                    this.isDone = true;
                }
            });
        }
    }

    public float getAvgVolume(File file) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            long length = ais.getFrameLength();
            if (length <= 0) {
                return 0;
            }
            byte[] bytes = new byte[(int) length*2];
            int numBytes = ais.read(bytes);
            float total = 0f;
            for (int i = 0 ; i < numBytes ; i += 2) {
                int audioVal = (bytes[i+1] << 8) | (bytes[i] & 0xff);
                total += Math.pow(audioVal, 2);
                //total += Math.abs(bytes[i]);
            }
            total /= length;
            total = (float) (Math.log10(total) * 10);
            //total = (float) Math.sqrt(total);
            //total /= Short.MAX_VALUE/2f;
            ais.close();
            return total;
        } catch (UnsupportedAudioFileException | IOException e) {
            MainModfile.logger.error("Could not load file:");
            e.printStackTrace();
        }
        return 0;
    }

    public void play(File file) {
        try {
            final Clip clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP)
                    clip.close();
            });

            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
        }
        catch (Exception e) {
            MainModfile.logger.error("Could not load file:");
            e.printStackTrace(System.out);
        }
    }

    private class CaptureThread extends Thread {
        @Override
        public void run() {
            AudioFormat format = new AudioFormat(32000f, 16, 1, true, false);
            try {
                input = AudioSystem.getTargetDataLine(format);
                AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
                input.open(format);
                input.start();
                AudioSystem.write(
                        new AudioInputStream(input),
                        fileType,
                        audioFile);
            } catch (LineUnavailableException e) {
                MainModfile.logger.error("No recording device detected:");
                e.printStackTrace();
            } catch (IOException e) {
                MainModfile.logger.error("Could not write to file:");
                e.printStackTrace();
            }
        }
    }
}
