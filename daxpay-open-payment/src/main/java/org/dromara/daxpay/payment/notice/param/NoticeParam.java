package org.dromara.daxpay.payment.notice.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import org.dromara.daxpay.payment.notice.enums.NoticeStatusEnum;
import org.dromara.daxpay.payment.notice.enums.NoticeTargetEnum;

import java.util.List;

/**
 * Notice parameter for create/update
 * @author xxm
 * @since 2024/12/05
 */
@Data
@Accessors(chain = true)
@Schema(title = "Notice Parameter")
public class NoticeParam {

    @Schema(description = "ID (required for update)")
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Schema(description = "Notice title")
    private String title;

    @NotBlank(message = "Content cannot be empty")
    @Schema(description = "Notice content")
    private String content;

    /**
     * Target type
     * @see NoticeTargetEnum
     */
    @NotBlank(message = "Target type cannot be empty")
    @Schema(description = "Target type: all or specific")
    private String targetType;

    @Schema(description = "Target merchant numbers (used when targetType is specific)")
    private List<String> targetMchNos;

    /**
     * Notice status
     * @see NoticeStatusEnum
     */
    @Schema(description = "Notice status")
    private String status;

    @Schema(description = "Sort order")
    private Integer sortNo;

    @Schema(description = "Whether this is a pinned notice")
    private Boolean pinned;
}
