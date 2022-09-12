package com.tiki.fantasiaabilities.inventory;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.tiki.fantasiaabilities.data.capabilities.Abilities;
import com.tiki.fantasiaabilities.data.capabilities.IAbilities;
import com.tiki.fantasiaabilities.network.CSetExperiencePacket;
import com.tiki.fantasiaabilities.util.ExpTable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class SkillLevelupButton extends SkillButton {
	
	private static final Minecraft mc = Minecraft.getInstance();
	
	public SkillLevelupButton(PlayerEntity player, int x, int y, int ability, IAbilities cap, ITooltip tooltip) {
		super(x, y, 17, 15, 135, 0, 9, 6, 28, 0, 73, 0, 0, 0, new TranslationTextComponent("skills.button.levelup"), new IPressable() {
			@Override
			public void onPress(Button button) {
				int[] abilityArray = cap.getAbilityArray();
				if(abilityArray[ability] < 50) {
					ExpTable table = ExpTable.FIFTY;
					table = ExpTable.getExpforLevelup(abilityArray[ability]);
					CSetExperiencePacket packet = new CSetExperiencePacket(-table.getLevels());
					if(player.experienceLevel >= table.getLevels()) {
						
						//Actually set the capability level
						player.giveExperienceLevels(-table.getLevels());
						cap.setAbility(ability, abilityArray[ability] + 1);
						if((abilityArray[ability] + 1) % 10 == 0) {
							cap.setAbilityUnlocked((5*ability)+(abilityArray[ability]/10));
							cap.synchronise(mc.player.getUUID(), cap.getAbilityArray(), cap.getEnabledAbilities());
							if(mc.screen instanceof SkillsScreen) {
								((SkillsScreen)mc.screen).checkAndUpdateButtons(ability);
							}
						}else if((abilityArray[ability] + 1) % 5 == 0) {
							cap.synchronise(mc.player.getUUID(), cap.getAbilityArray(), cap.getEnabledAbilities());
							if(mc.screen instanceof SkillsScreen) {
								((SkillsScreen)mc.screen).checkAndUpdateButtons(ability);
							}
						}
						
						cap.synchronise(mc.player.getUUID(), cap.getAbilityArray(), cap.getEnabledAbilities());
						Abilities.channel.sendToServer(packet);
						
						//Extra effects
						if(ability == Abilities.DEFENSE) {
							Multimap<Attribute, AttributeModifier> mods = ImmutableMultimap.of(Attributes.ARMOR, new AttributeModifier(UUID.randomUUID(), "Armor modifier", ((int)cap.getDefense()/5), Operation.ADDITION));
							player.getAttributes().addTransientAttributeModifiers(mods);
						}
						if(ability == Abilities.BODY) {
							Multimap<Attribute, AttributeModifier> mods = ImmutableMultimap.of(Attributes.MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), "Health modifier", player.getAttributeValue(Attributes.MAX_HEALTH) + (cap.getBody()/5), Operation.ADDITION));
							player.getAttributes().addTransientAttributeModifiers(mods);
						}
					}
				}
			}
		}, tooltip);
		this.width = 17;
		this.height = 15;
		if(cap.getAbilityArray()[ability] >= 50) {
			this.overlayX = 155;
			this.overlayY = 0;
			this.overlayWidth = 15;
			this.overlayHeight = 13;
		}
	}
}
