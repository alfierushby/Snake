package com.game.views;

import com.almasb.fxgl.entity.Entity;
import com.game.SnakeFactory;
import com.game.models.SnakeModel;

public abstract class View {
    public SnakeFactory getSnakeFactory() {
        return m_snakeFactory;
    }
    public SnakeModel getModel() {
        return m_model;
    }
    public void setModel(SnakeModel snakeModel) {
        m_model = snakeModel;
    }
    public boolean setSnakeFactory(SnakeFactory m_snakeFactory) {
        this.m_snakeFactory = m_snakeFactory;
        return true;
    }

    public View(SnakeFactory factory, SnakeModel model){
        setModel(model);
        setSnakeFactory(factory);
    }

    SnakeFactory m_snakeFactory;

    SnakeModel m_model;


}
