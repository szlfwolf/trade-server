package cn.iocoder.yudao.module.system.service.mail;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.iocoder.yudao.module.system.api.mail.dto.code.MailCodeSendReqDTO;
import cn.iocoder.yudao.module.system.api.mail.dto.code.MailCodeUseReqDTO;
import cn.iocoder.yudao.module.system.api.mail.dto.code.MailCodeValidateReqDTO;
import cn.iocoder.yudao.module.system.api.sms.dto.code.SmsCodeValidateReqDTO;
import cn.iocoder.yudao.module.system.dal.dataobject.mail.MailCodeDO;
import cn.iocoder.yudao.module.system.dal.dataobject.sms.SmsCodeDO;
import cn.iocoder.yudao.module.system.dal.mysql.mail.MailCodeMapper;
import cn.iocoder.yudao.module.system.enums.mail.MailSceneEnum;
import cn.iocoder.yudao.module.system.enums.sms.SmsSceneEnum;
import cn.iocoder.yudao.module.system.framework.mail.config.MailCodeProperties;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;

import static cn.hutool.core.util.RandomUtil.randomInt;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.isToday;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 短信验证码 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class MailCodeServiceImpl implements MailCodeService {

    @Resource
    private MailCodeProperties mailCodeProperties;

    @Resource
    private MailCodeMapper mailCodeMapper;

    @Resource
    private MailSendService mailSendService;

    @Override
    public void sendMailCode(MailCodeSendReqDTO reqDTO) {
        MailSceneEnum sceneEnum = MailSceneEnum.getCodeByScene(reqDTO.getScene());
        Assert.notNull(sceneEnum, "验证码场景({}) 查找不到配置", reqDTO.getScene());
        // 创建验证码
        String code = createSmsCode(reqDTO.getMobile(), reqDTO.getScene(), reqDTO.getCreateIp());
        // 发送验证码
        mailSendService.sendSingleMail(Arrays.asList(reqDTO.getMobile()), null, null,null,
                null,
                sceneEnum.getTemplateCode(), MapUtil.of("code", code));
    }

    private String createSmsCode(String mail, Integer scene, String ip) {
        // 校验是否可以发送验证码，不用筛选场景
        cn.iocoder.yudao.module.system.dal.dataobject.mail.MailCodeDO lastSmsCode = mailCodeMapper.selectLastByMail(mail, null, null);
        if (lastSmsCode != null) {
            if (LocalDateTimeUtil.between(lastSmsCode.getCreateTime(), LocalDateTime.now()).toMillis()
                    < mailCodeProperties.getSendFrequency().toMillis()) { // 发送过于频繁
                throw exception(SMS_CODE_SEND_TOO_FAST);
            }
            if (isToday(lastSmsCode.getCreateTime()) && // 必须是今天，才能计算超过当天的上限
                    lastSmsCode.getTodayIndex() >= mailCodeProperties.getSendMaximumQuantityPerDay()) { // 超过当天发送的上限。
                throw exception(SMS_CODE_EXCEED_SEND_MAXIMUM_QUANTITY_PER_DAY);
            }
            // TODO 芋艿：提升，每个 IP 每天可发送数量
            // TODO 芋艿：提升，每个 IP 每小时可发送数量
        }

        // 创建验证码记录
        String code = String.format("%0" + mailCodeProperties.getEndCode().toString().length() + "d",
                randomInt(mailCodeProperties.getBeginCode(), mailCodeProperties.getEndCode() + 1));
        cn.iocoder.yudao.module.system.dal.dataobject.mail.MailCodeDO newSmsCode = cn.iocoder.yudao.module.system.dal.dataobject.mail.MailCodeDO.builder().mail(mail).code(code).scene(scene)
                .todayIndex(lastSmsCode != null && isToday(lastSmsCode.getCreateTime()) ? lastSmsCode.getTodayIndex() + 1 : 1)
                .createIp(ip).used(false).build();
        mailCodeMapper.insert(newSmsCode);
        return code;
    }

    @Override
    public void useMailCode(MailCodeUseReqDTO reqDTO) {
        // 检测验证码是否有效
        MailCodeDO lastSmsCode = validateMailCode0(reqDTO.getMobile(), reqDTO.getCode(), reqDTO.getScene());
        // 使用验证码
        mailCodeMapper.updateById(cn.iocoder.yudao.module.system.dal.dataobject.mail.MailCodeDO.builder().id(lastSmsCode.getId())
                .used(true).usedTime(LocalDateTime.now()).usedIp(reqDTO.getUsedIp()).build());
    }

    @Override
    public void validateMailCode(MailCodeValidateReqDTO reqDTO) {
        validateMailCode0(reqDTO.getMail(), reqDTO.getCode(), reqDTO.getScene());
    }

    private MailCodeDO validateMailCode0(String mobile, String code, Integer scene) {
        // 校验验证码
        MailCodeDO lastSmsCode = mailCodeMapper.selectLastByMail(mobile, code, scene);
        // 若验证码不存在，抛出异常
        if (lastSmsCode == null) {
            throw exception(SMS_CODE_NOT_FOUND);
        }
        // 超过时间
        if (LocalDateTimeUtil.between(lastSmsCode.getCreateTime(), LocalDateTime.now()).toMillis()
                >= mailCodeProperties.getExpireTimes().toMillis()) { // 验证码已过期
            throw exception(SMS_CODE_EXPIRED);
        }
        // 判断验证码是否已被使用
        if (Boolean.TRUE.equals(lastSmsCode.getUsed())) {
            throw exception(SMS_CODE_USED);
        }
        return lastSmsCode;
    }

}
