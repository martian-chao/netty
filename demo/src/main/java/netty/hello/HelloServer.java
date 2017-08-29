package netty.hello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
/**
 * 服务端
 */
public class HelloServer {
	 public void start(int port) throws Exception {  
		 	//接收客户端业务请求
	        EventLoopGroup bossGroup = new NioEventLoopGroup();  
	        //处理客户端业务请求
	        EventLoopGroup workerGroup = new NioEventLoopGroup();  
	        try {  
	        	//辅助启动类
	            ServerBootstrap b = new ServerBootstrap(); 
	            //启动链
	            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)  
	                    .childHandler(new ChannelInitializer<SocketChannel>() {  
	                        @Override  
	                        public void initChannel(SocketChannel ch)  
	                                throws Exception {  
	                        	//注册handler，连接链加上各种各种类型的处理handler
	                            ch.pipeline().addLast(new HelloServerInHandler());  
	                        }  
	                    }).option(ChannelOption.SO_BACKLOG, 128)  
	                    .childOption(ChannelOption.SO_KEEPALIVE, true);  
	            //通知回调  绑定端口，同步等待成功.
	            ChannelFuture f = b.bind(port).sync();  
	            //等待服务器端监听端口关闭
	            f.channel().closeFuture().sync();  
	        } finally {  
	        	//优雅的退出，释放线程组资源
	            workerGroup.shutdownGracefully();  
	            bossGroup.shutdownGracefully();  
	        }  
	    }  
	 
	 public static void main(String[] args) throws Exception {  
	        HelloServer server = new HelloServer();  
	        System.out.println("服务器启动。。。");
	        server.start(8000);  
	    }  
}
