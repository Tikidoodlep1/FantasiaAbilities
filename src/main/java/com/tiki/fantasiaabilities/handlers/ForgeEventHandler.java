package com.tiki.fantasiaabilities.handlers;

import java.util.List;

import com.tiki.fantasiaabilities.FantasiaAbilities;
import com.tiki.fantasiaabilities.data.capabilities.Abilities;
import com.tiki.fantasiaabilities.data.capabilities.AbilitiesCapability;
import com.tiki.fantasiaabilities.init.KeybindingInit;
import com.tiki.fantasiaabilities.inventory.SkillsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.living.EntityTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = FantasiaAbilities.MOD_ID, bus = Bus.FORGE)
public class ForgeEventHandler {
	
	@SubscribeEvent
	public static void onDigSpeed(final PlayerEvent.BreakSpeed event) {
		event.getPlayer().getCapability(AbilitiesCapability.ABILITIES).ifPresent((a) -> {
			ToolType type = event.getState().getHarvestTool();
			//System.out.println("Correct Tool for drops? " + (event.getPlayer().getItemInHand(Hand.MAIN_HAND).getDestroySpeed(event.getState()) != 1.0F));
			if(event.getPlayer().getItemInHand(Hand.MAIN_HAND).getDestroySpeed(event.getState()) != 1.0F) {
				if(type == ToolType.PICKAXE) {
					event.setNewSpeed(event.getOriginalSpeed() * (1 + ((a.getMining() * 2.0F) / 100.0F)));
				}else if(type == ToolType.SHOVEL) {
					event.setNewSpeed(event.getOriginalSpeed() * (1 + ((a.getDigging() * 2.0F) / 100.0F)));
				}else if(type == ToolType.AXE) {
					event.setNewSpeed(event.getOriginalSpeed() * (1 + ((a.getChopping() * 2.0F) / 100.0F)));
				}
			}
		});
	}
	
	@SubscribeEvent
	public static void onEnderTeleport(final EntityTeleportEvent.EnderEntity event) {
		if(event.getEntityLiving() instanceof EndermanEntity || event.getEntityLiving() instanceof ShulkerEntity) {
			List<PlayerEntity> players = event.getEntityLiving().level.getNearbyPlayers(new EntityPredicate().range(16.0D), event.getEntityLiving(), event.getEntityLiving().getBoundingBox().inflate(16.0D));
			for(PlayerEntity player : players) {
				player.getCapability(AbilitiesCapability.ABILITIES).ifPresent((cap) -> {
					if(cap.getAbilityEnabled(Abilities.MAGIC, 0)) {
						event.setCanceled(true);
					}
				});
			}
		}
	}
	
	@EventBusSubscriber(modid = FantasiaAbilities.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
	public static class ClientHandler {
		@SubscribeEvent
		public static void onKeyPress(final InputEvent.KeyInputEvent event) {
			Minecraft mc = Minecraft.getInstance();
			if(mc.level==null)return;
			if(mc.screen == null && KeybindingInit.abiltiyMenu.isDown()) {
				AbilitiesCapability.get(mc.player).ifPresent((a) -> {
					mc.setScreen(new SkillsScreen(mc.player, a));
				});
			}
		}
	}
}
