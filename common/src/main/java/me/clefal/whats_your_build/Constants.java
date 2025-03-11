package me.clefal.whats_your_build;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.BusBuilder;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.IEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final String MOD_ID = "whats_your_build";
	public static final String MOD_NAME = "Whats-Your-Build";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static final IEventBus clientBus = BusBuilder.builder().setExceptionHandler((iEventBus, event, eventListeners, i, throwable) -> {
		try {
			throw throwable;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}).build();
	public static final IEventBus serverBus = BusBuilder.builder().setExceptionHandler((iEventBus, event, eventListeners, i, throwable) -> {
		try {
			throw throwable;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}).build();
}