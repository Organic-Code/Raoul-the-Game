package com.github.tiwindetea.raoulthegame.model.spells;

import com.github.tiwindetea.raoulthegame.model.Descriptable;
import com.github.tiwindetea.raoulthegame.model.livings.LivingThing;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.lang.ref.WeakReference;
import java.util.Collection;

/**
 * The Spell class.
 * This class should be extended when you want to describe a new spell for a LivingThing
 *
 * @author Lucas LAZARE
 */
public abstract class Spell implements Descriptable {

    private WeakReference<LivingThing> owner = null;
    protected int targetNumber;
    protected int range;
    protected int secondaryRange = 0; // for aoe spells
    private String description;

    /**
     * Instantiates a new Spell.
     *
     * @param owner          the spell's owner
     * @param targetNumber   the maximum number of target this spell can be used on. If set to 0, all LivingThing within range are passed as targets when the spell is casted
     * @param range          the cast range of this spell
     * @param secondaryRange the aoe range of this spell
     * @param description    the description of this spell
     */
    public Spell(LivingThing owner, int targetNumber, int range, int secondaryRange, String description) {
        this.owner = new WeakReference<>(owner);
        this.targetNumber = targetNumber;
        this.range = range;
        this.secondaryRange = secondaryRange;
        this.description = description;
    }

    /**
     * @return true if this spell is an aoe spell, false otherwise
     */
    public abstract boolean isAOE();

    public abstract boolean isPassive();

    /**
     * @return the owner of this spell
     */
    protected LivingThing getOwner() {
        return this.owner.get();
    }

    /**
     * @return the range of this spell
     */
    public int getRange() {
        return this.range;
    }

    /**
     * @return the range of the aoe effect
     */
    public int getSecondaryRange() {
        return this.secondaryRange;
    }

    /**
     * getter for the number of targets this spell can hold.
     *
     * @return the number of targets this spell can hold
     */
    public int getTargetNumber() {
        return this.targetNumber;
    }

    /**
     * Handler. This function should be called each time the spell's owner is attacked
     *
     * @param source the source of the damages
     */
    public abstract void ownerDamaged(@Nullable LivingThing source);

    /**
     * Handler. This function should be called time the spell's owner is attacking
     *
     * @param target the damages' target
     */
    public abstract void ownerAttacking(@NotNull LivingThing target);

    /**
     * Update the spell's stats.
     *
     * @param targetNumber the maximum number of target this spell can be used on. If set to 0, all LivingThing within range are passed as targets when the spell is casted
     * @param range        the new range of this spell
     * @param description  the new description of this spell
     */
    protected void updateStats(int targetNumber, int range, int secondaryRange, String description) {
        this.targetNumber = targetNumber;
        this.range = range;
        this.secondaryRange = secondaryRange;
        this.description = description;
    }

    /**
     * Handler. Updates the spell. This function should be called once and only once each turn.
     *
     * @param targets the new potential targets of this spell (empty if the spell isn't an AOE spell)
     */
    public abstract void update(Collection<LivingThing> targets);

    /**
     * Handler. This function should be called each time the spell's owner gained a level.
     */
    public abstract void nextOwnerLevel();

    /**
     * Handler. This function should be called each time the spell gained a level.
     */
    public abstract void nextSpellLevel();

    /**
     * Method called whenever the spell is casted on a LivingThing // a group of LivingThing(s)
     *
     * @param targets the potential targets of this spell (from 0 to targetNumber if the spell isn't an aoe spell)
     * @return true if the spell was successfully casted, false otherwise
     */
    public abstract boolean cast(Collection<LivingThing> targets);

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return this.description;
    }
}
