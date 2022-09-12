package com.tiki.fantasiaabilities.loot;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;
import com.tiki.fantasiaabilities.data.capabilities.AbilitiesCapability;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class PassiveLuckMod extends LootModifier {

public final ILootCondition[] conditions;
	
	protected PassiveLuckMod(ILootCondition[] conditionsIn) {
		super(conditionsIn);
		this.conditions = conditionsIn;
	}

	@Nonnull
	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		Random rand = context.getRandom();
		System.out.println("Last Damage Player: " + context.getParamOrNull(LootParameters.LAST_DAMAGE_PLAYER) + ", " + context.getParamOrNull(LootParameters.TOOL));
		if(context.hasParam(LootParameters.LAST_DAMAGE_PLAYER)) {
			context.getParamOrNull(LootParameters.LAST_DAMAGE_PLAYER).getCapability(AbilitiesCapability.ABILITIES).ifPresent((c) -> {
				if(c.getLuck() > 0 && rand.nextInt(100) + 1 < c.getLuck() * 2) {
					for(ItemStack stack : generatedLoot) {
						stack.setCount(stack.getCount() + rand.nextInt(3 + context.getLootingModifier()));
					}
				}
			});
		}
		return generatedLoot;
	}
	
	public static class Serializer extends GlobalLootModifierSerializer<PassiveLuckMod> {
		
		@Override
		public PassiveLuckMod read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
			System.out.println("ARE CONDITIONS NULL: " + conditions);
			return new PassiveLuckMod(conditions);
		}

		@Override
		public JsonObject write(PassiveLuckMod instance) {
			JsonObject res = this.makeConditions(instance.conditions);
			return res;
		}
	}
}
