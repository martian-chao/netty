package netty.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 客户端
 * @author yanChaoLiu
 *
 */
public class TimeClient {
	
	public void connect(String host,int port) throws Exception{
		//处理读写的线程池组
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
		//Bootstrap辅助启动类
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(workGroup)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new TimeClientHandler());
					}
				});
		
			ChannelFuture future = bootstrap.connect(host, port).sync();
			future.channel().closeFuture().sync();
		} finally{
			//释放业务线程组
			workGroup.shutdownGracefully();
		}
		
				
		
	}
	
	public static void main(String[] args) {
		try {
			new TimeClient().connect("127.0.0.1", 8088);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
