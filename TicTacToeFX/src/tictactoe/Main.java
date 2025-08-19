package tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {
    private Button[][] buttons = new Button[3][3];
    private char currentPlayer = 'X';
    private Label status = new Label("Am Zug: X");
    private boolean gameOver = false;

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setAlignment(Pos.CENTER);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Button b = new Button("");
                b.setPrefSize(90, 90);
                b.setStyle("-fx-font-size: 28; -fx-font-weight: bold;");
                final int row = r, col = c;
                b.setOnAction(e -> handleMove(row, col));
                buttons[r][c] = b;
                grid.add(b, c, r);
            }
        }

        Button reset = new Button("Neu starten");
        reset.setOnAction(e -> reset());

        BorderPane root = new BorderPane();
        BorderPane.setMargin(grid, new Insets(16));
        BorderPane.setMargin(status, new Insets(8,16,8,16));
        BorderPane.setMargin(reset, new Insets(0,0,16,0));
        root.setTop(status);
        root.setCenter(grid);
        root.setBottom(reset);
        BorderPane.setAlignment(reset, Pos.CENTER);

        Scene scene = new Scene(root, 340, 360);
        stage.setTitle("Tik Tak Toe — JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    private void handleMove(int r, int c) {
        if (gameOver) return;
        Button b = buttons[r][c];
        if (!b.getText().isEmpty()) return;
        b.setText(String.valueOf(currentPlayer));

        if (hasWinner()) {
            gameOver = true;
            showEndDialog(currentPlayer + " hat gewonnen!");
            status.setText("Gewonnen: " + currentPlayer);
            return;
        }
        if (isDraw()) {
            gameOver = true;
            showEndDialog("Unentschieden!");
            status.setText("Unentschieden");
            return;
        }
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        status.setText("Am Zug: " + currentPlayer);
    }

    private boolean hasWinner() {
        String[][] g = new String[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                g[r][c] = buttons[r][c].getText();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (!g[i][0].isEmpty() && g[i][0].equals(g[i][1]) && g[i][1].equals(g[i][2])) return true;
            if (!g[0][i].isEmpty() && g[0][i].equals(g[1][i]) && g[1][i].equals(g[2][i])) return true;
        }
        if (!g[0][0].isEmpty() && g[0][0].equals(g[1][1]) && g[1][1].equals(g[2][2])) return true;
        if (!g[0][2].isEmpty() && g[0][2].equals(g[1][1]) && g[1][1].equals(g[2][0])) return true;
        return false;
    }

    private boolean isDraw() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (buttons[r][c].getText().isEmpty()) return false;
            }
        }
        return true;
    }

    private void showEndDialog(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Spielende");
        alert.setHeaderText(null);
        alert.setContentText(message + "\nKlicke 'Neu starten' für eine neue Runde.");
        alert.show();
    }

    private void reset() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                buttons[r][c].setText("");
            }
        }
        currentPlayer = 'X';
        status.setText("Am Zug: X");
        gameOver = false;
    }
}
