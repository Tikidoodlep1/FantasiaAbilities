package com.tiki.fantasiaabilities.inventory;

import java.awt.Color;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tiki.fantasiaabilities.FantasiaAbilities;
import com.tiki.fantasiaabilities.data.capabilities.IAbilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.StringTextComponent;

public class LevelDisplay extends Widget {

	private static final Minecraft mc = Minecraft.getInstance();
	private static final ResourceLocation TEXTURES = new ResourceLocation(FantasiaAbilities.MOD_ID, "textures/gui/skills_buttons.png");
	private final IAbilities cap;
	private final int ability;
	
	public LevelDisplay(int x, int y, IAbilities cap, int ability) {
		super(x, y, 17, 15, new StringTextComponent(""));
		this.cap = cap;
		this.ability = ability;
	}
	
	@Override
	public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
		mc.textureManager.bind(TEXTURES);
		if(this.active) {
			int[] abilities = cap.getAbilityArray();
			if(abilities[ability] >= 50) {
				this.blit(matrix, this.x, this.y, 118, 0, this.width, this.height);
			}else {
				this.blit(matrix, this.x, this.y, 28, 0, this.width, this.height);
			}
			String s = String.valueOf(abilities[ability]);
			mc.font.draw(matrix, s, this.x - (mc.font.width(s) / 2) + 8, this.y + 4, Color.BLACK.getRGB());
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
	
	private static void BlitScale(MatrixStack matrixstack, int xStart, int xEnd, int yStart, int yEnd, int blitOffset, float minU, float maxU, float minV, float maxV) {
		Matrix4f matrix = matrixstack.last().pose();
		BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
	    bufferbuilder.vertex(matrix, (float)xStart, (float)yEnd, (float)blitOffset).uv(minU, maxV).endVertex();
	    bufferbuilder.vertex(matrix, (float)xEnd, (float)yEnd, (float)blitOffset).uv(maxU, maxV).endVertex();
	    bufferbuilder.vertex(matrix, (float)xEnd, (float)yStart, (float)blitOffset).uv(maxU, minV).endVertex();
	    bufferbuilder.vertex(matrix, (float)xStart, (float)yStart, (float)blitOffset).uv(minU, minV).endVertex();
	    bufferbuilder.end();
	    RenderSystem.enableAlphaTest();
	    WorldVertexBufferUploader.end(bufferbuilder);
	}
}
