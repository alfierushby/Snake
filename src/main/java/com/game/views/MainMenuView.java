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
import com.game.data.ColorSet;
import com.game.data.FoodImages;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.almasb.fxgl.dsl.FXGL.texture;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.game.data.Config.*;
import static javafx.animation.Animation.INDEFINITE;


public class MainMenuView extends FXGLMenu {

    public boolean setSystem(ParticleSystem m_system) {
        this.m_system = m_system;
        return true;
    }

    public boolean setEmitter(ParticleEmitter m_emitter) {
        this.m_emitter = m_emitter;
        return true;
    }
    public boolean setScreens(ScreenSet m_screens) {
        if(getScreens()==null){
            this.m_screens = m_screens;
            return true;
        }
        System.out.println("Screens already assigned!");
        return false;
    }

    public ScreenSet getScreens() {return m_screens;}
    public ParticleSystem getSystem() {
        return m_system;
    }

    public ParticleEmitter getEmitter() {
        return m_emitter;
    }

    public List<Animation<?>> getAnimations() {
        return m_animations;
    }

    public FoodImages food() {
        return m_food;
    }

    ParticleSystem m_system;
    ParticleEmitter m_emitter;
    private ScreenSet m_screens;
    private final List<Animation<?>> m_animations = new LinkedList<>();
    private final FoodImages m_food = new FoodImages();

    public MainMenuView(@NotNull MenuType type, SnakeModel model) {
        super(MenuType.MAIN_MENU);

        // Setup menus in Main Menu
        HighScoreController high_scores = createHighScoreMenu(model);
        MainMenuController main_menu = createMainMenu(model);
        // Store controllers for use
        setScreens(new ScreenSet(main_menu, high_scores,getContentRoot()));
        switchScreen(main_menu,DEFAULT_TRANSITION_LENGTH);
        m_system = new ParticleSystem();
        initParticles();

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
    private boolean loadUI(String url, MenuController controller){
        UI ui = getAssetLoader().loadUI(url,controller);
        getContentRoot().getChildren().add(ui.getRoot());
        controller.getTopRoot().setOpacity(0);
        return true;
    }
    private MainMenuController createMainMenu(SnakeModel model){
        MainMenuController controller= new MainMenuController(this,model);
        loadUI(DEFAULT_MAIN_UI,controller);
        return controller;
    }
    private HighScoreController createHighScoreMenu(SnakeModel model){
        HighScoreController controller = new HighScoreController(this,
                model);
        loadUI(DEFAULT_HIGH_SCORES_UI,controller);
        return controller;
    }

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


    private Timeline createColorTimeLine(ObjectProperty<Color> baseColor,
                                       Color c1, Color c2){
        KeyValue keyValue1 = new KeyValue(baseColor, c1
                , Interpolator.EASE_BOTH);
        KeyValue keyValue2 = new KeyValue(baseColor, c2
                , Interpolator.EASE_BOTH);
        KeyFrame keyFrame1 = new KeyFrame(Duration.ZERO, keyValue1);
        KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(2), keyValue2);
        Timeline timeline = new Timeline(keyFrame1, keyFrame2);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(INDEFINITE);
        return timeline;
    }

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
