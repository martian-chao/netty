package netty.coderhandler.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Server 启动netty服务，并注册handler、coder，注意注册的顺序
 * 测试coder 和 handler 的混合使用  
 * @author yanChaoLiu
 *
 */
public class Server {
	 public void start(int port) throws Exception {  
	        EventLoopGroup bossGroup = new NioEventLoopGroup();   
	        EventLoopGroup workerGroup = new NioEventLoopGroup();  
	        try {  
	            ServerBootstrap b = new ServerBootstrap();   
	            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)   
	                    .childHandler(new ChannelInitializer<SocketChannel>() {   
	                                @Override  
	                                public void initChannel(SocketChannel ch) throws Exception {  
	                                    // 都属于ChannelOutboundHandler，逆序执行  
	                                    ch.pipeline().addLast(new HttpResponseEncoder());  
	                                    //过StringEncoder把String转换成httpresponse，发送给客户端
	                                    ch.pipeline().addLast(new StringEncoder());  
	                                      
	                                    // 都属于ChannelIntboundHandler，按照顺序执行  
	                                    ch.pipeline().addLast(new HttpRequestDecoder());  
	                                    //StringDecoder把httprequest转换成String
	                                    ch.pipeline().addLast(new StringDecoder());  
	                                    ch.pipeline().addLast(new BusinessHandler());  
	                                }  
	                            }).option(ChannelOption.SO_BACKLOG, 128)   
	                    .childOption(ChannelOption.SO_KEEPALIVE, true);   
	  
	            ChannelFuture f = b.bind(port).sync();   
	  
	            f.channel().closeFuture().sync();  
	        } finally {  
	            workerGroup.shutdownGracefully();  
	            bossGroup.shutdownGracefully();  
	        }  
	    }  
	  
	    public static void main(String[] args) throws Exception {  
	        Server server = new Server();  
	        System.out.println("启动服务端。。");
	        server.start(8000);  
	    }  
}
