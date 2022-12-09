package com.game.views;

import com.game.SnakeFactory;
import com.game.data.Modeled;
import com.game.models.SnakeModel;

/**
 * Base View to be used by every View class.
 * @author Alfie Rushby
 */
public class View implements Modeled {
    /**
     * @param snakeModel Game's Snake Model
     * @return true
     */
    public boolean setModel(SnakeModel snakeModel) {
        m_model = snakeModel;
        return true;
    }

    /**
     * @param m_snakeFactory Game's Snake Factory
     * @return true
     */
    public boolean setSnakeFactory(SnakeFactory m_snakeFactory) {
        this.m_snakeFactory = m_snakeFactory;
        return true;
    }
    /**
     * @return Game's Snake Factory
     */
    public SnakeFactory getSnakeFactory() {
        return m_snakeFactory;
    }

    /**
     * @return Game's Snake Model
     */
    public SnakeModel getModel() {return m_model;}


    SnakeFactory m_snakeFactory;
    SnakeModel m_model;

    /**
     * @param factory Game's Snake Factory
     * @param model Game's Snake Model
     */
    public View(SnakeFactory factory, SnakeModel model){
        setModel(model);
        setSnakeFactory(factory);
    }



}
