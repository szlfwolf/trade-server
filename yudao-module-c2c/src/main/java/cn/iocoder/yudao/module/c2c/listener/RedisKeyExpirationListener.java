package cn.iocoder.yudao.module.c2c.listener;

import cn.iocoder.yudao.module.c2c.service.trade.TradeService;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.Resource;

@Configuration
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Resource
    private TradeService tradeService;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();

        if (expiredKey.startsWith("c2c:trade:pay:")) {
            Long tradeId = Long.valueOf(expiredKey.replace("c2c:trade:pay:", ""));
            tradeService.handleBuyerTimeout(tradeId);
        } else if (expiredKey.startsWith("c2c:trade:release:")) {
            Long tradeId = Long.valueOf(expiredKey.replace("c2c:trade:release:", ""));
            tradeService.handleSellerTimeout(tradeId);
        }
    }
}

