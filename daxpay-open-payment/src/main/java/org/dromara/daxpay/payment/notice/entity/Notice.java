package org.dromara.daxpay.payment.notice.entity;

import cn.bootx.platform.common.mybatisplus.base.MpBaseEntity;
import cn.bootx.platform.common.mybatisplus.function.ToResult;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.dromara.daxpay.payment.notice.convert.NoticeConvert;
import org.dromara.daxpay.payment.notice.enums.NoticeStatusEnum;
import org.dromara.daxpay.payment.notice.enums.NoticeTargetEnum;
import org.dromara.daxpay.payment.notice.result.NoticeResult;

import java.time.LocalDateTime;

/**
 * Notice entity for platform announcements
 * @author xxm
 * @since 2024/12/05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FieldNameConstants
@Accessors(chain = true)
@TableName("pay_notice")
public class Notice extends MpBaseEntity implements ToResult<NoticeResult> {

    /** Notice title */
    private String title;

    /** Notice content */
    private String content;

    /**
     * Target type: all merchants or specific merchants
     * @see NoticeTargetEnum
     */
    private String targetType;

    /** Target merchant numbers, comma separated (used when targetType is specific) */
    private String targetMchNos;

    /**
     * Notice status
     * @see NoticeStatusEnum
     */
    private String status;

    /** Publish time */
    private LocalDateTime publishTime;

    /** Publisher user id */
    private Long publisherId;

    /** Sort order, lower value means higher priority */
    private Integer sortNo;

    /** Whether this is a top/pinned notice */
    private Boolean pinned;

    @Override
    public NoticeResult toResult() {
        return NoticeConvert.CONVERT.toResult(this);
    }
}
