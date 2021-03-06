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
 * 客户端1发送Person格式的协议
 * @author yanChaoLiu
 *
 */
public class Client {
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
                    ch.pipeline().addLast(new PersonEncoder());  
                    Person person = new Person();  
                    person.setName("liuyc");  
                    person.setSex("man");  
                    person.setAge(99);  
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
        Client client = new Client();  
        client.connect("127.0.0.1", 8000);  
    }  
}
