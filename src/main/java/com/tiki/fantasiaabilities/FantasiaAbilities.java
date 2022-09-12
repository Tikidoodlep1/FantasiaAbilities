package com.tiki.fantasiaabilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tiki.fantasiaabilities.data.capabilities.Abilities;
import com.tiki.fantasiaabilities.data.capabilities.AbilitiesCapability;
import com.tiki.fantasiaabilities.init.KeybindingInit;
import com.tiki.fantasiaabilities.init.LootModifierInit;
import com.tiki.fantasiaabilities.network.AbilitiesPacket;
import com.tiki.fantasiaabilities.network.CSetExperiencePacket;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FantasiaAbilities.MOD_ID)
public class FantasiaAbilities {

	public static final String MOD_ID = "fantasiaabilities";
	public static final Logger LOGGER = LogManager.getLogger();
	
	public FantasiaAbilities() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        Abilities.channel.registerMessage(0, AbilitiesPacket.class, (packet, buffer) -> packet.write(buffer), (buffer) -> new AbilitiesPacket(buffer.readUUID(), buffer.readVarIntArray(), buffer.readVarIntArray()), (packet, context) -> packet.handle(context));
        Abilities.channel.registerMessage(1, CSetExperiencePacket.class, (packet, buffer) -> packet.write(buffer), (buffer) -> new CSetExperiencePacket(buffer.readInt()), (packet, context) -> packet.handle(context));
        LootModifierInit.register(eventBus);
        
        MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void setup(final FMLCommonSetupEvent event){
		AbilitiesCapability.register();
    }
    
    private void clientSetup(final FMLClientSetupEvent event) {
    	KeybindingInit.register(event);
    }
}
