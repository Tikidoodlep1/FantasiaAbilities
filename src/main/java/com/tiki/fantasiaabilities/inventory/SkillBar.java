package com.tiki.fantasiaabilities.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tiki.fantasiaabilities.FantasiaAbilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class SkillBar extends Widget {
	private static final Minecraft mc = Minecraft.getInstance();
	private static final ResourceLocation TEXTURES = new ResourceLocation(FantasiaAbilities.MOD_ID, "textures/gui/skills_buttons.png");
	private final int blitX, blitY;
	
	public SkillBar(int x, int y, int blitX, int blitY) {
		super(x, y, 4, 10, new StringTextComponent(""));
		this.blitX = blitX;
		this.blitY = blitY;
	}

	@Override
	public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
		mc.textureManager.bind(TEXTURES);
		if(this.active) {
			this.blit(matrix, this.x, this.y, blitX, blitY, 4, 10);
		}
	}
	
	public void setLocation(int x, int y) {
		this.x += x;
		this.y += y;
		if(this.x >= SkillsScreen.getScaledXOffset() + SkillsScreen.getScaledWidth() - SkillsScreen.SKILLSIZE || this.y >= SkillsScreen.getScaledYOffset() + SkillsScreen.getScaledHeight() - SkillsScreen.SKILLSIZE || this.x < SkillsScreen.getScaledXOffset() || this.y < SkillsScreen.getScaledYOffset()) {
			this.active = false;
		}else {
			this.active = true;
		}
	}
}
