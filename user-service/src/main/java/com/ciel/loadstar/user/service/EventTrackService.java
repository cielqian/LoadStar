package com.ciel.loadstar.user.service;

import com.ciel.loadstar.infrastructure.events.system.SystemEvent;
import com.ciel.loadstar.infrastructure.events.web.PageEventTrack;

public interface EventTrackService {
    void track(PageEventTrack pageEventTrack);

    void track(SystemEvent event);
}
