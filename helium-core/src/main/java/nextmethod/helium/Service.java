package nextmethod.helium;

import com.google.common.util.concurrent.Futures;
import com.google.inject.Provider;
import io.netty.bootstrap.ServerChannelFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import nextmethod.helium.base.HttpServiceInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.net.InetSocketAddress;

final class Service implements IService {

	private final Logger logger = LoggerFactory.getLogger(IService.class);

	private final Provider<EventLoopGroup> eventLoopGroupProvider;
	private final Provider<HttpServiceInitializer> httpServiceInitializerProvider;
	private final ServerChannelFactory<ServerSocketChannel> channelFactory;

	@Inject
	public Service(final Provider<EventLoopGroup> eventLoopGroupProvider, final Provider<HttpServiceInitializer> httpServiceInitializerProvider, final ServerChannelFactory<ServerSocketChannel> channelFactory) {
		this.eventLoopGroupProvider = eventLoopGroupProvider;
		this.httpServiceInitializerProvider = httpServiceInitializerProvider;
		this.channelFactory = channelFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void run(int port) throws Exception {
		final EventLoopGroup boss = eventLoopGroupProvider.get();
		final EventLoopGroup worker = eventLoopGroupProvider.get();

		try {
            final ServiceBootstrap b = new ServiceBootstrap();
            b.group(boss, worker)
				.channelFactory(channelFactory)
				.childHandler(httpServiceInitializerProvider.get())
				.childOption(ChannelOption.TCP_NODELAY, true)
//				.option(ChannelOption.SO_BACKLOG, 100)
			;

			final Channel channel = b.bind(port).sync().channel();
			logger.info("Listening on {}:{}", ((InetSocketAddress)channel.localAddress()).getHostString(), port);
			channel.closeFuture().sync();
		}
		finally {
			if (!boss.isShutdown()) Futures.getUnchecked(boss.shutdownGracefully());
			if (!worker.isShutdown()) Futures.getUnchecked(worker.shutdownGracefully());
		}
	}
}
