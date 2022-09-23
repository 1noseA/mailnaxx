package com.mailnaxx.controller;

import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mailnaxx.entity.Affiliations;
import com.mailnaxx.entity.Users;
import com.mailnaxx.mapper.AffiliationsMapper;
import com.mailnaxx.mapper.UsersMapper;
import com.mailnaxx.values.RoleClass;

@Controller
public class UsersController {

    @Autowired
    AffiliationsMapper affiliationsMapper;

    @Autowired
    UsersMapper usersMapper;

    @RequestMapping("/user-list")
    public String index(Model model) {

        List<Users> userList = usersMapper.selectAll();
        model.addAttribute("userList", userList);
        return "user-list";

    }

    // 登録画面初期表示
    @RequestMapping(value="/user-register", method = RequestMethod.GET)
    public String register(Model model) {

        // 入社年月プルダウン

        // 所属プルダウン
        List<Affiliations> affiliationList = affiliationsMapper.selectAll();
        model.addAttribute("affiliationList", affiliationList);

        // 権限区分プルダウン
        model.addAttribute("roleClassList", RoleClass.values());

        // 生年月日プルダウン
        int currentYear = YearMonth.now().getYear();
        int birthYearFrom = currentYear - 70;
        int birthYearTo = currentYear;
        int birthYearDefault = currentYear - 30;

        model.addAttribute("birthYearFrom", birthYearFrom);
        model.addAttribute("birthYearTo", birthYearTo);
        model.addAttribute("birthYearDefault", birthYearDefault);

        return "user-register";

    }

    // 登録画面登録処理
    @RequestMapping(value="/user-register", method = RequestMethod.POST)
    public String register(Users users) {

        usersMapper.register(users);
        return "user-register";

    }
}
