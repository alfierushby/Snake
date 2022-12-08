package com.game.data;

import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Color;

public record ColorSet(ObjectProperty<Color> baseColor,
                       ObjectProperty<Color> endColor){
}
