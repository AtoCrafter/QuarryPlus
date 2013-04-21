package org.yogpstop.qp;

import java.util.ArrayList;

import buildcraft.core.CreativeTabBuildCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockQuarry extends BlockContainer {

    public BlockQuarry(int i) {
        super(i, 4, Material.iron);
        setHardness(1.5F);
        setResistance(10F);
        setStepSound(soundStoneFootstep);
        setCreativeTab(CreativeTabBuildCraft.tabBuildCraft);
        setBlockName("QuarryPlus");
        setTextureFile("/mods/yogpstop/quarryplus/textures/blocks/chunk.png");
    }

    private final ArrayList<ItemStack> drop = new ArrayList<ItemStack>();

    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {
        this.drop.clear();
        TileQuarry tq = (TileQuarry) world.getBlockTileEntity(x, y, z);
        int count = quantityDropped(meta, 0, world.rand);
        int id1 = idDropped(meta, world.rand, 0);
        if (id1 > 0) {
            for (int i = 0; i < count; i++) {
                ItemStack is = new ItemStack(id1, 1, damageDropped(meta));
                tq.setEnchantment(is);
                this.drop.add(is);
            }
        }
        super.breakBlock(world, x, y, z, id, meta);
    }

    @Override
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = (ArrayList<ItemStack>) this.drop.clone();
        this.drop.clear();
        return ret;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBlockTextureFromSideAndMetadata(int i, int j) {
        if (j == 0 && i == 3)
            return 5;

        if (i == j)
            return 5;

        switch (i) {
        case 1:
            return 6;
        default:
            return this.blockIndexInTexture;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World w) {
        return new TileQuarry();
    }

    @Override
    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLiving el) {
        ItemStack stack = el.getHeldItem();
        ForgeDirection orientation = get2dOrientation(el.posX, el.posZ, x, z);
        w.setBlockMetadataWithNotify(x, y, z, orientation.getOpposite().ordinal());
        ((TileQuarry) w.getBlockTileEntity(x, y, z)).init(stack.getEnchantmentTagList());
    }

    private static ForgeDirection get2dOrientation(double x1, double z1, double x2, double z2) {
        double Dx = x1 - x2;
        double Dz = z1 - z2;
        double angle = Math.atan2(Dz, Dx) / Math.PI * 180 + 180;

        if (angle < 45 || angle > 315)
            return ForgeDirection.EAST;
        else if (angle < 135)
            return ForgeDirection.SOUTH;
        else if (angle < 225)
            return ForgeDirection.WEST;
        else
            return ForgeDirection.NORTH;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer ep, int par6, float par7, float par8, float par9) {
        if (ep.isSneaking())
            return false;
        ep.openGui(QuarryPlus.instance, QuarryPlus.guiIdContainerQuarry, world, x, y, z);
        return true;
    }

}