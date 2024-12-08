package edom.ormanu;

import edom.ormanu.Items.EDOMItems;
import edom.ormanu.Items.ScytheItemRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public class EDOMClient implements ClientModInitializer {


    public static void registerModelPredicateProviders() {
        ModelPredicateProviderRegistry.register(
                EDOMItems.Scythe,
                Identifier.of("edom", "in_hand"),
                (stack, world, entity, seed) -> {
                    if (entity != null) {
                        if (entity.getMainHandStack() == stack) {
                            return 1.0F; // Switch to in-hand model
                        }
                    }
                    return 0.0F; // Use inventory model
                }
        );


    }


    @Override
    public void onInitializeClient() {
        Identifier scytheId = Registries.ITEM.getId(EDOMItems.Scythe);
        registerModelPredicateProviders();

            ScytheItemRenderer scytheItemRenderer = new ScytheItemRenderer(scytheId);
            ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(scytheItemRenderer);
            BuiltinItemRendererRegistry.INSTANCE.register(EDOMItems.Scythe, scytheItemRenderer);
            FabricModelPredicateProviderRegistry.register(EDOMItems.Scythe, Identifier.of("in_hand"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);


    }
}
