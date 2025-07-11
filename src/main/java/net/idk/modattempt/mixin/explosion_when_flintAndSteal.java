package net.idk.modattempt.mixin;

import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(FlintAndSteelItem.class)
public class explosion_when_flintAndSteal {

    @Inject(method = "useOnBlock", at = @At("HEAD"))
    private void explosion(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir){
        World world = context.getWorld();
        Position pos = context.getHitPos();
        world.createExplosion(
                null,
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                10f,
                true,
                World.ExplosionSourceType.BLOCK
        );
    }
}
