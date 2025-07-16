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

        // Add the block's inventory slots (e.g. 9 slots in a single row)
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 18));
        }

        // Adjusted Y offset (original was 50)
        int startX = 8;
        int startY = 32; // Moved up by 18 pixels (1 slot height)

        // Player inventory (3 rows of 9)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, startX + col * 18, startY + row * 18));
            }
        }

        // Hotbar (1 row of 9)
        int hotbarY = startY + 58; // 3 rows * 18 + 4 padding = 54 + ~4
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, startX + col * 18, hotbarY));
        }
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
