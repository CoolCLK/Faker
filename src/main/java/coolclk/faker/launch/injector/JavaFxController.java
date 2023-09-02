package coolclk.faker.launch.injector;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class JavaFxController implements Initializable {
    public BorderPane parent;

    public Button minimizeWindowButton;
    public Button closeWindowButton;

    public ProgressBar progressBar;
    public Text progressName;

    public HBox loginPane;
    public TextField loginTextField;
    public PasswordField loginPasswordField;
    public Button loginButton;

    public ChoiceBox<Injector.VirtualMachineProcess> processChoiceBox;
    public Button processConfirmButton;
    public HBox selectProcessPane;

    public HBox injectPane;
    public TextArea injectLogArea;
    public Button injectRetryButton;
    public Button injectFinishButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progressBar.setProgress(0);
        progressName.setText("Step 0/4: Setup");

        minimizeWindowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((Stage) minimizeWindowButton.getScene().getWindow()).setIconified(true);
            }
        });
        closeWindowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((Stage) closeWindowButton.getScene().getWindow()).close();
            }
        });

        processLogin();
    }

    public void processLogin() {
        progressBar.setProgress(0.25);
        progressName.setText("Step 1/4: Login");

        loginPane.setVisible(true);
        loginPane.setManaged(true);
        loginTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                loginButton.setDisable(loginTextField.getText().trim().isEmpty() || loginPasswordField.getText().trim().isEmpty());
            }
        });
        loginPasswordField.setOnKeyPressed(loginTextField.getOnKeyPressed());
        loginTextField.getOnKeyPressed().handle(null);
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                closeLogin();
                processSelectProcess();
            }
        });
    }

    public void closeLogin() {
        loginPane.setVisible(false);
        loginPane.setManaged(false);
    }

    public void processSelectProcess() {
        progressBar.setProgress(0.5);
        progressName.setText("Step 2/4: Select a Minecraft process");
        if (Injector.findMinecraftProcesses().size() != 1) {
            selectProcessPane.setVisible(true);
            selectProcessPane.setManaged(true);
            processChoiceBox.setConverter(new StringConverter<Injector.VirtualMachineProcess>() {
                @Override
                public String toString(final Injector.VirtualMachineProcess object) {
                    if (object == Injector.VirtualMachineProcess.NULL) return "Select a Minecraft process";
                    return "[" + object.getVirtualMachine().id() + "] " + object.getWindowText();
                }

                @Override
                public Injector.VirtualMachineProcess fromString(String string) {
                    return null;
                }
            });
            processChoiceBox.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int lastValueProcessId = (processChoiceBox.getValue() != null && processChoiceBox.getValue() != Injector.VirtualMachineProcess.NULL) ? Integer.parseInt(processChoiceBox.getValue().virtualMachine.id()) : -1;
                    processChoiceBox.getItems().remove(1, processChoiceBox.getItems().size());
                    processChoiceBox.setValue(Injector.VirtualMachineProcess.NULL);
                    for (Injector.VirtualMachineProcess virtualMachineProcess : Injector.findMinecraftProcesses()) {
                        if (virtualMachineProcess == Injector.VirtualMachineProcess.NULL || Integer.parseInt(virtualMachineProcess.getVirtualMachine().id()) == Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]) || virtualMachineProcess.getWindowText().trim().isEmpty())
                            continue;
                        processChoiceBox.getItems().add(virtualMachineProcess);
                        if (Integer.parseInt(virtualMachineProcess.getVirtualMachine().id()) == lastValueProcessId) {
                            processChoiceBox.setValue(virtualMachineProcess);
                            break;
                        }
                    }
                }
            });
            processChoiceBox.getItems().add(Injector.VirtualMachineProcess.NULL);
            processChoiceBox.setValue(Injector.VirtualMachineProcess.NULL);

            processConfirmButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (processChoiceBox.getValue() != null && processChoiceBox.getValue() != Injector.VirtualMachineProcess.NULL) {
                        closeSelectProcess();
                        processInject(processChoiceBox.getValue());
                    }
                }
            });
            processChoiceBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    processConfirmButton.setDisable(processChoiceBox.getValue() == Injector.VirtualMachineProcess.NULL);
                }
            });
            processChoiceBox.getOnAction().handle(null);
        } else {
            closeSelectProcess();
            processInject(Injector.findProcesses().get(0));
        }
    }

    public void closeSelectProcess() {
        selectProcessPane.setVisible(false);
        selectProcessPane.setManaged(false);
    }

    public void processInject(final Injector.VirtualMachineProcess process) {
        progressBar.setProgress(0.75);
        progressName.setText("Step 3/4: Injecting");

        injectPane.setVisible(true);
        injectPane.setManaged(true);
        injectLogArea = new TextArea();
        injectLogArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                injectLogArea.setScrollTop(Double.MAX_VALUE);
            }
        });
        ((VBox) injectRetryButton.getParent().getParent()).getChildren().add(0, injectLogArea);
        injectRetryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                injectLogArea.setText("");
                if (process.inject(new PrintStream(new OutputStream() {
                    @Override
                    public void write(int b) {
                        System.out.write(b);
                        injectLogArea.appendText(new String(new byte[]{(byte) b}));
                    }
                }))) {
                    closeInject();
                    injectRetryButton.setDisable(true);
                    injectFinishButton.setDisable(false);
                } else {
                    injectFinishButton.setDisable(true);
                    injectRetryButton.setDisable(false);
                }
            }
        });
        injectFinishButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                closeInject();
                processComplete();
            }
        });
        injectRetryButton.getOnAction().handle(null);
    }

    public void closeInject() {
        injectPane.setVisible(false);
        injectPane.setManaged(false);
    }

    public void processComplete() {
        progressBar.setProgress(1);
        progressName.setText("Step 4/4: Complete");
        closeWindowButton.getOnAction().handle(null);
    }
}
