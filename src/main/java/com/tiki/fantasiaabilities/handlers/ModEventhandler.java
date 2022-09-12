package com.tiki.fantasiaabilities.handlers;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.tiki.fantasiaabilities.FantasiaAbilities;
import com.tiki.fantasiaabilities.data.capabilities.Abilities;
import com.tiki.fantasiaabilities.data.capabilities.AbilitiesCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FantasiaAbilities.MOD_ID)
public class ModEventhandler {

	@SubscribeEvent
	public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
		if(event.getObject() instanceof PlayerEntity) {
			final Abilities abilities = new Abilities();
			event.addCapability(AbilitiesCapability.ID, AbilitiesCapability.createProvider(abilities));
		}
	}
	
	@SubscribeEvent
	public static void onJoinWorld(final EntityJoinWorldEvent event) {
		if(event.getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) event.getEntity();
			if(!player.level.isClientSide) {
				AbilitiesCapability.get(player).ifPresent((cap) -> {
					cap.synchroniseClient(player.getUUID(), cap.getAbilityArray(), cap.getEnabledAbilities(), LazyOptional.of(() -> player));
					Multimap<Attribute, AttributeModifier> mods = ImmutableMultimap.of(Attributes.ARMOR, new AttributeModifier(UUID.randomUUID(), Attributes.ARMOR.getDescriptionId(), ((int)cap.getDefense()/5), Operation.ADDITION), Attributes.MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), Attributes.MAX_HEALTH.getDescriptionId(), (cap.getBody()/10), Operation.ADDITION));
					player.getAttributes().addTransientAttributeModifiers(mods);
				});
			}
		}
	}
	
	@SubscribeEvent
	public static void playerClone(final PlayerEvent.Clone event) {
		AbilitiesCapability.get(event.getOriginal()).ifPresent(oldAbilities -> {
			AbilitiesCapability.get(event.getPlayer()).ifPresent(newAbilities -> {
				newAbilities.synchronise(event.getPlayer().getUUID(), oldAbilities.getAbilityArray(), oldAbilities.getEnabledAbilities());
				Multimap<Attribute, AttributeModifier> mods = ImmutableMultimap.of(Attributes.ARMOR, new AttributeModifier(UUID.randomUUID(), Attributes.ARMOR.getDescriptionId(), ((int)oldAbilities.getDefense()/5), Operation.ADDITION), Attributes.MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), Attributes.MAX_HEALTH.getDescriptionId(), (oldAbilities.getBody()/10), Operation.ADDITION));
				event.getPlayer().getAttributes().addTransientAttributeModifiers(mods);
			});
		});
	}
	
	@SubscribeEvent
	public static void playerChangeDimension(final PlayerEvent.PlayerChangedDimensionEvent event) {
		AbilitiesCapability.get(event.getPlayer()).ifPresent((abilities) -> {
			if(event.getPlayer() instanceof ServerPlayerEntity) {
				ServerPlayerEntity serverPlayer = (ServerPlayerEntity) event.getPlayer();
				abilities.synchroniseClient(event.getPlayer().getUUID(), abilities.getAbilityArray(), abilities.getEnabledAbilities(), LazyOptional.of(() -> serverPlayer));
			}
			abilities.synchroniseClient(event.getPlayer().getUUID(), abilities.getAbilityArray(), abilities.getEnabledAbilities(), LazyOptional.empty());
		});
	}
}
