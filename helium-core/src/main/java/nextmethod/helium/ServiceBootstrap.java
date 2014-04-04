package nextmethod.helium;

import com.google.common.annotations.VisibleForTesting;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.bootstrap.ServerChannelFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.util.AttributeKey;

import javax.annotation.Nonnull;
import java.net.InetAddress;
import java.net.SocketAddress;

import static com.google.common.base.Preconditions.checkNotNull;

class ServiceBootstrap {

	private final ServerBootstrap bootstrap;

	@VisibleForTesting
	ServiceBootstrap(@Nonnull ServerBootstrap bootstrap) {
		this.bootstrap = checkNotNull(bootstrap);
	}

	public ServiceBootstrap() {
		this(new ServerBootstrap());
	}

	/**
	 * Specify the {@link io.netty.channel.EventLoopGroup} which is used for the parent (acceptor) and the child (client).
	 */
	public ServiceBootstrap group(final EventLoopGroup group) {
		bootstrap.group(group);
		return this;
	}

	/**
	 * Set the {@link io.netty.channel.ChannelHandler} which is used to serve the request for the {@link io.netty.channel.Channel}'s.
	 */
	public ServiceBootstrap childHandler(final ChannelHandler childHandler) {
		bootstrap.childHandler(childHandler);
		return this;
	}

	/**
	 * Set the specific {@link io.netty.util.AttributeKey} with the given value on every child {@link io.netty.channel.Channel}. If the value is
	 * {@code null} the {@link io.netty.util.AttributeKey} is removed
	 */
	public <T> ServiceBootstrap childAttr(final AttributeKey<T> childKey, final T value) {
		bootstrap.childAttr(childKey, value);
		return this;
	}

	/**
	 * Allow to specify a {@link io.netty.channel.ChannelOption} which is used for the {@link io.netty.channel.Channel} instances once they get created
	 * (after the acceptor accepted the {@link io.netty.channel.Channel}). Use a value of {@code null} to remove a previous set
	 * {@link io.netty.channel.ChannelOption}.
	 */
	public <T> ServiceBootstrap childOption(final ChannelOption<T> childOption, final T value) {
		bootstrap.childOption(childOption, value);
		return this;
	}

	public ServiceBootstrap validate() {
		bootstrap.validate();
		return this;
	}

	/**
	 * Allow to specify an initial attribute of the newly created {@link io.netty.channel.Channel}.  If the {@code value} is
	 * {@code null}, the attribute of the specified {@code key} is removed.
	 */
	public <T> ServiceBootstrap attr(final AttributeKey<T> key, final T value) {
		bootstrap.attr(key, value);
		return this;
	}

	/**
	 * Set the {@link io.netty.channel.EventLoopGroup} for the parent (acceptor) and the child (client). These
	 * {@link io.netty.channel.EventLoopGroup}'s are used to handle all the events and IO for {@link io.netty.channel.socket.SocketChannel} and
	 * {@link io.netty.channel.Channel}'s.
	 */
	public ServiceBootstrap group(final EventLoopGroup parentGroup, final EventLoopGroup childGroup) {
		bootstrap.group(parentGroup, childGroup);
		return this;
	}

	/**
	 * @see {@link #localAddress(java.net.SocketAddress)}
	 */
	public ServiceBootstrap localAddress(final String inetHost, final int inetPort) {
		bootstrap.localAddress(inetHost, inetPort);
		return this;
	}

	/**
	 * Allow to specify a {@link io.netty.channel.ChannelOption} which is used for the {@link io.netty.channel.Channel} instances once they got
	 * created. Use a value of {@code null} to remove a previous set {@link io.netty.channel.ChannelOption}.
	 */
	public <T> ServiceBootstrap option(final ChannelOption<T> option, final T value) {
		bootstrap.option(option, value);
		return this;
	}

	/**
	 * the {@link io.netty.channel.ChannelHandler} to use for serving the requests.
	 */
	public ServiceBootstrap handler(final ChannelHandler handler) {
		bootstrap.handler(handler);
		return this;
	}

	/**
	 * @see {@link #localAddress(java.net.SocketAddress)}
	 */
	public ServiceBootstrap localAddress(final int inetPort) {
		bootstrap.localAddress(inetPort);
		return this;
	}

	/**
	 * The {@link java.net.SocketAddress} which is used to bind the local "end" to.
	 *
	 */
	public ServiceBootstrap localAddress(final SocketAddress localAddress) {
		bootstrap.localAddress(localAddress);
		return this;
	}

	/**
	 * @see {@link #localAddress(java.net.SocketAddress)}
	 */
	public ServiceBootstrap localAddress(final InetAddress inetHost, final int inetPort) {
		bootstrap.localAddress(inetHost, inetPort);
		return this;
	}

	/**
	 * Create a new {@link io.netty.channel.Channel} and bind it.
	 */
	public ChannelFuture bind(final SocketAddress localAddress) {
		return bootstrap.bind(localAddress);
	}

	/**
	 * Create a new {@link io.netty.channel.Channel} and bind it.
	 */
	public ChannelFuture bind(final InetAddress inetHost, final int inetPort) {
		return bootstrap.bind(inetHost, inetPort);
	}

	/**
	 * Create a new {@link io.netty.channel.Channel} and bind it.
	 */
	public ChannelFuture bind(final String inetHost, final int inetPort) {
		return bootstrap.bind(inetHost, inetPort);
	}

	/**
	 * Create a new {@link io.netty.channel.Channel} and bind it.
	 */
	public ChannelFuture bind(final int inetPort) {
		return bootstrap.bind(inetPort);
	}

	/**
	 * Create a new {@link io.netty.channel.Channel} and bind it.
	 */
	public ChannelFuture bind() {
		return bootstrap.bind();
	}

	/**
	 * {@link io.netty.bootstrap.ChannelFactory} which is used to create {@link io.netty.channel.Channel} instances from
	 * when calling {@link #bind()}. This method is usually only used if {@link #channel(Class)}
	 * is not working for you because of some more complex needs. If your {@link io.netty.channel.Channel} implementation
	 * has a no-args constructor, its highly recommend to just use {@link #channel(Class)} for
	 * simplify your code.
	 */
	public ServiceBootstrap channelFactory(final ServerChannelFactory<? extends ServerChannel> channelFactory) {
		bootstrap.channelFactory(channelFactory);
		return this;
	}

	/**
	 * The {@link Class} which is used to create {@link io.netty.channel.Channel} instances from.
	 * You either use this or {@link #channelFactory(io.netty.bootstrap.ServerChannelFactory)} if your
	 * {@link io.netty.channel.Channel} implementation has no no-args constructor.
	 */
	public ServiceBootstrap channel(final Class<? extends ServerChannel> channelClass) {
		bootstrap.channel(channelClass);
		return this;
	}
}
