package net.idk.modattempt.Items.custom;

import net.idk.modattempt.ModScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.inventory.SimpleInventory;


public class minerBlockScreenHandler extends ScreenHandler {
    private Inventory inventory;

    public minerBlockScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.miner_block, syncId);
        this.inventory = inventory;

        int startX = 8;
        int startY = 18;
        int slotSpacing = 18; // ← Reduced from 18
        int slotIndex = 0;

        // 3×7 grid (21 slots)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 7; col++) {
                this.addSlot(new Slot(inventory, slotIndex++, 1 + (startX + col * slotSpacing), startY + row * slotSpacing));
            }
        }

        // Isolated redstone-only slot
        int isolatedX = startX + 7 * slotSpacing + 20; // moved accordingly
        int isolatedY = startY + 1 * slotSpacing;
        this.addSlot(new Slot(inventory, slotIndex++, isolatedX, isolatedY) {
            @Override
            public boolean canInsert(ItemStack stack) {
                Item item = stack.getItem();
                return item == Items.REDSTONE || item == Items.REDSTONE_BLOCK;
            }
        });

        // Player inventory (3 rows of 9)
        int playerInvY = isolatedY + slotSpacing + 32;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 1 + (startX + col * slotSpacing), playerInvY + row * slotSpacing));
            }
        }

        // Hotbar (1 row of 9)
        int hotbarY = playerInvY + 3 * slotSpacing + 4;
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, (startX + col * slotSpacing) + 1, hotbarY));
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
        this(syncId, playerInventory, new SimpleInventory(22)); // Replace with actual logic if needed
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
    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (index < 22) {
                // From block inventory → to player inventory
                if (!this.insertItem(originalStack, 22, 58, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // From player inventory → to block inventory
                if (!this.insertItem(originalStack, 0, 22, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

}
