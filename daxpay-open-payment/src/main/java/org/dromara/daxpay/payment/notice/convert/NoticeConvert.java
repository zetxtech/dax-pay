package org.dromara.daxpay.payment.notice.convert;

import org.dromara.daxpay.payment.notice.entity.Notice;
import org.dromara.daxpay.payment.notice.param.NoticeParam;
import org.dromara.daxpay.payment.notice.result.NoticeResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * Notice converter
 * @author xxm
 * @since 2024/12/05
 */
@Mapper
public interface NoticeConvert {
    NoticeConvert CONVERT = Mappers.getMapper(NoticeConvert.class);

    @Mapping(target = "targetMchNos", ignore = true)
    Notice toEntity(NoticeParam param);

    NoticeResult toResult(Notice entity);

    @Mapping(target = "targetMchNos", ignore = true)
    void copy(NoticeParam param, @MappingTarget Notice entity);
}
