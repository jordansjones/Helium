package nextmethod.helium;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import nextmethod.helium.base.HttpServiceHandler;
import nextmethod.helium.base.HttpServiceInitializer;
import nextmethod.helium.internal.InternalIocModule;

final class IocModule extends AbstractModule {

	/**
	 * Configures a {@link com.google.inject.Binder} via the exposed methods.
	 */
	@Override
	protected void configure() {
		bind(HeliumConfig.class).toInstance(HeliumConfig.Instance);
		bind(IService.class).to(Service.class).in(Scopes.SINGLETON);
		bind(HttpServiceHandler.class).in(Scopes.NO_SCOPE);
		bind(HttpServiceInitializer.class).in(Scopes.NO_SCOPE);

		install(new InternalIocModule());

		// Netty things
		bindNio();
	}

	private void bindNio() {
		bind(EventLoopGroup.class).to(NioEventLoopGroup.class).in(Scopes.NO_SCOPE);

	}

}
