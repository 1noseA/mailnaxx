package com.mailnaxx.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mailnaxx.entity.Projects;
import com.mailnaxx.entity.Users;
import com.mailnaxx.entity.WeeklyReports;
import com.mailnaxx.form.SearchWeeklyReportForm;
import com.mailnaxx.form.WeeklyReportForm;
import com.mailnaxx.mapper.ProjectsMapper;
import com.mailnaxx.mapper.UsersMapper;
import com.mailnaxx.mapper.WeeklyReportsMapper;
import com.mailnaxx.security.LoginUserDetails;

@Controller
public class WeeklyReportsController {

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    ProjectsMapper projectsMapper;

    @Autowired
    WeeklyReportsMapper weeklyReportsMapper;

    @ModelAttribute
    WeeklyReportForm setUpForm() {
        return new WeeklyReportForm();
    }

    @RequestMapping("/weekly-report/list")
    public String index(SearchWeeklyReportForm searchWeeklyReportForm, Model model, @AuthenticationPrincipal LoginUserDetails loginUser) {
        List<WeeklyReports> weeklyReportList = weeklyReportsMapper.findAll();
        model.addAttribute("weeklyReportList", weeklyReportList);
        model.addAttribute("loginUserInfo", loginUser.getLoginUser());
        return "weekly-report/list";
    }

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
    @GetMapping("/weekly-report/create")
    public String create(Model model, @AuthenticationPrincipal LoginUserDetails loginUser) {

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

        model.addAttribute("loginUserInfo", loginUser.getLoginUser());
        return "weekly-report/create";
    }
}
