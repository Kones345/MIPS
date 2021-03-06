package com.lordsofmidnight.gamestate.maps;

import java.util.Arrays;
import java.util.Random;

/**
 * Used to generate the random maps
 */
public class MapGenerator {
  /*
   Rules:
   No unacessable parts
   Mirror'd shape
   Walls around the edge except a loop around
  */

  public static void main(String[] args) {

    int[][] test1 = {
        {1, 1, 1, 1, 1},
        {1, 0, 1, 1, 1},
        {1, 0, 1, 1, 1},
        {1, 0, 1, 1, 1},
        {1, 1, 1, 1, 1}
    };
    int[][] test2 = {
        {1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1},
        {1, 0, 0, 0, 1},
        {1, 0, 0, 0, 1},
        {1, 1, 1, 1, 1}
    };
    int[][] test3 = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
        {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
        {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1},
        {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
        {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1},
        {1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1},
        {1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
        {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    if (validateMap(test1)) {
      System.out.println("test one true");
    }
    if (validateMap(test2)) {
      System.out.println("test two true");
    }
    if (validateMap(test3)) {
      System.out.println("test three true");
    }
    long[] diff = new long[10];
    long t2;
    long t1;
    for (int c = 0; c < 10; c++) {
      t1 = System.nanoTime();
      int[][] map = newRandomMap(2, 2);
      t2 = System.nanoTime();
      diff[c] = t2 - t1;
      System.out.println("****************");
      for (int[] bit : map) {
        System.out.println(Arrays.toString(bit));
      }
    }
    long avg = 0;
    for (long i : diff) {
      avg = avg + i;
    }
    avg = avg / 10;
    System.out.println(avg / 100000000);
  }

  /**
   * Creates a random map
   *
   * @param x_factor multiplier for the x axis
   * @param y_factor multiplier for the y axis
   * @return the new map
   */
  public static int[][] newRandomMap(int x_factor, int y_factor) {
    // Run it on a new thread
    return generateNewMap(14 + 3 * x_factor, 14 + 3 * y_factor);
  }

  /**
   * Generates a new map
   *
   * @param x the x dimension of the map
   * @param y the y dimension of the map
   * @return the map
   */
  public static int[][] generateNewMap(int x, int y) {
    int[][] map = null;
    int c = 0;
    int half = (y + 1) / 2;
    Random r = new Random();
    while (!validateMap(map)) {
      // System.out.println("attempt " + c++);
      map = new int[x][y];
      for (int i = 0; i < x; i++) {
        for (int j = 0; j < y; j++) {
          map[i][j] = 1;
        }
      }
      for (int i = 1; i < x - 3; i += 3) {
        for (int j = 1; j < half + 1; j += 3) {
          map = apply(MapParts.getRandom(r), map, i, j);
        }
      }
    }
    smoothDiagonals(map, r);
    for (int i = 1; i < map.length; i++) { // Reflects the map
      for (int j = 1; j < map[0].length / 2; j++) {
        map[i][map[0].length - j - 1] = map[i][j];
      }
    }
    addLoops(map); // Adds the loops round

    /*System.out.println("Map made ***************************************");
    for (int[] bit : map) {
      System.out.println(Arrays.toString(bit));
    }
    System.gc(); */
    return map;
  }

  /**
   * Adds the sections of the map that loop around
   *
   * @param map the new edited map
   */
  private static void addLoops(int[][] map) {
    int x = map.length;
    int y = map[0].length;
    if (map[x / 2][1] == 0 && map[x / 2][y - 2] == 0) {
      map[x / 2][0] = 0;
      map[x / 2][y - 1] = 0;
    }
    if (map[1][y / 2] == 0 && map[x - 2][y / 2] == 0) {
      map[0][y / 2] = 0;
      map[x - 1][y / 2] = 0;
    }
  }

  /**
   * Validates a given map
   *
   * @param map the map to validate
   * @return True if map is valid
   */
  public static boolean validateMap(int[][] map) {
    return map != null && checkConnected(map) && noDoubleLanes(map);
  }

  /**
   * Checks that the map is fully connected
   *
   * @param map The map to check
   * @return True if map is fully connected
   */
  private static boolean checkConnected(int[][] map) {
    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[0].length; y++) {
        if (map[x][y] == 0) {
          int[][] copy = new int[map.length][map[0].length];
          for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
              copy[i][j] = map[i][j];
            }
          }
          paint(copy, x, y);
          return !containsSpace(copy);
        }
      }
    }
    return false;
  }

