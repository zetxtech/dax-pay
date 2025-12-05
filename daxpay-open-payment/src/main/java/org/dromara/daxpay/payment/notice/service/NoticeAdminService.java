package org.dromara.daxpay.payment.notice.service;

import cn.bootx.platform.common.mybatisplus.util.MpUtil;
import cn.bootx.platform.core.exception.DataNotExistException;
import cn.bootx.platform.core.rest.param.PageParam;
import cn.bootx.platform.core.rest.result.PageResult;
import cn.bootx.platform.starter.auth.util.SecurityUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.daxpay.payment.notice.convert.NoticeConvert;
import org.dromara.daxpay.payment.notice.dao.NoticeManager;
import org.dromara.daxpay.payment.notice.dao.NoticeReadManager;
import org.dromara.daxpay.payment.notice.entity.Notice;
import org.dromara.daxpay.payment.notice.enums.NoticeStatusEnum;
import org.dromara.daxpay.payment.notice.param.NoticeParam;
import org.dromara.daxpay.payment.notice.param.NoticeQuery;
import org.dromara.daxpay.payment.notice.result.NoticeResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Notice admin service for platform operators
 * @author xxm
 * @since 2024/12/05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeAdminService {

    private final NoticeManager noticeManager;
    private final NoticeReadManager noticeReadManager;

    /**
     * Create a new notice
     */
    public void add(NoticeParam param) {
        Notice notice = NoticeConvert.CONVERT.toEntity(param);
        // Handle target merchant numbers
        if (CollUtil.isNotEmpty(param.getTargetMchNos())) {
            notice.setTargetMchNos(String.join(",", param.getTargetMchNos()));
        }
        // Set default values
        if (Objects.isNull(notice.getStatus())) {
            notice.setStatus(NoticeStatusEnum.DRAFT.getCode());
        }
        if (Objects.isNull(notice.getSortNo())) {
            notice.setSortNo(0);
        }
        if (Objects.isNull(notice.getPinned())) {
            notice.setPinned(false);
        }
        noticeManager.save(notice);
    }

    /**
     * Update a notice
     */
    public void update(NoticeParam param) {
        Notice notice = noticeManager.findById(param.getId())
                .orElseThrow(() -> new DataNotExistException("Notice not found"));

        BeanUtil.copyProperties(param, notice, CopyOptions.create()
                .ignoreNullValue()
                .setIgnoreProperties("id", "targetMchNos"));

        // Handle target merchant numbers
        if (CollUtil.isNotEmpty(param.getTargetMchNos())) {
            notice.setTargetMchNos(String.join(",", param.getTargetMchNos()));
        } else if (param.getTargetMchNos() != null) {
            notice.setTargetMchNos(null);
        }

        noticeManager.updateById(notice);
    }

    /**
     * Publish a notice
     */
    public void publish(Long id) {
        Notice notice = noticeManager.findById(id)
                .orElseThrow(() -> new DataNotExistException("Notice not found"));

        notice.setStatus(NoticeStatusEnum.PUBLISHED.getCode());
        notice.setPublishTime(LocalDateTime.now());
        notice.setPublisherId(SecurityUtil.getUserIdOrDefaultId());

        noticeManager.updateById(notice);
    }

    /**
     * Disable a notice
     */
    public void disable(Long id) {
        Notice notice = noticeManager.findById(id)
                .orElseThrow(() -> new DataNotExistException("Notice not found"));

        notice.setStatus(NoticeStatusEnum.DISABLED.getCode());
        noticeManager.updateById(notice);
    }

    /**
     * Delete a notice
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        noticeManager.deleteById(id);
        // Also delete read records
        noticeReadManager.deleteByNoticeId(id);
    }

    /**
     * Page query
     */
    public PageResult<NoticeResult> page(PageParam pageParam, NoticeQuery query) {
        return MpUtil.toPageResult(noticeManager.page(pageParam, query));
    }

    /**
     * Find by id
     */
    public NoticeResult findById(Long id) {
        return noticeManager.findById(id)
                .map(Notice::toResult)
                .orElseThrow(() -> new DataNotExistException("Notice not found"));
    }
}
