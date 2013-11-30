package vazkii.tinkerer.common.item;


import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.container.ContainerResearchTable;
import thaumcraft.common.container.SlotLimitedByClass;
import vazkii.tinkerer.common.block.tile.container.slot.SlotResearchTableOverride;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemInfusedInkwell extends ItemMod {

	public ItemInfusedInkwell(int par1) {
		super(par1);
		
		setMaxDamage(800);
		maxStackSize = 1;
		canRepair = true;
		setHasSubtypes(false);

		MinecraftForge.EVENT_BUS.register(this);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1)
	{
		return ConfigItems.itemInkwell.getIconFromDamage(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return true;
	}

	@ForgeSubscribe
	public void containerOpened(PlayerOpenContainerEvent event) {
		Container container = event.entityPlayer.openContainer;
		if(container instanceof ContainerResearchTable)
			messWithContainer(container);
	}
	
	public static void messWithContainer(Container container) {
		if(container.inventorySlots.size() < 3)
			return;
		
		Slot slot = (Slot) container.inventorySlots.get(0);
		if(slot instanceof SlotLimitedByClass) {
			Slot newSlot = new SlotResearchTableOverride(slot.inventory, slot.getSlotIndex(), slot.xDisplayPosition, slot.yDisplayPosition);
			
			container.inventorySlots.set(0, newSlot);
		}
	}

}