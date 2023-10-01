package com.mailnaxx.controller;

import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mailnaxx.entity.Affiliations;
import com.mailnaxx.entity.Users;
import com.mailnaxx.form.GroupOrder;
import com.mailnaxx.form.UsersForm;
import com.mailnaxx.mapper.AffiliationsMapper;
import com.mailnaxx.mapper.UsersMapper;
import com.mailnaxx.values.RoleClass;

@Controller
public class TestController {

    @Autowired
    AffiliationsMapper affiliationsMapper;

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 登録画面初期表示
    @GetMapping("/test/create")
    public String create(@ModelAttribute UsersForm usersForm, Model model) {
        // 入社年月プルダウン
        int currentYear = YearMonth.now().getYear();
        int currentMonth = YearMonth.now().getMonthValue();

        model.addAttribute("currentYear", currentYear);
        model.addAttribute("currentMonth", currentMonth);

        // 所属プルダウン
        List<Affiliations> affiliationList = affiliationsMapper.findAll();
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
        return "test/create";
    }

    // 登録画面登録処理
    @PostMapping("/test/create")
    public String create(@ModelAttribute @Validated(GroupOrder.class) UsersForm usersForm, BindingResult result, Model model) {
        // 入力エラーチェック
        if (result.hasErrors()) {
            return create(usersForm, model);
        }

        Users users = new Users();

        // ユーザID生成（これだとかぶることがある。ランダムじゃなくて連番にする？）
        String hireYear = String.valueOf(usersForm.getHireYear());
        String hireMonth = String.valueOf(usersForm.getHireMonth());
        if (hireMonth.length() == 1) {
            hireMonth = "0" + hireMonth;
        }
        int random = (int)(Math.random()*100);
        String num = random >= 10 ? Integer.toString(random) : "0" + Integer.toString(random);
        users.setUser_number(hireYear + hireMonth + num);

        // 氏名
        users.setUser_name(usersForm.getUserLastName() + " " + usersForm.getUserFirstName());
        users.setUser_name_kana(usersForm.getUserLastKana() + " " + usersForm.getUserFirstKana());

        // 入社年月
        users.setHire_date(hireYear + hireMonth + "01");

        // 所属
        users.setAffiliation_id(usersForm.getAffiliationId());

        // 権限区分
        users.setRole_class(usersForm.getRoleClass());

        // 生年月日
        String birthYear = String.valueOf(usersForm.getBirthYear());
        String birthMonth = String.valueOf(usersForm.getBirthMonth());
        String birthDay = String.valueOf(usersForm.getBirthDay());
        if (birthMonth.length() == 1) {
            birthMonth = "0" + birthMonth;
        }
        if (birthDay.length() == 1) {
            birthDay = "0" + birthDay;
        }
        users.setBirth_date(birthYear + birthMonth + birthDay);

        // 営業担当
        users.setSales_flg(usersForm.getSalesFlg());

        // 郵便番号
        users.setPost_code(usersForm.getPostCode1() + "-" +usersForm.getPostCode2());

        // 住所
        users.setAddress(usersForm.getAddress());

        // 電話番号
        users.setPhone_number(usersForm.getPhoneNumber1() + "-" + usersForm.getPhoneNumber2() + "-" + usersForm.getPhoneNumber3());

        // メールアドレス
        users.setEmail_address(usersForm.getEmailAddress());

        // パスワードはハッシュにする
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        users.setPassword(passwordEncoder.encode(usersForm.getPassword()));

        // 作成者はセッションのユーザID
        users.setCreated_by("test");

        usersMapper.insert(users);
        return "/";
    }
}
