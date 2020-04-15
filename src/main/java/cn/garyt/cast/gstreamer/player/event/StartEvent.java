package cn.garyt.cast.gstreamer.player.event;

import cn.garyt.cast.gstreamer.player.MediaPlayer;
import org.freedesktop.gstreamer.State;

/**
 * Based on code from FMJ by Ken Larson
 */
public class StartEvent extends TransitionEvent {

    private static final long serialVersionUID = -6525680700301322297L;

    private final long mediaTime;


    public StartEvent(MediaPlayer from, State previous, State current, State target,
                      long mediaTime) {
        super(from, previous, current, target);
        this.mediaTime = mediaTime;
    }

    public long getMediaTime() {
        return mediaTime;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[source=" + getSource() + ",previousState=" + getPreviousState() +
                ",currentState=" + getCurrentState() + ",targetState=" + getPendingState() +
                ",mediaTime=" + mediaTime + "]";
    }
}