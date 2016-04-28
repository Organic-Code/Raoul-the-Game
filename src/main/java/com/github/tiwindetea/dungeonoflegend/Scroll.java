package com.github.tiwindetea.dungeonoflegend;

/**
 * Created by maxime on 4/23/16.
 */
public class Scroll implements Consumable {

	private byte turns;
	private int healthModifierPerTick;
	private int healthModifierModifierPerTick;

	public Scroll(byte turns, int healthModifierPerTick, int healthModifierModifierPerTick) {
		this.turns = turns;
		this.healthModifierPerTick = healthModifierPerTick;
		this.healthModifierModifierPerTick = healthModifierModifierPerTick;
	}

	public int getHMPT() {
		return this.healthModifierModifierPerTick;
	}

	public int getHMMPT() {
		return this.healthModifierModifierPerTick;
	}

	@Override
	public void trigger(LivingThing livingThing) {
		//TODO
	}

	@Override
	public boolean nextTick() {
		//TODO
		return false;
	}

	@Override
	public ConsumableType getConsumableType() {
		//TODO
		return null;
	}
}
