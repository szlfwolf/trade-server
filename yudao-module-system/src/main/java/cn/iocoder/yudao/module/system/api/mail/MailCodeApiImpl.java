package cn.iocoder.yudao.module.system.api.mail;

import cn.iocoder.yudao.module.system.api.mail.dto.code.MailCodeSendReqDTO;
import cn.iocoder.yudao.module.system.api.mail.dto.code.MailCodeUseReqDTO;
import cn.iocoder.yudao.module.system.api.mail.dto.code.MailCodeValidateReqDTO;
import cn.iocoder.yudao.module.system.service.mail.MailCodeService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 短信验证码 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class MailCodeApiImpl implements MailCodeApi {

    @Resource
    private MailCodeService mailCodeService;

    @Override
    public void sendMailCode(MailCodeSendReqDTO reqDTO) {
        mailCodeService.sendMailCode(reqDTO);
    }

    @Override
    public void useMailCode(MailCodeUseReqDTO reqDTO) {
        mailCodeService.useMailCode(reqDTO);
    }

    @Override
    public void validateMailCode(MailCodeValidateReqDTO reqDTO) {
        mailCodeService.validateMailCode(reqDTO);
    }

}
