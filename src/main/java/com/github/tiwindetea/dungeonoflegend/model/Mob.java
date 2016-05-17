//////////////////////////////////////////////////////////////////////////////////
//                                                                              //
//     This Source Code Form is subject to the terms of the Mozilla Public      //
//     License, v. 2.0. If a copy of the MPL was not distributed with this      //
//     file, You can obtain one at http://mozilla.org/MPL/2.0/.                 //
//                                                                              //
//////////////////////////////////////////////////////////////////////////////////

package com.github.tiwindetea.dungeonoflegend.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Mob.
 *
 * @author Lucas LAZARE
 */
public class Mob extends LivingThing {
	// Mobs know the map perfectly.
	private static Map map;
	private State state;
	private Vector2i requestedPath;
	private Stack<Vector2i> requestedPathStack = new Stack<>();
	private int chaseRange;

	/**
	 * Sets the map.
	 *
	 * @param map the map
	 */
	public static void setMap(Map map) {
		Mob.map = map;
	}

	/**
	 * Instantiates a new Mob. (for comparison purposes)
	 *
	 * @param pos the pos
	 */
	public Mob(Vector2i pos) {
		this.position = pos;
	}

	/**
	 * Instantiates a new Mob.
	 *
	 * @param level        level
	 * @param maxHitPoints max hit points
	 * @param attackPower  attack power
	 * @param defensePower defense power
	 * @param position     position
	 */
	public Mob(int level, int maxHitPoints, int attackPower, int defensePower, int chaseRange, Vector2i position) {
		this.level = level;
		this.maxHitPoints = maxHitPoints;
		this.attackPower = attackPower;
		this.defensePower = defensePower;
		this.chaseRange = chaseRange;
		this.position = position;
		this.hitPoints = maxHitPoints;
	}

	private Tile map(Vector2i pos) {
		return Mob.map.getTile(pos);
	}

	private void keepPatroling() {
		Direction[] directions = {Direction.DOWN, Direction.LEFT, Direction.UP, Direction.RIGHT};
		int index;
		/* Looking for a wall to follow */
		for (index = 0; !Tile.isRoomBorder(map(this.position.copy().add(directions[index])))
				&& index < directions.length; ++index)
			;

		/* If you are in a corner (twice for corridors)*/
		int count = 0;
		while (Tile.isRoomBorder(map(this.position.copy().add(directions[(index + 1) % directions.length]))) && count++ < 2) {
			index = (index + 1) % directions.length;
		}

		/* Follow the wall, or try to find one */
		Vector2i next = this.position.copy().add(directions[(index + 1) % 5]);
		if (!Tile.isObstructed(map(next))) {
			this.requestedPath = next;
		} else {
			/* Look for the a non-obstructed tile, ignoring living entities */
			for (index = 0; Tile.isObstructed(map(this.position.copy().add(directions[index]))) && index < directions.length; ++index)
				;
			next = this.position.copy().add(directions[index]);
			if (!Tile.isObstructed(map(next))) {
				this.requestedPath = next;
			} else {
				this.requestedPath = next;
			}
		}
	}

	private void chase(Collection<LivingThing> collisionsEntities, Player player) {
		this.requestedPathStack = map.getPath(this.position, player.getPosition(), false, collisionsEntities);
	}

	private void wander() {
		Direction[] directions = {Direction.DOWN, Direction.LEFT, Direction.UP, Direction.DOWN};
		ArrayList<Direction> possibleDirs = new ArrayList<>(4);
		for (Direction direction : directions) {
			if (!Tile.isObstructed(map(this.position.copy().add(direction)))) {
				possibleDirs.add(direction);
			}
		}

		if (possibleDirs.size() > 0)
			this.requestedPath = this.position.copy().add(possibleDirs.get(new Random().nextInt(possibleDirs.size())));
		else
			this.requestedPath = this.position.copy();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		/* Asserting o to be a mob */
		return ((Mob) o).position.equals(this.position);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void live(Collection<Pair<Mob>> mobs, Collection<Pair<Player>> players, boolean[][] los) {
		int distance = this.chaseRange + 1;
		Collection<LivingThing> shadow = new ArrayList<>();
		Player chasedPlayer = null;
		shadow.addAll(mobs.stream().map(mob -> mob.object).collect(Collectors.toList()));


		for (Pair<Player> player : players) {
			Vector2i pos = player.object.getPosition();
			int dist = map.getPath(this.position, pos, false, shadow).size();
			// if the player is in our LOS
			if (los[this.position.x + los.length - pos.x][this.position.y + los[0].length - pos.y]) {
				if (distance > dist) {
					chasedPlayer = player.object;
					distance = dist;
				}
			}
		}
		if (distance <= this.chaseRange) {
			this.chase(shadow, chasedPlayer);
			this.state = State.CHASING;
		} else if (this.requestedPathStack.isEmpty()) {

			/* Let's do something at random, if I can't chase anyone */
			switch (this.state) {
				case PATROLING:
					this.keepPatroling();
					switch (new Random().nextInt(20)) {
						case 0:
							this.state = State.STANDING;
							break;
						case 1:
							this.state = State.WANDERING;
							break;
						default:
					}
					break;
				case STANDING:
					switch (new Random().nextInt(4)) {
						case 0:
							this.state = State.PATROLING;
							break;
						case 1:
							this.state = State.WANDERING;
							break;
						default:
					}
					break;
				case SLEEPING:
					switch (new Random().nextInt(3)) {
						case 0:
							this.state = State.STANDING;
							break;
						default:
					}
					break;
				case WANDERING:
					wander();
					switch (new Random().nextInt(20)) {
						case 0:
							this.state = State.PATROLING;
							break;
						case 1:
							this.state = State.STANDING;
							break;
						default:
					}
					this.wander();
					break;
				default:
					wander();
					this.state = State.WANDERING;
					break;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2i getRequestedMove() {
		if (this.requestedPathStack.size() > 0) {
			return this.requestedPathStack.pop();
		}
		return this.requestedPath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void attack(LivingThing target) {
		target.damage(this.attackPower);
	}

	@Override
	public LivingThingType getType() {
		return LivingThingType.MOB;
	}

	public int getChaseRange() {
		return this.chaseRange;
	}
	@Override
	public String getDescription() {
		return this.name + " (Lv" + this.level + ".)";
	}
}
