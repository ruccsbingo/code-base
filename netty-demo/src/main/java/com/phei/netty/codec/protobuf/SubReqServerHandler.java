package com.phei.netty.codec.protobuf;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zhangbing on 16/10/8.
 */
@ChannelHandler.Sharable
public class SubReqServerHandler extends ChannelHandlerAdapter{

    public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception{
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) o;

        if ("Lilinfeng".equalsIgnoreCase(req.getUserName())) {
            System.out.println("Server accept clinet subscribe req : [" + req.toString() + "]");
            ctx.writeAndFlush(resp(req.getSubReqId()));
        }
    }

    private SubscribeRespProto.SubscribeResp resp(int subReqID){
       SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqId(subReqID);
        builder.setRespCode(0);
        builder.setDesc("Netty book order succeed, 3 days later, sent to the designated address");

        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
