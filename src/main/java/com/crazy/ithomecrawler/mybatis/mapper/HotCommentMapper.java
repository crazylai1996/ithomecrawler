package com.crazy.ithomecrawler.mybatis.mapper;

import com.crazy.ithomecrawler.domain.HotComment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface HotCommentMapper {
    @Insert("INSERT INTO hot_comment(vCommentId,vUser,vComment,iUp,iDown,vPosandTime,vMobile,vArticleUrl) VALUES(#{commentId},#{user},#{comment},#{up},#{down},#{posandtime},#{mobile},#{articleUrl})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public void addHotComment(HotComment hotComment);
}
