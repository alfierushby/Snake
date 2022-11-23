package com.game.events;

import com.game.data.SnakeQueueItem;
import javafx.event.Event;
import javafx.event.EventType;

public class SnakeEvent extends Event {
    public SnakeQueueItem getQueueItem() {
        return m_snakeQueueItem;
    }

    public boolean setQueueItem(SnakeQueueItem direction) {
        m_snakeQueueItem = direction;
        return true;
    }

    private SnakeQueueItem m_snakeQueueItem;
    public static final EventType<SnakeEvent> NEW_DIRECTION =
            new EventType<>(Event.ANY, "NEW_DIRECTION");

    public SnakeEvent(EventType<? extends Event> eventType, SnakeQueueItem snakeQueueItem)
    {
        super(eventType);
        setQueueItem(snakeQueueItem);
    }
}