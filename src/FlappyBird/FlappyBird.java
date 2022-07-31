package FlappyBird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;

public class FlappyBird implements ActionListener, MouseListener, KeyListener {
    public final int WIDTH=800;// горизонталь x
    public final int  HEIGHT=800; //вертикаль y
    public Rectangle bird;
    public ArrayList<Rectangle> columns;
    public int score; //текущий результат
    public int record;
    public int yMotion; //движение по y
    public int ticks;
    public boolean gameOver;
    public boolean started;
    public Rendrer renderer;
    public static FlappyBird flappyBird;

    public FlappyBird() {
        JFrame jFrame=new JFrame();
        Timer timer= new Timer(20,this);
        renderer=new Rendrer();
        jFrame.add(renderer);
        jFrame.setTitle("FlappyBird");
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        //jFrame.setLocation(200,200);
        jFrame.setSize(WIDTH,HEIGHT);
        jFrame.setVisible(true);
        jFrame.addMouseListener(this);
        jFrame.addKeyListener(this);
        jFrame.setResizable(false);
        jFrame.setFocusable(true);
        bird= new Rectangle(WIDTH/2-10,HEIGHT/2-10,20,20);
        columns=new ArrayList<>();
        addColum(true);
        addColum(true);
        addColum(true);
        addColum(true);
        timer.start();
    }


    public void repaint(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(0,0,WIDTH,HEIGHT);
        g.setColor(Color.orange);
        g.fillRect(0,HEIGHT-150,800,150);
        g.setColor(Color.green);
        g.fillRect(0,HEIGHT-150,800,25);
        g.setColor(Color.red);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);
        for(Rectangle i:columns){
            paintColumn(g,i);
        }
        if(!started){
            g.drawString("Click to started",WIDTH/2-50,70);
        }
        if(gameOver){
            g.drawString("Game Over"+score,WIDTH/2,HEIGHT/2);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("abc",1,100));
        if(started&&!gameOver){
            if(score>record) {
                record = score;
            }
                g.drawString(score+"|"+record,WIDTH/2,100);
        }
    }

    public static void main(String[] args) {
        flappyBird= new FlappyBird();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int speed=10;
        ticks++;
        if(started){
            for(int i=0;i<columns.size();i++){
                Rectangle a=columns.get(i);
                a.x=a.x-speed; //изменения скорости колонны
            }
            if(ticks%2==0&&yMotion<15){
                yMotion=yMotion+2;
            }
            for(int i=0;i<columns.size();i++){
                Rectangle a=columns.get(i);
                if(a.x+a.width<0){
                    columns.remove(a);
                    addColum(false);
                }
            }
            bird.y= bird.y+yMotion;
            for(int i=0;i<columns.size();i++){
                Rectangle a=columns.get(i);
                if(bird.x+bird.width/2>a.x+a.width/2-10){
                    if(a.y==0){
                        if(bird.x+bird.width/2<a.x+a.width/2+10){
                            score++;
                        }
                    }
                }
                if(a.intersects(bird)){
                    gameOver=true;
                    if(bird.x<=a.x){
                        bird.x=a.x- bird.y;
                    }
                    else{
                        bird.y=a.height;
                    }
                }
            }
            if(bird.y<0||bird.y>650){
                gameOver=true;
            }
        }

        renderer.repaint();
    }
    public  void addColum(Boolean start){

        int space = 300;
        int width = 100;
        int height = (int)(Math.random()*50+300);


        if (start)
        {

            columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));

            columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
        }
        else
        {

            columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));

            columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));

        }
    }
    public void paintColumn(Graphics g,Rectangle colum){
        g.setColor(Color.BLACK);
        g.fillRect(colum.x,colum.y,colum.width,colum.height);
    }
    public void jamp (){
        if(gameOver){
            columns.clear();
            score=0;
            yMotion=0;
            addColum(true);
            addColum(true);
            addColum(true);
            addColum(true);
            bird=new Rectangle(WIDTH/2,HEIGHT/2,20,20);
            gameOver=false;
        }
        if(!started){
            started=true;
        }
        else if(!gameOver){
            if(yMotion>0){
                yMotion=0;
            }
            yMotion=yMotion-8;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            jamp();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        jamp();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        jamp();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
