package com.game.controllers;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.Texture;
import com.game.SnakeFactory;
import com.game.enums.DIRECTION;
import com.game.models.SnakeModel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;
import static com.game.data.Config.DEFAULT_FRAME_RATE;
import static com.game.enums.DIRECTION.*;
import static com.game.enums.DIRECTION.RIGHT;

/**
 * Snake body components will each have a queue, with each element being
 * in what direction, and for how long, to move. Each body part will move
 * in such a direction for the length, and if the queue is empty it will
 * move in the direction it was going at.
 *
 * It communicates via events
 */
public class SnakeController extends SnakeModel {


    public boolean setPhysics(PhysicsComponent physics) {
        m_physics = physics;
        return true;
    }
    public boolean setEntity(Entity entity) {
        m_entity = entity;
        return true;
    }
    public SnakeFactory getSnakeFactory() {
        return m_snakefactory;
    }

    public List<Entity> getBodyParts() {
        return m_bodyParts;
    }

    public List<Point2D> getBodyPoints() {return m_bodyPoints;}
    public PhysicsComponent getPhysics() {return m_physics;}
    public Color getInnerColor() {return m_innerColor;}
    public Texture getIMGSNAKEHEAD() {return m_IMGSNAKEHEAD;}

    private PhysicsComponent m_physics; // This is from the factory
    private Entity m_entity;
    private final Color m_borderColor = Color.GREEN.darker().darker();
    private final Color m_innerColor = Color.GREEN;
    private Texture m_IMGSNAKEHEAD;


    private final SnakeFactory m_snakefactory;
    private double delayed_x,delayed_y;
    private final List<Point2D> m_bodyPoints = new LinkedList<>();
    private final List<Entity> m_bodyParts = new LinkedList<>();

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
     * @param container The area where the rectangle can move
     */
    @Override
    public void onAdded() {
        setEntity(entity); // Protected FXGL var, setting to follow conventions
        setIMGSNAKEHEAD((Texture) getEntity().getViewComponent().getChildren().get(0));
        moveRotate();
        setLength(1);
        m_bodyParts.add(getSnakeFactory().newSnakeBody(
                new SpawnData(getEntity().getX(),getEntity().getY())));
        getGameTimer().runAtInterval(this::draw,
                Duration.millis(((int) 1/DEFAULT_FRAME_RATE)*1000));
    }
    public SnakeController(Rectangle container, SnakeFactory factory) {
        super(container);
        m_snakefactory = factory;

    }

    private boolean moveRotate(){
        getIMGSNAKEHEAD().setRotate(getDirectionMap().get(getDirection()));
        move();
        return true;
    }

    private void outofBounds()
    {
        boolean xOut = (getEntity().getX() <= 0
                || getEntity().getX() >= (870 - getWidth()));
        boolean yOut = (getEntity().getY() <= 40
                || getEntity().getY() >= (560 - getHeight()));
        if (xOut || yOut)
        {
            setState(false);
        }
    }

    public void draw()
    {
        outofBounds();
        getBodyPoints().add(new Point2D(getEntity().getX(),
                getEntity().getY()));
        if ( getBodyPoints().size() == (getLength() + 1) * getNum())
        {
            getBodyPoints().remove(0);
        }
        drawBody();
       // move();
    }
    public void drawBody()
    {
        int length = m_bodyPoints.size() - 1 - getNum();
        //System.out.println(length+" "+bodyPoints.size()+" "+num);
        for (int i = length; i >= getNum(); i -= getNum())
        {
            Point2D point = m_bodyPoints.get(i);
            getBodyParts().get(0).setPosition(point);
        }
    }


    @Override
    public void onUpdate(double tpf) {
        getPhysics().setBodyLinearVelocity(getVelocity());
    }

    public void keyPressed(DIRECTION direction){
        // check the key
        switch (direction)
        {
            case UP:
                if (getDirection()!=DOWN) {
                    setDirection(UP);
                }
                break;
            case DOWN:
                if (getDirection()!=UP) {
                    setDirection(DOWN);
                }
                break;
            case LEFT:
                if (getDirection()!=RIGHT) {
                    setDirection(LEFT);
                }
                break;
            case RIGHT:
                if (getDirection()!=LEFT) {
                    setDirection(RIGHT);
                }
            default:
                break;
        }
        moveRotate();
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
    public void moveTo(Point2D p){
        entity.setAnchoredPosition(p);
    }

    public boolean setIMGSNAKEHEAD(Texture IMGSNAKEHEAD) {
        m_IMGSNAKEHEAD = IMGSNAKEHEAD;
        return true;
    }
}
