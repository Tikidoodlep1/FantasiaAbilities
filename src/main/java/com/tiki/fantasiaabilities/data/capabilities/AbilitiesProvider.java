package com.tiki.fantasiaabilities.data.capabilities;

import java.util.Arrays;

import javax.annotation.Nullable;

import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class AbilitiesProvider implements ICapabilitySerializable<IntArrayNBT> {

	protected final Capability<IAbilities> capability;
	protected final Direction facing;
	protected final IAbilities instance;
	protected final LazyOptional<IAbilities> lazy;
	
	public AbilitiesProvider(final Capability<IAbilities> capability, @Nullable final Direction facing, @Nullable final IAbilities instance) {
		this.capability = capability;
		this.facing = facing;
		this.instance = instance;
		
		if(this.instance != null) {
			lazy = LazyOptional.of(() -> this.instance);
		}else {
			lazy = LazyOptional.empty();
		}
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return capability.orEmpty(cap, lazy);
	}

	@Override
	public IntArrayNBT serializeNBT() {
		int[] enabled = instance.getEnabledAbilities();
		int[] abilities = instance.getAbilityArray();
		int[] abilitiesAndEnabled = Arrays.copyOf(abilities, abilities.length - 1 + 15);
		for(int i = abilities.length - 1; i < abilitiesAndEnabled.length; i++) {
			System.out.println("Writing enabled Abilities, Index: " + (i-(abilities.length-1)) + ", Enabled: " + enabled[i - (abilities.length-1)]);
			abilitiesAndEnabled[i] = enabled[i-(abilities.length-1)];
		}
		IntArrayNBT tag = new IntArrayNBT(abilitiesAndEnabled);
		return tag;
	}

	@Override
	public void deserializeNBT(IntArrayNBT tag) {
		instance.setSurvival(tag.get(Abilities.SURVIVAL).getAsInt());
		instance.setMining(tag.get(Abilities.MINING).getAsInt());
		instance.setCreation(tag.get(Abilities.CREATION).getAsInt());
		instance.setLuck(tag.get(Abilities.LUCK).getAsInt());
		instance.setTalent(tag.get(Abilities.TALENT).getAsInt());
		instance.setBody(tag.get(Abilities.BODY).getAsInt());
		instance.setDigging(tag.get(Abilities.DIGGING).getAsInt());
		instance.setMagic(tag.get(Abilities.MAGIC).getAsInt());
		instance.setFarming(tag.get(Abilities.FARMING).getAsInt());
		instance.setAgility(tag.get(Abilities.AGILITY).getAsInt());
		instance.setCombat(tag.get(Abilities.COMBAT).getAsInt());
		instance.setDefense(tag.get(Abilities.DEFENSE).getAsInt());
		instance.setChopping(tag.get(Abilities.CHOPPING).getAsInt());
		instance.setCharisma(tag.get(Abilities.CHARISMA).getAsInt());
		instance.setArchery(tag.get(Abilities.ARCHERY).getAsInt());
		for(int i = 0; i < 15; i++) {
			int heapCode = tag.get(i + 14).getAsInt();
			System.out.println("Reading enabled Abilities, Ability: " + heapCode/5 + ", Skill: " + heapCode%5);
			if(heapCode > 0) {
				instance.setAbilityEnabled((heapCode/5), heapCode%5);
			}
		}
		boolean[] ab = instance.getUnlockedAbilities();
		for(int i = 0; i < ab.length; i++) {
			if(ab[i]) {
				instance.setAbilityUnlocked(i);
			}
		}
	}

}
