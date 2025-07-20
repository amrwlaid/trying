package net.idk.modattempt.Items.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;

import net.idk.modattempt.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.idk.modattempt.Items.ModBlockEntities;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;



public class MinerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, Inventory {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(22, ItemStack.EMPTY);
    private boolean isDoneScanning = false;
    private List<BlockPos> blocksToBreak = new ArrayList<>();
    private int fuel = 0;

    public MinerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MINER_BLOCK_ENTITY, pos, state);
    }

    public void addFuel() {
        ItemStack itemStack = items.get(21);
        String name =  itemStack.getItem().getName().getString();
        if (!itemStack.isEmpty()&& this.getFuel() < 1000){


            switch (name){
                case "Redstone Dust" :fuel+=2;
                break;
                case 	"Block of Redstone": fuel+=18;
                break;
                case 	"Coal": fuel+=1;
                break;
                case 	"Block of Coal": fuel+=9;
                break;
            }




            itemStack.decrement(1);
        }

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

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, items);
        nbt.putInt("fuel", this.fuel);
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        // Load inventory
        Inventories.readNbt(nbt, items);

        // Load other stuff
        this.fuel = nbt.getInt("fuel");
    }

    public DefaultedList<ItemStack> getItems(){
        return this.items;
    }






    public void tick(World world, BlockPos pos, BlockState state) {
        addFuel();
        BlockPos next;
        BlockState targetState;
        LootContextParameterSet.Builder builder;
        List<ItemStack> drops;

        if (!world.isClient && world.getTime() % 20 == 0 && this.fuel>0 && world.isReceivingRedstonePower(pos) && isDoneScanning && !blocksToBreak.isEmpty()) {

            if (world instanceof ServerWorld serverWorld ) {





                if (!blocksToBreak.isEmpty()) {
                    next = blocksToBreak.remove(0);
                    targetState = world.getBlockState(next);
                    if (!(targetState.getBlock() == Blocks.AIR)){
                        consumeFuel();
                    }




                    // Build the LootContextParameterSet
                    builder = new LootContextParameterSet.Builder((ServerWorld) world)
                            .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
                            .add(LootContextParameters.TOOL, ItemStack.EMPTY);









                    // Get the drops for this block
                    drops = targetState.getBlock().getDroppedStacks(targetState, builder);

                    // Attempt to insert drops into the inventory
                    for (ItemStack drop : drops) {
                        for (int i = 0; i < items.size(); i++) {
                            ItemStack slot = items.get(i);

                            if (slot.isEmpty()) {
                                items.set(i, drop.copy());
                                drop.setCount(0);
                                break;
                            } else if (ItemStack.canCombine(slot, drop)) {
                                int space = slot.getMaxCount() - slot.getCount();
                                int transfer = Math.min(space, drop.getCount());

                                if (transfer > 0) {
                                    slot.increment(transfer);
                                    drop.decrement(transfer);
                                    if (drop.isEmpty()) break;
                                }
                            }
                        }
                    }

                    // Remove the block from the world
                    world.removeBlock(next, false);

                }





            }
        }
        if (!isDoneScanning){
            Chunk chunk = world.getChunk(pos);

            // Get chunk start positions
            int startX = chunk.getPos().getStartX();
            int startZ = chunk.getPos().getStartZ();

            String key;
            // Loop through all x, y, z in the chunk
            for (int x = startX; x < startX + 16; x++) {
                for (int y = world.getBottomY(); y < world.getTopY(); y++) {
                    for (int z = startZ; z < startZ + 16; z++) {
                        BlockPos currentPos = new BlockPos(x, y, z);
                        BlockState blockState = world.getBlockState(currentPos);

                        // Do something with the block
                        System.out.println("Block at " + currentPos + ": " + blockState.getBlock().getTranslationKey());
                        key = blockState.getBlock().getTranslationKey();
                        switch (key) {
                            case "block.minecraft.coal_ore":
                            case "block.minecraft.iron_ore":
                            case "block.minecraft.copper_ore":
                            case "block.minecraft.gold_ore":
                            case "block.minecraft.redstone_ore":
                            case "block.minecraft.lapis_ore":
                            case "block.minecraft.diamond_ore":
                            case "block.minecraft.emerald_ore":
                            case "block.minecraft.deepslate_coal_ore":
                            case "block.minecraft.deepslate_iron_ore":
                            case "block.minecraft.deepslate_copper_ore":
                            case "block.minecraft.deepslate_gold_ore":
                            case "block.minecraft.deepslate_redstone_ore":
                            case "block.minecraft.deepslate_lapis_ore":
                            case "block.minecraft.deepslate_diamond_ore":
                            case "block.minecraft.deepslate_emerald_ore":
                            case "block.minecraft.nether_gold_ore":
                            case "block.minecraft.ancient_debris":
                                // This block is an ore
                                blocksToBreak.add(currentPos);
                                break;
                            default:
                                // Not an ore
                                break;
                        }

                    }
                }
            }
            this.isDoneScanning = true;
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

