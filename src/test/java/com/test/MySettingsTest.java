package com.test;

import com.almasb.fxgl.app.FXGLApplication;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.ReadOnlyGameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.game.MenuFactory;
import com.game.SnakeGameApplication;
import javafx.application.Platform;
import javafx.util.Duration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.game.data.Config.DEFAULT_GAME_HEIGHT;
import static com.game.data.Config.DEFAULT_GAME_WIDTH;
import static org.junit.jupiter.api.Assertions.*;
import com.almasb.fxgl.test.RunWithFX;

/**
 * Tests setting instantiation
 */
@ExtendWith(RunWithFX.class)
public class MySettingsTest {

    /**
     * Launches the javafx game.
     * @throws InterruptedException If thread couldn't sleep
     */
    @BeforeAll
    static void initGameTests() throws InterruptedException {
        Thread one = new Thread(()->{
            SnakeGameApplication.main(new String[] {});
        });
        one.start();
        Thread.sleep(2000); // Wait for application to start
    }

    /**
     * Tests expected settings.
     * @throws InterruptedException If thread couldn't sleep
     */
    @Test

    void testSettings() throws InterruptedException {
            ReadOnlyGameSettings settings = FXGL.getSettings();
            assertEquals("The Snake Game",settings.getTitle());
            assertEquals("v1.0",settings.getVersion());
            assertEquals(DEFAULT_GAME_WIDTH,settings.getWidth());
            assertEquals(DEFAULT_GAME_HEIGHT,settings.getHeight());
            assertTrue(settings.isMainMenuEnabled());
            assertFalse(settings.isNative());
            assertTrue(settings.getSceneFactory() instanceof MenuFactory);
    }


}