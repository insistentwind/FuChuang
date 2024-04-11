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

    public static final String WORK_VIEW_COUNT = "positionViewCount";

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
    public static final String NOW_USER_IS_NOT_COMPANY = "当前用户不是公司用户";
    //当前用户与公司不匹配
    public static final String HAS_NO_MATCHED_USER_COMPANY = "当前用户与公司不匹配";
    //当前用户没有权限修改此公司
    public static final String HAS_NO_PERMISSION = "当前用户没有权限修改此公司";
    public static final String WORK_NOT_EXIST = "当前职位不存在";
    public static final String USER_HAS_NO_RESUME = "当前用户没有此简历";
    public static final String USER_HAS_DILIVER_RESUME = "当前用户已上传简历，请修改唯一简历";
    public static final String HAS_NO_CATIGORY = "没有此分类";
    public static final String USER_NO_PERMITED = "该用户没有同意您查询简历";
    /**
     * 职位分类
     */
    public static final Object WORK_CATIGORY = "work_category_";
    /**
     * 职位的所有分类
     */
    public static final Object WORK_ALL_LIST = "work_all_list_";
    public static final String COMPANY_HAS_NO_POSITION = "当前公司没有发布职位";
    /**
     * 默认简历
     */
    public static final Integer IS_DEFAULT_RESUME = 1;
    /**
     * 不是默认的简历
     */
    public static final Integer IS_NOT_DEFAULT_RESUME = 0;
    /**
     * 城市分类
     */
    public static final Object CITY_CATEGORY = "city_category_";
    /**
     * 职位分类
     */
    public static final Object WORK_DEGREE = "work_degree_";
    public static final Object CATEGORY_LIST = "list_";
    public static final Object SCALE = "company_scale_";
    public static final Object WORK_EXP = "work_exp_";
    public static final String SUCCESS = "操作成功";

    public static final String USER_HAS_NO_DEFAULT_RESUME = "当前用户没有设置默认简历";
    public static final String HAS_NO_WORK_SALARY = "没有此薪资分类";
    public static final String HAS_NO_WORK_EXPERIENCE = "没有此经验分类";
    public static final String HAS_NO_SCALE = "没有此规模分类";
    public static final String HAS_NO_DEGREE = "没有此学历分类";
    public static final Object WORK_SALARY = "work_salary_";
    public static final Object SALARY_LIST = "list";
    public static final String IS_NOT_ADMIN = "当前用户非管理员用户";












    /**
     * TODO 这里为权限标识符
     */

    /**
     * 系统用户管理
     */
    public static final String SYSTEM_USER_LIST = "system:user:list";
    public static final String SYSTEM_USER_QUERY = "system:user:query";
    public static final String SYSTEM_USER_ADD = "system:user:add";
    public static final String SYSTEM_USER_EDIT = "system:user:edit";
    public static final String SYSTEM_USER_REMOVE = "system:user:remove";
    public static final String SYSTEM_USER_EXPORT = "system:user:export";
    public static final String SYSTEM_USER_IMPORT = "system:user:import";
    public static final String SYSTEM_USER_RESETPWD = "system:user:resetPwd";


    /**
     * 公司管理
     */
    /**
     * 员工管理
     */
    public static final String CONTENT_EMP_INDEX = "content:emp:index";


    /**
     * 公司通知公告
     */
    public static final String CONTENT_NOTIFY_INDEX = "content:notify:index";

    /**
     * 公司系统管理
     */
    public static final String CONTENT_SYSTEM_INDEX = "content:system:index";
    /**
     * 公司招聘管理
     */
    public static final String CONTENT_RECRUIT_INDEX = "content:recruit:index";


    public static final String UP_TIME = "任务超时";
    /**
     * 设置简历不可见
     */
    public static final Integer CAN_NOT_BE_SEEN = 1;
    /**
     * 设置简历可见
     */
    public static final Integer CAN_BE_SEEN = 0;
    public static final String PLEASE_CHECK_RESUME = "请检查简历信息是否填写完整";
    public static final String CANT_BE_ANALYZED = "简历信息无法解析，用户没有权限或不存在此简历";
    public static final String HAS_NO_KEY = "密钥解析失败";
    public static final String OPERATION_NOT_COMPARE_WITH_USER = "当前操作与用户不匹配";
    /**
     * 未读
     */
    public static final Integer HAS_NO_READ = 0;
    /**
     * 已读
     */
    public static final Integer HAS_READ = 1;
    /**
     * 密钥端url地址
     */
    public static final String KEY_CLIENT_URL = "http://124.220.208.63:8082";
    public static final String USER_HAS_NO_FAVOR = "该用户还没有收藏职位";
    public static final String USER_NOT_FAVOR_THIS_WORK = "该用户没有收藏该职位";
    /**
     * python端请求路径
     */
    public static final String PYTHON_URL = "http://10.33.53.216:4321/update_job?id=";
    /**
     * 请求成功响应码
     */
    public static final String CODE_SUCCESS = "200";
    /**
     * 职位插入操作
     */
    public static final Integer WORK_INSERT = 1;
    /**
     * 职位删除操作
     */
    public static final Integer WORK_DELETE = 0;
    /**
     * 职位更新操作
     */
    public static final Integer WORK_UPDATE = 2;
    public static final String USER_HAS_DELEVERED = "该用户在此职位已投递相同简历";
    public static final String USER_HAS_NOT_DELEVERED = "该用户还未投递此简历";
    /**
     * 加密解密，false是不开启
     */
    public static final boolean OUT_ENCODE_BUTTON = false;
    /**
     * 入参加密按钮，false不开启
     */
    public static final boolean IN_DECODE_BUTTON = false;
    public static final String USER_HAS_NO_KEY = "创建密钥时出现错误";
    /**
     * 允许查看简历
     */
    public static final Object AGREE_TO_SEE = 0;
    public static final String HAS_NO_COMPANY = "没有此公司";
    public static final String CANT_FIND_DRAW = "没有与之匹配的简历画像";
    public static final String RESUME_POOL_HAS_NO_RESUME = "当前简历池中没有简历信息";
    /**
     * 加入公共建立池
     * 0是1否
     */
    //加入公共建立池(0是1否)
    public static final Integer PUBLIC_POOL_OPEN = 0;
    //加入公共建立池(0是1否)
    public static final Integer PUBLIC_POOL_CLOSE = 1;
    public static final String DECODE_FAIL = "简历解密失败";
    public static final String HAS_NO_POOL_PERMS = "当前简历不允许被查看";
    public static final String CHECK_INPUT = "请检查输入";
    public static final String RESUME_HAS_DELETED_OR_NOT_EXIST = "简历被删除或不存在";
    /**
     * 删除标志位，1为删除
     */
    public static final Integer DEL_FLAG = 1;
}
