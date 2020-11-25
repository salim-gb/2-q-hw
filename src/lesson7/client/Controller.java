package lesson7.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lesson7.server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public TextArea textArea;
    @FXML
    public TextField textField;

    private final String IP_ADDRESS = "localhost";
    private final int PORT = 8181;
    @FXML
    public HBox authPanel;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public VBox msgPanel;
    @FXML
    public HBox listClients;
    @FXML
    public Label labelRecipients;
    @FXML
    public TextField textField1;
    @FXML
    public Button chooseRecipients;
    @FXML
    public VBox containerTop;

    private Socket socket;
    DataInputStream in;
    DataOutputStream out;

    private boolean authenticated;
    private String nickname;
    private final String TITLE = "ГикЧат";

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
        authPanel.setVisible(!authenticated);
        authPanel.setManaged(!authenticated);
        msgPanel.setVisible(authenticated);
        msgPanel.setManaged(authenticated);

        if (!authenticated) {
            nickname = "";
            textArea.clear();
        }
        setTitle(nickname);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAuthenticated(false);
        String[] users = {"asd", "qwe", "zxc" , "rom"};
        chooseRecipients.setOnAction(event -> {
            containerTop.setVisible(true);
            containerTop.setManaged(true);
            listClients.getChildren().clear();
            for (String user : users) {
                if (!user.equals(nickname)) {
                    Button button = new Button("" + user);
                    button.setPadding(new Insets(5,5,5,5));
                    listClients.getChildren().add(button);
                    button.setOnAction(e -> {
                        textField1.appendText(button.getText() + " ");
                        textField1.setVisible(true);
                        textField1.setManaged(true);
                        containerTop.setVisible(false);
                        containerTop.setManaged(false);
                    });
                }
            }
        });
    }

    private void connection() {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    // цикл аутентификации
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/authok")) {
                            nickname = str.split("\\s", 2)[1];
                            setAuthenticated(true);
                            break;
                        }
                        textArea.appendText(str + "\n");
                    }

                    // цикл работы
                    while (true) {
                        String str = in.readUTF();

                        if (str.equals("/end")) {
                            System.out.println("Клиент отключился");
                            break;
                        }

                        System.out.println("Клиент: " + str);
                        textArea.appendText(str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Мы отключились от сервера");
                    setAuthenticated(false);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(ActionEvent actionEvent) {
        if (!textField.getText().isEmpty()) {
            try {
                out.writeUTF(textField1.getText() + "->" + textField.getText());
                textField.clear();
                textField.requestFocus();
                textField1.clear();
                textField1.setVisible(false);
                textField1.setManaged(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void tryToAuth(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()) {
            connection();
        }
        try {
            out.writeUTF(String.format("/auth %s %s", loginField.getText().trim().toLowerCase(),
                    passwordField.getText().trim()));
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTitle(String nick) {
        Platform.runLater(() -> {
            ((Stage) textField.getScene().getWindow()).setTitle(TITLE + " " + nick);
        });
    }
}
