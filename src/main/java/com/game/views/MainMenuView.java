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
import com.game.controllers.MainMenuController;
import com.game.data.FoodImages;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.texture;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.game.data.Config.*;


public class MainMenuView extends FXGLMenu {

    public ParticleSystem getSystem() {
        return m_system;
    }

    public ParticleEmitter getEmitter() {
        return m_emitter;
    }

    public MainMenuController getMainMenuController() {
        return m_controller;
    }

    public List<Animation<?>> getAnimations() {
        return m_animations;
    }

    public FoodImages food() {
        return food;
    }

    public boolean setSystem(ParticleSystem m_system) {
        this.m_system = m_system;
        return true;
    }

    public boolean setEmitter(ParticleEmitter m_emitter) {
        this.m_emitter = m_emitter;
        return true;
    }

    public boolean setController(MainMenuController m_controller) {
        this.m_controller = m_controller;
        return true;
    }

    ParticleSystem m_system;
    ParticleEmitter m_emitter;
    MainMenuController m_controller;
    private final List<Animation<?>> m_animations = new LinkedList<>();
    private final FoodImages food = new FoodImages();
    private boolean initParticles(){


        Texture t =
                texture(food.getFoodindex(0), 25.0, 25);

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


    private boolean setAnimation(Node node, double i){
        Animation<?> animation =  animationBuilder()
                .delay(Duration.seconds(.07 + i))
                .interpolator(Interpolators.EXPONENTIAL.EASE_OUT())
                .duration(Duration.seconds(0.66))
                .translate(node)
                .from(new Point2D(600,600))
                .to(new Point2D(0.0, 0.0))
                .build();

        Animation<?> animation_scale =  animationBuilder()
                .delay(Duration.seconds(.07 + i))
                .interpolator(Interpolators.EXPONENTIAL.EASE_OUT())
                .duration(Duration.seconds(0.66))
                .scale(node)
                .from(new Point2D(0,0))
                .to(new Point2D(1, 1))
                .build();

        animation.start();
        animation_scale.start();
        getAnimations().add(animation);
        getAnimations().add(animation_scale);
        return true;
    }

    public MainMenuView(@NotNull MenuType type) {
        super(MenuType.MAIN_MENU);

        setController(new MainMenuController());


        UI root = getAssetLoader().loadUI(DEFAULT_MAIN_UI,getMainMenuController());
        getContentRoot().getChildren().add(root.getRoot());

        m_system = new ParticleSystem();

        initParticles();
        getContentRoot().getChildren().add(m_system.getPane());
        double i =0;
        for (Node node : getMainMenuController().getVbox().getChildren()){
            setAnimation(node,i);
            i=i+0.07;
        }

    }
    @Override
    protected void onUpdate(double tpf) {
        Texture t = texture(
                food.getFoodindex(random(0,13)), 25.0, 25).brighter();
        m_emitter.setSourceImage(t);
        m_system.onUpdate(tpf);
        for(Animation<?> animation: getAnimations()){
            animation.onUpdate(tpf);
        }
    }
}
