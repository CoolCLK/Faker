package coolclk.faker.launch.injector;

import com.sun.tools.attach.VirtualMachine;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.util.List;

public class Bootstrap extends Application {
    ChoiceBox<VirtualMachine> processChoiceBox;
    Boolean endThread = false;
    Thread updateChoiceBoxThread;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        double width = 300, height = 220;

        Scene scene = new Scene(new BorderPane(), width, height);
        BorderPane parent = (BorderPane) scene.getRoot();

        HBox hBox = new HBox();
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.prefHeight(5);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(vBox);
        parent.setCenter(hBox);

        Text titleText = new Text("Faker Injector");
        titleText.setFont(Font.font(20));
        processChoiceBox = new ChoiceBox<VirtualMachine>();
        processChoiceBox.setConverter(new StringConverter<VirtualMachine>() {
            @Override
            public String toString(VirtualMachine object) {
                return "[" + object.id() + "] " + object.provider().name();
            }

            @Override
            public VirtualMachine fromString(String string) {
                return null;
            }
        });
        (updateChoiceBoxThread = new Thread(new Task<Void>() {
            @Override
            public Void call() {
                long nextTime = 0;
                while (!endThread) {
                    if (System.currentTimeMillis() >= nextTime) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                VirtualMachine lastValue = processChoiceBox.getValue();
                                List<VirtualMachine> minecraftProcesses = Injector.findProcesses();
                                boolean same = true;
                                for (VirtualMachine virtualMachine : minecraftProcesses) if (!processChoiceBox.getItems().contains(virtualMachine)) same = false;
                                if (!same) processChoiceBox.getItems().setAll(minecraftProcesses);
                                if (processChoiceBox.getItems().contains(lastValue)) processChoiceBox.setValue(lastValue);
                            }
                        });
                        nextTime = System.currentTimeMillis() + 500;
                    }
                }
                return null;
            }
        })).start();
        titleText.setFont(Font.font(20));
        final Button injectButton = new Button("Inject");
        injectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    processChoiceBox.getValue().loadAgent(Bootstrap.class.getProtectionDomain().getCodeSource().getLocation().getFile());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        vBox.getChildren().addAll(titleText, processChoiceBox, injectButton);

        primaryStage.setTitle("Faker Injector");
        primaryStage.setResizable(false);
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.getIcons().add(new Image("/assets/faker/icons/icon_128x128.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        endThread = true;
    }
}
