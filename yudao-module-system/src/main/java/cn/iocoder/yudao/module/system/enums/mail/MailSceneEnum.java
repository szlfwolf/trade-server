package cn.iocoder.yudao.module.system.enums.mail;

import cn.hutool.core.util.ArrayUtil;
import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 用户短信验证码发送场景的枚举
 *
 * @author 芋道源码
 */
@Getter
@AllArgsConstructor
public enum MailSceneEnum implements ArrayValuable<Integer> {

    MEMBER_LOGIN(1, "user-mail-register", "会员用户 - 邮箱登陆"),
//    MEMBER_UPDATE_PASSWORD(2, "user-update-password", "会员用户 - 修改密码"),
//    MEMBER_RESET_PASSWORD(3, "user-reset-password", "会员用户 - 忘记密码")
    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(MailSceneEnum::getScene).toArray(Integer[]::new);

    /**
     * 验证场景的编号
     */
    private final Integer scene;
    /**
     * 模版编码
     */
    private final String templateCode;
    /**
     * 描述
     */
    private final String description;

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

    public static MailSceneEnum getCodeByScene(Integer scene) {
        return ArrayUtil.firstMatch(sceneEnum -> sceneEnum.getScene().equals(scene),
                values());
    }

}
