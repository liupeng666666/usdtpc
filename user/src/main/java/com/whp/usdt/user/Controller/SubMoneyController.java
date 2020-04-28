package com.whp.usdt.user.Controller;

import com.google.zxing.WriterException;
import com.whp.usdt.user.Interface.SubMoneyInterface;
import com.whp.usdt.user.utils.QrCodeCreateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 吴敬鹏
 * @date 2018-07-13
 * @Deprecated 用户钱包操作类
 */
@Controller
@RequestMapping("/submoney")
public class SubMoneyController {
    @Autowired
    private SubMoneyInterface subMoneyInterface;

}
