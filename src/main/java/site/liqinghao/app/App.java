package site.liqinghao.app;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Main class
 */
public class App extends GameApplication {
    private Entity player;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings s) {
        // init window setting
        s.setWidth(320);
        s.setHeight(180);
        s.setTitle("Basic Game App");
        s.setVersion(" ");
        s.setGameMenuEnabled(false);
    }

    @Override
    protected void initGame() {
        // set bg color black
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);

        // spawn player
        player = FXGL.entityBuilder()
                     .at(160 - 5, 90 - 5)
                     .view(new Rectangle(10, 10, Color.WHITESMOKE))
                     .buildAndAttach();
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.SPACE, () -> System.exit(0));

        FXGL.onKey(KeyCode.W, () -> player.translateY(-2));
        FXGL.onKey(KeyCode.S, () -> player.translateY(2));
        FXGL.onKey(KeyCode.A, () -> player.translateX(-2));
        FXGL.onKey(KeyCode.D, () -> player.translateX(2));
    }

    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50);
        textPixels.setTranslateY(100);
        textPixels.setText("Hello");
        FXGL.getGameScene().addUINode(textPixels);
    }
}
