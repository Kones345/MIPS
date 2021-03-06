package com.lordsofmidnight.ai.routefinding.routefinders;

import com.lordsofmidnight.ai.routefinding.RouteFinder;
import com.lordsofmidnight.ai.routefinding.SampleSearch;
import com.lordsofmidnight.gamestate.maps.Map;
import com.lordsofmidnight.gamestate.points.Point;
import com.lordsofmidnight.gamestate.points.PointMap;
import com.lordsofmidnight.objects.Pellet;
import com.lordsofmidnight.utils.enums.Direction;

/**
 * Route finding algorithm that will locate the nearest power pellet and patrol around it, but not
 * collect it.
 *
 * @author Lewis Ackroyd
 */
public class PowerUpBoxPatrolRouteFinder implements RouteFinder {
  private static final int SEARCH_DEPTH = 20;
  private static final int AVOID_DEPTH = 5;
  private final Map map;
  private final PointMap<Pellet> pellets;

  /**
   * Initialises this {@link RouteFinder} with the current {@link Map} and {@link Pellet}s on it.
   *
   * @param map The map being used
   * @param pellets A mapping from every point containing a pellet, to that pellet
   * @author Lewis Ackroyd
   */
  public PowerUpBoxPatrolRouteFinder(Map map, PointMap<Pellet> pellets) {
    this.map = map;
    this.pellets = pellets;
  }

  /**
   * Returns the direction to travel in until the next junction is reached such that the direction
   * avoids collecting any {@link com.lordsofmidnight.objects.PowerUpBox} whilst also travelling
   * towards the nearest {@link com.lordsofmidnight.objects.PowerUpBox PowerUpBox}.
   *
   * @param myLocation The start point.
   * @param targetLocation The target point.
   * @return The direction to travel in, or DEFAULT if no direction could be produced.
   * @author Lewis Ackroyd
   */
  @Override
  public Direction getRoute(Point myLocation, Point targetLocation) {
    SampleSearch sampleSearch = new SampleSearch(SEARCH_DEPTH, map);

    class PowerUpBoxCountCondition implements SampleSearch.ConditionalInterface {
      @Override
      public boolean condition(Point position) {
        if (pellets.containsKey(position)) {
          Pellet pellet = pellets.get(position);
          if (pellet.isPowerUpBox()) {
            return true;
          }
        }
        return false;
      }
    }
    int[] powerUpBoxAllCounts =
        sampleSearch.getDirectionCounts(myLocation, new PowerUpBoxCountCondition());
    sampleSearch = new SampleSearch(AVOID_DEPTH, map);
    int[] powerUpBoxAvoidCounts =
        sampleSearch.getDirectionCounts(myLocation, new PowerUpBoxCountCondition());

    int[] totals = {0, 0, 0, 0};
    for (int i = 0; i < powerUpBoxAllCounts.length; i++) {
      totals[i] = (powerUpBoxAvoidCounts[i] == 0) ? powerUpBoxAllCounts[i] : 0;
    }

    return maxDirection(totals);
  }

  /**
   * Determines which direction has the highest preference and returns it.
   *
   * @param totals The array of values representing all directions for the conditions specified
   * @return The direction with the highest associated value
   * @author Lewis Ackroyd
   */
  private Direction maxDirection(int[] totals) {
    int firstTwoIndex;
    if (totals[0] > totals[1]) {
      firstTwoIndex = 0;
    } else {
      firstTwoIndex = 1;
    }
    int secondTwoIndex;
    if (totals[2] > totals[3]) {
      secondTwoIndex = 2;
    } else {
      secondTwoIndex = 3;
    }
    if (totals[firstTwoIndex] > totals[secondTwoIndex]) {
      return Direction.fromInt(firstTwoIndex);
    } else {
      return Direction.fromInt(secondTwoIndex);
    }
  }
}
