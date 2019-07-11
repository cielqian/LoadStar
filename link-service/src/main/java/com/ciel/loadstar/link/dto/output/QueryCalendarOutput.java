package com.ciel.loadstar.link.dto.output;

import com.ciel.loadstar.link.entity.DailyStatistical;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/7/11 15:36
 */
@Data
public class QueryCalendarOutput extends DailyStatistical {
    String day;

    public String getDay(){
        return DateFormatUtils.format(getDate(), "YYYYMMdd");
    }
}
