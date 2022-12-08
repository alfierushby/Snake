package com.game.data;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public record Score(String name, int score) implements Comparable<Score>,
        Serializable {
    @Override
    public int compareTo(@NotNull Score o) {
        return Integer.compare(((Score) o).score(),score());
    }

}
