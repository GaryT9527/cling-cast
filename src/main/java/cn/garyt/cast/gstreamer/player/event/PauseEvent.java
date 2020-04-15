package cn.garyt.cast.gstreamer.player.event;

import cn.garyt.cast.gstreamer.player.MediaPlayer;
import org.freedesktop.gstreamer.State;

public class PauseEvent extends StopEvent {

    private static final long serialVersionUID = -6562753860450657360L;

    public PauseEvent(MediaPlayer source, State previous, State current, State pending, long mediaTime) {
        super(source, previous, current, pending, mediaTime);
    }
}
