package org.dromara.daxpay.payment.notice.result;

import cn.bootx.platform.core.result.BaseResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dromara.daxpay.payment.notice.enums.NoticeStatusEnum;
import org.dromara.daxpay.payment.notice.enums.NoticeTargetEnum;

import java.time.LocalDateTime;

/**
 * Notice result
 * @author xxm
 * @since 2024/12/05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Schema(title = "Notice Result")
public class NoticeResult extends BaseResult {

    @Schema(description = "Notice title")
    private String title;

    @Schema(description = "Notice content")
    private String content;

    /**
     * Target type
     * @see NoticeTargetEnum
     */
    @Schema(description = "Target type")
    private String targetType;

    @Schema(description = "Target merchant numbers")
    private String targetMchNos;

    /**
     * Notice status
     * @see NoticeStatusEnum
     */
    @Schema(description = "Notice status")
    private String status;

    @Schema(description = "Publish time")
    private LocalDateTime publishTime;

    @Schema(description = "Publisher user id")
    private Long publisherId;

    @Schema(description = "Sort order")
    private Integer sortNo;

    @Schema(description = "Whether pinned")
    private Boolean pinned;

    @Schema(description = "Whether read by current user (for merchant view)")
    private Boolean read;
}
