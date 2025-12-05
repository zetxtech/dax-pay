package org.dromara.daxpay.payment.notice.service;

import cn.bootx.platform.common.mybatisplus.util.MpUtil;
import cn.bootx.platform.core.exception.DataNotExistException;
import cn.bootx.platform.core.rest.param.PageParam;
import cn.bootx.platform.core.rest.result.PageResult;
import cn.bootx.platform.starter.auth.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.daxpay.payment.merchant.local.MchContextLocal;
import org.dromara.daxpay.payment.notice.dao.NoticeManager;
import org.dromara.daxpay.payment.notice.dao.NoticeReadManager;
import org.dromara.daxpay.payment.notice.entity.Notice;
import org.dromara.daxpay.payment.notice.entity.NoticeRead;
import org.dromara.daxpay.payment.notice.result.NoticeResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Notice service for merchants
 * @author xxm
 * @since 2024/12/05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeManager noticeManager;
    private final NoticeReadManager noticeReadManager;

    /**
     * Page query notices for current merchant
     */
    public PageResult<NoticeResult> page(PageParam pageParam) {
        String mchNo = MchContextLocal.getMchNo();
        var page = noticeManager.pageForMerchant(pageParam, mchNo);

        // Get read notice ids for current user
        Long userId = SecurityUtil.getUserIdOrDefaultId();
        List<Long> readNoticeIds = noticeReadManager.findReadNoticeIdsByUserId(userId);

        // Convert to result and set read status
        var resultPage = MpUtil.toPageResult(page);
        resultPage.getRecords().forEach(result -> {
            result.setRead(readNoticeIds.contains(result.getId()));
        });

        return resultPage;
    }

    /**
     * Find notice by id
     */
    public NoticeResult findById(Long id) {
        String mchNo = MchContextLocal.getMchNo();
        Long userId = SecurityUtil.getUserIdOrDefaultId();

        Notice notice = noticeManager.findById(id)
                .orElseThrow(() -> new DataNotExistException("Notice not found"));

        NoticeResult result = notice.toResult();

        // Check if read by current user
        boolean read = noticeReadManager.existsByNoticeIdAndUserId(id, userId);
        result.setRead(read);

        return result;
    }

    /**
     * Mark a notice as read
     */
    public void markAsRead(Long noticeId) {
        String mchNo = MchContextLocal.getMchNo();
        Long userId = SecurityUtil.getUserIdOrDefaultId();

        // Check if already read
        if (noticeReadManager.existsByNoticeIdAndUserId(noticeId, userId)) {
            return;
        }

        // Check if notice exists
        if (!noticeManager.existedById(noticeId)) {
            throw new DataNotExistException("Notice not found");
        }

        NoticeRead noticeRead = new NoticeRead()
                .setNoticeId(noticeId)
                .setMchNo(mchNo)
                .setUserId(userId);
        noticeReadManager.save(noticeRead);
    }

    /**
     * Mark all notices as read
     */
    public void markAllAsRead() {
        String mchNo = MchContextLocal.getMchNo();
        Long userId = SecurityUtil.getUserIdOrDefaultId();

        // Get all published notices for this merchant
        List<Notice> notices = noticeManager.findPublishedForMerchant(mchNo);

        // Get already read notice ids
        List<Long> readNoticeIds = noticeReadManager.findReadNoticeIdsByUserId(userId);

        // Mark unread notices as read
        for (Notice notice : notices) {
            if (!readNoticeIds.contains(notice.getId())) {
                NoticeRead noticeRead = new NoticeRead()
                        .setNoticeId(notice.getId())
                        .setMchNo(mchNo)
                        .setUserId(userId);
                noticeReadManager.save(noticeRead);
            }
        }
    }

    /**
     * Get unread notice count for current merchant
     */
    public long getUnreadCount() {
        String mchNo = MchContextLocal.getMchNo();
        Long userId = SecurityUtil.getUserIdOrDefaultId();

        List<Long> readNoticeIds = noticeReadManager.findReadNoticeIdsByUserId(userId);
        return noticeManager.countUnreadForMerchant(mchNo, readNoticeIds);
    }
}
