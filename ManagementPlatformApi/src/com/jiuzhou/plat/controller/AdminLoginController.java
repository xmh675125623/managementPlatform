package com.jiuzhou.plat.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.cache.LoginValidateCode;
import com.jiuzhou.plat.service.AdminLoginService;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONObject;

/**
 * 登录相关controller
 * @author xingmh
 *
 */
@Controller
@RequestMapping("/admin_login")
public class AdminLoginController {
	
	@Autowired
	AdminLoginService adminLoginService;

	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @param jsonParam
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/login.do")
	public String login(HttpServletRequest request, 
			HttpServletResponse response,
			@RequestBody String jsonParam) {
		
		try {
			
			JSONObject paramJson = JSONObject.fromObject(jsonParam);
			
			return adminLoginService.Login(paramJson);
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject resultJson = new JSONObject();
			resultJson.put("status", "error");
			resultJson.put("errorMsg", "系统发生未知错误");
			return resultJson.toString();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/logout.do")
	public String logout(HttpServletRequest request, 
			HttpServletResponse response,
			@RequestBody String jsonParam) {
		JSONObject paramJson = JSONObject.fromObject(jsonParam);
		if (!paramJson.has("aid") || !paramJson.has("sessionid")) {
			return "";
		}
		int aid = paramJson.getInt("aid");
		String sessionid = paramJson.getString("sessionid");
		AdminUserLoginInfo loginInfo = 
				new ServiceBase().getCache(ServiceBase.CACHE_LOGIN_INFO + aid, 
						AdminUserLoginInfo.class);
		if (loginInfo != null && sessionid.equals(loginInfo.getSessionid())) {
			new ServiceBase().deleteCache(ServiceBase.CACHE_LOGIN_INFO + aid);
		}
		
		return "";
	}
	
	
	private char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	
	
	/**
     * 设置验证码颜色
     * @param fc
     * @param bc
     * @return
     * @author tianqq
     * @date 2014年12月5日
     * @version 0.1
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
    
	
	/**
     * 生成验证码
     * @param request
     * @param response
     *
     */
    @RequestMapping("/validate_code.do")
    public void createCode(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value="token", required=true)String token) throws Exception {
        try {
            int width = 80;
            int height = 30;
            // 生成随机类
            Random random = new Random();
            // randomCode记录随机产生的验证码
            StringBuffer randomCode = new StringBuffer();
            // 取得一个4位随机字母数字字符串
            for (int i = 0; i < 4; i++) {
                String strRand = String.valueOf(codeSequence[random
                        .nextInt(codeSequence.length)]);
                // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
                // 将产生的四个随机数组合在一起。
                randomCode.append(strRand);
            }
            String s = randomCode.toString();
            // 保存入缓存,用于与用户的输入进行比较.
            LoginValidateCode loginValidateCode = 
            		new LoginValidateCode(System.currentTimeMillis(), 3*1000);
            loginValidateCode.setCode(s);
            loginValidateCode.setToken(token);
            loginValidateCode.setCache_add_time(System.currentTimeMillis());
            loginValidateCode.setCache_save_time(3*60*1000);
            new ServiceBase().setCache(ServiceBase.CACHE_LOGIN_VALIDATE_CODE+token, 
            		loginValidateCode);

            response.setContentType("images/jpeg");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            ServletOutputStream out = response.getOutputStream();
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            // 设定背景色
            g.setColor(getRandColor(200, 250));
            g.fillRect(0, 0, width, height);

            // 设定字体
            Font mFont = new Font("Times New Roman", Font.BOLD, 24);// 设置字体
            g.setFont(mFont);

            // 随机产生干扰线，使图象中的认证码不易被其它程序探测到
            g.setColor(getRandColor(160, 200));

            for (int i = 0; i < 155; i++) {
                int x2 = random.nextInt(width);
                int y2 = random.nextInt(height);
                int x3 = random.nextInt(20);
                int y3 = random.nextInt(20);
                g.drawLine(x2, y2, x2 + x3, y2 + y3);
            }

            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));

            g.drawString(s, 2, 24);

            // 图象生效
            g.dispose();
            // 输出图象到页面
            ImageIO.write(image, "JPEG", out);
            out.close();
        } catch (Exception e) {
            throw e;
        }
    }
	
}
