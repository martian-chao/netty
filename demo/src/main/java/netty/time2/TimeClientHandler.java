package netty.time2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
/**
 * 实际业务处理
 * @author yanChaoLiu
 *
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter{
	private byte [] req;
	//分割符
	private static String entry ="\r\n";
	//计数器
	private int  counter; 
	
	public TimeClientHandler() {
		req = ("QUERY TIME ORDER"+entry).getBytes();
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String)msg;
		
//		ByteBuf buf =(ByteBuf) msg;
//		byte [] responseMsg= new byte[buf.readableBytes()];
//		buf.readBytes(responseMsg);
//		String msg1 = new String(responseMsg, "UTF-8");
		System.out.println("第"+(++counter)+"次更新时间："+body);
	}
	
	// 连接成功后，向server发送消息 
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		ByteBuf buf=null;
		for (int i = 0; i < 100; i++) {
			//这里体现了Netty的高效性
			buf=Unpooled.buffer(req.length);
			buf.writeBytes(req);
			ctx.writeAndFlush(buf);
		}
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (this==ctx.pipeline().last()) {
			System.out.println("用户:"+ctx.channel().id()+" 异常下线");
		}
		ctx.channel().close();
		ctx.close();
	}

}
