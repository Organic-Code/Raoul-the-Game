package com.github.tiwindetea.dungeonoflegend;

/**
 * Created by maxime on 4/23/16.
 */
public interface Consumable {

	void trigger(LivingThing livingThing);

	boolean nextTick();

	ConsumableType getConsumableType();
}
