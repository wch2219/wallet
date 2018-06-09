package com.hjzn.wallet.utils;

public class Api {
    private static String BaseUrl = NetConstant.BAES_UEL_REALSE;


    //登录
    public static String Login = BaseUrl + "api/sys/login";
    //邀请码校验
    public static String InviteCodeCheck = BaseUrl + "api/user/getUseExt";
    //发送短信验证码
    public static String SendSms = BaseUrl + "api/sms/getRegistVerificationCode";
    //注册
    public static String Register = BaseUrl + "/api/user/save";
    //忘记密码校验银行卡
    public static String ForgrtPwd = BaseUrl + "api/user/valiadUserByBank";
    //充值密码
    public static String ResetPwd = BaseUrl + "api/user/update";
    //获取个人信息
    public static String GetUserInfo = BaseUrl + "api/user/get";
    /**
     * 修改个人信息
     */
    public static String ChangePwd = BaseUrl + "api/user/update";
    /**
     * 银行卡列表
     */
    public static String BankList = BaseUrl + "api/userBank/list";
    /**
     * 更新银行卡
     */
    public static String AddBank = BaseUrl+"api/userBank/saveOrUpdate";
    /**
     * 删除银行卡
     */
    public static String DeleteBank = BaseUrl + "api/userBank/delete";
    /**
     * 实名认证
     */
    public static String RealName = BaseUrl + "api/user/update";
    /**
     * 提币
     */
    public static String MenMone = BaseUrl + "api/gscFlow/save";
    /**
     * 收益提现
     */
    public static String WithDraw = BaseUrl + "api/integration/cash";
}
