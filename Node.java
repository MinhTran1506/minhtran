/*
 * This is a Node file for the GUI.fxml 
 * The file contain all functions for a branch (leaf) of a tree
 * @author: Ngo Dinh Anh Khoa
 */

import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

public class Node {
    private Line data;
    private double angle;
    private double length;
    private int level;
    private Node left;
    private Node right;
    private Node parent;

    /**
     * This function is use to create a Node
     * @param x         the starting X position in the coordinate
     * @param y         the starting Y position in the coordinate
     * @param length    the length of a branch (leaf)
     * @param strokeW   the width of a branch (leaf)
     * @param angle     the rotate angle of the branch (leaf)
     * @param level     the level of the branch (leaf)
     */
    Node(double x, double y, double length, double strokeW,double angle, int level){
        this.level = level;
        this.angle = angle;
        this.length = length;
        data = new Line();
        data.setStartX(x);
        data.setEndX(x);
        data.setStartY(y);
        data.setEndY(y-length);
        data.setStrokeWidth(strokeW);
        setRotate(angle);
        left = right = parent = null;
    }

    /**
     * This function is used to rotate the branch (leaf)
     * @param: angle    the rotate angle of the branch (leaf)
     */
    private void setRotate(double angle){
        Rotate rotate = new Rotate();
        rotate.setPivotX(data.getStartX());
        rotate.setPivotY(data.getStartY());
        rotate.setAngle(angle);
        data.getTransforms().add(rotate);
    }

    /**
     * This function is used to change the angle
     * @param: angle     the rotate angle of the branch (leaf)
     */
    public void setAngle(double angle){
        this.angle = angle;
    }

    /**
     * This function is used to change parent of the branch (leaf)
     * @param: parent   the parent of the branch
     */
    public void setParent(Node parent){
        this.parent = parent;
    }

    /**
     * This function is used to change the left branch (leaf) of the current branch
     * @param: left: the left branch (leaf)
     */
    public void setLeft(Node left){
        this.left = left;
    }

    /**
     * This function is used to change the right branch (leaf) of the current branch
     * @param: right: the right branch (leaf)
     */
    public void setRight(Node right){
        this.right = right;
    }

    /**
     * This function returns the data of the branch
     */
    public Line getData(){
        return data;
    }

    /**
     * This function returns the left branch (leaf) of the current branch
     */
    public Node getLeftNode(){
        return left;
    }

    /**
     * This function returns the right branch (leaf) of the current branch
     */
    public Node getRightNode(){
        return right;
    }

    /**
     * This function returns the parent of the current branch (leaf)
     */
    public Node getParentNode(){
        return parent;
    }

    /**
     * This function returns the rotate angle of the current branch (leaf)
     */
    public double getAngle(){
        return angle;
    }

    /**
     * This function returns the length of the current branch (leaf)
     */
    public double getLength(){
        return length;
    }

    public void setLength(double length){
        this.length = length;
        data.setEndY(data.getStartY()-length);
    }

    /**
     * This function returns the ending X position of the current branch (leaf)
     */
    public double getDataX(){
        return data.localToParent(data.getEndX(),data.getEndY()).getX();
    }

    /**
     * This function returns the ending Y position of the current branch (leaf)
     */
    public double getDataY(){
        return data.localToParent(data.getEndX(),data.getEndY()).getY();
    }

    /**
    * This function returns the level of the branch (leaf)
    */
    public int getLevel(){
        return level;
    }

    /**
     * This function is used to update the left and right branch (leaf) base on the current branch
     * @param RAngleC       the changed angle
     */
    public void UpdateChildBranchForTree2(double RAngleC){
        //Update left child
        left.data.setStartX(getDataX());
        left.data.setEndX(getDataX());
        left.data.setStartY(getDataY());
        left.data.setEndY(getDataY()-left.length);
        left.data.setEndY(getDataY()-this.getLength()/1.3);
        left.data.getTransforms().clear();
        left.setAngle(this.angle - RAngleC);
        left.setRotate(this.angle - RAngleC);
        left.data.setStrokeWidth(this.data.getStrokeWidth()/1.3);
        left.setLength(this.getLength()/1.3);

        //Update right child
        right.data.setStartX(getDataX());
        right.data.setEndX(getDataX());
        right.data.setStartY(getDataY());
        right.data.setEndY(getDataY()-left.length);
        right.data.setEndY(getDataY()-this.getLength()/1.3);
        right.data.getTransforms().clear();
        right.setAngle(this.angle + RAngleC);
        right.setRotate(this.angle + RAngleC);
        right.data.setStrokeWidth(this.data.getStrokeWidth()/1.3);
        right.setLength(this.getLength()/1.3);
    }

    /**
     * This function is used to update the left or right branch (leaf) base on the current branch
     * @param RAngleC       the changed angle
     * @param LeftOrRight   the direction needed to be draw
     */
    public void UpdateChildBranchForTree1(double RAngleC, int LeftOrRight){
        //Update child
        left.data.setStartX(getDataX());
        left.data.setEndX(getDataX());
        left.data.setStartY(getDataY());
        left.data.setEndY(getDataY()-left.length);
        left.data.getTransforms().clear();
        left.setAngle(this.angle + LeftOrRight*RAngleC);
        left.setRotate(this.angle + LeftOrRight * RAngleC);
        left.data.setStrokeWidth(this.data.getStrokeWidth()/1.3);
        left.setLength(this.getLength()/1.3);
    }

    /**
     * This function is used to update the left and right branch (leaf) base on the current branch
     * @param RAngleC       the changed angle
     * @param AngleBetweenBranch    the angle between the children branches
     */
    public void UpdateChildBranchForTree3(double RAngleC, double AngleBetweenBranch){
        //Update left child
        left.data.setStartX(getDataX());
        left.data.setEndX(getDataX());
        left.data.setStartY(getDataY());
        left.data.setEndY(getDataY()-left.length);
        left.data.setEndY(getDataY()-this.getLength()/1.3);
        left.data.getTransforms().clear();
        left.setAngle(this.angle - RAngleC);
        left.setRotate(this.angle - RAngleC);
        left.data.setStrokeWidth(this.data.getStrokeWidth()/1.3);
        left.setLength(this.getLength()/1.3);

        //Update right child
        right.data.setStartX(getDataX());
        right.data.setEndX(getDataX());
        right.data.setStartY(getDataY());
        right.data.setEndY(getDataY()-right.length);
        right.data.getTransforms().clear();
        right.setAngle(left.getAngle() + AngleBetweenBranch);
        right.setRotate(left.getAngle() + AngleBetweenBranch);
        right.data.setStrokeWidth(this.data.getStrokeWidth()/1.3);
        right.setLength(this.getLength()/1.3);
    }
}
