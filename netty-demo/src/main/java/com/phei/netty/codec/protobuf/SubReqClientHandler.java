package com.phei.netty.codec.protobuf;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangbing on 16/10/9.
 */
@ChannelHandler.Sharable
public class SubReqClientHandler extends ChannelHandlerAdapter{

    public SubReqClientHandler(){

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        for (int i = 0; i < 10; ++i){
            ctx.write(subReq(i));
        }

        ctx.flush();
    }

    private SubscribeReqProto.SubscribeReq subReq(int i) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(i);
        builder.setUserName("Lilinfeng");
        builder.setProductName("Netty Book For ProtoBuf");
        List<String> address = new ArrayList<>();
        address.add("NanJing");
        address.add("Beijing");
        address.add("Shenzheng");
        builder.addAllAddress(address);
        return builder.build();
    }

    public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {
        System.out.println("Receive server response : [" + o + "]");
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
