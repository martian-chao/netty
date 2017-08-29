package netty.objectcoder.client2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.objectcoder.bean.Person;
/**
 * 向服务端发送Person对象
 * @author yanChaoLiu
 *
 */
public class ClientInitHandler extends ChannelInboundHandlerAdapter{
	 private static Logger   logger  = LoggerFactory.getLogger(ClientInitHandler.class);  
	    private Person person;  
	    public ClientInitHandler(Person person){  
	        this.person = person;  
	    }  
	    @Override  
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
	        logger.info("ClientInitHandler.channelActive");  
//	        System.out.println("ClientInitHandler.channelActive");
	        ctx.write(person);  
	        ctx.flush();  
	    }
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			if (this==ctx.pipeline().last()) {
				System.out.println("客户端异常"+cause);
			}
		}  
	    
	    
}
