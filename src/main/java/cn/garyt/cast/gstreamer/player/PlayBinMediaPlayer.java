package cn.garyt.cast.gstreamer.player;


import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.State;
import org.freedesktop.gstreamer.elements.PlayBin;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Basic implementation of a MediaPlayer
 */
public class PlayBinMediaPlayer extends PipelineMediaPlayer {
    private static final Executor defaultExec = Executors.newSingleThreadExecutor();
    private final PlayBin playbin;

    public PlayBinMediaPlayer(String name, Executor eventExecutor) {
        super(new PlayBin(name), eventExecutor);
        playbin = (PlayBin) getPipeline();

    }

    public PlayBinMediaPlayer() {
        this("VideoPlayer", defaultExec);
    }

    /**
     * Sets the sink element to use for audio output.
     *
     * @param sink The sink to use for audio output.
     */
    public void setAudioSink(Element sink) {
        playbin.setAudioSink(sink);
    }

    /**
     * Sets the sink element to use for video output.
     *
     * @param sink The sink to use for video output.
     */
    public void setVideoSink(Element sink) {
        playbin.setVideoSink(sink);
    }

    /**
     * Tests if this media player is currently playing a media file.
     *
     * @return true if a media file is being played.
     */
    public boolean isPlaying() {
        return playbin.isPlaying();
    }

    /**
     * Pauses playback of a media file.
     */
    public void pause() {
        if (playbin.isPlaying()) {
            playbin.pause();
        }
    }

    /**
     * Starts or resumes playback of a media file.
     */
    public void play() {
        if (!playbin.isPlaying()) {
            playbin.play();
        }
    }

    /**
     * Stops playback of a media file.
     */
    public void stop() {
        playbin.stop();
    }

    /**
     * Sets the media file to play.
     *
     * @param uri The URI that describes the location of the media file.
     */
    public void setURI(URI uri) {

        State old = playbin.getState();
//        playbin.ready();
        playbin.setURI(uri);
        playbin.setState(old);
    }

    /**
     * Sets the current file to play.
     *
     * @param file the {@link File} to play.
     */
    public void setInputFile(File file) {
        setURI(file.toURI());
    }
    /**
     * Sets the audio output volume.
     *
     * @param volume a number between 0.0 and 1.0 representing the percentage of
     * the maximum volume.
     */
    public void setVolume(double volume) {
        playbin.setVolume(volume);
    }

    /**
     * Gets the current audio output volume.
     *
     * @return a number between 0.0 and 1.0 representing the percentage of
     * the maximum volume.
     */
    public double getVolume() {
        return playbin.getVolume();
    }


    /**
     * Parses the URI in the String.
     * <p>
     * This method will check if the uri is a file and return a valid URI for that file.
     *
     * @param uri the string representation of the URI.
     * @return a {@link URI}
     */
    protected static URI parseURI(String uri) {
        try {
            URI u = new URI(uri);
            if (u.getScheme() == null) {
                throw new URISyntaxException(uri, "Invalid URI scheme");
            }
            return u;
        } catch (URISyntaxException e) {
            File f = new File(uri);
            if (!f.exists()) {
                throw new IllegalArgumentException("Invalid URI/file " + uri, e);
            }
            return f.toURI();
        }
    }
}
