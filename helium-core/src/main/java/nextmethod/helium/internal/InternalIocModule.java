package nextmethod.helium.internal;

import com.google.inject.PrivateModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import io.netty.bootstrap.ChannelFactory;
import io.netty.channel.socket.ServerSocketChannel;

public class InternalIocModule extends PrivateModule {
	/**
	 * Creates bindings and other configurations private to this module. Use {@link #expose(Class)
	 * expose()} to make the bindings in this module available externally.
	 */
	@Override
	protected void configure() {

		final TypeLiteral<ChannelFactory<ServerSocketChannel>> factoryTypeLiteral = new TypeLiteral<ChannelFactory<ServerSocketChannel>>() {};
		bind(factoryTypeLiteral).to(NettyServiceChannelFactory.class).in(Scopes.SINGLETON);
		expose(factoryTypeLiteral);
	}
}
