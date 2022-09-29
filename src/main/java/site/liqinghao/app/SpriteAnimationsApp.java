package site.liqinghao.app;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameScene;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * 精灵图动画
 */
public class SpriteAnimationsApp extends GameApplication {
    private Entity player;

    /**
     * Initialize app settings.
     */
    @Override
    protected void initSettings(GameSettings s) {
        s.setStageStyle(StageStyle.UNDECORATED);
        s.setWidth(520);
        s.setHeight(288);
        s.setTitle(" ");
        s.setVersion(" ");
        s.setGameMenuEnabled(false);
    }

    /**
     * Initialize game objects.
     */
    @Override
    protected void initGame() {
        GameScene gameScene = FXGL.getGameScene();
        gameScene.setCursor(Cursor.DEFAULT);
        gameScene.setBackgroundColor(Color.web("#2b2b2b"));

        player = FXGL.entityBuilder()
                     .at(200, 200)
                     .with(new AnimationComponent())
                     .buildAndAttach();
    }

    @Override
    protected void initInput() {
        FXGL.getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponent.class).moveRight();
            }
        }, KeyCode.D);
        FXGL.getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponent.class).moveLeft();
            }
        }, KeyCode.A);

        FXGL.onKey(KeyCode.SPACE, () -> {
            FXGL.getPrimaryStage().hide();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/**
 * 动画组件
 */
class AnimationComponent extends Component {
    private int speed = 0;

    private final AnimatedTexture texture;
    private final AnimationChannel idle, walk;

    public AnimationComponent() {
        idle = new AnimationChannel(FXGL.image("newdude.png"), 4, 32, 42, Duration.seconds(1), 1, 1);
        walk = new AnimationChannel(FXGL.image("newdude.png"), 4, 32, 42, Duration.seconds(1), 0, 3);

        texture = new AnimatedTexture(idle);
    }

    /**
     * Called after the component is added to entity.
     */
    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }

    /**
     * Called each frame when not paused.
     *
     * @param tpf time per frame
     */
    @Override
    public void onUpdate(double tpf) {
        entity.translateX(speed * tpf);
        if (speed != 0) {
            if (texture.getAnimationChannel().equals(idle)) {
                texture.loopAnimationChannel(walk);
            }
            speed = (int) (speed * 0.9);

            if (FXGLMath.abs(speed) < 1) {
                speed = 0;
                texture.loopAnimationChannel(idle);
            }
        }
    }

    public void moveRight() {
        speed = 150;
        entity.setScaleX(1);
    }

    public void moveLeft() {
        speed = -150;
        entity.setScaleX(-1);
    }
}