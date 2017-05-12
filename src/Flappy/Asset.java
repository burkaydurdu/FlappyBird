package Flappy;

import java.awt.*;

public abstract class Asset {

    private int x = 50;
    private int y = 50;
    private int width;
    private int height;
    private Color color;
    private Color backgroundColor;
    private Graphics drawing;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Graphics getDrawing() {
        return drawing;
    }

    public void setDrawing(Graphics drawing) {
        this.drawing = drawing;
    }

    public abstract void draw();

    public abstract void remove();

    public void moveTo(int x , int y){
        remove();
        setX(getX() + x);
        setY(getY() + y);
        draw();
    }

}
