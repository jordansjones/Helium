package nextmethod.helium.internal;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.netty.bootstrap.ChannelFactory;
import io.netty.channel.socket.ServerSocketChannel;

public class NettyServiceChannelFactory implements ChannelFactory<ServerSocketChannel> {

	private final Provider<ServerSocketChannel> channelProvider;

	@Inject
	public NettyServiceChannelFactory(final Provider<ServerSocketChannel> channelProvider) {
		this.channelProvider = channelProvider;
	}

	/**
	 * Creates a new channel.
	 */
	@Override
	public ServerSocketChannel newChannel() {
		return channelProvider.get();
	}
}
