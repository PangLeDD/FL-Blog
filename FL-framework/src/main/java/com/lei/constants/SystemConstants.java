package com.lei.constants;




public class SystemConstants {

    /**
     * 文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;

    /**
     * 文章是正式文章
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 文章状态 0正常
     */
    public static final int ARTICLE_STATUS_TURE = 0;
    /**
     * 文章状态 1禁用
     */
    public static final int ARTICLE_STATUS_FALSE = 0;

    /**
     * 审核通过0 审核未通过2 未审核未通过 1
    */
    public static final String Link_STATUS_REVIEWED_PASS = "0";
    /**
     * 审核通过0 审核未通过2 未审核未通过 1
     */
    public static final String Link_STATUS_Not_REVIEWED = "2";
    /**
     * 审核通过0 审核未通过2 未审核未通过 1
     */
    public static final String Link_STATUS_REVIEWED_NOT_PASS = "1";

/**
 *      根节点
*/
    public static final int COMMENT_ROOT = -1;
    /**
     * 文章的评论为 “0” 友链的评论为 “1”
    */
    public static final String ARTICLE_COMMENT = "0";

    public static final String LINK_COMMENT = "1";

}
