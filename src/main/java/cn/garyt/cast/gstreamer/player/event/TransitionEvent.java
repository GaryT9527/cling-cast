package cn.garyt.cast.gstreamer.player.event;

import cn.garyt.cast.gstreamer.player.MediaPlayer;
import org.freedesktop.gstreamer.State;

/**
 * Based on code from FMJ by Ken Larson
 */
public class TransitionEvent extends MediaEvent {

    private static final long serialVersionUID = 708470584903128800L;

    public final State previousState;
    public final State currentState;
    public final State pendingState;

    public TransitionEvent(MediaPlayer player, State previousState, State currentState,
                           State pendingState) {
        super(player);
        this.previousState = previousState;
        this.currentState = currentState;
        this.pendingState = pendingState;
    }

    public State getPreviousState() {
        return previousState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public State getPendingState() {
        return pendingState;
    }
    public State getTargetState() {
        return pendingState;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[source=" + getSource() + ",previousState=" + previousState +
                ",currentState=" + currentState + ",targetState=" + pendingState + "]";
    }
}
