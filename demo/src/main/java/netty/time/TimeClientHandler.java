package netty.time;

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
	private  ByteBuf firstMsg;
	private byte [] req;
	public TimeClientHandler() {
		req = "QUERY TIME ORDER".getBytes();
		
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf =(ByteBuf) msg;
		byte [] responseMsg= new byte[buf.readableBytes()];
		buf.readBytes(responseMsg);
		String msg1 = new String(responseMsg, "UTF-8");
		System.out.println("上线时间："+msg1);
	}
	
	// 连接成功后，向server发送消息 
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 100; i++) {
			firstMsg=Unpooled.buffer(req.length);
			firstMsg.writeBytes(req);
			ctx.writeAndFlush(firstMsg);
		}
		
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (this==ctx.pipeline().last()) {
			System.out.println("用户异常下线");
		}
		ctx.channel().close();
		ctx.close();
	}

}
