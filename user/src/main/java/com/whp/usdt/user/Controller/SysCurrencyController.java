package com.whp.usdt.user.Controller;

import com.whp.usdt.user.Interface.SysCurrencyInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 吴敬鹏
 * @date 2018-07-13
 * @Deprecated 币种种类操作类
 */
@Controller
@RequestMapping("/syscurrency")
public class SysCurrencyController {
    @Autowired
    private SysCurrencyInterface sysCurrencyInterface;
}
