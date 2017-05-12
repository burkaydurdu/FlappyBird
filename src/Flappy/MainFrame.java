package Flappy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import static javax.swing.JOptionPane.*;

public class MainFrame extends JFrame implements ActionListener{

    private final Graphics g;
    private JButton startButton,pauseButton;
    private Panel panel;
    private Hero hero;
    private final int width = 800;
    private int height = 400;
    private Random random;
    private ArrayList<Container> containersList;
    private int puan = 0;
    private int count = 0;
    private int jumper = 0;
    private boolean jumpSpace = false;
    private boolean game_over = false;
    private boolean START = false;
    private boolean isPause =true;
    private String VK_W = "VK_W";

    public MainFrame(){
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(800,440));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.pack();
        this.setVisible(true);

        random = new Random();
        containersList = new ArrayList<>();
        Loop threads = new Loop();
        threads.start();
        /*
        * @setFocusable(true) this frame focusable and its run
        * */
        JPanel panelControl = new JPanel();
        panelControl.setLayout(new FlowLayout());

        startButton = new JButton("Start");
        startButton.addActionListener(this);
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(this);

        panelControl.add(startButton);
        panelControl.add(pauseButton);

        panel = new Panel();
        height = panel.getHeight();
        add(panel, BorderLayout.CENTER);
        add(panelControl,BorderLayout.SOUTH);

        ActionMap actionMap = panel.getActionMap();
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), VK_W);
        actionMap.put(VK_W, new KeyAction(VK_W));

        g = panel.getGraphics();
        hero = new Hero(50,50,Color.ORANGE,Color.BLUE,g);
        this.repaint();
    }


    private void containerCreate(){

        boolean upDown = random.nextBoolean();
        int hei = 80 + random.nextInt(height-150);
        Container container = new Container(50, hei, Color.GREEN, Color.BLUE, g);
        if(upDown)
            container.setY(0);
        else
            container.setY(height -hei );
        container.setX(width);
        containersList.add(container);
    }

    private void containerMove() {
        for (Container aContainersList : containersList) {
            aContainersList.moveTo(-2, 0);
            if (aContainersList.getX() <= 0) {
                aContainersList.remove();
                puan +=10;
            }
        }
    }

    private void heroVsContainer(){
        for (Container aContainersList : containersList) {
            if ((hero.getX() <= aContainersList.getX() &&
                    hero.getX() + hero.getWidth() >= aContainersList.getX()) ||
                    (hero.getX() <= aContainersList.getX() + aContainersList.getWidth() &&
                            hero.getX() + hero.getWidth() >= aContainersList.getX() + aContainersList.getWidth())) {

                if ((hero.getY() <= aContainersList.getY() &&
                        hero.getY() + hero.getHeight() >= aContainersList.getY()) ||
                        (hero.getY() <= aContainersList.getY() + aContainersList.getHeight() &&
                                hero.getY() >= aContainersList.getY()) ||
                        (hero.getY() + hero.getHeight() > aContainersList.getY() &&
                                hero.getY() + hero.getHeight() < aContainersList.getY() + aContainersList.getHeight())) {
                    gameOver();
                    break;
                }
            }
        }
    }

    public void gameOver(){
        game_over = true;
        START = false;
        containersList.clear();
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        showMessageDialog(null, "Game Over\nPUAN : " + String.valueOf(puan));
    }

    public void heroMove(){

        if (!jumpSpace)
            hero.moveTo(0,5);
        else {
            if(jumper == 5) jumpSpace = false;
            jumper++;
            hero.moveTo(0,-5);
        }

        if(hero.getY()<0 || hero.getY()>height)
            gameOver();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(startButton)){
            panel.repaint();
            hero.setY(50);
            puan = 0;
            START = true;
            game_over = false;
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
        }
        else if(e.getSource().equals(pauseButton)){
            if(isPause){
                START = false;
                isPause = false;
                pauseButton.setText("Resume");
            }
            else{
                START = true;
                isPause = true;
                pauseButton.setText("Pause");
            }
        }
    }

    public class Loop extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    if (START) {
                        if (count == 0) {
                            containerCreate();
                            count = 90;
                        }
                        count--;
                        heroVsContainer() ;
                        heroMove();
                        containerMove();
                    }

                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class KeyAction extends AbstractAction {
        public KeyAction(String actionCommand) {
            putValue(ACTION_COMMAND_KEY, actionCommand);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvt) {
            if (actionEvt.getActionCommand().equals(VK_W)){
                if (START) {
                    jumper = 0;
                    jumpSpace = true;
                }
            }
        }
    }
}
