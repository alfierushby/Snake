package com.game;

import com.game.enums.DIRECTION;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import static com.game.enums.DIRECTION.*;


/**
 * Static subclass of MyFrame. This entails
 * that it cannot access any of the object functions
 * of its frame, as it's an object.
 * Should call into question the validity of this
 * implementation.
 * <p>
 * The general areas of state information set are:
 * <ul>
 * <li> The x,y coordinates of the snake</li>
 * <li> The image of the snake body, i</li>
 * <li> The width and height of the frame, w and h</li>
 * <li> The game end variable, l</li>
 * <li> The speed of the snake, speed_XY</li>
 * <li> The default length of the snake, length</li>
 * <li> The number of draws needed to travel the length of a snake body part, num</li>
 * <li> The snake head buffer used for rotation, newImgSnakeHead</li>
 * </ul>
 * <p>
 *	Is an extended implementation of {@link SnakeObject} where it adds
 *	functionality specialisation for the snake playable object.
 *	The general goal of this class is to draw and move the playable
 *	snake.
 * </p>
 */
public class MySnake extends SnakeObject implements movable
{


    /**
     * @return returns speed of snake per draw/frame
     */
    public int getSpeed_XY() {
        return m_speedXY;
    }
    /**
     * @return returns score of player
     */
    public int getScore() {
        return m_score;
    }
    /**
     * @param score add the score to the player
     */
    public void addScore(int score) {
        this.m_score += score;
    }
    /**
     * @return returns the length of the snake
     */
    public int getLength()
    {
        return m_length;
    }

    /**
     * @param length sets the length of the snake
     */
    public void changeLength(int length)
    {
        this.m_length = length;
    }

    // The game changer.
    private final int m_speedXY;
    private int m_length;
    private final int m_num; // The number of draws needed to travel the length of a snake body part
    private int m_score = 0;
    private final BufferedImage m_IMGSNAKEHEAD
            = (BufferedImage) ImageUtil.images.get("snake-head-right");
    private final List<Point> m_bodyPoints = new LinkedList<>();
    private BufferedImage m_image;
    private DIRECTION m_direction;
    Map<DIRECTION, Integer> m_direction_map
            = Map.of(UP, -90, DOWN, 90, LEFT, -180, RIGHT, 0);


    /**
     * State information set:
     * <ul>
     * <li> The x,y coordinates of the snake</li>
     * <li> The image of the snake body, i</li>
     * <li> The width and height of the frame, w and h</li>
     * <li> The game end variable, l</li>
     * <li> The speed of the snake, speed_XY</li>
     * <li> The default length of the snake, length</li>
     * <li> The number of draws needed to travel the length of a snake body part, num</li>
     * <li> The snake head buffer used for rotation, newImgSnakeHead</li>
     * </ul>
     * <p>
     *     This constructor provides arbitrary default values.
     *     This constructor's parameters have no default checking.
     * </p>
     * @param x This is the x position of the instantiated snake
     * @param y This is the y position of the instantiated snake
     */
    public MySnake(int x, int y)
    {
        setState(true);
        setX(x);
        setY(y);
        setImage(ImageUtil.images.get("snake-body"));
        setWidth(getImage().getWidth(null));
        setHeight(getImage().getHeight(null));

        this.m_speedXY = 5;
        this.m_length = 1;

        /*
         * Attention : ?
         */
        this.m_num = getWidth() / m_speedXY;
        m_image = m_IMGSNAKEHEAD;
        System.out.println(getWidth() +" "+this.m_num);

    }

