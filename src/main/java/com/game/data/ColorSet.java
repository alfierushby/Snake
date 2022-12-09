package com.game.data;

import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Color;

/**
 * Set used in defining gradient animations.
 * @param baseColor Colour at the start of the gradient
 * @param endColor Colour at the end of the gradient
 */
public record ColorSet(ObjectProperty<Color> baseColor,
                       ObjectProperty<Color> endColor){
}
