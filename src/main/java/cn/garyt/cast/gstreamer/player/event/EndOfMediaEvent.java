package cn.garyt.cast.gstreamer.player.event;

import cn.garyt.cast.gstreamer.player.MediaPlayer;
import org.freedesktop.gstreamer.ClockTime;
import org.freedesktop.gstreamer.State;

public class EndOfMediaEvent extends StopEvent {

    private static final long serialVersionUID = -263597731897962635L;

    public EndOfMediaEvent(MediaPlayer source, State previous, State current, State pending) {
        super(source, previous, current, pending, ClockTime.NONE);
    }
}