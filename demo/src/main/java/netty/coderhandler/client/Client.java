package netty.coderhandler.client;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;
import netty.coderhandler.server.BusinessHandler;

/**
 * Client 与Server端建立连接，并向Server端发送HttpRequest请求
 * @author yanChaoLiu
 *
 */
public class Client {
	private Logger  logger  = LoggerFactory.getLogger(Client.class);
	public void connect(String host, int port) throws Exception {  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
  
        try {  
            Bootstrap b = new Bootstrap();   
            b.group(workerGroup);   
            b.channel(NioSocketChannel.class);   
            b.option(ChannelOption.SO_KEEPALIVE, true);   
            b.handler(new ChannelInitializer<SocketChannel>() {  
                @Override  
                public void initChannel(SocketChannel ch) throws Exception {  
                    ch.pipeline().addLast(new HttpResponseDecoder());  
                    ch.pipeline().addLast(new HttpRequestEncoder());  
                    ch.pipeline().addLast(new ClientInitHandler());  
                }  
            });  
  
            // Start the client.  
            ChannelFuture f = b.connect(host, port).sync();  
            logger.info("http client");
            URI uri = new URI("http://127.0.0.1:8000");  
            String msg = "Are you ok?";  
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,  
                    uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes()));  
  
            request.headers().set(HttpHeaders.Names.HOST, host);  
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);  
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());  
            f.channel().write(request);  
            f.channel().flush();  
            f.channel().closeFuture().sync();  
        } finally {  
            workerGroup.shutdownGracefully();  
        }  
  
    }  
  
    public static void main(String[] args) throws Exception {  
        Client client = new Client();  
        client.connect("127.0.0.1", 8000);  
    }  
}
