package net.idk.modattempt.Items.custom;

import net.idk.modattempt.ModScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.inventory.SimpleInventory;


public class minerBlockScreenHandler extends ScreenHandler {
    private Inventory inventory;

    public minerBlockScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.miner_block, syncId);
        this.inventory = inventory;

        // Add inventory slots (9-slot in this example)
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 18));
        }

        // Add player inventory slots here
    }

    /*public minerBlockScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(ModScreenHandlers.miner_block, syncId);

        BlockPos pos = buf.readBlockPos(); // 🔴 Required for registerExtended!
        Inventory inventory = playerInventory.player.getWorld().getBlockEntity(pos) instanceof MinerBlockEntity be ? be : new SimpleInventory(9);

        this.inventory = inventory;

        // Add custom slots
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 18));
        }

        // Add player inventory here if needed
    }*/





    public minerBlockScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, new SimpleInventory(9)); // Replace with actual logic if needed
        // Or if you're passing block pos from server:
        // BlockPos pos = buf.readBlockPos();
    }


    protected minerBlockScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }
}
