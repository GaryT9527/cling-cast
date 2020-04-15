package cn.garyt.cast.gstreamer.player;

import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.Pipeline;

import java.net.URI;
import java.util.Collection;

public interface MediaPlayer {
    /**
     * Gets the {@link Pipeline} that the MediaPlayer uses to play media.
     *
     * @return A Pipeline
     */
    Pipeline getPipeline();

    /**
     * Sets the Element to use for audio output.
     *
     * @param sink The {@link Element} to use for audio output.
     */
    public void setAudioSink(Element sink);

    /**
     * Sets the Element to use for video output.
     *
     * @param sink The {@link Element} to use for video output.
     */
    public void setVideoSink(Element sink);

    /**
     * Sets the media file to play.
     *
     * @param uri The URI that describes the location of the media file.
     */
    void setURI(URI uri);

    /**
     * Starts playing the media (as set by {@link #setURI}.
     */
    void play();

    /**
     * Pauses playing the currently playing media file.
     */
    void pause();

    /**
     * Stops playing the currently playing media file.
     */
    void stop();

    /**
     * Tests if this media player is currently playing a media file.
     *
     * @return true if a media file is being played.
     */
    public boolean isPlaying();

    /**
     * Adds a uri to the playlist
     *
     * @param uri The uri to add to the playlist.
     */
    void enqueue(URI uri);

    /**
     * Adds a list of media files to the playlist.
     *
     * @param playlist The list of media files to add.
     */
    void enqueue(Collection<URI> playlist);

    /**
     * Replaces the current playlist with a new playlist.
     * @param playlist The new playlist.
     */
    void setPlaylist(Collection<URI> playlist);

    /**
     * Removes a file from the playlist.
     * @param uri The uri to remove.
     */
    void remove(URI uri);

    void setVolume(double volume);

    double getVolume();

    /**
     * Adds a listener for media events
     *
     * @param listener The {@link MediaListener} to receive the events.
     */
    void addMediaListener(MediaListener listener);

    /**
     * Removes a listener for media events.
     * @param listener The previously added {@link MediaListener}.
     */
    void removeMediaListener(MediaListener listener);
}
