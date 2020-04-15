package cn.garyt.cast.gstreamer.player.event;

import cn.garyt.cast.gstreamer.player.MediaPlayer;

public class DurationChangedEvent extends MediaEvent {

    private static final long serialVersionUID = 3789108588436981142L;

    final long duration;

    public DurationChangedEvent(MediaPlayer from, long newDuration) {
        super(from);
        this.duration = newDuration;
    }

    public long getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[source=" + getSource() + ",duration=" + duration + "]";

    }
}