package coolclk.faker.feature.modules.movement;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.lwjgl.util.vector.Vector2f;

@ModuleInfo(name = "Scaffold", category = ModuleCategory.Movement)
public class Scaffold extends Module {
    private BlockPos lastFeetBlockPos = null;
    private EntityPlayerSP player = null;

    @Override
    public void onEnable() {
        player = ModuleUtil.gEP();
    }

    @Override
    public void onEnabling() {
        BlockPos feetBlockPos = new BlockPos(player.posX, player.posY - 0.1D, player.posZ);
        if (player.onGround) lastFeetBlockPos = feetBlockPos;
        if (ModuleUtil.gEP().getEntityWorld().getBlockState(feetBlockPos).getBlock() == Blocks.air) {
            EnumFacing facing = null;
            if (player.posX > lastFeetBlockPos.getX()) facing = EnumFacing.EAST;
            if (player.posX < lastFeetBlockPos.getX()) facing = EnumFacing.WEST;
            if (player.posZ > lastFeetBlockPos.getZ()) facing = EnumFacing.SOUTH;
            if (player.posZ < lastFeetBlockPos.getZ()) facing = EnumFacing.NORTH;
            if (player.posY >= lastFeetBlockPos.getY() + 1) facing = EnumFacing.UP;
            if (facing != null) {
                double facingX = lastFeetBlockPos.getX(), facingY = lastFeetBlockPos.getY(), facingZ = lastFeetBlockPos.getZ();
                switch (facing) {
                    case EAST: {
                        facingX += 0.5;
                        break;
                    }
                    case WEST: {
                        facingX -= 0.5;
                        break;
                    }
                    case NORTH: {
                        facingZ += 0.5;
                        break;
                    }
                    case SOUTH: {
                        facingZ -= 0.5;
                        break;
                    }
                    case UP: {
                        facingY += 0.5;
                        break;
                    }
                }
                Vector2f rotation = ModuleUtil.positionToPositionYawAndPitch(player.posX, player.posY + player.getEyeHeight(), player.posZ, facingX, facingY, facingZ);
                ModuleUtil.gNM().sendPacket(new C03PacketPlayer.C05PacketPlayerLook(rotation.x, rotation.y, ModuleUtil.gEP().onGround));
                ModuleUtil.gPC().onPlayerRightClick(player, ModuleUtil.gW(), player.getHeldItem(), feetBlockPos, facing, new Vec3(0, 0, 0));
                lastFeetBlockPos = feetBlockPos;
            }
        }
    }

    @Override
    public void onDisable() {
        lastFeetBlockPos = null;
    }
}
