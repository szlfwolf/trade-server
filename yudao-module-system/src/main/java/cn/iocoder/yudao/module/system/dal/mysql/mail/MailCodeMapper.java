package cn.iocoder.yudao.module.system.dal.mysql.mail;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.mail.MailCodeDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailCodeMapper extends BaseMapperX<MailCodeDO> {

    /**
     * 获得邮箱地址的最后一个验证码
     *
     * @param mail 邮箱地址
     * @param scene 发送场景，选填
     * @param code 验证码 选填
     * @return 验证码
     */
    default MailCodeDO selectLastByMail(String mail, String code, Integer scene) {
        return selectOne(new QueryWrapperX<MailCodeDO>()
                .eq("mail", mail)
                .eqIfPresent("scene", scene)
                .eqIfPresent("code", code)
                .orderByDesc("id")
                .limitN(1));
    }

}
