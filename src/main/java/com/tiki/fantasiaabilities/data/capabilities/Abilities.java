package com.tiki.fantasiaabilities.data.capabilities;

import java.util.Arrays;
import java.util.UUID;

import com.tiki.fantasiaabilities.FantasiaAbilities;
import com.tiki.fantasiaabilities.network.AbilitiesPacket;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.PacketDistributor.TargetPoint;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Abilities implements IAbilities {
	
	public static final int MINING = 0;
	public static final int CHOPPING = 1;
	public static final int DIGGING = 2;
	public static final int COMBAT = 3;
	public static final int DEFENSE = 4;
	public static final int ARCHERY = 5;
	public static final int AGILITY = 6;
	public static final int MAGIC = 7;
	public static final int SURVIVAL = 8;
	public static final int FARMING = 9;
	public static final int CHARISMA = 10;
	public static final int BODY = 11;
	public static final int LUCK = 12;
	public static final int CREATION = 13;
	public static final int TALENT = 14;
	
	protected static final UUID uuid = UUID.fromString("7ec8e601-2466-4bac-b314-a1111a203088");
	protected static final String name = "Skills";
	private int mining;
	private int digging;
	private int chopping;
	private int defense;
	private int combat;
	private int archery;
	private int agility;
	private int magic;
	private int farming;
	private int charisma;
	private int talent;
	private int luck;
	private int body;
	private int survival;
	private int creation;
	private boolean[] unlockedAbilities;
	private int[] enabledAbilities = new int[15];
	private int enabledIndex = 0;
	//private int[] abilityArray = {survival, mining, creation, luck, talent, body, digging, magic, farming, agility, combat, defense, chopping, charisma, archery};
	public static final SimpleChannel channel = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(FantasiaAbilities.MOD_ID, "main_channel"))
			.clientAcceptedVersions("1"::equals)
			.serverAcceptedVersions("1"::equals)
			.networkProtocolVersion(() -> "1")
			.simpleChannel();
	public static int index = 0;
	
	public Abilities() {
		mining = 0;
		digging = 0;
		chopping = 0;
		defense = 0;
		combat = 0;
		archery = 0;
		agility = 0;
		magic = 0;
		farming = 0;
		charisma = 0;
		talent = 0;
		luck = 0;
		body = 0;
		survival = 0;
		creation = 0;
		unlockedAbilities = new boolean[75];
		Arrays.fill(enabledAbilities, -1);
	}

	@Override
	public int getMining() {
		return mining;
	}

	@Override
	public int getDigging() {
		return digging;
	}

	@Override
	public int getChopping() {
		return chopping;
	}

	@Override
	public int getDefense() {
		return defense;
	}

	@Override
	public int getCombat() {
		return combat;
	}

	@Override
	public int getArchery() {
		return archery;
	}

	@Override
	public int getAgility() {
		return agility;
	}

	@Override
	public int getMagic() {
		return magic;
	}

	@Override
	public int getFarming() {
		return farming;
	}

	@Override
	public int getCharisma() {
		return charisma;
	}

	@Override
	public int getTalent() {
		return talent;
	}

	@Override
	public int getLuck() {
		return luck;
	}

	@Override
	public int getBody() {
		return body;
	}
	
	@Override
	public int getSurvival() {
		return survival;
	}

	@Override
	public int getCreation() {
		return creation;
	}

	@Override
	public void setMining(final int m) {
		mining = m;
	}

	@Override
	public void setDigging(final int d) {
		digging = d;
	}

	@Override
	public void setChopping(final int c) {
		chopping = c;
	}

	@Override
	public void setDefense(final int r) {
		defense = r;
	}

	@Override
	public void setCombat(final int s) {
		combat = s;
	}

	@Override
	public void setArchery(final int a) {
		archery = a;
	}

	@Override
	public void setAgility(final int a) {
		agility = a;
	}

	@Override
	public void setMagic(final int m) {
		magic = m;
	}

	@Override
	public void setFarming(final int f) {
		farming = f;
	}

	@Override
	public void setCharisma(final int c) {
		charisma = c;
	}

	@Override
	public void setTalent(final int t) {
		talent = t;
	}

	@Override
	public void setLuck(final int l) {
		luck = l;
	}

	@Override
	public void setBody(final int b) {
		body = b;
	}
	
	@Override
	public void setSurvival(int s) {
		survival = s;
	}

	@Override
	public void setCreation(int c) {
		creation = c;
	}
	
	@Override
	public void setAbilityUnlocked(int index) {
		this.unlockedAbilities[index] = true;
	}
	
	@Override
	public boolean[] getUnlockedAbilitiesSimple() {
		return this.unlockedAbilities;
	}
	
	@Override
	public int[] getEnabledAbilities() {
		return this.enabledAbilities;
	}
	
	@Override
	public boolean getAbilityEnabled(int ability, int skill) {
		int ind = Arrays.binarySearch(this.enabledAbilities, (5*ability)+skill);
		if(enabledAbilities[ind] == (5*ability)+skill) {
			return true;
		}
		return false;
	}

	@Override
	public boolean setAbilityEnabled(int ability, int skill) {
		System.out.println("Enabled index: " + this.enabledIndex);
		if(this.enabledIndex > 14 || this.enabledIndex < 0) {
			return false;
		}
		if(this.enabledIndex == 0) {
			this.enabledAbilities[this.enabledIndex++] = (5*ability)+skill;
		}else {
			int i = 1;
			while(i < 15 && this.enabledAbilities[this.enabledIndex-i] > (5*ability)+skill) {
				int temp = this.enabledAbilities[this.enabledIndex-i];
				this.enabledAbilities[this.enabledIndex-i] = (5*ability)+skill;
				this.enabledAbilities[this.enabledIndex-(i+1)] = temp;
				i++;
			}
			this.enabledIndex++;
		}
		System.out.println("Enabled Abilities: " + Arrays.toString(this.enabledAbilities));
		return true;
	}
	
	@Override
	public void setAbilityDisabled(int ability, int skill) {
		System.out.println("indices: " + ability + ", " + skill + ", " + ((5*ability)+skill));
		int ind = Arrays.binarySearch(this.enabledAbilities, (5*ability)+skill);
		if(ind >= 0) {
			this.enabledAbilities[ind] = -1;
		}
//		else {
//			throw new IndexOutOfBoundsException("Ability was not contained in the Enabled Abilities Array!");
//		}
	}
	
	@Override
	public void setAbility(int ability, int val) {
		switch(ability) {
		case SURVIVAL:
			setSurvival(val);
			break;
		case MINING:
			setMining(val);
			break;
		case CREATION:
			setCreation(val);
			break;
		case LUCK:
			setLuck(val);
			break;
		case TALENT:
			setTalent(val);
			break;
		case BODY:
			setBody(val);
			break;
		case DIGGING:
			setDigging(val);
			break;
		case MAGIC:
			setMagic(val);
			break;
		case FARMING:
			setFarming(val);
			break;
		case AGILITY:
			setAgility(val);
			break;
		case COMBAT:
			setCombat(val);
			break;
		case DEFENSE:
			setDefense(val);
			break;
		case CHOPPING:
			setChopping(val);
			break;
		case CHARISMA:
			setCharisma(val);
			break;
		case ARCHERY:
			setArchery(val);
			break;
		}
	}

	@Override
	public void synchronise(UUID uuid, int[] abilities, int[] enabled) {
		channel.sendToServer(new AbilitiesPacket(uuid, abilities, enabled));
	}
	
	@Override
	public void synchroniseClient(UUID uuid, int[] abilities, int[] enabled, LazyOptional<PlayerEntity> lazy) {
		lazy.ifPresent((player) -> {
			if(player instanceof ServerPlayerEntity) {
				ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
				channel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new AbilitiesPacket(uuid, abilities, enabled));
			}else {
				TargetPoint tp = new TargetPoint(player.getX(), player.getY(), player.getZ(), 3, player.level.dimension());
				channel.send(PacketDistributor.NEAR.with(() -> tp), new AbilitiesPacket(uuid, abilities, enabled));
			}
			
		});
	}
}
