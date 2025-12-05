package org.dromara.daxpay.payment.notice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Notice target enumeration
 * @author xxm
 * @since 2024/12/05
 */
@Getter
@AllArgsConstructor
public enum NoticeTargetEnum {

    /** All merchants */
    ALL("all", "All Merchants"),
    /** Specific merchants */
    SPECIFIC("specific", "Specific Merchants");

    private final String code;
    private final String name;

    /**
     * Find enum by code
     */
    public static NoticeTargetEnum findByCode(String code) {
        for (NoticeTargetEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
