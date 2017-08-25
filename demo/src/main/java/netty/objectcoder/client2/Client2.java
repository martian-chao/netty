package netty.objectcoder.client2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.objectcoder.bean.Person;
/**
 * 客户端2发送String格式的协议：Client2 StringEncoder 
 * 同样使用了客户端1中定义的ClientInitHandler 进行数据发送操作。
 * @author yanChaoLiu
 *
 */
public class Client2 {
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
	                    ch.pipeline().addLast(new StringEncoder());  
	                    Person person = new Person();  
	                    person.setName("guoxy");  
	                    person.setSex("girl");  
	                    person.setAge(4);  
	                    ch.pipeline().addLast(new ClientInitHandler(person));  
	                }  
	            });  
	  
	            ChannelFuture f = b.connect(host, port).sync();  
	            f.channel().closeFuture().sync();  
	        } finally {  
	            workerGroup.shutdownGracefully();  
	        }  
	  
	    }  
	  
	    public static void main(String[] args) throws Exception {  
	        Client2 client = new Client2();  
	        client.connect("127.0.0.1", 8000);  
	    }  
}
