package com.hjzn.wallet.model;

public class PublicMode extends ParentModel {

    /**
     * msg : {"info":"成功!","code":200,"success":true}
     * data : {"birthday":1516636800000,"avatarUrl":"e59a7296b8a74997adaa04dc8db9054d.jpeg","nickName":"张长东1","sex":0,"mobile":"18701222933","id":1,"userName":"18701222933","userId":"3fe8e8b8e8654d22b07168c56e16fde2"}
     */

    private LoginBean.MsgBean msg;


    public LoginBean.MsgBean getMsg() {
        return msg;
    }

    public void setMsg(LoginBean.MsgBean msg) {
        this.msg = msg;
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
}
