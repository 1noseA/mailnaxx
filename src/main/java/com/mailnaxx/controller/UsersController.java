package com.mailnaxx.controller;

import java.sql.Date;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mailnaxx.entity.Affiliations;
import com.mailnaxx.entity.Users;
import com.mailnaxx.form.UsersForm;
import com.mailnaxx.mapper.AffiliationsMapper;
import com.mailnaxx.mapper.UsersMapper;
import com.mailnaxx.values.RoleClass;

@Controller
public class UsersController {

    @Autowired
    AffiliationsMapper affiliationsMapper;

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

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
        int currentYear = YearMonth.now().getYear();
        int currentMonth = YearMonth.now().getMonthValue();

        model.addAttribute("currentYear", currentYear);
        model.addAttribute("currentMonth", currentMonth);

        // 所属プルダウン
        List<Affiliations> affiliationList = affiliationsMapper.selectAll();
        model.addAttribute("affiliationList", affiliationList);

        // 権限区分プルダウン
        model.addAttribute("roleClassList", RoleClass.values());

        // 生年月日プルダウン
        int birthYearFrom = currentYear - 70;
        int birthYearTo = currentYear - 20;
        int birthYearDefault = currentYear - 30;

        model.addAttribute("birthYearFrom", birthYearFrom);
        model.addAttribute("birthYearTo", birthYearTo);
        model.addAttribute("birthYearDefault", birthYearDefault);

        return "user-register";

    }

    // 登録画面登録処理
    @RequestMapping(value="/user-register", method = RequestMethod.POST)
    public String register(@ModelAttribute UsersForm usersForm, Model model) {

        Users users = new Users();

        // ユーザID生成
        String hire = usersForm.getHireYear() + usersForm.getHireMonth();
        int random = (int)(Math.random()*100);
        String num = random > 10 ? Integer.toString(random) : "0" + Integer.toString(random);
        users.setUser_id(hire + num);

        // 氏名
        users.setUser_name(usersForm.getUserLastName() + " " + usersForm.getUserFirstName());
        users.setUser_name_kana(usersForm.getUserLastKana() + " " + usersForm.getUserFirstKana());

        // 入社年月
        users.setHire_date(Date.valueOf(usersForm.getHireYear() + "/" + usersForm.getHireMonth()));

        // 所属
        users.setAffiliation_id(usersForm.getAffiliation_id());

        // 権限区分
        users.setRole_class(usersForm.getRoleClass());

        // 生年月日
        users.setBirth_date(Date.valueOf(usersForm.getBirthYear() + "/" + usersForm.getBirthMonth() + "/" + usersForm.getBirthDay()));

        // 営業担当
        if (usersForm.isSales() == true) {
            users.set_sales(true);
        } else {
            users.set_sales(false);
        }

        // 郵便番号
        users.setPost_code(usersForm.getPostCode1() + "-" +usersForm.getPostCode2());

        // 住所
        users.setAddress(usersForm.getAddress());

        // 電話番号
        users.setPhone_number(usersForm.getPhoneNumber1() + "-" + usersForm.getPhoneNumber2() + "-" + usersForm.getPhoneNumber3());

        // メールアドレス
        users.setEmail_address(usersForm.getEmail_address());

        // パスワードはハッシュにする
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        users.setPassword(passwordEncoder.encode(usersForm.getPassword()));

        // パスワード変更日時はなし
        // 前回パスワードはなし
        // 最終ログイン日時はなし
        // 削除フラグはデフォルト
        // 作成者はセッションのユーザID（現状は仮値）
        users.setCreated_by("XXX");

        // 更新者もなしか
        usersMapper.insert(users);
        return "user-register";

    }

}
