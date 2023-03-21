package com.mailnaxx.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mailnaxx.entity.Projects;
import com.mailnaxx.entity.Users;
import com.mailnaxx.form.WeeklyReportForm;
import com.mailnaxx.mapper.ProjectsMapper;
import com.mailnaxx.mapper.UsersMapper;

@Controller
public class WeeklyReportsController {

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    ProjectsMapper projectsMapper;

    @ModelAttribute
    WeeklyReportForm setUpForm() {
        return new WeeklyReportForm();
    }

//    @RequestMapping("/weekly-report/list")
//    public String index(Model model) {
//
//        List<Users> userList = usersMapper.findAll();
//        model.addAttribute("userList", userList);
//        model.addAttribute("roleClassList", RoleClass.values());
//        return "user/list";
//
//    }

    // 詳細画面初期表示（仮）
//    @RequestMapping("/weekly-report/detail/{user_id:.+}")
//    public String detail(@PathVariable("user_id") int user_id, Model model) {
//
//        Users userInfo = usersMapper.findOne(user_id);
//        model.addAttribute("userInfo", userInfo);
//        return "user/detail";
//
//    }

    // 登録画面初期表示
    @RequestMapping(value="/weekly-report/create", method = RequestMethod.GET)
    public String create(Model model) {

        // 担当営業
        List<Users> salesList = usersMapper.findBySales();
        model.addAttribute("salesList", salesList);

        // 現場プルダウン
        List<Projects> projectList = projectsMapper.findAll();
        model.addAttribute("projectList", projectList);

        // 報告対象週ラベル
        // 現在日付を取得
        LocalDate now = LocalDate.now();
        // 現在日の週の月曜日を取得
        LocalDate monday = now.with(DayOfWeek.MONDAY);
        // 現在日の週の日曜日を取得
        LocalDate sunday = now.with(DayOfWeek.SUNDAY);
        String reportDate =
                monday.format(DateTimeFormatter.ofPattern("yyyy/MM/dd(E)")) + " 〜 " + sunday.format(DateTimeFormatter.ofPattern("yyyy/MM/dd(E)"));
        model.addAttribute("reportDate", reportDate);

        // ラジオボタン
        Map<String, String> radioThree = new LinkedHashMap<>();
        radioThree.put("1", "良い");
        radioThree.put("2", "やや良い");
        radioThree.put("3", "普通");
        radioThree.put("4", "やや悪い");
        radioThree.put("5", "悪い");
        model.addAttribute("radioProgress", radioThree);
        model.addAttribute("radioCondition", radioThree);
        model.addAttribute("radioRelationship", radioThree);

        // 現場社員プルダウン
        List<Users> userList = usersMapper.findAll();
        model.addAttribute("userList", userList);

        return "weekly-report/create";

    }

    // 登録画面登録処理
//    @RequestMapping(value="/weekly-report/create", method = RequestMethod.POST)
//    public String register(UsersForm usersForm, Model model) {
//
//        Users users = new Users();
//
//        // ユーザID生成（これだとかぶることがある。ランダムじゃなくて連番にする？）
//        String hireYear = String.valueOf(usersForm.getHireYear());
//        String hireMonth = String.valueOf(usersForm.getHireMonth());
//        if (hireMonth.length() == 1) {
//            hireMonth = "0" + hireMonth;
//        }
//        int random = (int)(Math.random()*100);
//        String num = random >= 10 ? Integer.toString(random) : "0" + Integer.toString(random);
//        users.setUser_number(hireYear + hireMonth + num);
//
//        // 氏名
//        users.setUser_name(usersForm.getUserLastName() + " " + usersForm.getUserFirstName());
//        users.setUser_name_kana(usersForm.getUserLastKana() + " " + usersForm.getUserFirstKana());
//
//        // 入社年月
//        users.setHire_date(hireYear + hireMonth + "01");
//
//        // 所属
//        users.setAffiliation_id(usersForm.getAffiliationId());
//
//        // 権限区分
//        users.setRole_class(usersForm.getRoleClass());
//
//        // 生年月日
//        String birthYear = String.valueOf(usersForm.getBirthYear());
//        String birthMonth = String.valueOf(usersForm.getBirthMonth());
//        String birthDay = String.valueOf(usersForm.getBirthDay());
//        if (birthMonth.length() == 1) {
//            birthMonth = "0" + birthMonth;
//        }
//        if (birthDay.length() == 1) {
//            birthDay = "0" + birthDay;
//        }
//        users.setBirth_date(birthYear + birthMonth + birthDay);
//
//        // 営業担当
//        users.setSales_flg(usersForm.getSalesFlg());
//
//        // 郵便番号
//        users.setPost_code(usersForm.getPostCode1() + "-" +usersForm.getPostCode2());
//
//        // 住所
//        users.setAddress(usersForm.getAddress());
//
//        // 電話番号
//        users.setPhone_number(usersForm.getPhoneNumber1() + "-" + usersForm.getPhoneNumber2() + "-" + usersForm.getPhoneNumber3());
//
//        // メールアドレス
//        users.setEmail_address(usersForm.getEmailAddress());
//
//        // パスワードはハッシュにする
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        users.setPassword(passwordEncoder.encode(usersForm.getPassword()));
//
//        // 作成者はセッションのユーザID（現状は仮値）
//        users.setCreated_by(1);
//
//        usersMapper.insert(users);
//        return "redirect:/user/list";
//
//    }
//
//    // 論理削除処理
//    @RequestMapping(value="/user/delete")
//    public String delete(Users users) {
//
//        usersMapper.delete(users);
//        return "redirect:/user/list";
//
//    }

}
