package nextmethod.helium;

import com.google.inject.Provider;
import io.netty.bootstrap.ChannelFactory;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import nextmethod.helium.base.HttpServiceInitializer;

import javax.inject.Inject;

final class Service implements IService {

	private final Provider<EventLoopGroup> eventLoopGroupProvider;
	private final Provider<HttpServiceInitializer> httpServiceInitializerProvider;
	private final ChannelFactory<ServerSocketChannel> channelFactory;

	@Inject
	public Service(final Provider<EventLoopGroup> eventLoopGroupProvider, final Provider<HttpServiceInitializer> httpServiceInitializerProvider, final ChannelFactory<ServerSocketChannel> channelFactory) {
		this.eventLoopGroupProvider = eventLoopGroupProvider;
		this.httpServiceInitializerProvider = httpServiceInitializerProvider;
		this.channelFactory = channelFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void run(int port) throws Exception {
		final EventLoopGroup boss = eventLoopGroupProvider.get();
		final EventLoopGroup worker = eventLoopGroupProvider.get();

		try (ServiceBootstrap b = new ServiceBootstrap()) {
			b.group(boss, worker)
				.channelFactory(channelFactory)
				.childHandler(httpServiceInitializerProvider.get());

			final Channel channel = b.bind(port).sync().channel();
			channel.closeFuture().sync();
		}
		finally {
			if (!boss.isShutdown()) boss.shutdown();
			if (!worker.isShutdown()) worker.shutdown();
		}
	}
}
