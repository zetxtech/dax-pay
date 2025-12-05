package org.dromara.daxpay.payment.notice.entity;

import cn.bootx.platform.common.mybatisplus.base.MpCreateEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * Notice read record entity
 * Records which merchant users have read which notices
 * @author xxm
 * @since 2024/12/05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FieldNameConstants
@Accessors(chain = true)
@TableName("pay_notice_read")
public class NoticeRead extends MpCreateEntity {

    /** Notice id */
    private Long noticeId;

    /** Merchant number */
    private String mchNo;

    /** User id who read the notice */
    private Long userId;
}
