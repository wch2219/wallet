package com.hjzn.wallet.model;

import java.util.List;

public class UserInfo {


    /**
     * msg : {"info":"","code":0,"success":true}
     * data : {"birthday":1516636800000,"gsc_num":1884.0681,"idCard":"410327195807076011","invitecode":"b550ae419353478f9847a716eae80105","holdDongNum":0,"roleType":0,"inviteNum":2,"dongPrice":"10","password":"670b14728ad9902aecba32e22fa4f6bd","canReAmount":[360],"email":null,"ticket":16.5,"hold_gsc_num":7375.569,"avatarUrl":"e59a7296b8a74997adaa04dc8db9054d.jpeg","nickName":"张长东1","sex":0,"depIntNum":5000,"mobile":"18701222933","dong":0,"userName":"18701222933","userId":"3fe8e8b8e8654d22b07168c56e16fde2","transactionPassword":"670b14728ad9902aecba32e22fa4f6bd","holdIntNum":0,"shopcoin":9999788693,"integration":900}
     */

    private MsgBean msg;
    private DataBean data;

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class MsgBean {
        /**
         * info :
         * code : 0
         * success : true
         */

        private String info;
        private int code;
        private boolean success;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }

    public static class DataBean {
        /**
         * birthday : 1516636800000
         * gsc_num : 1884.0681
         * idCard : 410327195807076011
         * invitecode : b550ae419353478f9847a716eae80105
         * holdDongNum : 0.0
         * roleType : 0
         * inviteNum : 2
         * dongPrice : 10
         * password : 670b14728ad9902aecba32e22fa4f6bd
         * canReAmount : [360]
         * email : null
         * ticket : 16.5
         * hold_gsc_num : 7375.569
         * avatarUrl : e59a7296b8a74997adaa04dc8db9054d.jpeg
         * nickName : 张长东1
         * sex : 0
         * depIntNum : 5000.0
         * mobile : 18701222933
         * dong : 0.0
         * userName : 18701222933
         * userId : 3fe8e8b8e8654d22b07168c56e16fde2
         * transactionPassword : 670b14728ad9902aecba32e22fa4f6bd
         * holdIntNum : 0.0
         * shopcoin : 9999788693
         * integration : 900.0
         */
        private float bj;
        private long birthday;
        private double gsc_num;
        private String idCard;
        private String invitecode;
        private double holdDongNum;
        private int roleType;
        private int inviteNum;
        private String dongPrice;
        private String password;
        private Object email;
        private double ticket;
        private double hold_gsc_num;
        private String avatarUrl;
        private String nickName;
        private int sex;
        private double depIntNum;
        private String mobile;
        private double dong;
        private String userName;
        private String userId;
        private String transactionPassword;
        private double holdIntNum;
        private long shopcoin;
        private float integration;
        private float gsc_return_speed;
        private List<Integer> canReAmount;
        private String region;

        public float getBj() {
            return bj;
        }

        public void setBj(float bj) {
            this.bj = bj;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public float getGsc_return_speed() {
            return gsc_return_speed;
        }

        public void setGsc_return_speed(float gsc_return_speed) {
            this.gsc_return_speed = gsc_return_speed;
        }

        public long getBirthday() {
            return birthday;
        }

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

        public double getGsc_num() {
            return gsc_num;
        }

        public void setGsc_num(double gsc_num) {
            this.gsc_num = gsc_num;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getInvitecode() {
            return invitecode;
        }

        public void setInvitecode(String invitecode) {
            this.invitecode = invitecode;
        }

        public double getHoldDongNum() {
            return holdDongNum;
        }

        public void setHoldDongNum(double holdDongNum) {
            this.holdDongNum = holdDongNum;
        }

        public int getRoleType() {
            return roleType;
        }

        public void setRoleType(int roleType) {
            this.roleType = roleType;
        }

        public int getInviteNum() {
            return inviteNum;
        }

        public void setInviteNum(int inviteNum) {
            this.inviteNum = inviteNum;
        }

        public String getDongPrice() {
            return dongPrice;
        }

        public void setDongPrice(String dongPrice) {
            this.dongPrice = dongPrice;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public double getTicket() {
            return ticket;
        }

        public void setTicket(double ticket) {
            this.ticket = ticket;
        }

        public double getHold_gsc_num() {
            return hold_gsc_num;
        }

        public void setHold_gsc_num(double hold_gsc_num) {
            this.hold_gsc_num = hold_gsc_num;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public double getDepIntNum() {
            return depIntNum;
        }

        public void setDepIntNum(double depIntNum) {
            this.depIntNum = depIntNum;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public double getDong() {
            return dong;
        }

        public void setDong(double dong) {
            this.dong = dong;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getTransactionPassword() {
            return transactionPassword;
        }

        public void setTransactionPassword(String transactionPassword) {
            this.transactionPassword = transactionPassword;
        }

        public double getHoldIntNum() {
            return holdIntNum;
        }

        public void setHoldIntNum(double holdIntNum) {
            this.holdIntNum = holdIntNum;
        }

        public long getShopcoin() {
            return shopcoin;
        }

        public void setShopcoin(long shopcoin) {
            this.shopcoin = shopcoin;
        }

        public float getIntegration() {
            return integration;
        }

        public void setIntegration(float integration) {
            this.integration = integration;
        }

        public List<Integer> getCanReAmount() {
            return canReAmount;
        }

        public void setCanReAmount(List<Integer> canReAmount) {
            this.canReAmount = canReAmount;
        }
    }
}
