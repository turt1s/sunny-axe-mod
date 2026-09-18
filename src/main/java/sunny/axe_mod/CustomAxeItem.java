package sunny.axe_mod;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.math.Direction;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class CustomAxeItem extends AxeItem {
    public CustomAxeItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }













    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {

        if (!world.isClient() && state.isIn(BlockTags.LOGS)){
            breakConnectedLogs((ServerWorld) world, pos);
        }
        return super.postMine(stack, world, state, pos, miner);
    }

private void breakConnectedLogs(ServerWorld world, BlockPos pos) {
    Queue<BlockPos> blocksToCheck = new LinkedList<>();
    blocksToCheck.add(pos);

    Set<BlockPos> checkedBlocks = new HashSet<>();

    while (!blocksToCheck.isEmpty()) {
        BlockPos currentPos = blocksToCheck.poll();

        if (!checkedBlocks.add(currentPos)) {
            continue;}

        BlockState currentState = world.getBlockState(currentPos);

        if (currentState.isIn(BlockTags.LOGS)) {
            world.breakBlock(currentPos, true);
            for (Direction direction : Direction.values()) {
                blocksToCheck.add(currentPos.offset(direction));
            }
        }
    }
}

}

