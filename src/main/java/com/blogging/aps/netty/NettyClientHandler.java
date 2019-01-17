package com.blogging.aps.netty;


import com.blogging.aps.model.constant.NettyHeader;
import com.blogging.aps.model.entity.NettyReqEntity;
import com.blogging.aps.model.entity.NettyRespEntity;
import com.blogging.aps.model.eureka.RegistryInfo;
import com.blogging.aps.support.spring.ApplicationContextCache;
import com.blogging.aps.support.utils.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

/**
 * @author techoneduan
 * @date 2018/12/15
 *
 * netty客户端处理器
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClientHandler.class);

    private ByteBuf MSG;

    private static ChannelFuture future = null;


    public NettyClientHandler () {
        /**
         * 连接时扫描组装注册信息
         */
        NettyReqEntity nettyReqEntity = new NettyReqEntity();
        RegistryInfo registryInfo = ApplicationContextCache.getRegistryInfo();
        nettyReqEntity.setRequestId(UUID.randomUUID().toString());
        nettyReqEntity.setHeader(NettyHeader.REGISTRY);
        nettyReqEntity.setParams(JsonUtil.toString(registryInfo));
        if (null != registryInfo.getServiceConfigs() && registryInfo.getServiceConfigs().size() > 0) {
            byte[] req = JsonUtil.toString(nettyReqEntity).getBytes();
            MSG = Unpooled.buffer(req.length);
            MSG.writeBytes(req);
        }
    }

    @Override
    public void channelActive (ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(MSG);
    }

    @Override
    public void channelRead (ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        LOG.info("response:{}", body);
        NettyRespEntity respEntity = JsonUtil.toBean(body, NettyRespEntity.class);
        if (respEntity == null)
            return;
        /**
         * 处理不同请求 策略模式
         */
        ApplicationContextCache.getFactoryListHolder().getNettyService().getBean(respEntity.getRespType()).dealRequest(respEntity);

    }

    @Override
    public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