    /**
     * Sets the correct direction to true.
     * @param e The key event derived from the JFrame's {@link KeyListener}
     */
    public void keyPressed(KeyEvent e)
    {
        // check the key
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_UP:
                if (m_direction!=DOWN) {
                    m_direction=UP;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (m_direction!=UP) {
                    m_direction=DOWN;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (m_direction!=RIGHT) {
                    m_direction=LEFT;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (m_direction!=LEFT) {
                    m_direction=RIGHT;
                }
            default:
                break;
        }
        m_image = (BufferedImage) GameUtil.rotateImage(m_IMGSNAKEHEAD,
                m_direction_map.get(m_direction));
    }


    /**
     * Sets the direction to move based on the
     */
    public void move()
    {
        if (m_direction == UP)
        {
            this.setY(getY()-m_speedXY);
        } else if (m_direction == DOWN)
        {
            this.setY(getY()+m_speedXY);
        } else if (m_direction == LEFT)
        {
            this.setX(getX()-m_speedXY);
        } else if (m_direction == RIGHT)
        {
            this.setX(getX()+m_speedXY);
        }

    }

    /**
     * This adds a point up to the length +1 * num. Essentially, if the snake is
     * of length 1, then it would add a point up to length 9, and then eternally remain
     * adding and removing point 10.
     * It adds a point every draw, thus every position in these points will have moved
     * m pixels either in x or y, where m is {@link #m_speedXY}.
     * <p>
     *     Functionality on this is explained in {@link #drawBody(Graphics)}.
     * </p>
     * @param g The graphic parameter pertaining to {@link MyFrame}
     */
    @Override
    public void draw(Graphics g)
    {
        outofBounds();
        eatBody();

        m_bodyPoints.add(new Point(getX(), getY()));

        if (m_bodyPoints.size() == (this.m_length + 1) * m_num)
        {
            m_bodyPoints.remove(0);
        }
        g.drawImage(m_image, getX(), getY(), null);
        drawBody(g);

        move();
    }

    /**
     * Point to make - this is a nested loop!
     * This functions on the principle that if there exists two
     * points that intersect, then the body has touched itself.
     * <p>
     * The principle itself is simple, but this implementation
     * is naive.
     */
    public void eatBody()
    {
        for (Point point : m_bodyPoints)
        {
            for (Point point2 : m_bodyPoints)
            {
                if (point.equals(point2) && point != point2)
                {
                    setState(false);
                }
            }
        }
    }

    /**
     * There are a couple components integral to this drawing system.
     * First of all, the 'num' variable is essentially what determines
     * the unit of drawing. The default snake head is of size 25,
     * and thus as the speed increases, the points will need to be
     * drawn faster.
     * <p>
     *     Moreso, say the snake has a speed of 5. This means on
     *     every draw, it will travel 5 pixels. So, I will need
     *     5 draws to travel the equivalent distance of the entire
     *     snake.
     *     Ie, 25/5 = 5.
     * </p>
     * Every point in the list will be drawn one after another, ie every
     * point going up will be travelling a unit of distance, determined by
     * {@link #m_speedXY}, further than the previous element.
     * This makes the very last element the location of the top of the snake,
     * and every other element below it its location history of every frame rendered,
     * determined by {@link MyFrame.MyThread}.
     * <p>
     *     Thus, if on the assumption that the speed is 5, I will need to go in
     *     increments of 5 to travel the equivalent of the snakes head. Ie,
     *     every 5 draws, I will have travelled 25 pixels.
     *     This entails that on every 5th element, I can draw a snake body part
     *     without the body parts touching, because every body part is 25 pixels
     *     in length.
     *     If the speed increase, it will take fewer draws to travel he 25 pixels,
     *     thus the value {@link #m_num} will decrease on the formula:
     *     <p>
     *         m_w/{@link #m_speedXY}
     * </p>
     *
     * @param g The graphic parameter pertaining to {@link MyFrame}
     */
    public void drawBody(Graphics g)
    {
        int length = m_bodyPoints.size() - 1 - m_num;
        //System.out.println(length+" "+bodyPoints.size()+" "+num);
        for (int i = length; i >= m_num; i -= m_num)
        {
            Point point = m_bodyPoints.get(i);
            g.drawImage(getImage(), point.x, point.y, null);
        }
    }

    /**
     * This uses hard-coded bounds for a non-fixed frame!
     * <p>
     * Is a simple 'if' that sees if the snake has moved
     * itself out of bounds.
     */
    private void outofBounds()
    {
        boolean xOut = (getX() <= 0 || getX() >= (870 - getWidth()));
        boolean yOut = (getY() <= 40 || getY() >= (560 - getHeight()));
        if (xOut || yOut)
        {
            setState(false);
        }
    }
}