package nextmethod.helium;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Monitor;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public enum HeliumConfig {

	Instance;

	private final Monitor monitor = new Monitor();

	private final List<Class<?>> serviceClasses;
	private Injector injector;

	private HeliumConfig() {
		if (!SLF4JBridgeHandler.isInstalled()) {
			SLF4JBridgeHandler.install();
		}
		serviceClasses = Lists.newArrayList();
	}

	public HeliumConfig addServiceClass(final Class<?> service) {
		serviceClasses.add(checkNotNull(service));
		return this;
	}

	public Class<?>[] getServices() {
		return serviceClasses.toArray(new Class<?>[serviceClasses.size()]);
	}

	public void runService(final int port) throws Exception {
		getInjector().getInstance(IService.class).run(port);
	}

	private Injector getInjector() {
		monitor.enter();
		try {
			if (injector == null) {
				injector = Guice.createInjector(new IocModule());
			}
		}
		finally {
			monitor.leave();
		}
		return injector;
	}
}
