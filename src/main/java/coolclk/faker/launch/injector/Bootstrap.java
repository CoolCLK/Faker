package coolclk.faker.launch.injector;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Bootstrap extends Application {
    double mouseLastX = -1, mouseLastY = -1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        primaryStage.setTitle("Faker Injector");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getIcons().add(new Image("/assets/faker/icons/icon_128x128.png"));
        final Scene scene = new Scene(new FXMLLoader(this.getClass().getResource("/assets/faker/javafx/javafx.faker.fxml")).<Parent>load());
        scene.setFill(null);
        scene.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    primaryStage.setX(primaryStage.getX() + event.getScreenX() - mouseLastX);
                    primaryStage.setY(primaryStage.getY() + event.getScreenY() - mouseLastY);
                }
                mouseLastX = event.getScreenX();
                mouseLastY = event.getScreenY();
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
