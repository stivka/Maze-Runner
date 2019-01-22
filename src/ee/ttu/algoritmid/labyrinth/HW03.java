package ee.ttu.algoritmid.labyrinth;

import java.util.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class HW03 {
    private MazeRunner mazeRunner;
    private String[] directions = {"W", "N", "E", "S"};
    private List<String> possibleDirections = new ArrayList<>();
    private List<String> pathTaken = new ArrayList<>();
    private String leftWallDirection;
    private String movingDirection;
    private boolean isLoop = false;
    private int firstPositionMovedOnto;


    public HW03(String fileName) throws IOException, URISyntaxException {
        mazeRunner = new MazeRunner(fileName);
    }

    public MazeRunner getMazeRunner() {
        return mazeRunner;
    }

    /**
     * Returns the list of steps to take to get from beginning ("B") to
     * the treasure ("T").
     *
     * @return return the steps taken as a list of strings (e.g., ["E", "E", "E"])
     * return null if there is no path (there is no way to get to the treasure).
     */
    public List<String> solve() {
        int stopper = 0;

        if (mazeRunner.getSize() == null) {
            return null;
        }

        moveIntoMaze();

        int longestPath = mazeRunner.getSize().getValue() * mazeRunner.getSize().getKey();
        System.out.println(longestPath);
        // till we ain't on treasure
        while (mazeRunner.scan().get(1).get(1) != -2) {
            if (mazeRunner.scan().get(1).get(1) == 0) {
                return null;
            } else if (pathTaken.size() > longestPath) {
                return null;
                // If there is no wall or beginning point to the left, rotate ccw
            } else if (whatIsAhead(leftWallDirection) != -1) {
                rotate("ccw");
                moveForward();
                // If there is no wall or beginning point in front, move in movingDirection
            } else if (whatIsAhead(movingDirection) != -1) {
                moveForward();
            } else {
                rotate("cw");
                if (whatIsAhead(movingDirection) != -1) {
                    moveForward();
                } else {
                    rotate("cw");
                    moveForward();
                }
            }
        }
        System.out.println(pathTaken.size());
        printPath();
        return pathTaken;
    }

    private void moveForward() {
        if (movingDirection.equals("W") && (mazeRunner.scan().get(0).get(1) >= 0 || mazeRunner.scan().get(0).get(1) == -2)) {
            mazeRunner.move("W");
            pathTaken.add("W");
        } else if (movingDirection.equals("N") && (mazeRunner.scan().get(1).get(0) >= 0 || mazeRunner.scan().get(1).get(0) == -2)) {
            mazeRunner.move("N");
            pathTaken.add("N");
        } else if (movingDirection.equals("E") && (mazeRunner.scan().get(2).get(1) >= 0 || mazeRunner.scan().get(2).get(1) == -2)) {
            mazeRunner.move("E");
            pathTaken.add("E");
        } else if (movingDirection.equals("S") && (mazeRunner.scan().get(1).get(2) >= 0 || mazeRunner.scan().get(1).get(2) == -2)) {
            mazeRunner.move("S");
            pathTaken.add("S");
        } else {

        }
    }

    private void rotate(String rotation) {
        if (rotation.equals("cw")) {
            switch (movingDirection) {
                case "W":
                    movingDirection = "N";
                    leftWallDirection = "W";
                    break;
                case "N":
                    movingDirection = "E";
                    leftWallDirection = "N";
                    break;
                case "E":
                    movingDirection = "S";
                    leftWallDirection = "E";
                    break;
                case "S":
                    movingDirection = "W";
                    leftWallDirection = "S";
                    break;
            }
        } else if (rotation.equals("ccw")) {
            switch (movingDirection) {
                case "W":
                    movingDirection = "S";
                    leftWallDirection = "E";
                    break;
                case "N":
                    movingDirection = "W";
                    leftWallDirection = "S";
                    break;
                case "E":
                    movingDirection = "N";
                    leftWallDirection = "W";
                    break;
                case "S":
                    movingDirection = "E";
                    leftWallDirection = "N";
                    break;
            }
        } else {
            System.out.println("Couldn't assign direction");
        }
    }

    private void moveIntoMaze() {
        findPossibleDirections();
        firstPositionMovedOnto = mazeRunner.scan().get(1).get(0);
        mazeRunner.move(possibleDirections.get(0));
        pathTaken.add(possibleDirections.get(0));
        assignRotation();
    }

    private void assignRotation() {
        movingDirection = possibleDirections.get(0);
        switch (movingDirection) {
            case "W":
                leftWallDirection = "S";
                break;
            case "N":
                leftWallDirection = "W";
                break;
            case "E":
                leftWallDirection = "N";
                break;
            case "S":
                leftWallDirection = "E";
                break;
            default:
                System.out.println("Error assigning directions");
                break;
        }
    }

    private void findPossibleDirections() {
        possibleDirections.clear();
        for (String direction : directions) {

            if (direction.equals("W") && mazeRunner.scan().get(0).get(1) != null) {
                if (mazeRunner.scan().get(0).get(1) > 0) {
                    possibleDirections.add("W");
                }
            }
            if (direction.equals("N") && mazeRunner.scan().get(1).get(0) != null) {
                if (mazeRunner.scan().get(1).get(0) > 0) {
                    possibleDirections.add("N");
                }
            }
            if (direction.equals("E") && mazeRunner.scan().get(2).get(1) != null) {
                if (mazeRunner.scan().get(2).get(1) > 0) {
                    possibleDirections.add("E");
                }
            }
            if (direction.equals("S") && mazeRunner.scan().get(1).get(2) != null) {
                if (mazeRunner.scan().get(1).get(2) > 0) {
                    possibleDirections.add("S");
                }
            }
        }
    }

    /**
     *
     * @param direction
     * @return
     */
    private int whatIsAhead(String direction) {
        if (direction.equals("W")) {
            return mazeRunner.scan().get(0).get(1);
        }
        if (direction.equals("N")) {
            return mazeRunner.scan().get(1).get(0);
        }
        if (direction.equals("E")) {
            return mazeRunner.scan().get(2).get(1);
        }
        if (direction.equals("S")) {
            return mazeRunner.scan().get(1).get(2);
        } else {
            System.out.println("Error checking whatIsAhead");
        }
        return -3;
    }

    private void printPath() {
        System.out.println();
        for (String direction : pathTaken) {
            System.out.print(direction + " ");
        }
    }
}