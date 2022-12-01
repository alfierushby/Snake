package com.game.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event used when the Snake Eats food.
 */
public class SnakeEvent  extends Event {
    public static final EventType<SnakeEvent> EAT_FOOD =
            new EventType<>(Event.ANY, "EAT_FOOD");

    /**
     * Creates the event for firing and listening.
     * @param eventType Defines the event when instantiating
     */
    public SnakeEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
