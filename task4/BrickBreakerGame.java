import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BrickBreakerGame extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 10;
    private static final int BALL_RADIUS = 10;
    private static final int BRICK_WIDTH = 50;
    private static final int BRICK_HEIGHT = 20;

    private double paddleX = WIDTH / 2 - PADDLE_WIDTH / 2;
    private double ballX = WIDTH / 2;
    private double ballY = HEIGHT / 2;
    private double ballXSpeed = 5;
    private double ballYSpeed = 5;
    private boolean leftKeyPressed = false;
    private boolean rightKeyPressed = false;

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                leftKeyPressed = true;
            } else if (event.getCode() == KeyCode.RIGHT) {
                rightKeyPressed = true;
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                leftKeyPressed = false;
            } else if (event.getCode() == KeyCode.RIGHT) {
                rightKeyPressed = false;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Brick Breaker Game");
        primaryStage.setResizable(false);
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render(gc);
            }
        }.start();
    }

    private void update() {
        if (leftKeyPressed && paddleX > 0) {
            paddleX -= 5;
        }
        if (rightKeyPressed && paddleX < WIDTH - PADDLE_WIDTH) {
            paddleX += 5;
        }

        ballX += ballXSpeed;
        ballY += ballYSpeed;

        if (ballX >= WIDTH - BALL_RADIUS || ballX <= BALL_RADIUS) {
            ballXSpeed = -ballXSpeed;
        }
        if (ballY <= BALL_RADIUS) {
            ballYSpeed = -ballYSpeed;
        }
        if (ballY >= HEIGHT - BALL_RADIUS) {
            // Game over condition
            // You can handle game over logic here
        }

        // Collision detection with paddle
        if (ballY + BALL_RADIUS >= HEIGHT - PADDLE_HEIGHT && ballX >= paddleX && ballX <= paddleX + PADDLE_WIDTH) {
            ballYSpeed = -ballYSpeed;
        }
    }

    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        // Draw paddle
        gc.setFill(Color.BLUE);
        gc.fillRect(paddleX, HEIGHT - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);

        // Draw ball
        gc.setFill(Color.RED);
        gc.fillOval(ballX - BALL_RADIUS, ballY - BALL_RADIUS, 2 * BALL_RADIUS, 2 * BALL_RADIUS);

        // Draw bricks
        // You can add brick drawing logic here
    }

    public static void main(String[] args) {
        launch(args);
    }
}