  /**
   * Checks that there are no "double lanes" in the map e.g. a two wide path
   *
   * @param map The map to check
   * @return True if there are no double lanes
   */
  private static boolean noDoubleLanes(int[][] map) {
    for (int x = 1; x < map.length - 1; x++) {
      for (int y = 1; y < map[0].length - 1; y++) {
        if (map[x][y] == 0 && map[x + 1][y] == 0) {
          if (map[x][y + 1] == 0 && map[x + 1][y + 1] == 0) {
            return false;
          }
          if (map[x][y - 1] == 0 && map[x + 1][y - 1] == 0) {
            return false;
          }
        }
        if (map[x][y] == 0 && map[x][y + 1] == 0) {
          if (map[x + 1][y] == 0 && map[x + 1][y + 1] == 0) {
            return false;
          }
          if (map[x - 1][y] == 0 && map[x - 1][y + 1] == 0) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * This method removes any odd endings from the map where two tiles are diagonally connected
   *
   * @param map The map
   * @param r A reference to the random number generator
   */
  private static void smoothDiagonals(int[][] map, Random r) {
    for (int x = 1; x < map.length - 1; x++) {
      for (int y = 1; y < map[0].length - 1; y++) {
        if (map[x][y] == 0 && map[x + 1][y + 1] == 0 && map[x + 1][y] == 1 && map[x][y + 1] == 1) {
          if (r.nextInt(1) == 0) {
            map[x + 1][y] = 0;
            if (!noDoubleLanes(map)) {
              map[x + 1][y] = 1;
              map[x][y + 1] = 0;
              if (!noDoubleLanes(map)) {
                map[x][y + 1] = 1;
              }
            }
          } else {
            map[x][y + 1] = 0;
            if (!noDoubleLanes(map)) {
              map[x][y + 1] = 1;
              map[x + 1][y] = 0;
              if (!noDoubleLanes(map)) {
                map[x + 1][y] = 1;
              }
            }
          }
        } else if (map[x][y] == 0
            && map[x - 1][y + 1] == 0
            && map[x][y + 1] == 1
            && map[x - 1][y] == 1) {
          if (r.nextInt(1) == 0) {
            map[x - 1][y] = 0;
            if (!noDoubleLanes(map)) {
              map[x - 1][y] = 1;
              map[x][y + 1] = 0;
              if (!noDoubleLanes(map)) {
                map[x][y + 1] = 1;
              }
            }
          } else {
            map[x][y + 1] = 0;
            if (!noDoubleLanes(map)) {
              map[x][y + 1] = 1;
              map[x - 1][y] = 0;
              if (!noDoubleLanes(map)) {
                map[x - 1][y] = 1;
              }
            }
          }
        }
      }
    }
  }

  /**
   * Simple check that there is empty space somewhere in the map
   *
   * @param map The map
   * @return True if there is a space in the map
   */
  private static boolean containsSpace(int[][] map) {
    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[0].length; y++) {
        if (map[x][y] == 0) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Fills the map with 1's in adjacent tiles used to check connectedness
   *
   * @param map The map
   * @param x Current x coord in traversal
   * @param y Current y coord in traversal
   */
  private static void paint(int[][] map, int x, int y) {
    if (x == map.length || y == map[0].length || x < 0 || y < 0) {
      return;
    }
    if (map[x][y] == 1) {
      return;
    }
    map[x][y] = 1;
    paint(map, x + 1, y);
    paint(map, x - 1, y);
    paint(map, x, y - 1);
    paint(map, x, y + 1);
  }

  /**
   * Puts the given part into the Map
   *
   * @param part The part to place
   * @param map The Map
   * @param x The X coord of reference point
   * @param y The Y coord of reference point
   * @return The new Map
   */
  private static int[][] apply(int[][] part, int[][] map, int x, int y) {
    // System.out.println(part.length + " " + part[0].length + " " + map.length + " " +
    // map[0].length + " " + x + " " + y);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        map[x][y] = part[i][j];
        y++;
      }
      y -= 3;
      x++;
    }
    return map;
  }
}
