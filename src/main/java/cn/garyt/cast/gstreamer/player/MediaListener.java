package cn.garyt.cast.gstreamer.player;

import cn.garyt.cast.gstreamer.player.event.DurationChangedEvent;
import cn.garyt.cast.gstreamer.player.event.EndOfMediaEvent;
import cn.garyt.cast.gstreamer.player.event.PositionChangedEvent;
import cn.garyt.cast.gstreamer.player.event.StartEvent;
import cn.garyt.cast.gstreamer.player.event.StopEvent;

public interface MediaListener {
    void pause(StopEvent evt);
    void start(StartEvent evt);
    void stop(StopEvent evt);
    void endOfMedia(EndOfMediaEvent evt);
    void positionChanged(PositionChangedEvent evt);
    void durationChanged(DurationChangedEvent evt);
}