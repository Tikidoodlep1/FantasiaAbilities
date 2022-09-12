package com.tiki.fantasiaabilities.network;

import java.io.IOException;
import java.util.function.Supplier;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CSetExperiencePacket {

	private int exp;
	
	public CSetExperiencePacket(int exp) {
		this.exp = exp;
	}
	
	public void write(PacketBuffer buffer) {
		buffer.writeInt(exp);
	}
	
	public void read(PacketBuffer buffer) throws IOException {
		exp = buffer.readInt();
	}
	
	public void handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			if(player != null) {
				//player.setExperiencePoints(player.totalExperience + this.exp);
				//player.totalExperience += this.exp;
				//player.experienceProgress = player.totalExperience/player.getXpNeededForNextLevel();
				player.giveExperiencePoints(this.exp);
			}
		});
		context.setPacketHandled(true);
	}
}
