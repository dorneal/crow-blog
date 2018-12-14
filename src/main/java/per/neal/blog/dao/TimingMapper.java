package per.neal.blog.dao;

import per.neal.blog.entity.TbTiming;

/**
 * @author neal
 */
public interface TimingMapper {
    /**
     * 更新发布时间
     *
     * @param tbTiming TbTiming
     */
    void updateTiming(TbTiming tbTiming);

    /**
     * 新增定时发布数据
     *
     * @param tbTiming TbTiming
     */
    void saveTiming(TbTiming tbTiming);

    /**
     * 根据文章ID查找该任务
     *
     * @param id 文章ID
     * @return TbTiming
     */
    TbTiming findByArticleId(long id);
}
