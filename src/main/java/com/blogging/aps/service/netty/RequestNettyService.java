package com.blogging.aps.service.netty;



import com.blogging.aps.model.constant.NettyHeader;
import com.blogging.aps.model.entity.NettyRespEntity;
import com.blogging.aps.model.syncMap.SyncMap;
import com.blogging.aps.service.netty.AbstractNettyService;
import org.springframework.stereotype.Service;

/**
 * @author techoneduan
 * @date 2018/12/17
 */
@Service
public class RequestNettyService extends AbstractNettyService {

    @Override
    public void dealRequest (NettyRespEntity respEntity) {
        if (!SyncMap.hasKey(respEntity.getRequestId())) {
            SyncMap.put(respEntity.getRequestId(), respEntity.getResponse());
        }
    }

    @Override
    public boolean matching (String factor) {
        return NettyHeader.REQUEST.equals(factor);
    }
}
