package com.game.controllers;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
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
import static com.game.data.Config.*;
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
public class SnakeController extends Component {

    public boolean setEntity(Entity entity) {
        m_entity = entity;
        return true;
    }
    public boolean setPhysics(PhysicsComponent physics) {
        m_physics = physics;
        return true;
    }

    public boolean setModel(SnakeModel model) {
        m_model = model;
        return true;
    }

    public SnakeFactory getSnakeFactory() {
        return m_snakefactory;
    }
    public PhysicsComponent getPhysics() {return m_physics;}
    public List<Entity> getBodyParts() {
        return m_bodyParts;
    }

    public Color getInnerColor() {return m_innerColor;}
    public Texture getIMGSNAKEHEAD() {return m_IMGSNAKEHEAD;}
    private Entity m_entity;
    private final Color m_borderColor = Color.GREEN.darker().darker();
    private final Color m_innerColor = Color.GREEN;
    private Texture m_IMGSNAKEHEAD;
    public SnakeModel getModel() {return m_model;}

    private final SnakeFactory m_snakefactory;
    private double delayed_x,delayed_y;
    private final List<Entity> m_bodyParts = new LinkedList<>();
    private SnakeModel m_model;
    private PhysicsComponent m_physics;

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
        setModel(new SnakeModel(getEntity().getWidth(),
                getEntity().getHeight(),
                new Rectangle(DEFAULT_GAME_WIDTH, DEFAULT_GAME_HEIGHT)));
        setIMGSNAKEHEAD((Texture) getEntity().getViewComponent()
                .getChildren().get(0));
        moveRotate();

        // Test Code
        getModel().setLength(3);
        getBodyParts().add(getSnakeFactory().newSnakeBody(
                new SpawnData(getEntity().getX(),getEntity().getY())));
        getBodyParts().add(getSnakeFactory().newSnakeBody(
                new SpawnData(getEntity().getX(),getEntity().getY())));
        // Test Code

        int time = (int) (1000/DEFAULT_FRAME_RATE);
          getGameTimer().runAtInterval(this::drawBody,
               Duration.millis(time));
    }

    public SnakeController(SnakeFactory factory) {
        m_snakefactory = factory;
    }

    private boolean moveRotate(){
        getIMGSNAKEHEAD().setRotate(getModel().getDirectionMap()
                .get(getModel().getDirection()));
        getModel().move();
        return true;
    }

    public void drawBody()
    {
        getModel().draw();
        getEntity().setPosition(getModel().getPosition());
        int length = getModel().getBodyPoints().size() - getModel().getNum();
        List<Point2D> bodyPoints = getModel().getBodyPoints();
        //System.out.println(length+" "+bodyPoints.size()+" "+num);
        int index = 0;
        for (int i = length; i >= getModel().getNum(); i -= getModel().getNum())
        {
            Point2D point = bodyPoints.get(i);
            getBodyParts().get(index).setPosition(point);
            index++;
        }
    }


    @Override
    public void onUpdate(double tpf) {
    }

    public void keyPressed(DIRECTION direction){
        // check the key
        switch (direction)
        {
            case UP:
                if (getModel().getDirection()!=DOWN) {
                    getModel().setDirection(UP);
                }
                break;
            case DOWN:
                if (getModel().getDirection()!=UP) {
                    getModel().setDirection(DOWN);
                }
                break;
            case LEFT:
                if (getModel().getDirection()!=RIGHT) {
                    getModel().setDirection(LEFT);
                }
                break;
            case RIGHT:
                if (getModel().getDirection()!=LEFT) {
                    getModel().setDirection(RIGHT);
                }
            default:
                break;
        }
        moveRotate();
        //getPhysics().setBodyLinearVelocity(getModel().getVelocity());
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
