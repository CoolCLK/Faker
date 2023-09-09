package coolclk.faker.feature.modules.movement;

import coolclk.faker.event.api.SubscribeEvent;
import coolclk.faker.event.events.PacketEvent;
import coolclk.faker.event.events.PlayerUpdateEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
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
    public void onUpdate(PlayerUpdateEvent event) {
        player = event.getEntityPlayer();
        BlockPos feetBlockPos = new BlockPos(player.posX, player.posY - 0.1D, player.posZ);
        if (player.onGround) lastFeetBlockPos = feetBlockPos;
        if (player.getEntityWorld().getBlockState(feetBlockPos).getBlock() == Blocks.air) {
            EnumFacing facing = null;
            if (player.getEntityWorld().getBlockState(feetBlockPos).getBlock() == Blocks.air && player.getEntityWorld().getBlockState(lastFeetBlockPos).getBlock() != Blocks.air) {
                placeBlock(player, event.getPlayerController(), event.getWorldClient(), lastFeetBlockPos.add(0, 1, 0), EnumFacing.UP);
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
                placeBlock(player, event.getPlayerController(), event.getWorldClient(), feetBlockPos, facing);
                lastFeetBlockPos = feetBlockPos;
            }
        }
    }

    @Override
    public void onDisable() {
        lastFeetBlockPos = null;
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent event) {
        if (this.getEnable() && event.getType() == PacketEvent.Type.SEND) {
            if (event.isTypeOf(C03PacketPlayer.C05PacketPlayerLook.class)) {
                event.setPacket(new C03PacketPlayer.C05PacketPlayerLook(playerFacingYaw, playerFacingPitch, ((C03PacketPlayer.C05PacketPlayerLook) event.getPacket()).isOnGround()));
            } else if (event.isTypeOf(C03PacketPlayer.C06PacketPlayerPosLook.class)) {
                event.setPacket(new C03PacketPlayer.C06PacketPlayerPosLook(((C03PacketPlayer.C06PacketPlayerPosLook) event.getPacket()).getPositionX(), ((C03PacketPlayer.C06PacketPlayerPosLook) event.getPacket()).getPositionY(), ((C03PacketPlayer.C06PacketPlayerPosLook) event.getPacket()).getPositionZ(), playerFacingYaw, playerFacingPitch, ((C03PacketPlayer.C06PacketPlayerPosLook) event.getPacket()).isOnGround()));
            }
        }
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent event) {
        if (this.getEnable() && event.getType() == PacketEvent.Type.RECEIVE) {
            if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                event.setPacket(new S08PacketPlayerPosLook(((S08PacketPlayerPosLook) event.getPacket()).getX(), ((S08PacketPlayerPosLook) event.getPacket()).getY(), ((S08PacketPlayerPosLook) event.getPacket()).getZ(), player.rotationYaw, player.rotationPitch, ((S08PacketPlayerPosLook) event.getPacket()).func_179834_f()));
            }
        }
    }

    public static void placeBlock(EntityPlayerSP entityPlayer, PlayerControllerMP playerController, WorldClient worldClient, BlockPos blockPos, EnumFacing facing) {
        if (entityPlayer.inventory.getCurrentItem() != null) {
            entityPlayer.swingItem();
            playerController.onPlayerRightClick(entityPlayer, worldClient, entityPlayer.inventory.getCurrentItem(), blockPos, facing, new Vec3(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ));
        }
    }
}
