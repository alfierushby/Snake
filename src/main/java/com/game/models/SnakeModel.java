package com.game.models;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.Texture;
import com.game.Snake;
import com.game.data.SnakeQueueItem;
import com.game.enums.DIRECTION;
import com.game.events.SnakeEvent;
import javafx.geometry.Point2D;

import java.awt.*;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.cpuNanoTime;
import static com.game.data.Config.DEFAULT_DIRECTION;
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
public class SnakeModel extends SnakeBodyModel {
    public boolean setPhysics(PhysicsComponent physics) {
        m_physics = physics;
        return true;
    }
    public boolean setEntity(Entity entity) {
        m_entity = entity;
        return true;
    }
    public boolean setMoveAmount(int[] moveAmount) {
        m_moveAmount = moveAmount;
        return true;
    }
    @Override
    public boolean setDirection(DIRECTION direction) {
        // Snake body will count how long it has been moving, and through this
        // work out the remainder it must move in a direction.
        getEventBus().fireEvent(new SnakeEvent(SnakeEvent.NEW_DIRECTION
                ,new SnakeQueueItem(getDirection(),
                cpuNanoTime()-getSavedtime(),direction)));
        super.setDirection(direction);
        return true;
    }
    public PhysicsComponent getPhysics() {return m_physics;}
    public Color getInnerColor() {return m_innerColor;}
    public Map<DIRECTION, Integer> getDirection_map() {return m_direction_map;}
    public int[] getMoveAmount() {return m_moveAmount;}
    public int getMin() {return m_min;}
    public int getMax() {return m_max;}
    public Texture getIMGSNAKEHEAD() {return m_IMGSNAKEHEAD;}

    private PhysicsComponent m_physics; // This is from the factory
    private Entity m_entity;
    private final Color m_borderColor = Color.GREEN.darker().darker();
    private final Color m_innerColor = Color.GREEN;
    private Map<DIRECTION, Integer> m_direction_map
            = Map.of(UP, -90, DOWN, 90, LEFT, -180, RIGHT, 0);

    private int[] m_moveAmount = {0,0};
    private final int m_min;
    private final int m_max;
    private Texture m_IMGSNAKEHEAD;

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
    public SnakeModel(Rectangle container) {
        super(DEFAULT_DIRECTION);
        getVelocity().set(0,getSpeed()); // Default movement
        m_min = container.x;
        m_max = getMin() + container.width;
    }

    @Override
    public void onUpdate(double tpf) {
        setEntity(entity); // Protected FXGL var, setting to follow conventions
        setIMGSNAKEHEAD((Texture) getEntity().getViewComponent().getChildren().get(0));
        // velocity.mulLocal(SPEED_DECAY);
      //  if (getEntity().getX() < 0) {
            //m_velocity.set(BOUNCE_FACTOR * (float) -getEntity().getX(), 0);
      //  } else if (getEntity().getRightX() > getAppWidth()) {
          //  m_velocity.set(BOUNCE_FACTOR * (float) -(getEntity().getRightX
            //  () - getAppWidth()), 0);
      //  }

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
        getIMGSNAKEHEAD().setRotate(getDirection_map().get(getDirection()));
        move();
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

    public boolean setDirection_map(Map<DIRECTION, Integer> direction_map) {
        m_direction_map = direction_map;
        return true;
    }

    public boolean setIMGSNAKEHEAD(Texture IMGSNAKEHEAD) {
        m_IMGSNAKEHEAD = IMGSNAKEHEAD;
        return true;
    }
}
