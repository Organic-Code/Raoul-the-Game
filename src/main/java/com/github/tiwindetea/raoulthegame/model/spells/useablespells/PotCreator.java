//////////////////////////////////////////////////////////////////////////////////
//                                                                              //
//     This Source Code Form is subject to the terms of the Mozilla Public      //
//     License, v. 2.0. If a copy of the MPL was not distributed with this      //
//     file, You can obtain one at http://mozilla.org/MPL/2.0/.                 //
//                                                                              //
//////////////////////////////////////////////////////////////////////////////////

package com.github.tiwindetea.raoulthegame.model.spells.useablespells;

import com.github.tiwindetea.raoulthegame.events.game.spells.SpellCooldownUpdateEvent;
import com.github.tiwindetea.raoulthegame.events.game.spells.SpellCreationEvent;
import com.github.tiwindetea.raoulthegame.events.game.spells.SpellDeletionEvent;
import com.github.tiwindetea.raoulthegame.events.game.spells.SpellDescriptionUpdateEvent;
import com.github.tiwindetea.raoulthegame.model.Pair;
import com.github.tiwindetea.raoulthegame.model.items.Pot;
import com.github.tiwindetea.raoulthegame.model.livings.LivingThing;
import com.github.tiwindetea.raoulthegame.model.livings.Player;
import com.github.tiwindetea.raoulthegame.model.space.Vector2i;
import com.github.tiwindetea.raoulthegame.model.spells.Spell;
import com.github.tiwindetea.raoulthegame.view.entities.SpellType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Random;

/**
 * The type PotCreator.
 *
 * @author Maxime PINARD
 */
public class PotCreator extends Spell<Player> {

	private static final int BASE_COOLDOWN = 20;

	private int baseCooldown = BASE_COOLDOWN;
	private int cooldown = 0;
	private double manaCost = 5;

	private Random random = new Random();
	private int heal = 100;
	private int mana_heal = 40;

	/**
	 * Instantiates a new PotCreator.
	 *
	 * @param owner the owner
	 */
	public PotCreator(Player owner) {
		super(owner, owner.getSpells().size());
		updateDescription();
		fire(new SpellCreationEvent(
		  owner.getNumber(),
		  this.id,
		  getSpellType(),
		  this.baseCooldown,
		  this.description
		));
	}

	@Override
	public boolean isAOE() {
		return false;
	}

	@Override
	public boolean isPassive() {
		return false;
	}

	@Override
	public Vector2i getSpellSource() {
		return null;
	}

	@Override
	public double ownerDamaged(@Nullable LivingThing source, double damages) {
		return 0;
	}

	@Override
	public double ownerAttacking(@Nonnull LivingThing target) {
		return 0;
	}

	@Override
	public void update(Collection<LivingThing> targets) {
		if(this.cooldown > 0) {
			--this.cooldown;
		}

		Player owner = getOwner();
		if(owner != null) {
			fire(new SpellCooldownUpdateEvent(
			  owner.getNumber(),
			  this.id,
			  this.baseCooldown,
			  this.cooldown
			));
		}
	}

	@Override
	public void nextOwnerLevel() {

	}

	@Override
	public void spellUpgraded() {
		this.heal += 20 + this.random.nextInt(20);
		this.mana_heal += 10 + this.random.nextInt(10);
		updateDescription();
		Player owner = getOwner();
		if(owner != null) {
			fire(new SpellDescriptionUpdateEvent(
			  owner.getNumber(),
			  this.id,
			  this.description
			));
		}
	}

	@Override
	public boolean cast(Collection<LivingThing> targets, Vector2i sourcePosition) {
		if(this.cooldown == 0) {
			Player owner = getOwner();
			if(owner != null && owner.useMana(this.manaCost)) {
				int turns = this.random.nextInt(10);
				Pot pot;
				if(this.random.nextBoolean()) {
					pot = new Pot(turns, this.heal / (double) turns, 0, 0, 0, 0, 0);
				}
				else {
					pot = new Pot(turns, 0, this.mana_heal / (double) turns, 0, 0, 0, 0);
				}
				owner.addToInventory(new Pair<>(pot));
				this.cooldown = this.baseCooldown;
				fire(new SpellCooldownUpdateEvent(
				  owner.getNumber(),
				  this.id,
				  this.baseCooldown,
				  this.cooldown
				));
				return true;
			}
		}
		return false;
	}

	@Override
	public void nextFloor() {

	}

	@Override
	protected void forgotten() {
		Player owner = getOwner();
		if(owner != null) {
			fire(new SpellDeletionEvent(
			  owner.getNumber(),
			  this.id
			));
		}
	}

	@Override
	public SpellType getSpellType() {
		return SpellType.POT_CREATOR;
	}

	private void updateDescription() {
		this.description = "Pot Creator (active)\n" +
		  "Randomly create a pot.\n\n" +
		  "Cost: " + this.manaCost + " mana.";
	}
}
