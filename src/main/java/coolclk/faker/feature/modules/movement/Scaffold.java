package coolclk.faker.feature.modules.movement;

import coolclk.faker.event.events.PacketEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

@ModuleInfo(name = "Scaffold", category = ModuleCategory.Movement, defaultKeycode = Keyboard.KEY_H)
public class Scaffold extends Module {
    private BlockPos lastFeetBlockPos = null;
    private EntityPlayerSP player = null;
    private float playerFacingYaw = 0, playerFacingPitch = 0;

    @Override
    public boolean getCanKeepEnable() {
        return false;
    }

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
            if (player.getEntityWorld().getBlockState(feetBlockPos).getBlock() == Blocks.air && player.getEntityWorld().getBlockState(lastFeetBlockPos).getBlock() != Blocks.air) {
                placeBlock(player, lastFeetBlockPos.add(0, 1, 0), EnumFacing.UP);
                lastFeetBlockPos = lastFeetBlockPos.add(0, 1, 0);
            }
            if (player.posX > lastFeetBlockPos.getX()) facing = EnumFacing.EAST;
            if (player.posX < lastFeetBlockPos.getX()) facing = EnumFacing.WEST;
            if (player.posZ > lastFeetBlockPos.getZ()) facing = EnumFacing.SOUTH;
            if (player.posZ < lastFeetBlockPos.getZ()) facing = EnumFacing.NORTH;
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
                }
                Vector2f facingRotation = ModuleUtil.positionToPositionYawAndPitch(player.posX, player.posY + player.getEyeHeight(), player.posZ, facingX, facingY, facingZ);
                playerFacingYaw = facingRotation.x;
                playerFacingPitch = facingRotation.y;
                placeBlock(player, feetBlockPos, facing);
                lastFeetBlockPos = feetBlockPos;
            }
        }
    }

    @Override
    public void onDisable() {
        lastFeetBlockPos = null;
    }

    @SideOnly(value = Side.CLIENT)
    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (this.getEnable()) {
            if (event.packet instanceof C03PacketPlayer.C05PacketPlayerLook) {
                event.packet = new C03PacketPlayer.C05PacketPlayerLook(playerFacingYaw, playerFacingPitch, ((C03PacketPlayer.C05PacketPlayerLook) event.packet).isOnGround());
            } else if (event.packet instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
                event.packet = new C03PacketPlayer.C06PacketPlayerPosLook(((C03PacketPlayer.C06PacketPlayerPosLook) event.packet).getPositionX(), ((C03PacketPlayer.C06PacketPlayerPosLook) event.packet).getPositionY(), ((C03PacketPlayer.C06PacketPlayerPosLook) event.packet).getPositionZ(), playerFacingYaw, playerFacingPitch, ((C03PacketPlayer.C06PacketPlayerPosLook) event.packet).isOnGround());
            }
        }
    }

    @SideOnly(value = Side.CLIENT)
    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        if (this.getEnable()) {
            if (event.packet instanceof S08PacketPlayerPosLook) {
                event.packet = new S08PacketPlayerPosLook(((S08PacketPlayerPosLook) event.getPacket()).getX(), ((S08PacketPlayerPosLook) event.getPacket()).getY(), ((S08PacketPlayerPosLook) event.getPacket()).getZ(), player.rotationYaw, player.rotationPitch, ((S08PacketPlayerPosLook) event.getPacket()).func_179834_f());
            }
        }
    }

    public static void placeBlock(EntityPlayer player, BlockPos blockPos, EnumFacing facing) {
        if (player.inventory.getCurrentItem() != null) {
            ModuleUtil.gEP().swingItem();
            ModuleUtil.gPC().onPlayerRightClick(ModuleUtil.gEP(), ModuleUtil.gW(), player.inventory.getCurrentItem(), blockPos, facing, new Vec3(player.posX, player.posY, player.posZ));
        }
    }
}
