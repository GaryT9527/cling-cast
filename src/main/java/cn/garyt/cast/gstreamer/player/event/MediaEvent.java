package cn.garyt.cast.gstreamer.player.event;

import cn.garyt.cast.gstreamer.player.MediaPlayer;

/**
 * Based on code from FMJ by Ken Larson
 */
public class MediaEvent extends java.util.EventObject {

    private static final long serialVersionUID = -398789526176265556L;

    public MediaEvent(MediaPlayer source) {
        super(source);
    }
    @Override
    public String toString() {
        return getClass().getName() + "[source=" + getSource() + "]";
    }
}
