/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.util.LinkedList;

public class PathFinder {

    Maze maze;
    LinkedList<Coordinate> myPath = new LinkedList<Coordinate>();

    public PathFinder(Maze iMaze) {
        maze = iMaze;
    }

    boolean tracePath(int r, int c, char d) {
        boolean exitFound = false;
        if (maze.isExit(r, c)) {
            return (true);
        } else {
            if (!maze.hasNorthWall(r, c) && d != 's' && !exitFound) {
                exitFound = tracePath(r - 1, c, 'n');
            }
            if (!maze.hasSouthWall(r, c) && d != 'n' && !exitFound) {
                exitFound = tracePath(r + 1, c, 's');
            }
            if (!maze.hasEastWall(r, c) && d != 'w' && !exitFound) {
                exitFound = tracePath(r, c + 1, 'e');
            }
            if (!maze.hasWestWall(r, c) && d != 'e' && !exitFound) {
                exitFound = tracePath(r, c - 1, 'w');
            }
        }
        if (exitFound) {
            myPath.add(new Coordinate(r, c));
        }
        return (exitFound);
    }

    public LinkedList<Coordinate> findPath(int startRow, int startColumn) {
        myPath.clear();
        this.tracePath(startRow, startColumn, 'r');
        return (myPath);
    }
}
