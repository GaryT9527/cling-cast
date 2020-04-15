package cn.garyt.cast.gstreamer.player.event;

import cn.garyt.cast.gstreamer.player.MediaPlayer;

public class PositionChangedEvent extends MediaEvent {

    private static final long serialVersionUID = 269889318281659313L;

    public final long position;
    public final int percent;
    public PositionChangedEvent(MediaPlayer player, long position, int percent) {
        super(player);
        this.position = position;
        this.percent = percent;
    }

    public int getPercent() {
        return percent;
    }

    public long getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[source=" + getSource() + ",position=" + position + "]";

    }
}