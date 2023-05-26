import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text; 

public class TreeApp extends Application {
    
    private GameMenu gameMenu; 
    static Stage window;
    static Pane root;
    static Scene scene;
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * start function is to create a primary stage and scene
     * @param primaryStage a window of the application
     * @return nothing
     * @author Tran Quang Minh
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        root = new Pane();
        root.setPrefSize(1250, 600);
        Image backgroundImage = new Image("file:UI/tree.jfif", 1920, 1080, false, true);
        Image image = new Image("file:UI/src/FractalTree/TreeIcon.jpg");
        //Image backgroundImage = new Image("D:/VGU_GIt/FractalTree/UI/tree.jfif", 1920, 1080, false, true);
        //Image image = new Image("D:/VGU_GIt/FractalTree/UI/src/FractalTree/TreeIcon.jpg");
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        root.setBackground(new Background(background));
        window.getIcons().add(image);
        
        gameMenu = new GameMenu();
        gameMenu.setVisible(true);
        
        root.getChildren().addAll(gameMenu);
        scene = new Scene(root);
        //music();
        // disable maximize button
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.setScene(scene);
        primaryStage.show();
        


        
    }
    /*MediaPlayer mediaPlayer;
    public void music() {
        String fileName = "sound.mp3";
        //String path = getClass().getResource(fileName).getPath();
        Media media = new Media(Paths.get(fileName).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }*/

    /**
     * This class in charge of creating menus layouts as well as 
     * button of the menus.
     * @author Tran Quang Minh
     */

    private class GameMenu extends Parent {
        public GameMenu() throws IOException {

            VBox menu0 = new VBox(15);
            VBox menu1 = new VBox(15);
            VBox menu2 = new VBox(15);

            menu0.setTranslateX(100);
            menu0.setTranslateY(200);

            menu1.setTranslateX(100);
            menu1.setTranslateY(200);

            menu2.setTranslateX(100);
            menu2.setTranslateY(200);

            final int offset = 400;
            
            menu1.setTranslateX(offset);
            GameViewManager gameManager = new GameViewManager();


            // creating buttons
            MenuButton btnResume = new MenuButton("START");
            btnResume.setOnMouseClicked(e -> {
                getChildren().add(menu2);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
                tt.setToX(menu0.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu2);
                tt1.setToX(menu0.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu0);
                });
                

            });

            MenuButton btnExit = new MenuButton("EXIT");
            btnExit.setOnMouseClicked(e -> {
                System.exit(0);
            });

            MenuButton btnBack = new MenuButton("BACK");
            btnBack.setOnMouseClicked(e -> {
                getChildren().add(menu0);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
                tt.setToX(menu1.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
                tt1.setToX(menu1.getTranslateX());

                tt.play();
                tt1.play(); 

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu1);
                });
            });

            MenuButton btnBack2 = new MenuButton("BACK");
            btnBack2.setOnMouseClicked(e -> {
                getChildren().add(menu0);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu2);
                tt.setToX(menu2.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
                tt1.setToX(menu2.getTranslateX());

                tt.play();
                tt1.play(); 

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu2);
                });
            });


            MenuButton btnSound = new MenuButton("SOUND");
            btnSound.setOnMouseClicked(e -> {
                //mediaPlayer.play();
            });
            MenuButton btnVideo = new MenuButton("VIDEO");
            MenuButton btnTree1 = new MenuButton("SYMMETRIC TREE");
            btnTree1.setOnMouseClicked(e -> {
                gameManager.createNewGame2(window);
            });

            MenuButton btnTree2 = new MenuButton("SPIRAL TREE");
            btnTree2.setOnMouseClicked(e -> {
                gameManager.createNewGame1(window);
            });

            MenuButton btnTree3 = new MenuButton("BINARY TREE");
            btnTree3.setOnMouseClicked(e -> {
                gameManager.createNewGame3(window);
            });



            menu0.getChildren().addAll(btnResume, btnExit);
            menu1.getChildren().addAll(btnSound, btnVideo, btnBack);
            menu2.getChildren().addAll(btnTree1, btnTree2, btnTree3, btnBack2);

            Rectangle bg = new Rectangle(1920, 1080);
            bg.setFill(Color.GREY);
            bg.setOpacity(0.4);

            getChildren().addAll(bg, menu0);

        }
    }


    /**
     * This class in charge of creating the properties of the
     * buttons as well as the effect whenever the mouse enters
     * the buttons
     * @author Tran Quang Minh
     */

    private static class MenuButton extends StackPane {
        private Text text;

        public MenuButton(String name) {
            text = new Text(name);
            //text.setFont(this.text.getFont().font(20));
            text.setFont(Font.font(20));
            text.setFill(Color.WHITE);

            Rectangle bg = new Rectangle(200, 30);
            bg.setOpacity(0.6);
            bg.setFill(Color.BLACK);
            bg.setEffect(new GaussianBlur(3.5));

            this.setAlignment(Pos.CENTER_LEFT);
            getChildren().addAll(bg, text);

            this.setOnMouseEntered(e -> {
                bg.setTranslateX(10);
                text.setTranslateX(10);
                bg.setFill(Color.WHITE);
                text.setFill(Color.BLACK);
            });

            this.setOnMouseExited(e -> {
                bg.setTranslateX(0);
                text.setTranslateX(0);
                bg.setFill(Color.BLACK);
                text.setFill(Color.WHITE);
            });

            DropShadow drop = new DropShadow(50, Color.WHITE);
            drop.setInput(new Glow());
            
            setOnMousePressed(e -> setEffect(drop));
            setOnMouseReleased(e -> setEffect(null));

        }
    }
    
}