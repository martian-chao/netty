package netty.objectcoder.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.objectcoder.bean.Person;

/**
 *  向Server发送Person对象
 * @author yanChaoLiu
 *
 */
public class ClientInitHandler extends ChannelInboundHandlerAdapter {
	 private static Logger   logger  = LoggerFactory.getLogger(ClientInitHandler.class);  
	    @Override  
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
	        logger.info("HelloClientIntHandler.channelActive");  
//	        System.out.println("HelloClientIntHandler.channelActive");
	        Person person = new Person();  
	        person.setName("liu");  
	        person.setSex("man");  
	        person.setAge(99);  
	        ctx.write(person);  
	        ctx.flush();  
	    }  
}
