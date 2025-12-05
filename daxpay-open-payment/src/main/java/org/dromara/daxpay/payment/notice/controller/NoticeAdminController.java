package org.dromara.daxpay.payment.notice.controller;

import cn.bootx.platform.core.annotation.ClientCode;
import cn.bootx.platform.core.annotation.OperateLog;
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
import org.dromara.daxpay.payment.notice.param.NoticeParam;
import org.dromara.daxpay.payment.notice.param.NoticeQuery;
import org.dromara.daxpay.payment.notice.result.NoticeResult;
import org.dromara.daxpay.payment.notice.service.NoticeAdminService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Notice admin controller for platform operators
 * @author xxm
 * @since 2024/12/05
 */
@Validated
@Tag(name = "Platform Notice Management")
@RestController
@RequestMapping("/admin/notice")
@ClientCode({DaxPayCode.Client.ADMIN})
@RequestGroup(groupCode = "NoticeAdmin", groupName = "Platform Notice Management", moduleCode = "notice", moduleName = "(DaxPay)Notice Management")
@RequiredArgsConstructor
public class NoticeAdminController {

    private final NoticeAdminService noticeAdminService;

    @RequestPath("Create Notice")
    @Operation(summary = "Create Notice")
    @PostMapping("/add")
    @OperateLog(title = "Create Notice", businessType = OperateLog.BusinessType.ADD, saveParam = true)
    public Result<Void> add(@RequestBody @Validated NoticeParam param) {
        noticeAdminService.add(param);
        return Res.ok();
    }

    @RequestPath("Update Notice")
    @Operation(summary = "Update Notice")
    @PostMapping("/update")
    @OperateLog(title = "Update Notice", businessType = OperateLog.BusinessType.UPDATE, saveParam = true)
    public Result<Void> update(@RequestBody @Validated NoticeParam param) {
        noticeAdminService.update(param);
        return Res.ok();
    }

    @RequestPath("Publish Notice")
    @Operation(summary = "Publish Notice")
    @PostMapping("/publish")
    @OperateLog(title = "Publish Notice", businessType = OperateLog.BusinessType.UPDATE)
    public Result<Void> publish(@NotNull(message = "Notice ID cannot be empty") Long id) {
        noticeAdminService.publish(id);
        return Res.ok();
    }

    @RequestPath("Disable Notice")
    @Operation(summary = "Disable Notice")
    @PostMapping("/disable")
    @OperateLog(title = "Disable Notice", businessType = OperateLog.BusinessType.UPDATE)
    public Result<Void> disable(@NotNull(message = "Notice ID cannot be empty") Long id) {
        noticeAdminService.disable(id);
        return Res.ok();
    }

    @RequestPath("Delete Notice")
    @Operation(summary = "Delete Notice")
    @PostMapping("/delete")
    @OperateLog(title = "Delete Notice", businessType = OperateLog.BusinessType.DELETE)
    public Result<Void> delete(@NotNull(message = "Notice ID cannot be empty") Long id) {
        noticeAdminService.delete(id);
        return Res.ok();
    }

    @TransMethodResult
    @RequestPath("Page Query Notices")
    @Operation(summary = "Page Query Notices")
    @GetMapping("/page")
    public Result<PageResult<NoticeResult>> page(PageParam pageParam, NoticeQuery query) {
        return Res.ok(noticeAdminService.page(pageParam, query));
    }

    @TransMethodResult
    @RequestPath("Find Notice By ID")
    @Operation(summary = "Find Notice By ID")
    @GetMapping("/findById")
    public Result<NoticeResult> findById(@NotNull(message = "Notice ID cannot be empty") Long id) {
        return Res.ok(noticeAdminService.findById(id));
    }
}
