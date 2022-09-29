package site.liqinghao.app;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.LocalTimer;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;

import javax.management.timer.Timer;
import java.util.Map;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;


/**
 * Main class
 */
public class App extends GameApplication {
    private Entity player;
    private LocalTimer spawnCoinTimer;

    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings s) {
        // init window setting
        s.setWidth(512);
        s.setHeight(288);
        s.setTitle("Basic Game App");
        s.setVersion(" ");
        s.setGameMenuEnabled(false);
        s.setStageStyle(StageStyle.UNDECORATED);
    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.web("#2b2b2b"));
        getGameScene().setCursor(Cursor.DEFAULT);


        player = entityBuilder().type(EntityType.PLAYER)
                                .at(200, 200)
                                .viewWithBBox("brick.png")
                                .scale(0.4, 0.4)
                                .with(new CollidableComponent(true))
                                .buildAndAttach();

        this.spawnCoinTimer = getGameTimer().newLocalTimer();
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN) {
            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {
                coin.removeFromWorld();
                inc("score", 1);
            }
        });
    }

    @Override
    protected void onUpdate(double tpf) {
        if (spawnCoinTimer.elapsed(Duration.seconds(0.1))) {
            spawnCoinTimer.capture();
            entityBuilder().type(EntityType.COIN)
                           .at(random(0, getAppWidth() - 6), random(0, getAppHeight() - 16))
                           .viewWithBBox(new Circle(6, 6, 6, Color.GOLDENROD))
                           .with(new CollidableComponent(true))
                           .buildAndAttach();
        }
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.SPACE, () -> System.exit(0));

        FXGL.onKey(KeyCode.W, () -> {
            player.translateY(-2);
            set("MoveTo", "Top");
        });
        FXGL.onKey(KeyCode.S, () -> {
            player.translateY(2);
            set("MoveTo", "Bottom");
        });
        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-2);
            set("MoveTo", "Left");
        });
        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(2);
            set("MoveTo", "Right");
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
    }

    @Override
    protected void initUI() {
        Text scoreT = new Text();
        scoreT.setFill(Color.WHITE);
        scoreT.setTranslateX(10);
        scoreT.setTranslateY(20);
        scoreT.textProperty().bind(getWorldProperties().intProperty("score").asString());
        getGameScene().addUINode(scoreT);
    }

    enum EntityType {
        PLAYER, COIN
    }
}
