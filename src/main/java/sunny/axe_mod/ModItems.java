package sunny.axe_mod;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;


public class ModItems {
    public static Item register(Item item, String id) {
        // Creates the identifier for the item.
        Identifier itemID = Identifier.of(AxeMod.MOD_ID, id);

        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        // Return the registered item
        return registeredItem;
    }



    public static final FoodComponent POISON_FOOD_COMPONENT = new FoodComponent.Builder()
            .alwaysEdible() // can be eaten no matter the hunger
            .snack() //makes this item a snack and eaten quickly.
            .nutrition(3) //restores 3 hunger points
            .saturationModifier(4) //saturation is 4. for reference, a regular apple restores 4 and gives 2.4 saturation.
            // The duration is in ticks, 20 ticks = 1 second
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 10 * 20, 2), 0.5f)
            //Creates poison status effect, dealing damage 10 times, once every 20 ticks. amplifier makes it poison II. 1/2 chance of happening
            .build();
    public static final Item POISONOUS_APPLE = register(
            new Item(new Item.Settings().food(POISON_FOOD_COMPONENT)),
            "poisonous_apple"
    );
    public static final Item FOREST_CORE = register(
            new Item(new Item.Settings()
                    .rarity(Rarity.UNCOMMON)), //this setting makes the Forest_core item have yellow text
            "forest_core"
    );
    public static final Item FOREST_AXE = register(
            new CustomAxeItem(ForagingMaterial.INSTANCE, new Item.Settings()
                    .attributeModifiers(AxeItem.createAttributeModifiers(ForagingMaterial.INSTANCE, 10F, -3.75F))),
            "forest_axe" //attack speed is .25, which is 4 times slower the speed of a regular axe. the base attack damage is 10, which makes the total damage 20, twice the netherite axe's damage
    );


    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ModItems.POISONOUS_APPLE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> itemGroup.add(ModItems.FOREST_AXE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> itemGroup.add(ModItems.FOREST_CORE));
    }

}
