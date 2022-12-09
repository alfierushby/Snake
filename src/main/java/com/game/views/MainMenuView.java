package com.game.views;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.particle.ParticleEmitter;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.particle.ParticleSystem;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.UI;
import com.game.controllers.HighScoreController;
import com.game.controllers.MainMenuController;
import com.game.controllers.MenuController;
import com.game.controllers.OptionsController;
import com.game.data.ColorSet;
import com.game.data.FoodImages;
import com.game.data.Modeled;
import com.game.data.ScreenSet;
import com.game.models.SnakeModel;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static com.almasb.fxgl.dsl.FXGL.getDialogService;
import static com.almasb.fxgl.dsl.FXGL.texture;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.game.data.Config.*;
import static javafx.animation.Animation.INDEFINITE;


/**
 * The Main Menu View that handles visual effects beyond the fxml, and
 * transitions.
 * @author Alfie Rushby
 */

public class MainMenuView extends FXGLMenu implements Modeled {

    /**
     * @param m_screens Data of all the Menus
     * @return true if it isn't already assigned.
     */
    public boolean setScreens(ScreenSet m_screens) {
        if(getScreens()==null){
            this.m_screens = m_screens;
            return true;
        }
        System.out.println("Screens already assigned!");
        return false;
    }

    /**
     * @return Central Snake Model
     */
    @Override
    public SnakeModel getModel() {
        return m_model;
    }

    /**
     * @return Screen record containing all the Menus
     */
    public ScreenSet getScreens() {return m_screens;}

    /**
     * @return System that contains all the particle emitters
     */
    public ParticleSystem getSystem() {
        return m_system;
    }

    /**
     * @return All the animations that are playing/stopped.
     */
    public List<Animation<?>> getAnimations() {
        return m_animations;
    }

    /**
     * @return Food object that maps numbers to food images.
     */
    public FoodImages getFood() {
        return m_food;
    }

    private final ParticleSystem m_system;
    private ParticleEmitter m_emitter;
    private ScreenSet m_screens;
    private final SnakeModel m_model;
    private final List<Animation<?>> m_animations = new LinkedList<>();
    private final FoodImages m_food = new FoodImages();

    /**
     * Sets up every menu to be used, and then transitions main menu with a
     * smooth animation.
     * @param type Type of menu, assumed to be MAIN_MENU
     * @param model Snake Model of the game
     */
    public MainMenuView(@NotNull MenuType type, SnakeModel model) {
        super(type);
        m_model = model;
        m_system = new ParticleSystem();

        // Setup menus in Main Menu
        HighScoreController high_scores = createHighScoreMenu(model);
        MainMenuController main_menu = createMainMenu(model);
        OptionsController options = createOptionsMenu(model);
        // Store controllers for use
        setScreens(new ScreenSet(main_menu, high_scores,options,
                getContentRoot()));
        switchScreen(main_menu,DEFAULT_TRANSITION_LENGTH);

        //Creates particle effect
        initParticles();

        //Transitions Main Menu smoothly in
        main_menu.getTopRoot().getChildren().add(1,m_system.getPane());
        double i =0;
        for (Node node : main_menu.getRoot().getChildren()){
            setAnimation(node,i);
            i=i+0.07;
        }
        animateGradient(main_menu.getHighscores_btn(), Color.WHITE,
                Color.MEDIUMVIOLETRED);
        setInfiniteBobble(main_menu.getTitle(),1);
    }

    /**
     * Switches the Menu with a smooth animation.
     * @param screen Menu to switch to
     * @param time Time it takes to switch
     * @return true if the animation has started
     */
    public boolean switchScreen(MenuController screen, double time){
        getScreens().hideAll();
        Pane top = screen.getTopRoot();
        top.setMouseTransparent(false);
        top.setOpacity(1);
        Animation<?> animation =  animationBuilder()
                .interpolator(Interpolators.EXPONENTIAL.EASE_OUT())
                .duration(Duration.seconds(time))
                .scale(screen.getRoot())
                .from(new Point2D(2,2))
                .to(new Point2D(1,1))
                .build();
        animation.start();
        getAnimations().add(animation);
        return animation.isAnimating();
    }

    /**
     * Loads the fxml file and makes it invisible, adding it to the parent of
     * all menus.
     * @param url .fxml file to load as a Menu
     * @param controller MenuController that controls the menu
     * @return true
     */
    private boolean loadUI(String url, MenuController controller){
        UI ui = getAssetLoader().loadUI(url,controller);
        getContentRoot().getChildren().add(ui.getRoot());
        controller.getTopRoot().setOpacity(0);
        return true;
    }

    /**
     * Creates the Main Menu.
     * @param model Snake Model of game
     * @return Controller of the Main Menu
     */
    private MainMenuController createMainMenu(SnakeModel model){
        MainMenuController controller= new MainMenuController(this,model);
        loadUI(DEFAULT_MAIN_UI,controller);
        return controller;
    }

    /**
     * Creates the Options Menu
     * @param model Snake Model of game
     * @return Controller of Options Menu
     */
    private OptionsController createOptionsMenu(SnakeModel model){
        OptionsController controller= new OptionsController(this,model);
        loadUI(DEFAULT_OPTIONS_UI,controller);
        return controller;
    }

    /**
     * Creates the HighScores Menu
     * @param model Snake Model of game
     * @return Controller of HighScores Menu
     */
    private HighScoreController createHighScoreMenu(SnakeModel model){
        HighScoreController controller = new HighScoreController(this,
                model);
        loadUI(DEFAULT_HIGH_SCORES_UI,controller);
        return controller;
    }

