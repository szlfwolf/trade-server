package cn.iocoder.yudao.module.c2c.job;

import cn.iocoder.yudao.module.c2c.service.trade.TradeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class C2CExpireJob {

    @Resource
    private TradeService tradeService;

    @Scheduled(fixedRate = 60000)
    public void processExpiredTrades() {
        tradeService.handleExpiredTrades();
    }
}

