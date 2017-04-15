package com.zzuzl.controller;

import com.zzuzl.dao.PicDao;
import com.zzuzl.dto.SubPageVo;
import com.zzuzl.model.Area;
import com.zzuzl.model.Pic;
import com.zzuzl.model.WebSite;
import com.zzuzl.service.RedisService;
import com.zzuzl.util.JsonUtil;
import com.zzuzl.util.NetUtil;
import com.zzuzl.util.PathUtil;
import com.zzuzl.dto.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {
    @Resource
    private PicDao picDao;
    private Logger logger = LogManager.getLogger(getClass());

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = "/")
    public String index(HttpSession session, Device device) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return PathUtil.path(device, "index");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Device device) {
        return PathUtil.path(device, "login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(String schoolNum, String password, HttpSession session) {
        Result result = NetUtil.connect("http://jw.zzu.edu.cn/scripts/stuinfo.dll/check",
                schoolNum, password, "input[name='userid']");
        if (result.isSuccess()) {
            session.setAttribute("user", schoolNum);
        }
        return result;
    }

    @RequestMapping(value = "start", method = RequestMethod.POST)
    @ResponseBody
    public Object start(HttpSession session) throws IOException {
        Result<SubPageVo> result = new Result<SubPageVo>();
        result.setSuccess(false);

        try {
            if (session.getAttribute("user") == null) {
                result.setError("未认证的身份");
            } else {
                result.setSuccess(true);
                List<SubPageVo> list = new ArrayList<SubPageVo>();
                String json = picDao.getJson("se");
                WebSite webSite = JsonUtil.fromJson(json, WebSite.class);
                if (webSite != null && webSite.getAreas() != null && webSite.getAreas().size() > 0) {
                    for (Area area : webSite.getAreas()) {
                        SubPageVo subPageVo = new SubPageVo();
                        subPageVo.setKey(area.getKey());
                        subPageVo.setPicList(picDao.getTodayImg(area.getKey()));
                        list.add(subPageVo);
                    }
                }
                result.setList(list);
            }
        } catch (Exception e) {
            logger.error(e);
            result.setSuccess(false);
            result.setError(e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "updateConfig", method = RequestMethod.POST)
    @ResponseBody
    public Result updateConfig(String json, String key, HttpSession session) throws IOException {
        Result result = new Result();
        result.setSuccess(false);

        if (session.getAttribute("user") == null) {
            result.setError("未认证的身份");
            return result;
        }

        if (StringUtils.isEmpty(json) || StringUtils.isEmpty(key)) {
            result.setError("配置为空");
        } else {
            try {
                // redisService.update(key, json);
                result.setSuccess(true);
            } catch (Exception e) {
                logger.error(e.getMessage());
                result.setSuccess(false);
                result.setError(e.getMessage());
            }
        }
        return result;
    }

}
