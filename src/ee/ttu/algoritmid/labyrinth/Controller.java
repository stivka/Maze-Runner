package ee.ttu.algoritmid.labyrinth;

import java.io.IOException;
import java.net.URISyntaxException;

public class Controller {
    public static void main(String[] args) {
        HW03 hw03 = null;
        try {
            hw03 = new HW03("maze.txt");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        System.out.println("Starting at X=Y -> " + hw03.getMazeRunner().getPosition());
        System.out.println(hw03.solve().size());
    }

}