    /**
     * Creates a food throwing particle effect.
     * @return true
     */
    private boolean initParticles(){
        Texture t =
                texture(m_food.getFoodindex(0), 25.0, 25);

        m_emitter = ParticleEmitters.newFireEmitter(DEFAULT_GAME_WIDTH);
        m_emitter.setBlendMode(BlendMode.SRC_OVER);
        m_emitter.setSourceImage(t.getImage());
        m_emitter.setSize(1, 1.25);
        m_emitter.setNumParticles(10);
        m_emitter.setEmissionRate( 0.01);
        m_emitter.setVelocityFunction(i->
                new Point2D(random() * 2.5,
                        -random(3,5) * random(80, 120))

        );
        m_emitter.setExpireFunction (i-> Duration.seconds(random(30,50)));
        m_emitter.setScaleFunction (i -> new Point2D(.15, .15));
        m_emitter.setAccelerationFunction(() -> new Point2D(random(-200,200),
                300));
        m_emitter.setSpawnPointFunction (i ->
                new Point2D(random(0.0, DEFAULT_GAME_WIDTH)
                        , 120.0));

        m_system.addParticleEmitter(m_emitter, 0,
                DEFAULT_GAME_HEIGHT);
        return true;
    }


    /**
     * @param baseColor Colour property that will be modified in the animation
     * @param c1 Colour of the baseColor at the start of the animation
     * @param c2 Colour of the baseColor at the end of the animation
     * @return TimeLine of the animation
     */
    private Timeline createColorTimeLine(ObjectProperty<Color> baseColor,
                                       Color c1, Color c2){
        KeyValue keyValue1 = new KeyValue(baseColor, c1
                , Interpolator.EASE_BOTH);
        KeyValue keyValue2 = new KeyValue(baseColor, c2
                , Interpolator.EASE_BOTH);
        KeyFrame keyFrame1 = new KeyFrame(Duration.ZERO, keyValue1);
        KeyFrame keyFrame2 = new KeyFrame(
                Duration.seconds(DEFAULT_GRADIENT_TRANSITION_LENGTH), keyValue2);
        Timeline timeline = new Timeline(keyFrame1, keyFrame2);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(INDEFINITE);
        return timeline;
    }

    /**
     * Asks the player for their name.
     * @param start_new_game Determines if a new game should be started after
     *                       user input.
     */
    public void playerNameInput(boolean start_new_game){
        Consumer<String> result = new Consumer<String>() {
            @Override
            public void accept(String name) {
                getModel().setPlayerName(name);
                if(start_new_game){
                    getGameController().startNewGame();
                }
            }
        };
        getDialogService().showInputBox("Please enter your name to save"
                        + " on the leaderboard." ,
                result);
    }

    /**
     * Sets the styling of both ends of the gradient on the node, and adds a
     * listener to both base colours, such that whenever they change, the css
     * gradient colour ends change.
     * @param node Node to initialise a gradient on.
     * @return A {@link ColorSet} to be used in animating
     */
    private ColorSet doubleGradientListener(Node node){
        ObjectProperty<Color> baseColour = new SimpleObjectProperty<>();
        ObjectProperty<Color> baseColour1 = new SimpleObjectProperty<>();
        AtomicReference<String> grad_base = new AtomicReference<>("");

        baseColour.addListener((obs, oldColor, newColor) -> {
            grad_base.set(String.format("-gradient-base: #%02x%02x%02x; ",
                    (int) (newColor.getRed() * 255),
                    (int) (newColor.getGreen() * 255),
                    (int) (newColor.getBlue() * 255)));
        });

        baseColour1.addListener((obs, oldColor, newColor) -> {
            node.setStyle(String.format("-gradient-bottom: #%02x%02x%02x;"
                            + grad_base.get(),
                    (int)(newColor.getRed()*255),
                    (int)(newColor.getGreen()*255),
                    (int)(newColor.getBlue()*255)));
        });
       return new ColorSet(baseColour, baseColour1);
    }

    /**
     * Creates an infinite animation of moving a gradient from one end of a node
     * to its other end.
     * @param node Node to animate on
     * @param base Colour at the base of the gradient
     * @param end Colour at the end of the gradient
     * @return true
     */
    public boolean animateGradient(Node node, Color base, Color end){
        ColorSet colours = doubleGradientListener(node);

        node.getStyleClass().add("gradient_animate");
        Timeline timeline = createColorTimeLine(colours.baseColor(),
                base, end);

        Timeline timeline1 = createColorTimeLine(colours.endColor(),
                end,base);

        timeline1.play();
        timeline.play();
        return true;
    }

    public Animation<?> setInfiniteBobble(Node node, double speed){
            Animation<?> animation_scale =  animationBuilder()
                    .interpolator(Interpolators.QUADRATIC.EASE_IN_OUT())
                    .duration(Duration.seconds(speed))
                    .autoReverse(true)
                    .repeatInfinitely()
                    .scale(node)
                    .from(new Point2D(1,1))
                    .to(new Point2D(1.1, 1.1))
                    .build();
            animation_scale.start();
            getAnimations().add(animation_scale);
            return animation_scale;
    }

    private boolean setAnimation(Node node, double i){
        Animation<?> animation =  animationBuilder()
                .delay(Duration.seconds(.07 + i))
                .interpolator(Interpolators.EXPONENTIAL.EASE_OUT())
                .duration(Duration.seconds(0.66))
                .translate(node)
                .from(new Point2D(0,600))
                .to(new Point2D(0.0, 0.0))
                .build();
        animation.start();
        getAnimations().add(animation);

        return true;
    }

    @Override
    protected void onUpdate(double tpf) {
        Texture t = texture(
                m_food.getFoodindex(random(0,13)), 25.0, 25).brighter();
        m_emitter.setSourceImage(t);
        m_system.onUpdate(tpf);
        for(Animation<?> animation: getAnimations()){
            animation.onUpdate(tpf);
        }
    }

}
