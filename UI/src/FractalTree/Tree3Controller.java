/**
 * This is a Controller file for the GUI3.fxml 
 * The file contain all functions for drawing the tree
 * @author: Ngo Dinh Anh Khoa
 */

 import java.io.IOException;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Random;
 import java.util.ResourceBundle;
 import javafx.beans.value.ChangeListener;
 import javafx.animation.KeyFrame;
 import javafx.animation.Timeline;
 import javafx.beans.value.ObservableValue;
 import javafx.event.ActionEvent;
 import javafx.fxml.FXML;
 import javafx.fxml.Initializable;
 import javafx.scene.Scene;
 import javafx.scene.control.Button;
 import javafx.scene.control.Label;
 import javafx.scene.control.Slider;
 import javafx.scene.layout.Pane;
 import javafx.scene.paint.Color;
 import javafx.stage.Stage;
 import javafx.util.Duration;
 
 public class Tree3Controller implements Initializable {
     @FXML
     private Pane myPane;
     @FXML 
     private Button appearButton;
     @FXML 
     private Button AnimationButton;
     @FXML 
     private Button deleteButton;
     @FXML 
     private Button UpdateColorButtonS;
     @FXML
     private Slider AngleSlider;
     @FXML
     private Slider AngleBetweenBranchSlider;
     @FXML
     private Slider WidthSlider;
     @FXML
     private Slider LengthSlider;
     @FXML
     private Slider LevelSlider;
     @FXML
     private Label VariableLabel;
 
     
     /*
      * checkButtonDrawPress: to check if the draw button is pressed or not
      * change: to check if there is any change in the variables that are use to draw the tree
      * strokeW: the tree's trunk thickness (the branch thickness decreases after each level)
      * length: the tree's trunk length (the branch length decreases after each level)
      * RAngleC: the change in the angle after each level
      * AngleBetweenBranch: the angle between the left and right branches
      * Original_level: store the level of the tree
      * Temp_level: the level of the branches (leaf)
      * Temp_level1: the current level of the tree
      * x: the starting X position of the tree base on coordinate
      * y: the starting Y position of the tree base on coordinate 
      * root: the tree trunk
      * branchList: store every tree branch
      * tl: the timeline to deal with animation when changing the angle
      * animation: the timeline to deal with animation
      * colorForTree: get the color base on HSB color system
      */  
     private boolean checkButtonDrawPress = false;
     private boolean checkButtonAnimationPress = false;
     private boolean change = false;
     private double strokeW = 1;
     private double y = 720;
     private double x = 1280/2+100;
     private double length = 50;
     private double RAngleC = 0;
     private double AngleBetweenBranch = 0;
     private int Original_level = 0;
     private int Temp_level = 1;
     private int Temp_level1 = 0;
     private Timeline tl;
     private Timeline animation;
     Node root;
     private List<Node> branchList = new ArrayList<Node>();
     private double colorForTree=0;
 
     /**
      * initialize:
      * get the value from mySlider and draw the Label
      */
     @Override
     public void initialize(URL arg0, ResourceBundle arg1) {
         AngleSlider.valueProperty().addListener(new ChangeListener<Number>() {
             public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                 RAngleC =(double) newValue;
                 updateLabel();
                 change = true;
             }
         });
 
         AngleBetweenBranchSlider.valueProperty().addListener(new ChangeListener<Number>() {
             public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                 AngleBetweenBranch =(double) newValue;
                 updateLabel();
                 change = true;
             }
         });
 
         WidthSlider.valueProperty().addListener(new ChangeListener<Number>() {
             public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                 strokeW =(double) newValue;
                 updateLabel();
                 change = true;
             }
         });
 
         LengthSlider.valueProperty().addListener(new ChangeListener<Number>() {
             public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                 length =(double) newValue;
                 updateLabel();
                 change = true;
             }
         });
 
         LevelSlider.valueProperty().addListener(new ChangeListener<Number>() {
             public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                 if ((double)newValue == (int) Math.round( (double) newValue)){
                     Original_level = (int) Math.round( (double) newValue);
                     updateLabel();
                     change = true;
                 }
             }
         });
 
         updateLabel();
     }
 
     /**
      * The function is to return back to the Login GUI
      * @param: event an event happen when the button is select
      */
 
     public void switchToScene3(ActionEvent event) throws IOException {
         deleteTree();
         AngleSlider.setValue(0);
         AngleBetweenBranchSlider.setValue(0);
         WidthSlider.setValue(1);
         LengthSlider.setValue(50);
         LevelSlider.setValue(0);
         resetVariable();
         updateLabel();
         Stage stage;
         stage = TreeApp.window;
         Scene scene = TreeApp.scene;
         stage.setScene(scene);
         stage.show();
         GameViewManager.gameStage3.close();
     }
 
     /**
      * Reset all variable need for drawing
      */
     public void resetVariable(){
         length = 50;
         strokeW = 1;
         Original_level = 0;
         RAngleC = 0;
         AngleBetweenBranch =0;
     }
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 /*
  * Start of version 2
  */
 /**
  * Delete the tree base on the branchList and then clear the branchList
  */
     public void deleteTree(){
         if (checkButtonDrawPress == true){
             for (int i=0;i<branchList.size();i++){
                 myPane.getChildren().remove(branchList.get(i).getData());
             }
             // Original_level = 0;
             Temp_level = 1;
             checkButtonDrawPress = false;
             tl.stop();
             branchList.clear();
         }
         if (checkButtonAnimationPress == true){
             animation.stop();
             checkButtonAnimationPress = false;
         }
         updateLabel();
     }
 /**
  * Update a new label if the Original_level or strokeW or level or RAngleC change
  */
     public void updateLabel(){
         VariableLabel.setText("Length: "+length+"\nWidth: "+strokeW+"\nLevel: "+
             Original_level+" out of 12"+"\nAngle: "+RAngleC+"\nAngle between branch: "+AngleBetweenBranch +
             "\nDrawing: "+checkButtonDrawPress +"\nAnimating: "+checkButtonAnimationPress);
     }
 /**
  * Function for draw the tree branch by branch and change the tree base on the the change of RAngleC
  */
     public void draw(){
         if (checkButtonDrawPress == false){
             Temp_level1 = Original_level;
             root = new Node(x,y,length,strokeW,0,0);
             branchList.add(root);
             myPane.getChildren().add(root.getData());
             checkButtonDrawPress = true;
             if (Original_level > 0){
                 Timeline drawTreeTimeLine = new Timeline(new KeyFrame(Duration.millis(300), e ->
                 {
                     drawBinary();
                 }
                 ));
                 drawTreeTimeLine.setCycleCount(Original_level);
                 drawTreeTimeLine.play();
             }
             tl = new Timeline(new KeyFrame(Duration.millis(10), e ->
             {
                 updateBinary();
             }
             ));
             tl.setCycleCount(Timeline.INDEFINITE);
             tl.play();
             updateLabel();
         }
     }
 /**
  * Function adding new line base on a parent line
  */
     public void drawBinary(){
         //using for loop
             for (int i = branchList.size()-1; i >=0;i--){
                 if (branchList.get(i).getLevel() == (Temp_level-1)){
                     double strokeWTemp = branchList.get(i).getData().getStrokeWidth();
                     double lengthTemp = branchList.get(i).getLength();
                     strokeWTemp /= 1.3;
                     lengthTemp /= 1.3;
                     Node left = new Node(branchList.get(i).getDataX(), branchList.get(i).getDataY(), 
                                         lengthTemp, strokeWTemp, branchList.get(i).getAngle()-RAngleC,Temp_level);
                     Node right = new Node(branchList.get(i).getDataX(), branchList.get(i).getDataY(), 
                                         lengthTemp, strokeWTemp, left.getAngle() + AngleBetweenBranch,Temp_level);
         
                     left.setParent(branchList.get(i));
                     right.setParent(branchList.get(i));
         
                     branchList.get(i).setLeft(left);
                     branchList.get(i).setRight(right);
         
                     branchList.add(left);
                     branchList.add(right);
     
                     myPane.getChildren().add(0,left.getData());
                     myPane.getChildren().add(0,right.getData());
 
                 }
             }
             Temp_level++;
     } 
 
 /**
  * Update the position if the RAngleC change
  */
     public void updateBinary(){
         if (change == true){
             updateBranchPosition(root);
             updateLevel();
             change = false;
         }
     }
     /**
      * Update the color for the tree
      */
     public void updateTreeColor(){
         colorForTree = (new Random()).nextInt(360);
         updateBranchColor(root,colorForTree);
         //updateBranchColor1(root);
     }
 
     /**
      * Change the color randomly
      */
     public void updateBranchColor(Node root, double colorForTree){
         if (root.getLeftNode() != null){
             updateBranchColor(root.getLeftNode(),colorForTree+20);
             updateBranchColor(root.getRightNode(),colorForTree-20);
         }
         root.getData().setStroke(Color.hsb(colorForTree, 0.8, 0.9));
     }
 
     /**
      * Change the color base on a real tree
      */
     public void updateBranchColor1(Node root){
         if (root.getLeftNode() != null){
             updateBranchColor1(root.getLeftNode());
             updateBranchColor1(root.getRightNode());
             root.getData().setStroke(Color.rgb(200,100,0));
         } else {
             root.getData().setStroke(Color.rgb(50,200,50));
         }
     }
     
 
     /**
      * Using recursion to update each branch a new position base on the new RAngleC
      */
     public void updateBranchPosition(Node root){
         if (this.root == root){
             root.getData().setStrokeWidth(strokeW);
             root.setLength(length);
         }
         if (root.getLeftNode() != null && root.getRightNode() != null){
             root.UpdateChildBranchForTree3(RAngleC,AngleBetweenBranch);
             updateBranchPosition(root.getLeftNode());
             updateBranchPosition(root.getRightNode());
         }
     }
     /**
      * Update the tree when the level is changed
      */
     public void updateLevel(){
         if (Temp_level1 > Original_level){
             Timeline updateLevel = new Timeline(new KeyFrame(Duration.millis(1), e ->
                 {
                     for (int i=branchList.size()-1; i>0;i--){
                         if (branchList.get(i).getLevel() == Temp_level1){
                             myPane.getChildren().remove(branchList.get(i).getData());
                             branchList.remove(i);
                         }
                     }
                     Temp_level1--;
                 }
                 ));
             if (Original_level == 0){
                 updateLevel.setCycleCount(Temp_level1);
             } else {
                 updateLevel.setCycleCount(Temp_level1-Original_level);
             }
             updateLevel.play();
         } else if (Temp_level1 < Original_level){
             Temp_level = Temp_level1+1;
             Timeline updateLevel = new Timeline(new KeyFrame(Duration.millis(1), e ->
                 {
                     drawBinary();
                 }
                 ));
             updateLevel.setCycleCount(Original_level-Temp_level1);
             Temp_level1 = Original_level;
             updateLevel.play();
         }
     }
 /**
 * This function is used to start the animation for the tree
 */
     public void animationStart(ActionEvent event){
         if (checkButtonAnimationPress == false && checkButtonDrawPress == true){
             tl.stop();
             animation = new Timeline(new KeyFrame(Duration.millis(50), e ->
                 {
                     AngleBetweenBranch+=3;
                     RAngleC+=2;
                     updateBranchPosition(root);
                     updateLevel();
                 }
             ));
             animation.setCycleCount(Timeline.INDEFINITE);
             animation.play();
             checkButtonAnimationPress=true;
         } else if (checkButtonAnimationPress == true) {
             checkButtonAnimationPress=false;
             animation.stop();
             tl.play();
             RAngleC = RAngleC % 360;
             if (RAngleC > 180){
                 RAngleC = RAngleC-360;
             }
             AngleBetweenBranch = AngleBetweenBranch % 360;
             if (AngleBetweenBranch > 180){
                 AngleBetweenBranch = AngleBetweenBranch-360;
             }
             AngleSlider.setValue(RAngleC);
             AngleBetweenBranchSlider.setValue(AngleBetweenBranch);
         }
         updateLabel();
     }
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
 /*
  * End of version 2
  */   
 
 
 }