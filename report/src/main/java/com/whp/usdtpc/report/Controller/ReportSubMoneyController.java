package com.whp.usdtpc.report.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtpc.report.Interface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/11/20 15:16
 * @descrpition :
 */
@RestController
@RequestMapping("/Report")
public class ReportSubMoneyController {
    @Autowired
    private ReportSubMoneyInterface subMoneyInterface;

    @Autowired
    private ReportSubTeamInterface subTeamInterface;
    @Autowired
    private ReportSubExtractInterface subExtractInterface;
    @Autowired
    private ReportSubTeamMoneyInterface subTeamMoneyInterface;
    @Autowired
    private ReportSysTransInterface sysTransInterface;

    @PostMapping("/SubMoneySelect")
    public JSONObject SubMoneySelect(String pid) {
        JSONObject json = subMoneyInterface.SubMoneySelect(pid);
        return json;
    }

    @PostMapping("/SubTeamSelect")
    public JSONObject SubTeamSelect(String pid, int page, int num) {
        JSONObject json = subTeamInterface.SubTeam(pid, page, num);
        return json;
    }

    @PostMapping("/SubExtractSelect")
    public JSONObject SubExtractSelect(String pid, int page, int num) {
        JSONObject json = subExtractInterface.SubExtractSelect(pid, page, num);
        return json;
    }

    @PostMapping("/SubTeamMoneyInsert")
    public JSONObject SubTeamMoneyInsert(@RequestParam Map<String, Object> map) {
        JSONObject json = subTeamMoneyInterface.SubTeamMoneyInsert(map);
        return json;
    }

    @PostMapping("/SubTeamMoneySelect")
    public JSONObject SubTeamMoneySelect(String userid, int page, int num) {
        JSONObject json = new JSONObject();
        json.put("userid", userid);
        json.put("page", (page - 1) * num);
        json.put("num", num);
        JSONObject jsons = subTeamMoneyInterface.SubTeamMoneySelect(json);
        return jsons;
    }

    @PostMapping("/SysTransSelect")
    public JSONObject SysTransSelect() {
        JSONObject json = sysTransInterface.ReportSysTrans();
        return json;
    }
}
