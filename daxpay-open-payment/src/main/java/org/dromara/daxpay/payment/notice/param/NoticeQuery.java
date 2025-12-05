package org.dromara.daxpay.payment.notice.param;

import cn.bootx.platform.core.annotation.QueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.dromara.daxpay.payment.notice.enums.NoticeStatusEnum;
import org.dromara.daxpay.payment.notice.enums.NoticeTargetEnum;

/**
 * Notice query parameter
 * @author xxm
 * @since 2024/12/05
 */
@Data
@QueryParam(type = QueryParam.CompareTypeEnum.LIKE)
@Accessors(chain = true)
@Schema(title = "Notice Query Parameter")
public class NoticeQuery {

    @Schema(description = "Notice title")
    private String title;

    /**
     * Target type
     * @see NoticeTargetEnum
     */
    @QueryParam(type = QueryParam.CompareTypeEnum.EQ)
    @Schema(description = "Target type")
    private String targetType;

    /**
     * Notice status
     * @see NoticeStatusEnum
     */
    @QueryParam(type = QueryParam.CompareTypeEnum.EQ)
    @Schema(description = "Notice status")
    private String status;

    @QueryParam(type = QueryParam.CompareTypeEnum.EQ)
    @Schema(description = "Whether pinned")
    private Boolean pinned;
}
