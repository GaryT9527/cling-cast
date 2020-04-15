package cn.garyt.cast.gstreamer.player;

import cn.garyt.cast.gstreamer.player.event.DurationChangedEvent;
import cn.garyt.cast.gstreamer.player.event.EndOfMediaEvent;
import cn.garyt.cast.gstreamer.player.event.PauseEvent;
import cn.garyt.cast.gstreamer.player.event.StopEvent;
import cn.garyt.cast.gstreamer.player.event.PositionChangedEvent;
import cn.garyt.cast.gstreamer.player.event.StartEvent;
import org.freedesktop.gstreamer.Bus;
import org.freedesktop.gstreamer.ClockTime;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.Pipeline;
import org.freedesktop.gstreamer.State;

import java.net.URI;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * A MediaPlayer that uses a {@link Pipeline}
 */
public abstract class PipelineMediaPlayer extends AbstractMediaPlayer {
    protected final Pipeline pipeline;
    private volatile ScheduledFuture<?> positionTimer = null;

    protected PipelineMediaPlayer(Pipeline pipeline, Executor executor) {
        super(executor);
        this.pipeline = pipeline;
        getPipeline().getBus().connect(eosSignal);
        getPipeline().getBus().connect(stateChanged);
    }
    @Override
    public synchronized void addMediaListener(MediaListener listener) {
        // Only run the timer when needed
        if (getMediaListeners().isEmpty()) {
            positionTimer = Gst.getExecutor().scheduleAtFixedRate(positionUpdater, 1, 1, TimeUnit.SECONDS);
        }
        super.addMediaListener(listener);
    }

    @Override
    public synchronized void removeMediaListener(MediaListener listener) {
        super.removeMediaListener(listener);
        // Only run the timer when needed

        if (getMediaListeners().isEmpty() && positionTimer != null) {
            positionTimer.cancel(true);
            positionTimer = null;
        }
    }
    /**
     * Gets the {@link Pipeline} that the MediaPlayer uses to play media.
     *
     * @return A Pipeline
     */
    public Pipeline getPipeline() {
        return pipeline;
    }

    private Runnable positionUpdater = new Runnable() {
        private long lastPosition = 0;
        private long lastDuration = ClockTime.ZERO;
        public void run() {
            final long position = pipeline.queryPosition(Format.TIME);
            final long percent = pipeline.queryPosition(Format.PERCENT);
            final long duration = pipeline.queryDuration(TimeUnit.NANOSECONDS);
            final boolean durationChanged = !(duration == lastDuration)
                    && !(duration == ClockTime.ZERO)
                    && !(duration == ClockTime.NONE);
            lastDuration = duration;
            final boolean positionChanged = position != lastPosition && position >= 0;
            lastPosition = position;
            final PositionChangedEvent pue = new PositionChangedEvent(PipelineMediaPlayer.this,
                    position, (int) percent);
            final DurationChangedEvent due = new DurationChangedEvent(PipelineMediaPlayer.this,
                    duration);
            for (MediaListener l : getMediaListeners()) {
                if (durationChanged) {
                    l.durationChanged(due);
                }
                if (positionChanged) {
                    l.positionChanged(pue);
                }
            }
        }
    };
    /*
     * Handle EOS signals.
     */
    private Bus.EOS eosSignal = new Bus.EOS() {
        public void endOfStream(GstObject source) {
            URI next = playList.poll();
            if (next != null) {
                setURI(next);
            } else {
                final EndOfMediaEvent evt = new EndOfMediaEvent(PipelineMediaPlayer.this,
                        State.PLAYING, State.NULL, State.VOID_PENDING);
                // Notify any listeners that the last media file is finished
                fireEndOfMediaEvent(evt);
            }

        }
    };

    private final Bus.STATE_CHANGED stateChanged = new Bus.STATE_CHANGED() {
        public void stateChanged(GstObject source, State old, State newState, State pending) {
            if (!source.equals(getPipeline()))
                return;
            final long position = getPipeline().queryPosition(TimeUnit.NANOSECONDS);
            if (newState.equals(State.PLAYING) && old.equals(State.PAUSED)) {
                fireStartEvent(new StartEvent(PipelineMediaPlayer.this, old, newState, State.VOID_PENDING, position));
            } else if (newState.equals(State.PAUSED) && pending.equals(State.VOID_PENDING)) {
                firePauseEvent(new PauseEvent(PipelineMediaPlayer.this, old, newState, State.VOID_PENDING, position));
            } else if (newState.equals(State.READY) && pending.equals(State.NULL)) {
                fireStopEvent(new StopEvent(PipelineMediaPlayer.this, old, newState, pending, position));
            }

            // Anything else means we are transitioning!
            if (!pending.equals(State.VOID_PENDING) && !pending.equals(State.NULL)) {
                // TODO: Maybe a new callback method on MediaListener with TransitionEvent?
            }
        }
    };
}
