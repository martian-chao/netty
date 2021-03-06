package netty.objectcoder.server2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.objectcoder.bean.Person;

public class BusinessHandler extends ChannelInboundHandlerAdapter{
	private Logger  logger  = LoggerFactory.getLogger(BusinessHandler.class);  
	  
    @Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
        Person person = (Person) msg;  
        logger.info("BusinessHandler read msg from client:" + person);  
//        System.out.println("BusinessHandler read msg from client :" + person);
    }  
  
    @Override  
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {  
        ctx.flush();  
    }

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (this==ctx.pipeline().last()) {
			System.out.println("服务异常:"+cause);
		}
	}  
    
    
}
