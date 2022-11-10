package com.game;
import java.awt.*;


/**
 * This class is not used anywhere.
 * It contains the following statement information:
 * <ul>
 * <li> The rectangle that will move, paddleFace</li>
 * <li> The rectangle's position, ballPoint</li>
 * <li> The distance to move in x per call, moveAmount</li>
 * <li> The min and max it can move in x, min, max</li>
 * </ul>
 * This is a point mover class object that can move either left or right
 * in the x dimension, via a rectangle with its position set by a
 * ballPoint.
 * This class is not drawable, but instead works as a method to calculate
 * position.
 */
public class Paddle {
    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    public static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle paddleFace;
    private Point ballPoint;
    private int moveAmount;
    private int min;
    private int max;


    /**
     * This sets the position of the rectangle, ballPoint, and resets
     * moveAmount to 0, and then makes the rectangle that we will
     * move in the x space. We then calculate the min amd max it
     * can move via the container's width and position.
     * Min is calculated via the x + half the width of the rectangle
     * we are moving.
     * <p>
     *     Determined an issue: Rectangles position's anchor point is
     *     upper left. This entails that the min calculation is wrong
     *     and should be the opposite on the assumption the rectangle
     *     doesn't have its position edited.
     * </p>
     *
     * @param ballPoint The default position of the rectangle
     * @param width The width of the rectangle
     * @param height The height of the rectangle
     * @param container The area where the rectangle can move
     */
    public Paddle(Point ballPoint, int width, int height, Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        paddleFace = makeRectangle(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;

    }

    /**
     * This makes the rectangle to be manipulated. The position of the rectangle
     * is calculated with the removal of half the rectangle's width.
     * <p>
     *     Rectangle's anchor point is left up, meaning that the offset applied
     *     is wrong, and perhaps unneeded if the bounds min and max are offset
     *     to accommodate for this already.
     * </p>
     * @param width width of the rectangle
     * @param height height of the rectangle
     * @return returns the rectangle in the position
     */
    public Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }

    /**
     * This moves the snake by the moveAmount variable. It doesn't move if it
     * exceeds the min or max, and if it doesn't it sets the ballPoint and
     * paddleFace's position.
     * <p>
     *     paddleFace has a concerning position allocation. Rectangle has a left
     *     anchor point, so this would be the wrong offset direction.
     *     Min and Max might be offset already to accommodate this, so any offset
     *     may be unneeded.
     * </p>
     */
    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x,ballPoint.getY());
        paddleFace.setLocation(ballPoint.x - (int) paddleFace.getWidth()/2,ballPoint.y);
    }

    /**
     * Sets the rectangle to move in the set speed left.
     * This speed is specified by {@link #DEF_MOVE_AMOUNT}.
     */
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    /**
     * Sets the rectangle to move in the set speed right.
     * This speed is specified by {@link #DEF_MOVE_AMOUNT}.
     */
    public void movRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    /**
     * Stops moving.
     */
    public void stop(){
        moveAmount = 0;
    }

    /**
     * @return returns the rectangle that moves.
     */
    public Shape getPaddleFace(){
        return paddleFace;
    }

    /**
     * This sets the location. This piece of code is used elsewhere, so should
     * be a separate function.
     * <p>
     *     The offset on the x coordinate for the rectangle is mis-judged,
     *     and should be addition due to the anchor point being left up.
     *     The offset may not need an implementation as min and max already
     *     have an offset applied.
     * </p>
     * @param p the explicit position to move the rectangle to.
     */
    public void moveTo(Point p){
        ballPoint.setLocation(p);
        paddleFace.setLocation(ballPoint.x - (int) paddleFace.getWidth()/2,ballPoint.y);
    }


}
