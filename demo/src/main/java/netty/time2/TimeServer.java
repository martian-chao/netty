package netty.time2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 用户上线提醒服务
 * @author yanChaoLiu
 *
 */
public class TimeServer {
	//构建模式启动
	public void bind(int port) throws Exception {
		//接收业务请求
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//处理业务
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		try {
		//启动链
		ServerBootstrap bootstrap = new ServerBootstrap();
		//参数启动
		bootstrap.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG,1024)
			.childHandler(new ChildChannelHandler());
		
		//回调  阻塞 同步
			ChannelFuture future = bootstrap.bind(port).sync();
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	/**
	 * 拦截链
	 */
	
	private  class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			//编码 解码器
			//字节码信息进行断包处理
			ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
			//明文 处理
			ch.pipeline().addLast(new StringDecoder());
			
			//连接链加上各种各种类型的处理handler
			ch.pipeline().addLast(new TimeServerHandler());
		}
	}
	public static void main(String[] args) {
		int port =8088;
		System.out.println("服务器初始化完毕！BOSS线程组监控端口"+port);
		try {
			new TimeServer().bind(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
