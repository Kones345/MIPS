package ai.routefinding;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Random;
import objects.Entity;
import utils.enums.Direction;

public class RandomRouteFinder implements RouteFinder {
	private static final Random R = new Random();
	private static final Direction DEFAULT = Direction.UP;
	private Entity[] gameAgents;
	private int myId;
	private boolean agentsSet;

	/**
	 * Creates an instance of this routeFinder. As it is random the map is
	 * irrelevant.
	 */
	public RandomRouteFinder() {
		this.agentsSet = false;
	}

	/**
	 * Set all agents in the game for reference and set which of those agents the
	 * current route is being generated for.
	 * 
	 * @param gameAgents
	 *            The array containing all agents within the game exactly once only.
	 * @param myId
	 *            The enum referring to the current agent for which this routefinder
	 *            is being assigned.
	 * @throws IllegalArgumentException gameAgent cannot contain duplicate IDs.
	 * @throws IllegalStateException
	 *             The game agents cannot be re-assigned (this method can only be
	 *             called once).
	 */
	public void setAgents(Entity[] gameAgents, int myId) {
		HashSet<Integer> ids = new HashSet<Integer>();
		for (Entity e : gameAgents) {
			if (!ids.add(e.getClientId())) {
				throw new IllegalArgumentException("gameAgent array contains duplicate IDs.");
			}
		}
		if (this.agentsSet) {
			throw new IllegalStateException("gameAgents already assigned.");
		}
		this.gameAgents = gameAgents;
		this.myId = myId;
		this.agentsSet = true;
	}

	/**
	 * Returns the direction to travel in until the next junction is reached.
	 * Requires {@link #setAgents(Entity[], EntityType) setAgents()} method to have
	 * been called before use.
	 * 
	 * @return The direction to travel in.
	 * @throws IllegalStateException
	 *             The gameAgents have not been set. Call
	 *             {@link #setAgents(Entity[], EntityType) setAgents()} before
	 *             calling this method.
	 */
	@Override
	public Direction getRoute() {
		if (!agentsSet) {
			throw new IllegalStateException("gameAgents have not been set.");
		}
		Direction dir;
		int dirValue = R.nextInt(6);
		switch (dirValue) {
		case 0: {
			dir = Direction.UP;
			break;
		}
		case 1: {
			dir = Direction.DOWN;
			break;
		}
		case 2: {
			dir = Direction.LEFT;
			break;
		}
		case 3: {
			dir = Direction.RIGHT;
			break;
		}
		case 4: {
			Point2D.Double mmanPos = gameAgents[0].getLocation();
			Point2D.Double myPos = gameAgents[myId].getLocation();
			if (myPos.getY() > mmanPos.getY()) {
				dir = Direction.UP;
			} else {
				dir = Direction.DOWN;
			}
			break;
		}
		case 5: {
			Point2D.Double mmanPos = gameAgents[0].getLocation();
			Point2D.Double myPos = gameAgents[myId].getLocation();
			if (myPos.getX() > mmanPos.getX()) {
				dir = Direction.LEFT;
			} else {
				dir = Direction.RIGHT;
			}
			break;
		}
		default: {
			System.err.println("Value out of range. Default value given: " + DEFAULT);
			dir = DEFAULT;
		}
		}
		return dir;
	}
}
