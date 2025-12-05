package org.dromara.daxpay.payment.notice.controller;

import cn.bootx.platform.core.annotation.ClientCode;
import cn.bootx.platform.core.annotation.RequestGroup;
import cn.bootx.platform.core.annotation.RequestPath;
import cn.bootx.platform.core.rest.Res;
import cn.bootx.platform.core.rest.param.PageParam;
import cn.bootx.platform.core.rest.result.PageResult;
import cn.bootx.platform.core.rest.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.core.trans.anno.TransMethodResult;
import org.dromara.daxpay.payment.common.code.DaxPayCode;
import org.dromara.daxpay.payment.notice.result.NoticeResult;
import org.dromara.daxpay.payment.notice.service.NoticeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Notice controller for merchants
 * @author xxm
 * @since 2024/12/05
 */
@Validated
@Tag(name = "Merchant Notice")
@RestController
@RequestMapping("/notice")
@ClientCode({DaxPayCode.Client.MERCHANT})
@RequestGroup(groupCode = "Notice", groupName = "Merchant Notice", moduleCode = "notice", moduleName = "(DaxPay)Notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @TransMethodResult
    @RequestPath("Page Query Notices")
    @Operation(summary = "Page Query Notices")
    @GetMapping("/page")
    public Result<PageResult<NoticeResult>> page(PageParam pageParam) {
        return Res.ok(noticeService.page(pageParam));
    }

    @TransMethodResult
    @RequestPath("Find Notice By ID")
    @Operation(summary = "Find Notice By ID")
    @GetMapping("/findById")
    public Result<NoticeResult> findById(@NotNull(message = "Notice ID cannot be empty") Long id) {
        return Res.ok(noticeService.findById(id));
    }

    @RequestPath("Mark Notice As Read")
    @Operation(summary = "Mark Notice As Read")
    @PostMapping("/markAsRead")
    public Result<Void> markAsRead(@NotNull(message = "Notice ID cannot be empty") Long noticeId) {
        noticeService.markAsRead(noticeId);
        return Res.ok();
    }

    @RequestPath("Mark All Notices As Read")
    @Operation(summary = "Mark All Notices As Read")
    @PostMapping("/markAllAsRead")
    public Result<Void> markAllAsRead() {
        noticeService.markAllAsRead();
        return Res.ok();
    }

    @RequestPath("Get Unread Notice Count")
    @Operation(summary = "Get Unread Notice Count")
    @GetMapping("/unreadCount")
    public Result<Long> getUnreadCount() {
        return Res.ok(noticeService.getUnreadCount());
    }
}
