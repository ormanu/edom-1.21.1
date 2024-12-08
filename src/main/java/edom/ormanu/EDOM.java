package edom.ormanu;

import edom.ormanu.Items.EDOMItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EDOM implements ModInitializer {
	public static final String MOD_ID = "edom";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		EDOMItems.initialize();

		LOGGER.info("coaie merge?");
	}

}