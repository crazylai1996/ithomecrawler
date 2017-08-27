package com.crazy.ithomecrawler.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName="hotcomments",type="hotcomment",indexStoreType="fs",shards=5,replicas=1,refreshInterval="-1")
public class HotComment implements Serializable{

    private static final long serialVersionUID = -4249699545233058684L;
    @Id
    private Long id;//热评编号
    private String commentId;
    private String user;//用户
    private String comment;//内容
    private int up;//支持数
    private int down;//反对数
    private String posandtime;//位置和时间
    private String mobile;//设备
    private String articleUrl;//源文章地址

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public String getPosandtime() {
        return posandtime;
    }

    public void setPosandtime(String posandtime) {
        this.posandtime = posandtime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    @Override
    public String toString() {
        return "HotComment{" +
                "id='" + id + '\'' +
                "commentId='" + commentId + '\'' +
                ", user='" + user + '\'' +
                ", comment='" + comment + '\'' +
                ", up=" + up +
                ", down=" + down +
                ", posandtime='" + posandtime + '\'' +
                ", mobile='" + mobile + '\'' +
                ", articleUrl='" + articleUrl + '\'' +
                '}';
    }
}
