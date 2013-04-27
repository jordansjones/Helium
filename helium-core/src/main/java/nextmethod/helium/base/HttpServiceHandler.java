package nextmethod.helium.base;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;

public class HttpServiceHandler extends ChannelInboundMessageHandlerAdapter<FullHttpMessage> {

	private final AtomicLong messageCount = new AtomicLong(0);

	/**
	 * Is called once a message was received.
	 *
	 * @param ctx the {@link io.netty.channel.ChannelHandlerContext} which this {@link io.netty.channel.ChannelHandler} belongs to
	 * @param msg the message to handle
	 */
	@Override
	public void messageReceived(final ChannelHandlerContext ctx, final FullHttpMessage msg) throws Exception {
		HttpRequest request = (HttpRequest) msg;
		boolean keepAlive = isKeepAlive(msg);

		final DefaultFullHttpResponse response;
		if (request.getUri().equalsIgnoreCase("/favicon.ico")) {
			response = new DefaultFullHttpResponse(
				request.getProtocolVersion(),
				HttpResponseStatus.NOT_FOUND
			);
		}
		else {
			final long msgCount = messageCount.incrementAndGet();
			final String resMsg = String.format("Message #%d", msgCount);

			response = new DefaultFullHttpResponse(
				request.getProtocolVersion(),
				HttpResponseStatus.OK,
				Unpooled.copiedBuffer(resMsg, CharsetUtil.UTF_8)
			);
		}
		response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
		HttpHeaders.addDateHeader(response, HttpHeaders.Names.DATE, new Date());

		if (keepAlive) {
			response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.data().readableBytes());
			response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
		}

		ctx.nextOutboundMessageBuffer().add(response);
		if (!keepAlive) {
			ctx.flush().addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void endMessageReceived(final ChannelHandlerContext ctx) throws Exception {
		super.endMessageReceived(ctx);
		ctx.flush();
	}
}
