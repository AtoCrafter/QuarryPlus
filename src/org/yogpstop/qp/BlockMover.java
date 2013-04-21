package org.yogpstop.qp;

import static buildcraft.core.CreativeTabBuildCraft.tabBuildCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockMover extends Block {

    public BlockMover(int par1) {
        super(par1, 1, Material.iron);
        setHardness(1.2F);
        this.setCreativeTab(tabBuildCraft);
        setBlockName("EnchantMover");
        setTextureFile("/mods/yogpstop/quarryplus/textures/blocks/chunk.png");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBlockTextureFromSide(int i) {
        switch (i) {
        case 1:
            return 3;
        case 0:
            return 2;
        default:
            return this.blockIndexInTexture;
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9) {
        if (world.isRemote) { return true; }
        entityPlayer.openGui(QuarryPlus.instance, QuarryPlus.guiIdContainerMover, world, x, y, z);
        return true;

    }
}
