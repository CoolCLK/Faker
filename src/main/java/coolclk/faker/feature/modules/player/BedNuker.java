package coolclk.faker.feature.modules.player;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsInteger;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ModuleInfo(name = "BedNuker", category = ModuleCategory.Player)
public class BedNuker extends Module {
    static class BedTask {
        BlockPos blockPos;
        IBlockState blockState;
        float blockDamage = 0;

        BedTask(BlockPos blockPos, IBlockState blockState) {
            this.blockPos = blockPos;
            this.blockState = blockState;
        }

        void start() {
            ModuleUtil.gNM().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, null));
        }

        boolean action() {
            this.blockDamage += blockState.getBlock().getPlayerRelativeBlockHardness(ModuleUtil.gEP(), ModuleUtil.gEP().getEntityWorld(), blockPos);
            ModuleUtil.gW().sendBlockBreakProgress(ModuleUtil.gEP().getEntityId(), blockPos, (int) (blockDamage * 10.0F) - 1);
            return this.blockDamage >= 9;
        }

        void end() {
            ModuleUtil.gW().sendBlockBreakProgress(ModuleUtil.gEP().getEntityId(), blockPos, 0);
            ModuleUtil.gNM().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, null));
        }

        void abort() {
            ModuleUtil.gW().sendBlockBreakProgress(ModuleUtil.gEP().getEntityId(), blockPos, 0);
            ModuleUtil.gNM().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, blockPos, null));
        }
    }

    public SettingsInteger range = new SettingsInteger(this, "range", 5, 0, 10);
    public SettingsInteger count = new SettingsInteger(this, "count", 1, 0, 6);

    private final List<BedTask> tasks = new ArrayList<BedTask>();

    @Override
    public void onEnabling() {
        List<BlockPos> newTasks = ModuleUtil.findBlockPosWithDistance(ModuleUtil.gEP().getEntityWorld(), ModuleUtil.gEP().getPosition(), Blocks.bed, range.getValue());
        for (BedTask task : Arrays.copyOf(tasks.toArray(new BedTask[0]), tasks.size())) {
            int index = newTasks.indexOf(task.blockPos);
            if (index < 0) {
                task.abort();
                tasks.remove(task);
            } else {
                newTasks.remove(index);
                if (task.action()) {
                    task.end();
                    tasks.remove(task);
                }
            }
        }
        int nowCounts = tasks.size();
        for (BlockPos blockPos : newTasks) {
            if (nowCounts > count.getValue()) {
                break;
            }
            BedTask task = new BedTask(blockPos, ModuleUtil.gEP().getEntityWorld().getBlockState(blockPos));
            task.start();
            tasks.add(task);
            nowCounts++;
        }
    }
}
