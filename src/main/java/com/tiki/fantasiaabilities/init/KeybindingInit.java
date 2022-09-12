package com.tiki.fantasiaabilities.init;

import java.awt.event.KeyEvent;

import com.tiki.fantasiaabilities.FantasiaAbilities;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class KeybindingInit {
	public static final KeyBinding abiltiyMenu = createKey("ability_menu", KeyEvent.VK_G);
	
	private static KeyBinding createKey(String name, int key) {
		return new KeyBinding("key." + FantasiaAbilities.MOD_ID + "." + name, key, "key.category." + FantasiaAbilities.MOD_ID);
	}
	
	public static void register(final FMLClientSetupEvent event) {
		ClientRegistry.registerKeyBinding(abiltiyMenu);
	}
}
