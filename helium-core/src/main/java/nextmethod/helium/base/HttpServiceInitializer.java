package nextmethod.helium.base;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpServiceInitializer extends ChannelInitializer<SocketChannel> {

	private final Provider<HttpServiceHandler> handlerProvider;

	@Inject
	public HttpServiceInitializer(final Provider<HttpServiceHandler> handlerProvider) {
		this.handlerProvider = handlerProvider;
	}

	/**
	 * This method will be called once the {@link io.netty.channel.Channel} was registered. After the method returns this instance
	 * will be removed from the {@link io.netty.channel.ChannelPipeline} of the {@link io.netty.channel.Channel}.
	 *
	 * @param ch the {@link io.netty.channel.Channel} which was registered.
	 * @throws Exception is thrown if an error accours. In that case the {@link io.netty.channel.Channel} will be closed.
	 */
	@Override
	protected void initChannel(final SocketChannel ch) throws Exception {
		final ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast("decoder", new HttpRequestDecoder());
		pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
		pipeline.addLast("encoder", new HttpResponseEncoder());
//		pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());

		pipeline.addLast("handler", handlerProvider.get());
	}
}
