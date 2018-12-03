import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    String OS = System.getProperty("os.name");

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = configureScene();

        primaryStage.setScene(scene);
        configureStage(primaryStage);


        primaryStage.show();
    }


    private Scene configureScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));

        // The window needs to not be entirely transparent; otherwise it will not catch any events.
        // It seems like something built in to avoid people not realizing there's a window up.
        Color nearlyTransparent = new Color(0f, 0f, 0f, 0.01f);
        Scene scene = new Scene(root, nearlyTransparent);

        scene.addEventFilter(KeyEvent.ANY, event -> {
            System.out.println(event.toString());
            event.consume();
        });

        scene.addEventFilter(MouseEvent.ANY, event -> {
            System.out.println(event.toString());
            event.consume();
        });

        return scene;
    }

    private void configureStage(Stage primaryStage) {

        primaryStage.setTitle("Input Blocker");

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setAlwaysOnTop(true);

        // Fullscreen is really only useful on Windows
        if (OS.contains("Windows")) {
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitHint("Input is being blocked. To resume, press the ESC key.");
            primaryStage.fullScreenProperty().addListener((observable, wasFullScreen, isFullScreen) -> primaryStage.setIconified(wasFullScreen));
        }

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

    }


    public static void main(String[] args) {
        launch(args);
    }
}
