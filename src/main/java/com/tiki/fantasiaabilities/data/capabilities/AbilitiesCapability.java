package com.tiki.fantasiaabilities.data.capabilities;

import java.util.Arrays;

import com.tiki.fantasiaabilities.FantasiaAbilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class AbilitiesCapability {

	public static Capability<IAbilities> ABILITIES = null;
	public static final Direction DEFAULT_FACING = null;
	public static final ResourceLocation ID = new ResourceLocation(FantasiaAbilities.MOD_ID, "skills");
	
	@CapabilityInject(IAbilities.class)
	private static void onAbilitiesInit(Capability<IAbilities> cap) {
		ABILITIES = cap;
	}
	
	public static void register() {
		CapabilityManager.INSTANCE.register(IAbilities.class, new Capability.IStorage<IAbilities>() {
			
			@Override
			public INBT writeNBT(Capability<IAbilities> capability, IAbilities instance, Direction side) {
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
			public void readNBT(Capability<IAbilities> capability, IAbilities instance, Direction side, INBT nbt) {
				IntArrayNBT tag = (IntArrayNBT)nbt;
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
		}, () -> new Abilities());
	}
	
	public static LazyOptional<IAbilities> get(final PlayerEntity entity) {
		return entity.getCapability(ABILITIES, DEFAULT_FACING);
	}
	
	public static IAbilities getAbilities(final PlayerEntity entity) {
		return entity.getCapability(ABILITIES).orElseThrow(() -> new IllegalArgumentException("Player " + entity.getName().getContents() + " does not have the abilities capability!"));
	}
	
	public static ICapabilityProvider createProvider(final IAbilities abilities) {
		return new AbilitiesProvider(ABILITIES, DEFAULT_FACING, abilities);
	}
	
	public static String formatAbility(final int ability) {
		return ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(ability);
	}
	
	
}
