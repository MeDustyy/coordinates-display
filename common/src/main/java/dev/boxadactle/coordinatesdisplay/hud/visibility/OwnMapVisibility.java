package dev.boxadactle.coordinatesdisplay.hud.visibility;

import dev.boxadactle.boxlib.util.WorldUtils;
import dev.boxadactle.coordinatesdisplay.hud.HudVisibility;
import dev.boxadactle.coordinatesdisplay.hud.HudVisibilityFilter;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Items;

@HudVisibility("own_map")
public class OwnMapVisibility implements HudVisibilityFilter {
    @Override
    public boolean isVisible() {
        Inventory inventory = WorldUtils.getPlayer().getInventory();

        return inventory.contains((itemStack -> {
            if (itemStack.isEmpty()) {
                return false;
            }
            return itemStack.is(Items.MAP) || itemStack.is(Items.FILLED_MAP);
        }));
    }
}
