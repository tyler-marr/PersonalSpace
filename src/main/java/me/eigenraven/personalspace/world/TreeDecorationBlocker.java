package me.eigenraven.personalspace.world;

import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * When "Foliage" is enabled, the chunk provider runs the biome's full vanilla decorator, which also places the biome's
 * own trees (e.g. jungle bushes). Deny that tree pass in personal dimensions whose "Trees" toggle is off, so the toggle
 * actually controls tree generation.
 */
public class TreeDecorationBlocker {

    @SubscribeEvent
    public void onDecorate(DecorateBiomeEvent.Decorate event) {
        if (event.type != DecorateBiomeEvent.Decorate.EventType.TREE
                && event.type != DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM) {
            return;
        }
        if (event.world == null || event.world.isRemote) return;
        if (!(event.world.provider instanceof PersonalWorldProvider provider)) return;
        DimensionConfig config = provider.getConfig();
        if (config == null || config.isGeneratingTrees()) return;
        event.setResult(Event.Result.DENY);
    }
}
