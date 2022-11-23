package com.game.data;

import com.game.enums.DIRECTION;

public record SnakeQueueItem(
        DIRECTION direction,
        long end_time
) { }