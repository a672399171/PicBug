package com.zzuzl.dao;

import com.zzuzl.model.JsonVo;
import com.zzuzl.model.Pic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/4/9.
 */
@Component
public interface PicDao {
    int batchInsert(@Param("picList") List<Pic> picList);

    List<Pic> getTodayImg(@Param("key") String key);

    String getJson(@Param("key") String key);

    List<JsonVo> listJson();
}
