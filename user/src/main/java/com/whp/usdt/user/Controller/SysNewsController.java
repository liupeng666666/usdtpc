package com.whp.usdt.user.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Interface.SysNewsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 吴敬鹏
 * @date 2018-07-14
 * @Deprecated 新闻操作类
 */
@Controller
@RequestMapping("/sysnews")
public class SysNewsController {

    @Autowired
    private SysNewsInterface sysNewsInterface;

    /**
     * 根据分类查询新闻
     *
     * @param sys_class_id 新闻分类id
     * @return
     */
    @RequestMapping(value = "/getSysNewsByClass", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getSysNewsByClass(String sys_class_id, int page, int number) {
        JSONObject json = new JSONObject();
        json = sysNewsInterface.getSysNewsByClass(sys_class_id, page, number);
        return json;
    }

    /**
     * 时间倒序查询24小时快讯
     *
     * @return
     */
    @RequestMapping(value = "/getSysNewsFlash", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getSysNewsFlash(int page, int number) {
        JSONObject json = new JSONObject();
        json = sysNewsInterface.getSysNewsFlash(page, number);
        return json;
    }
}
