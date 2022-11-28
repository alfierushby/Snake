package com.game.events;

import javafx.event.Event;
import javafx.event.EventType;

public class SnakeEvent  extends Event {

    public static final EventType<SnakeEvent> MAKE_SNAKE_BODY =
            new EventType<>(Event.ANY, "MAKE_SNAKE_BODY");


    public SnakeEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
