import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * This class controls how the menu will be executed
 * @author Tran Quang Minh
 */
public class GameViewManager {
    Image image = new Image("file:UI/src/FractalTree/TreeIcon.jpg");
    static Stage gameStage;
    static Stage gameStage2;
    static Scene gameScene;
    private Stage menuStage;
    static Scene gameScene2;
    static Scene gameScene3;
    static Stage gameStage3;
    public GameViewManager() throws IOException {
        initializeStage();

    }

    /**
     * This function is to initialize the stage and scene
     * @return nothing
     * @author Tran Quang Minh
     */
    private void initializeStage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI1.fxml"));
        gameScene = new Scene(root);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        //Image image = new Image("D:/Game/UI/src/FractalTree/TreeIcon.jpg");
        gameStage.setResizable(false);
        gameStage.getIcons().add(image);
        gameStage.setTitle("Fractal Tree");

        Parent root2 = FXMLLoader.load(getClass().getClassLoader().getResource("GUI2.fxml"));
        gameScene2 = new Scene(root2);
        gameStage2 = new Stage();
        gameStage2.setScene(gameScene2);

        Parent root3 = FXMLLoader.load(getClass().getClassLoader().getResource("GUI3.fxml"));
        gameScene3 = new Scene(root3);
        gameStage3 = new Stage();
        gameStage3.setScene(gameScene3);
    }

    /**
     * This function create new stage of the application
     * @param menuStage a window of the application
     * @return nothing
     * @author Tran Quang Minh
     */
    public void createNewGame1(Stage menuStage) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.show();
    }
    /**
     * This function create new stage of the application
     * @param menuStage a window of the application
     * @return nothing
     * @author Tran Quang Minh
     */
    public void createNewGame2(Stage menuStage) {
        gameStage2.getIcons().add(image);
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage2.show();
    }
    /**
     * This function create new stage of the application
     * @param menuStage a window of the application
     * @return nothing
     * @author Tran Quang Minh
     */
    public void createNewGame3(Stage menuStage) {
        gameStage3.getIcons().add(image);
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage3.show();
    }

    /**
     * This function closes the stage of the application
     * @return nothing
     * @author Tran Quang Minh
     */
    public void closeStage() {
        gameStage.close();
        gameStage2.close();
        gameStage3.close();
    }
}