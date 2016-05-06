package com.github.tiwindetea.dungeonoflegend.events.players;

/**
 * Created by maxime on 5/5/16.
 */
public class PlayerStatEvent extends PlayerEvent {
	public enum StatType {
		HEALTH,
		MANA
	}

	public enum ValueType {
		ACTUAL,
		MAX
	}

	public StatType statType;
	public ValueType valueType;
	public int value;

	public PlayerStatEvent(int playerNumber, StatType statType, ValueType valueType, int value) {
		super(playerNumber);
		this.statType = statType;
		this.valueType = valueType;
		this.value = value;
	}
}
