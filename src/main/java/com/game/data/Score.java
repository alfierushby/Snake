package com.game.data;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * An entry in the high-score save.
 * @param name Name of the player
 * @param score Score of the player
 * @param difficulty Difficult of the game
 */
public record Score(String name, int score, String difficulty) implements Comparable<Score>,
        Serializable {
    @Override
    public int compareTo(@NotNull Score o) {
        return Integer.compare(((Score) o).score(),score());
    }

}
