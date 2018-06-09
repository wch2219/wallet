package com.hjzn.wallet.model;

public class LoginBean extends ParentModel{


    /**
     * msg : {"info":"成功!","code":200,"success":true}
     * data : {"birthday":1516636800000,"avatarUrl":"e59a7296b8a74997adaa04dc8db9054d.jpeg","nickName":"张长东1","sex":0,"mobile":"18701222933","id":1,"userName":"18701222933","userId":"3fe8e8b8e8654d22b07168c56e16fde2"}
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
         * info : 成功!
         * code : 200
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
         * avatarUrl : e59a7296b8a74997adaa04dc8db9054d.jpeg
         * nickName : 张长东1
         * sex : 0
         * mobile : 18701222933
         * id : 1
         * userName : 18701222933
         * userId : 3fe8e8b8e8654d22b07168c56e16fde2
         */

        private String birthday;
        private String avatarUrl;
        private String nickName;
        private int sex;
        private String mobile;
        private int id;
        private String userName;
        private String userId;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    }
}
