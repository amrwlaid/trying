package net.idk.modattempt.Items.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.idk.modattempt.Items.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class MinerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, Inventory {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(22, ItemStack.EMPTY);


    private int fuel = 0;

    public MinerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MINER_BLOCK_ENTITY, pos, state);
    }

    public void addFuel(int amount) {
        fuel += amount;
        markDirty();
    }

    public boolean consumeFuel() {
        if (fuel > 0) {
            fuel--;
            markDirty();
            return true;
        }
        return false;
    }

    public int getFuel() {
        return fuel;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (!world.isClient && world.getTime() % 20 == 0 && this.fuel!=0) {
            consumeFuel();
        }
    }



    @Override
    public Text getDisplayName() {
        return Text.literal("Miner Block");
    }


    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new minerBlockScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (stack != null)if (!stack.isEmpty()) return false;
        }
        return true;
    }


    @Override
    public ItemStack getStack(int slot) {
        return items.get(slot);
    }
    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = ItemStack.EMPTY;
        if (!(items.get(slot) == null) && !(result == null)){
            if (!items.get(slot).isEmpty()) {
                result = items.get(slot).split(amount);
                if (items.get(slot).isEmpty()) {
                    items.set(slot, ItemStack.EMPTY);
                }
                markDirty();
            }
        }
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        if (slot < 0 || slot >= items.size()) return ItemStack.EMPTY;

        ItemStack stack = items.get(slot);
        if (stack == null) {
            if (stack.isEmpty())
                return ItemStack.EMPTY;
        }

        items.set(slot, ItemStack.EMPTY);
        markDirty();
        return stack;
    }


    @Override
    public void setStack(int slot, ItemStack stack) {
        if (!(stack == null)){
        items.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        markDirty();
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(getPos()); // ⚠️ Required for registerExtended!
    }

}

