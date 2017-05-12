package Flappy;

import java.awt.*;
public class Container extends Asset {

    public Container(int width, int height, Color color, Color backgroundColor, Graphics drawing){
        setWidth(width);
        setHeight(height);
        setColor(color);
        setBackgroundColor(backgroundColor);
        setDrawing(drawing);

    }
    @Override
    public void draw() {
        getDrawing().setColor(getColor());
        getDrawing().fillRect(getX(), getY(), getWidth(),getHeight());
    }

    @Override
    public void remove() {
        getDrawing().setColor(getBackgroundColor());
        getDrawing().fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
