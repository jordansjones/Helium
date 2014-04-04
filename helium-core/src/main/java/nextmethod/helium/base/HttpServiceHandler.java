package nextmethod.helium.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

import javax.ws.rs.core.HttpHeaders;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;

public class HttpServiceHandler extends SimpleChannelInboundHandler<HttpRequest> {

    @Override
    protected void messageReceived(final ChannelHandlerContext ctx, final HttpRequest msg) throws Exception {

        final boolean keepAlive = isKeepAlive(msg);

        final String uri = msg.getUri();

        if (uri.equalsIgnoreCase("/favicon.ico")) {
            final ChannelFuture write = ctx.channel().write(new DefaultFullHttpResponse(msg.getProtocolVersion(), HttpResponseStatus.NOT_FOUND));
            if (!keepAlive) {
                write.addListener(ChannelFutureListener.CLOSE);
            }
            return;
        }

        final ByteBuf content = Unpooled.copiedBuffer("Poop", CharsetUtil.UTF_8);
        final DefaultFullHttpResponse poop = new DefaultFullHttpResponse(msg.getProtocolVersion(), HttpResponseStatus.OK, content);
        poop.headers().set(HttpHeaders.CONTENT_TYPE, "text/plain");
        setContentLength(poop, content.readableBytes());

        ctx.channel().writeAndFlush(poop).addListener(ChannelFutureListener.CLOSE);
    }

}
