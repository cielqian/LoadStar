package com.ciel.loadstar.user.service;

import com.ciel.loadstar.infrastructure.events.web.PageEventTrack;

public interface EventTrackService {
    void track(PageEventTrack pageEventTrack);
}
