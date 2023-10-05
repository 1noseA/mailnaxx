package com.mailnaxx.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

import com.mailnaxx.constants.UserConstants;
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
        // 現在
        int currentYear = YearMonth.now().getYear();
        int currentMonth = YearMonth.now().getMonthValue();
        // 入社年月_年プルダウン
        List<String> hireYearList = new ArrayList<>();
        for (int i = currentYear; i <= currentYear+1; i++) {
            hireYearList.add(String.valueOf(i));
        }
        // 月プルダウン
        List<String> monthList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            monthList.add(String.valueOf(i));
        }
        model.addAttribute("currentYear", String.valueOf(currentYear));
        model.addAttribute("currentMonth", String.valueOf(currentMonth));
        model.addAttribute("hireYearList", hireYearList);
        model.addAttribute("monthList", monthList);

        // 所属プルダウン
        List<Affiliations> affiliationList = affiliationsMapper.findAll();
        model.addAttribute("affiliationList", affiliationList);

        // 権限区分プルダウン
        model.addAttribute("roleClassList", RoleClass.values());

        // 生年月日_年プルダウン
        List<String> birthYearList = new ArrayList<>();
        for (int i = currentYear-70; i <= currentYear-20; i++) {
            birthYearList.add(String.valueOf(i));
        }
        // 初期値
        String birthYearDefault = String.valueOf(currentYear-30);
        model.addAttribute("birthYearList", birthYearList);
        model.addAttribute("birthYearDefault", birthYearDefault);
        model.addAttribute("notAffiliation", UserConstants.NOT_AFFILIATION);
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

        // 社員番号生成
        String hireYear = usersForm.getHireYear();
        String hireMonth = usersForm.getHireMonth();
        if (hireMonth.length() == 1) {
            hireMonth = "0" + hireMonth;
        }
        LocalDate hireDate = LocalDate.parse(hireYear + hireMonth + "01", DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<Users> usersList =  usersMapper.findAll();
        int max = (int) usersList.stream()
                .filter(u -> u.getHire_date().isEqual(hireDate))
                .count() + 1;
        String num = max >= 10 ? String.valueOf(max) : "0" + String.valueOf(max);
        users.setUser_number(hireYear + hireMonth + num);

        // 氏名
        users.setUser_name(usersForm.getUserLastName() + " " + usersForm.getUserFirstName());
        users.setUser_name_kana(usersForm.getUserLastKana() + " " + usersForm.getUserFirstKana());

        // 入社年月
        users.setHire_date(hireDate);

        // 所属
        users.setAffiliation_id(Integer.parseInt(usersForm.getAffiliationId()));

        // 権限区分
        users.setRole_class(usersForm.getRoleClass());

        // 生年月日
        String birthYear = usersForm.getBirthYear();
        String birthMonth = usersForm.getBirthMonth();
        String birthDay = usersForm.getBirthDay();
        if (birthMonth.length() == 1) {
            birthMonth = "0" + birthMonth;
        }
        if (birthDay.length() == 1) {
            birthDay = "0" + birthDay;
        }
        users.setBirth_date(LocalDate.parse(birthYear + birthMonth + birthDay, DateTimeFormatter.ofPattern("yyyyMMdd")));

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
        return "login/login";
    }
}