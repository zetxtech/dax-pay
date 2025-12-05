package org.dromara.daxpay.payment.notice.dao;

import cn.bootx.platform.common.mybatisplus.impl.BaseManager;
import cn.bootx.platform.common.mybatisplus.query.generator.QueryGenerator;
import cn.bootx.platform.common.mybatisplus.util.MpUtil;
import cn.bootx.platform.core.rest.param.PageParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.daxpay.payment.notice.entity.Notice;
import org.dromara.daxpay.payment.notice.enums.NoticeStatusEnum;
import org.dromara.daxpay.payment.notice.enums.NoticeTargetEnum;
import org.dromara.daxpay.payment.notice.param.NoticeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Notice manager
 * @author xxm
 * @since 2024/12/05
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class NoticeManager extends BaseManager<NoticeMapper, Notice> {

    /**
     * Page query for admin
     */
    public Page<Notice> page(PageParam pageParam, NoticeQuery query) {
        Page<Notice> mpPage = MpUtil.getMpPage(pageParam);
        QueryWrapper<Notice> wrapper = QueryGenerator.generator(query);
        wrapper.orderByDesc(Notice.Fields.pinned)
                .orderByAsc(Notice.Fields.sortNo)
                .orderByDesc(MpUtil.getColumnName(Notice::getCreateTime));
        return this.page(mpPage, wrapper);
    }

    /**
     * Page query for merchant
     * Only returns published notices that target the merchant
     */
    public Page<Notice> pageForMerchant(PageParam pageParam, String mchNo) {
        Page<Notice> mpPage = MpUtil.getMpPage(pageParam);
        return this.lambdaQuery()
                .eq(Notice::getStatus, NoticeStatusEnum.PUBLISHED.getCode())
                .and(w -> w.eq(Notice::getTargetType, NoticeTargetEnum.ALL.getCode())
                        .or()
                        .like(Notice::getTargetMchNos, mchNo))
                .orderByDesc(Notice::getPinned)
                .orderByAsc(Notice::getSortNo)
                .orderByDesc(Notice::getPublishTime)
                .page(mpPage);
    }

    /**
     * Find all published notices for a merchant
     */
    public List<Notice> findPublishedForMerchant(String mchNo) {
        return this.lambdaQuery()
                .eq(Notice::getStatus, NoticeStatusEnum.PUBLISHED.getCode())
                .and(w -> w.eq(Notice::getTargetType, NoticeTargetEnum.ALL.getCode())
                        .or()
                        .like(Notice::getTargetMchNos, mchNo))
                .orderByDesc(Notice::getPinned)
                .orderByAsc(Notice::getSortNo)
                .orderByDesc(Notice::getPublishTime)
                .list();
    }

    /**
     * Count unread notices for a merchant
     */
    public long countUnreadForMerchant(String mchNo, List<Long> readNoticeIds) {
        var query = this.lambdaQuery()
                .eq(Notice::getStatus, NoticeStatusEnum.PUBLISHED.getCode())
                .and(w -> w.eq(Notice::getTargetType, NoticeTargetEnum.ALL.getCode())
                        .or()
                        .like(Notice::getTargetMchNos, mchNo));
        if (readNoticeIds != null && !readNoticeIds.isEmpty()) {
            query.notIn(Notice::getId, readNoticeIds);
        }
        return query.count();
    }
}
