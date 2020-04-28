package com.whp.usdt.user.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Interface.SysClassInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 吴敬鹏
 * @date 2018-07-13
 * @Deprecated 平台新闻分类操作类
 */
@Controller
@RequestMapping("/sysclass")
public class SysClassController {

    @Autowired
    private SysClassInterface sysClassInterface;

    /**
     * 根据类型获取分类
     *
     * @param style 类型 0:新闻分类，1公告分类，2关于我们
     * @return
     */
    @RequestMapping(value = "/getClassByStyle", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getClassByStyle(String style) {
        JSONObject json = new JSONObject();
        json = sysClassInterface.getClassByStyle(style);
        return json;
    }
}
