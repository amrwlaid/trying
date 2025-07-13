package net.idk.modattempt.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(Item.class)
public class mixinAttempt {
    @Inject(method = "useOnBlock", at = @At("HEAD"))
    private void sendidk_Message(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (context.getWorld().isClient)
            context.getPlayer().sendMessage(Text.literal("idk"));
    }

    @Inject(method = "finishUsing", at = @At("RETURN"))
    private void call_fatty(ItemStack stack, World world, LivingEntity user , CallbackInfoReturnable<ActionResult> cir){
        if (!world.isClient)
            user.sendMessage(Text.literal("fatty"));
    }



}
