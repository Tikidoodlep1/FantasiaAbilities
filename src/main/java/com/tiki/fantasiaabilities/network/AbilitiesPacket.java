package com.tiki.fantasiaabilities.network;

import java.io.IOException;
import java.util.UUID;
import java.util.function.Supplier;

import com.tiki.fantasiaabilities.data.capabilities.Abilities;
import com.tiki.fantasiaabilities.data.capabilities.AbilitiesCapability;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class AbilitiesPacket {

	private int[] enabled;
	private int[] abilities;
	private UUID id;
	
	public AbilitiesPacket(UUID id, int[] abilities, int[] enabled) {
		this.id = id;
		this.abilities = abilities;
		this.enabled = enabled;
	}
	
	public AbilitiesPacket(UUID id, int abilityToSet, int value, int[] abilities, int[] enabled) {
		this.id = id;
		this.abilities = abilities;
		this.abilities[abilityToSet] = value;
		this.enabled = enabled;
	}
	
	public void write(PacketBuffer buffer) {
		buffer.writeUUID(id);
		buffer.writeVarIntArray(abilities);
		buffer.writeVarIntArray(enabled);
	}
	
	public void read(PacketBuffer buffer) throws IOException {
		id = buffer.readUUID();
		abilities = buffer.readVarIntArray();
		enabled = buffer.readVarIntArray();
	}
	
	public void handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		Minecraft mc = Minecraft.getInstance();
		context.enqueueWork(() -> {
			ServerPlayerEntity entity = context.getSender();
			if(entity != null) {
				entity.getCapability(AbilitiesCapability.ABILITIES).ifPresent((cap) -> {
					cap.setSurvival(abilities[Abilities.SURVIVAL]);
					cap.setMining(abilities[Abilities.MINING]);
					cap.setCreation(abilities[Abilities.CREATION]);
					cap.setLuck(abilities[Abilities.LUCK]);
					cap.setTalent(abilities[Abilities.TALENT]);
					cap.setBody(abilities[Abilities.BODY]);
					cap.setDigging(abilities[Abilities.DIGGING]);
					cap.setMagic(abilities[Abilities.MAGIC]);
					cap.setFarming(abilities[Abilities.FARMING]);
					cap.setAgility(abilities[Abilities.AGILITY]);
					cap.setCombat(abilities[Abilities.COMBAT]);
					cap.setDefense(abilities[Abilities.DEFENSE]);
					cap.setChopping(abilities[Abilities.CHOPPING]);
					cap.setCharisma(abilities[Abilities.CHARISMA]);
					cap.setArchery(abilities[Abilities.ARCHERY]);
					for(int i = 0; i < enabled.length; i++) {
						cap.setAbilityEnabled(enabled[i]/5, enabled[i]%5);
					}
					boolean[] ab = cap.getUnlockedAbilities();
					for(int i = 0; i < ab.length; i++) {
						if(ab[i]) {
							cap.setAbilityUnlocked(i);
						}
					}
				});
			}
			ClientWorld level = mc.level;
			PlayerEntity player = level.getPlayerByUUID(id);
			if(player != null) {
				player.getCapability(AbilitiesCapability.ABILITIES).ifPresent((cap) -> {
					cap.setSurvival(abilities[Abilities.SURVIVAL]);
					cap.setMining(abilities[Abilities.MINING]);
					cap.setCreation(abilities[Abilities.CREATION]);
					cap.setLuck(abilities[Abilities.LUCK]);
					cap.setTalent(abilities[Abilities.TALENT]);
					cap.setBody(abilities[Abilities.BODY]);
					cap.setDigging(abilities[Abilities.DIGGING]);
					cap.setMagic(abilities[Abilities.MAGIC]);
					cap.setFarming(abilities[Abilities.FARMING]);
					cap.setAgility(abilities[Abilities.AGILITY]);
					cap.setCombat(abilities[Abilities.COMBAT]);
					cap.setDefense(abilities[Abilities.DEFENSE]);
					cap.setChopping(abilities[Abilities.CHOPPING]);
					cap.setCharisma(abilities[Abilities.CHARISMA]);
					cap.setArchery(abilities[Abilities.ARCHERY]);
					for(int i = 0; i < enabled.length; i++) {
						cap.setAbilityEnabled(enabled[i]/5, enabled[i]%5);
					}
					boolean[] ab = cap.getUnlockedAbilities();
					for(int i = 0; i < ab.length; i++) {
						if(ab[i]) {
							cap.setAbilityUnlocked(i);
						}
					}
				});
			}
		});
		context.setPacketHandled(true);
	}
}
