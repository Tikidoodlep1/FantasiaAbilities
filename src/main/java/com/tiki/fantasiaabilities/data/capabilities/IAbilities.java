package com.tiki.fantasiaabilities.data.capabilities;

import java.util.UUID;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.LazyOptional;

public interface IAbilities {

	int getMining();
	int getDigging();
	int getChopping();
	int getDefense();
	int getCombat();
	int getArchery();
	int getAgility();
	int getMagic();
	int getFarming();
	int getCharisma();
	int getTalent();
	int getLuck();
	int getBody();
	int getSurvival();
	int getCreation();
	boolean[] getUnlockedAbilitiesSimple();
	int[] getEnabledAbilities();
	boolean getAbilityEnabled(int ability, int skill);
	
	void setMining(int m);
	void setDigging(int d);
	void setChopping(int c);
	void setDefense(int r);
	void setCombat(int s);
	void setArchery(int a);
	void setAgility(int a);
	void setMagic(int m);
	void setFarming(int f);
	void setCharisma(int c);
	void setTalent(int t);
	void setLuck(int l);
	void setBody(int b);
	void setSurvival(int s);
	void setCreation(int c);
	void setAbility(int ability, int val);
	void setAbilityUnlocked(int ability);
	boolean setAbilityEnabled(int ability, int skill);
	void setAbilityDisabled(int ability, int skill);
	
	void synchronise(UUID uuid, int[] abilities, int[] enabled);
	
	void synchroniseClient(UUID uuid, int[] abilities, int[] enabled, LazyOptional<PlayerEntity> lazy);
	
	default boolean[] getUnlockedAbilities() {
		int[] arr = getAbilityArray();
		boolean[] unlocked = new boolean[75];
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i]/10; j++) {
				unlocked[(5*i)+j] = true;
			}
		}
		return unlocked;
	}
	
	default int[] getAbilityArray() {
		return new int[] {getMining(), getChopping(), getDigging(), getCombat(), getDefense(), getArchery(), getAgility(), getMagic(), getSurvival(), 
				getFarming(), getCharisma(), getBody(), getLuck(), getCreation(), getTalent()};
	}
}
