package edom.ormanu.Items;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.Collections;

public class ScytheItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer, SimpleSynchronousResourceReloadListener {

    private final Identifier id;
    private final Identifier scytheId;
    private ItemRenderer itemRenderer;
    private BakedModel scytheinventory;
    private static final Identifier INVENTORY_TEXTURE = Identifier.of("edom:item/scythe_inventory");
    private static final Identifier IN_HAND_TEXTURE = Identifier.of("edom:item/scythe_in_hand");

    public ScytheItemRenderer(Identifier scytheId) {
        this.id = Identifier.of(scytheId.getNamespace(), scytheId.getPath() + "_renderer");
        this.scytheId = scytheId;

    }

    @Override
    public Identifier getFabricId() {
        return this.id;
    }

    @Override
    public Collection<Identifier> getFabricDependencies() {
        return Collections.singletonList(ResourceReloadListenerKeys.MODELS);
    }


    @Override
    public void reload(ResourceManager manager) {
        MinecraftClient mc = MinecraftClient.getInstance();
        this.itemRenderer = mc.getItemRenderer();
        this.scytheinventory = mc.getBakedModelManager().getModel(new ModelIdentifier(this.scytheId, "in_hand"));
    }

    @Override
    public void render(ItemStack itemStack, ModelTransformationMode renderMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED) {
            matrices.pop(); // cancel the previous transformation and pray that we are not breaking the state
            matrices.push();
            itemRenderer.renderItem(itemStack, renderMode, false, matrices, vertexConsumers, light, overlay, this.scytheinventory);
        } else {
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);
            matrices.pop();
        }

        boolean isInHand = itemStack.getItem() == EDOMItems.Scythe && MinecraftClient.getInstance().player.isUsingItem();

        Identifier texture = isInHand ? IN_HAND_TEXTURE : INVENTORY_TEXTURE;

        MinecraftClient.getInstance().getTextureManager().bindTexture(texture);

        MinecraftClient.getInstance().getItemRenderer();
    }
}
