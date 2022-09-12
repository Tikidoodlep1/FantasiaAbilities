package com.tiki.fantasiaabilities.init;

import com.tiki.fantasiaabilities.FantasiaAbilities;
import com.tiki.fantasiaabilities.loot.PassiveLuckMod;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LootModifierInit {

	public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, FantasiaAbilities.MOD_ID);
	
	RegistryObject<PassiveLuckMod.Serializer> LUCK_MOD = LOOT_MODS.register("passive_luck", () -> new PassiveLuckMod.Serializer());
	
	public static void register(IEventBus bus) {
		LOOT_MODS.register(bus);
	}
}
