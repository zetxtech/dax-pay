package org.dromara.daxpay.payment.notice.dao;

import cn.bootx.platform.common.mybatisplus.impl.BaseManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.daxpay.payment.notice.entity.NoticeRead;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Notice read record manager
 * @author xxm
 * @since 2024/12/05
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class NoticeReadManager extends BaseManager<NoticeReadMapper, NoticeRead> {

    /**
     * Check if a notice has been read by a user
     */
    public boolean existsByNoticeIdAndUserId(Long noticeId, Long userId) {
        return this.lambdaQuery()
                .eq(NoticeRead::getNoticeId, noticeId)
                .eq(NoticeRead::getUserId, userId)
                .exists();
    }

    /**
     * Check if a notice has been read by merchant
     */
    public boolean existsByNoticeIdAndMchNo(Long noticeId, String mchNo) {
        return this.lambdaQuery()
                .eq(NoticeRead::getNoticeId, noticeId)
                .eq(NoticeRead::getMchNo, mchNo)
                .exists();
    }

    /**
     * Find all read notice ids for a merchant
     */
    public List<Long> findReadNoticeIdsByMchNo(String mchNo) {
        return this.lambdaQuery()
                .eq(NoticeRead::getMchNo, mchNo)
                .list()
                .stream()
                .map(NoticeRead::getNoticeId)
                .toList();
    }

    /**
     * Find all read notice ids for a user
     */
    public List<Long> findReadNoticeIdsByUserId(Long userId) {
        return this.lambdaQuery()
                .eq(NoticeRead::getUserId, userId)
                .list()
                .stream()
                .map(NoticeRead::getNoticeId)
                .toList();
    }

    /**
     * Delete read records by notice id
     */
    public boolean deleteByNoticeId(Long noticeId) {
        return this.deleteByField(NoticeRead::getNoticeId, noticeId);
    }
}
