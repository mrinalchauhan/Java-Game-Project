import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth=360;
        int boardHeight=640;

        JFrame frame=new JFrame("JAVA GAME");
        //frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);// will place to center
        frame.setResizable(false); // user wll not be able to resize
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // To terminate the game

        JavaGame gamepanel=new JavaGame();
       
        frame.add(gamepanel);
        frame.pack(); // to give width height excluding the title area
        gamepanel.requestFocus();
        frame.setVisible(true);
        
    }
}
