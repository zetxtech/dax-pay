package org.dromara.daxpay.payment.notice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Notice status enumeration
 * @author xxm
 * @since 2024/12/05
 */
@Getter
@AllArgsConstructor
public enum NoticeStatusEnum {

    /** Draft */
    DRAFT("draft", "Draft"),
    /** Published */
    PUBLISHED("published", "Published"),
    /** Disabled */
    DISABLED("disabled", "Disabled");

    private final String code;
    private final String name;

    /**
     * Find enum by code
     */
    public static NoticeStatusEnum findByCode(String code) {
        for (NoticeStatusEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
