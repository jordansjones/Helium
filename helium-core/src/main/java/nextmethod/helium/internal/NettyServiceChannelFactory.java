package nextmethod.helium.internal;

import io.netty.bootstrap.ServerChannelFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServiceChannelFactory implements ServerChannelFactory<ServerSocketChannel> {

    @Override
    public ServerSocketChannel newChannel(final EventLoop eventLoop, final EventLoopGroup childGroup) {
        return new NioServerSocketChannel(eventLoop, childGroup);
    }
}
