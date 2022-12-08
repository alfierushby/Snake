package com.game.controllers;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.game.data.Modeled;
import com.game.enums.DIRECTION;
import com.game.models.SnakeModel;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.texture;
import static com.game.enums.DIRECTION.*;
import static com.game.enums.DIRECTION.RIGHT;

/**
 * Controller for the Snake Head element the player moves.
 * Communicates with {@link SnakeModel}, and takes in user input.
 */
public class SnakeController extends Component implements Modeled {

    /**
     * @param model SnakeModel used by {@link com.game.SnakeGameApplication}
     * @return True if the model was set
     */
    public boolean setModel(SnakeModel model) {
        m_model = model;
        return true;
    }

    /**
     * @param img Image for head of snake
     * @return true if the snake changed image
     */
    public boolean setIMGSNAKEHEAD(Texture img) {
        m_IMGSNAKEHEAD = img;
        getEntity().getViewComponent().getChildren().set(0,img.getNode());
        return getEntity().getViewComponent().getChildren().get(0).equals(img);
    }

    /**
     * @return SnakeModel used by {@link com.game.SnakeGameApplication}
     */
    public SnakeModel getModel() {return m_model;}

    /**
     * @return Image used for the snake head
     */
    public Texture getIMGSNAKEHEAD() {return m_IMGSNAKEHEAD;}
    private Texture m_IMGSNAKEHEAD;
    private SnakeModel m_model;

    /**
     * Called when the Snake Head is created.
     * Sets the image of the snake head, and rotates it to the default rotation,
     * defined in {@link com.game.data.Config}.
     */
    @Override
    public void onAdded() {
        setIMGSNAKEHEAD((Texture) getEntity().getViewComponent()
                .getChildren().get(0));
        moveRotate();
    }

    /**
     * Sets the snake model to be used by the Snake Head.
     * @param model SnakeModel used by {@link com.game.SnakeGameApplication}
     */
    public SnakeController(SnakeModel model) {
        setModel(model);
    }

    /**
     * Rotates the Snake Head according to the Direction in the SnakeModel.
     * @return True if the model moved
     */
    private boolean moveRotate(){
        getIMGSNAKEHEAD().setRotate(getModel().getDirectionMap()
                .get(getModel().getDirection()));
        return  getModel().move();
    }

    /**
     * Changes direction of snake with plausibility checks.
     * Also rotates snake.
     * @param direction Direction to change to
     */
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
     * Teleports snake to specified point.
     * @param p Point to move snake to
     */
    public void moveTo(Point2D p){
        entity.setAnchoredPosition(p);
    }
}
