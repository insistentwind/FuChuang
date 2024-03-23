package com.ning.constants;

public class SystemConstants {


    /**
     * redis中登录用户名的key
     */
    public static final String USER_LOGIN = "userLogin:";

    /**
     * redis中后台登录用户的key
     */
    public static final String ADMIN_LOGIN = "adminLogin:";

    /**
     * 请求头中的token条目信息
     */
    public static final String TOKEN = "token";

    /**
     * 后台用户
     */
    public static final String ADMIN = "1";
    /**
     * C：菜单
     */
    public static final String MENU = "C";
    /**
     * F：按钮
     */
    public static final String BUTTON = "F";
    /**
     * M：目录
     */
    public static final String CONTENT = "M";

    /**
     * 是否启用，0启用
     */
    public static final String STATUS_NORMAL = "0";
    /**
     * 代表是否是管理端用户
     */
    public static final Integer IS_ADMIN = 2;
    /**
     * 用户未登录或信息错误
     */
    public static final String USER_NOT_LOGIN_OR_ERROR = "用户未登录或信息错误";
    /**
     * --------------------------------------分割线---------------------------
     */

    /**
     * 招聘状态：否
     */
    public static final int WORK_STATUS_NO = 0;
    /**
     * 招聘状态：是
     */
    public static final int WORK_STATUS_YES = 1;

    public static final String WORK_VIEW_COUNT = "articleViewCount";

    /**
     * 不需要筛选通知
     */
    public static final Integer NOTIFY_STATUS = 2;

    /**
     * 未读消息
     */
    public static final Integer CHAT_NO_READ = 0;
    public static final String COMPANY = "companyList";
    //注册的用户是公司hr
    public static final Integer IS_COMPANY = 1;
    //注册的用户是公司hr
    public static final Integer IS_NOT_COMPANY = 0;
    //当前用户与公司不匹配
    public static final String HAS_NO_MATCHED_USER_COMPANY = "当前用户与公司不匹配";
    //当前用户没有权限修改此公司
    public static final String HAS_NO_PERMISSION = "当前用户没有权限修改此公司";
    public static final String WORK_NOT_EXIST = "当前职位不存在";
    public static final String USER_HAS_NO_RESUME = "当前用户暂未创建简历";
    public static final String USER_HAS_DILIVER_RESUME = "当前用户已上传简历，请修改唯一简历";
    public static final String HAS_NO_CATIGORY = "没有此分类";
    public static final String USER_NO_PERMITED = "该用户没有同意您查询简历";
    /**
     * 职位分类
     */
    public static final Object WORK_CATIGORY = "work_category";
}
