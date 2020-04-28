package com.whp.usdt.user.Controller;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;
import com.whp.usdt.user.Dao.SubUserDao;
import com.whp.usdt.user.Interface.SubUserInterface;
import com.whp.usdt.user.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 吴敬鹏
 * @date 2018-07-13
 * @Deprecated 平台用户操作类
 */
@RestController
@RequestMapping("/subuser")
public class SubUserController {
    @Autowired
    private SubUserInterface subUserInterface;
    @Autowired
    private SubUserDao subUserDao;


    @RequestMapping(value = "/SubYanZhengMa", method = RequestMethod.GET)
    @ResponseBody
    public void SubYanZhengMa(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            int width = 180;
            int height = 40;
            String imgType = "jpeg";
            OutputStream output = response.getOutputStream();
            String code = GraphicHelper.create(width, height, imgType, output, request);
            session.setAttribute("code", code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/getUserLoginQrCode", method = RequestMethod.GET)
    @ResponseBody
    public void getUserLoginQrCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            int width = 180;
            int height = 40;
            String imgType = "jpeg";
            OutputStream output = response.getOutputStream();
            String content = "HelloWord";
            boolean flag = QrCodeCreateUtil.createQrCode(output, content, 900, imgType);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据用户id 查询用户详细信息
     *
     * @param userid
     * @return
     */
    @RequestMapping(value = "/getUserInfoById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getUserInfoById(String userid) {
        JSONObject json = new JSONObject();
        json = subUserInterface.getUserInfoById(userid);
        return json;
    }

    /**
     * 修改用户信息
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "changUserInfoById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject changUserInfoById(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        if (map.containsKey("phone")) {
            String value = Redis.get_yzm("phone." + map.get("phone"), 7);
            if (!map.get("yzm").equals(value)) {
                json.put("code", 141);
                return json;
            }
            Redis.del_yzm("phone." + map.get("phone"), 7);
        }
        if (map.containsKey("email") && map.containsKey("yzm")) {
            String value = Redis.get_yzm("email." + map.get("email"), 7);
            if (!map.get("yzm").equals(value)) {
                json.put("code", 141);
                return json;
            }
            Redis.del_yzm("email." + map.get("email"), 7);
        }
        String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        map.put("userid", userid);
        json = subUserInterface.changUserInfoById(map);
        return json;
    }

    /**
     * 查询用户天、周、月收益率以及排行榜
     *
     * @param userid
     * @return
     */
    @RequestMapping(value = "getUserEarningsTopByTime", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getUserEarningsTopByTime(String userid) {
        JSONObject json = new JSONObject();
        json = subUserInterface.getUserEarningsTopByTime(userid);
        return json;
    }

    /**
     * 验证交易密码是否正确
     *
     * @param password 交易密码
     * @return
     */
    @RequestMapping(value = "/checkUserTradePassword", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkUserTradePassword(String password, int type, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        json = subUserInterface.checkUserTradePassword(userid, password, type);
        return json;
    }

    /**
     * 用户上传头像
     *
     * @param img
     * @param request
     * @return
     */
    @RequestMapping(value = "/UserImgUpload", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject UserImgUpload(@RequestParam(value = "img", required = false) MultipartFile img, HttpServletRequest request) {
        String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = new JSONObject();
        String url = ImgUtil.url(img, 200, 200, 1, userid);
        if (url != null && !"".equals(url)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userid", userid);
            map.put("img", url);
            json = subUserInterface.changUserInfoById(map);
            json.put("url", url);
        } else {
            json.put("code", 101);
        }
        return json;
    }

    @RequestMapping(value = "/UserIdCardUpload", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject UserIdCardUpload(@RequestParam(value = "img", required = false) MultipartFile img, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String url = ImgUtil.url(img, 600, 600, 1);
        if (url != null && !"".equals(url)) {
            json.put("url", url);
            json.put("code", 100);
        } else {
            json.put("code", 101);
        }
        return json;
    }

    /**
     * 获取谷歌验证器
     *
     * @param userid   用户id
     * @param email    邮箱
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getGoogleQrCode", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getGoogleQrCode(String userid, String email, String secretkey, int isgoogle, HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        try {
            String secret = "";
            if (isgoogle == 0) {
                secret = GoogleAuthenticator.generateSecretKey();
                request.getSession().setAttribute("secret", secret);
            } else {
                secret = secretkey;
                request.getSession().setAttribute("secret", secret);
            }
            String content = GoogleAuthenticator.getQRBarcode(email, secret);
            json.put("code", 100);
            json.put("content", content);
            json.put("secret", secret);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    /**
     * 验证谷歌验证器是否正确
     *
     * @param code
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkGoogleCode", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkGoogleCode(long code, String secret, String trade_password, String yzm, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        try {
            String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
            Map<String, Object> map = subUserDao.ReportSubUserDan(userid);
            if (map != null) {
                String value = Redis.get_yzm("phone." + map.get("phone"), 7);
                if (!yzm.equals(value)) {
                    json.put("code", 141);
                    return json;
                }
                Redis.del_yzm("phone." + map.get("phone"), 7);
            } else {
                json.put("code", 141);
                return json;
            }
            JSONObject jsons = subUserInterface.checkUserTradePassword(userid, trade_password, 2);
            if (jsons.getInteger("code") == 100) {
                long t = System.currentTimeMillis();
                GoogleAuthenticator ga = new GoogleAuthenticator();
                boolean flag = ga.check_code(secret, code, t);
                if (flag) {
                    json.put("code", 100);
                } else {
                    json.put("code", 101);
                }
            } else {
                json.put("code", 102);
            }


        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    /**
     * 验证谷歌验证器是否正确
     *
     * @param code
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkGoogleCodeYanZheng", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkGoogleCodeYanZheng(long code, String secret, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        try {
            String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");

            long t = System.currentTimeMillis();
            GoogleAuthenticator ga = new GoogleAuthenticator();
            boolean flag = ga.check_code(secret, code, t);
            if (flag) {
                json.put("code", 100);
            } else {
                json.put("code", 101);
            }

        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @RequestMapping(value = "/addRealnameAudit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addRealnameAudit(String userid, String realname, String idcard, String img) {
        JSONObject json = new JSONObject();
        json = subUserInterface.addRealnameAudit(userid, realname, idcard, img);
        return json;
    }

    /**
     * 获取邀请码
     *
     * @return
     */
    @RequestMapping(value = "/getInviteFriendsCode", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getInviteFriendsCode(String userid) {
        JSONObject json = new JSONObject();
        try {

            String value = Redis.getInviteCode(userid);
            if (value == null || "".equals(value)) {
                value = MD5Util.YaoQingMa();
                json = Redis.setInviteCode(value, userid);
            }
            json.put("code", 100);
            json.put("uuid", value);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    /**
     * app扫一扫
     *
     * @param jsonlogin
     * @return
     */
    @RequestMapping(value = "/scanLoginSet", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject scanLoginSet(String jsonlogin, String scanid) {
        JSONObject json = new JSONObject();
        try {
//            System.out.println(jsonlogin);
            int num = Redis.setScanLoginInfo(scanid, jsonlogin);
            if (num > 0) {
                json.put("code", 100);
            } else {
                json.put("code", 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    /**
     * app检查是否登录成功
     *
     * @param scanid
     * @return
     */
    @RequestMapping(value = "/checkScanLogin", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkScanLogin(String scanid) {
        JSONObject json = new JSONObject();
        try {
            int num = Redis.checkScanLoginInfo(scanid);
            if (num == 0) {
                json.put("code", 100);
            } else {
                json.put("code", 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @RequestMapping(value = "/getLoginPlatformInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getLoginPlatformInfo(String userid, String type, String uuid, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        try {
            String rkey = userid + "_" + type;
            JSONObject rjson = Redis.getLoginPlatformInfo(rkey);
            if (rjson.getInteger("code") == 100) {
                if (!rjson.getString("content").equals(uuid)) {
                    json.put("code", 100);
                } else {
                    json.put("code", 101);
                }
            } else {
                json.put("code", 100);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @RequestMapping(value = "/FsPhone", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject FsPhone(HttpServletRequest request, int state) {
        String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = subUserInterface.FsPhone(userid, state);
        return json;
    }
}
