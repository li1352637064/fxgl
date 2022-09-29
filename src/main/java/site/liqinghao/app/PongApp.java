package site.liqinghao.app;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;

/**
 * pingpong game
 */
public class PongApp extends GameApplication {
    private Entity board;
    private Entity pong;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    protected void initSettings(GameSettings s) {
        s.setTitle(" ");
        s.setVersion(" ");
        s.setStageStyle(StageStyle.UNDECORATED);
        s.setWidth(320);
        s.setHeight(180);
        s.setGameMenuEnabled(false);
    }

    @Override
    protected void initGame() {
        GameScene gameScene = FXGL.getGameScene();
        gameScene.setBackgroundColor(Color.web("#2b2b2b"));
        gameScene.setCursor(Cursor.DEFAULT);

        board = FXGL.entityBuilder()
                    .type(EntityType.BOARD)
                    .viewWithBBox(new Rectangle(60, 10, Color.GHOSTWHITE))
                    .at(FXGL.getAppCenter().getX() - 30, FXGL.getAppHeight() - 10)
                    .with(new CollidableComponent(true))
                    .buildAndAttach();

        pong = FXGL.entityBuilder()
                   .type(EntityType.PONG)
                   .viewWithBBox(new Circle(6, 6, 6, Color.PALEGOLDENROD))
                   .at(FXGL.getAppCenter().subtract(new Point2D(3, 3)))
                   .with(new CollidableComponent(true))
                   .buildAndAttach();

        FXGL.onKey(KeyCode.SPACE, () -> {
            FXGL.getPrimaryStage().hide();
            System.exit(0);
        });
    }

    @Override
    protected void initPhysics() {
        FXGL.onCollisionBegin(EntityType.BOARD, EntityType.PONG, (board, pong) -> {
        });
    }


    enum EntityType {
        BOARD, PONG
    }
}
