import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random; // to place pipes in random position
import javax.swing.*;

public class JavaGame extends JPanel implements ActionListener,KeyListener {  // We will use all the functionality of JPanel in JavaGame using inheritance
    int boardWidth=450;
    int boardHeight=640;

    // images
    Image backgroundimg;
    Image birdimg;
    Image bottomPipeimg;
    Image topPipeimg;

    //bird
    int birdX=boardWidth/8;
    int birdY=boardHeight/2;
    int birdWidth=34;
    int birdHeight=24;

    class Bird{
        int x=birdX;
        int y=birdY;
        int Width=birdWidth;
        int height=birdHeight;
        Image img;
        

        Bird(Image img){
            this.img=img;

        }
    }

    int pipeX=boardWidth;
    int pipeY=0;
    int pipeWidth=64;
    int pipeHeight=512;


    class Pipe{
        int x=pipeX;
        int y=pipeY;
        int width=pipeWidth;
        int height=pipeWidth;
        Image img;
    
        boolean passed=false;

        Pipe(Image img){
            this.img=img;
        }

    }

    Bird bird;  // game logic
    int velocityY=0; // move bird upperward/ downward 
    int velocityX=-9; // move pipes to left speed
    int gravity=1;
    Timer gameloop;
    Timer placepipeTimer;
    ArrayList<Pipe> pipes;
    boolean gameOver = false;  
    double score=0;
    
    JavaGame(){
        setPreferredSize(new Dimension(boardWidth,boardHeight));
       // setBackground(Color.blue);
        
       setFocusable(true);
       addKeyListener(this);
        // loading image
        backgroundimg = new ImageIcon(getClass().getResource("./background.jpeg")).getImage();
        birdimg = new ImageIcon(getClass().getResource("./bird.png")).getImage();
        topPipeimg=new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeimg=new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        bird=new Bird(birdimg);
        pipes = new ArrayList<Pipe>();

        placepipeTimer=new Timer(1500,new ActionListener() {  // places pipes every 1.5 sec
            @Override
            public void actionPerformed(ActionEvent e){
                placepipes();
            }

            
        });
        

        placepipeTimer.start();

        gameloop=new Timer(1000/55, this);
        gameloop.start();

    }

    public void placepipes(){
       int randomPipeY =(int) (pipeY-pipeHeight/4 - Math.random()*(pipeHeight/2));
       int openingspace=boardHeight/4;

       Pipe topPipe=new Pipe(topPipeimg);
       topPipe.x = boardWidth; 
       if(topPipeimg == null){
        System.out.println("top pipe not visible");
       }
       topPipe.width = pipeWidth;
       topPipe.height = pipeHeight;
       topPipe.y= randomPipeY;
       pipes.add(topPipe);
       System.out.println(topPipe);
       Pipe bottomPipe=new Pipe(bottomPipeimg);
       bottomPipe.x = boardWidth;
       bottomPipe.width = pipeWidth;
       bottomPipe.height = pipeHeight;
       bottomPipe.y= topPipe.y + pipeHeight +openingspace;
       pipes.add(bottomPipe);
       System.out.println(bottomPipe);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(backgroundimg, 0, 0, this.boardWidth,this.boardHeight , null );
        g.drawImage(bird.img, bird.x, bird.y, bird.Width, bird.height, null);
       
        // draw the pipes
        for(int i=0; i < pipes.size(); i++){
            Pipe pipe=pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);

        
                System.out.println("Drawing pipe at: x=" + pipe.x + ", y=" + pipe.y + ", width=" + pipe.width + ", height=" + pipe.height);
                g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
         g.setColor(Color.RED);
         g.setFont(new Font("Arial", Font.ROMAN_BASELINE , 40));
         if(gameOver){
            g.drawString("Game Over :"+ String.valueOf((int)score) ,10 , 45);
                                                  
         }
         else{
            g.drawString("Current Score : "+String.valueOf((int)score), 10 , 45);
         }

    }
    public void move(){
        velocityY += gravity;
        bird.y +=velocityY;
        bird.y = Math.max(bird.y , 0);
        
        // move pipe 
        for(int i=0;i<pipes.size();i++){
            Pipe pipe=pipes.get(i);
            pipe.x += velocityX;

             // Remove pipe if it moves off-screen
        if (pipe.x + pipe.width < 0) {
            pipes.remove(i);
            i--; // Adjust the index after removal
        }
        if(!pipe.passed && bird.x>pipe.x + pipe.width ){
            pipe.passed=true;
            score +=0.5;
        } 

        if(collision(bird, pipe)){
            gameOver=true;
        }

        }

        if(bird.y>boardHeight){
            gameOver=true;
        }
    }
     
    public boolean collision(Bird a, Pipe b ){
        return a.x < b.x + b.width &&   //a's top left corner doesn't reach b's top right corner
        a.x + a.Width > b.x &&   //a's top right corner passes b's top left corner
        a.y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
        a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
}
    @Override
    public void actionPerformed(ActionEvent e){ // action performed 60 times 1 sec
        move();   // in every screen we do move the bird then repeat the screen 
        repaint(); // repeat the screen;

        if(gameOver){
            placepipeTimer.stop();
            gameloop.stop();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if(e.getKeyCode()== KeyEvent.VK_SPACE){
            velocityY=-9;
            if(gameOver){
                // reset the game by resetting the conditions
                bird.y=birdY;
                velocityY=0;
                pipes.clear();
                score=0;
                gameOver=false;
                gameloop.start();
                placepipeTimer.start();
            }

        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

}
