package coolclk.faker.event.events;

import coolclk.faker.event.api.Event;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockActivatedEvent extends Event {
    protected World hitWorld;
    protected BlockPos hitPosition;
    protected IBlockState hitBlockState;
    protected EntityPlayer hitEntityPlayer;
    protected EnumFacing hitSide;
    protected float hitX, hitY, hitZ;

    public BlockActivatedEvent(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        this.hitWorld = worldIn;
        this.hitPosition = pos;
        this.hitBlockState = state;
        this.hitEntityPlayer = playerIn;
        this.hitSide = side;
        this.hitX = hitX;
        this.hitY = hitY;
        this.hitZ = hitZ;
    }

    public World getWorld() {
        return this.hitWorld;
    }

    public BlockPos getPosition() {
        return this.hitPosition;
    }

    public EntityPlayer getEntityPlayer() {
        return this.hitEntityPlayer;
    }

    public IBlockState getBlockState() {
        return this.hitBlockState;
    }

    public EnumFacing getSide() {
        return this.hitSide;
    }

    public float getX() {
        return this.hitX;
    }

    public float getY() {
        return this.hitY;
    }

    public float getZ() {
        return this.hitZ;
    }
}
