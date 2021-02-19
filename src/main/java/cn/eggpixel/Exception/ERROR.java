
package cn.eggpixel.Exception;

import cn.eggpixel.Message;


public class ERROR {
    /**
     * 制造错误信息
     * */
    public ERROR(Exception ExceptionInfo) {
        new Message("=================THIS IS A BUG OR FILE NOT EXIST!=============").error();
        new Message("错误信息:" + ExceptionInfo.getMessage()).error();
        new Message(ExceptionInfo.toString()).error();
        new Message("反馈:https://www.eggpixel.cn/FeedBack.html").error();
        new Message("==============================================================").error();
    }
}
