package com.game;

import java.awt.*;

/**
 * The general areas of state information set are:
 * <ul>
 * <li> The x,y coordinates of the snake</li>
 * <li> The image of the snake body</li>
 * <li> The width and height of the frame</li>
 * <li> The game end variable, l</li>
 * </ul>
 * An abstract class that is implemented by {@link MySnake} and {@link Food}.
 */
public abstract class SnakeObject
{
    /**
     * @return returns x coordinate of the object
     */
    public int getX() {
        return m_x;
    }
    /**
     * @return returns y coordinate of the object
     */
    public int getY() {
        return m_y;
    }
    /**
     * @param x sets the x coordinate of the object
     */
    public void setX(int x) {
        m_x = x;
    }
    /**
     * @param y sets the y coordinate of the object
     */
    public void setY(int y) {
        m_y = y;
    }
    /**
     * @return gets the image of the object used to render on the screen
     */
    public Image getImage() {
        return m_i;
    }
    /**
     * @param i sets the image of the object rendered on the screen
     */
    public void setImage(Image i) {
        m_i = i;
    }
    /**
     * @return returns the width of the object
     */
    public int getWidth() {
        return m_w;
    }
    /**
     * @param w sets the width of the object
     */
    public void setWidth(int w) {
        m_w = w;
    }
    public int getHeight() {
        return m_h;
    }
    public void setHeight(int h) {
        m_h = h;
    }
    public boolean getState() {
        return m_l;
    }
    public void setState(boolean l) {
        m_l = l;
    }
    private int m_x;
    private int m_y;
    private Image m_i;
    private int m_w;
    private int m_h;
    private boolean m_l;


    /**
     * The abstract draw class is used as a general function implement
     * drawing, and in this case it is for the snake body and food.
     * This is called during the {@link Paint} function call of {@link MyFrame},
     * used whenever repainting and initialisation.
     * @param g The graphic parameter pertaining to {@link MyFrame}
     */
    public abstract void draw(Graphics g);

    /**
     * This is used solely on {@link Food} that implements this abstract class.
     * Its role is in collision detection when a snake eats a piece of food, using the
     * rectangle property intersects.
     * @return Returns a rectangle to be used in collision detection
     */
    public Rectangle getRectangle()
    {
        return new Rectangle(m_x, m_y, m_w, m_h);
    }
}