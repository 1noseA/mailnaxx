package com.mailnaxx.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mailnaxx.entity.Affiliations;
import com.mailnaxx.entity.Projects;
import com.mailnaxx.entity.Users;
import com.mailnaxx.entity.WeeklyReports;
import com.mailnaxx.form.SearchWeeklyReportForm;
import com.mailnaxx.form.WeeklyReportForm;
import com.mailnaxx.security.LoginUserDetails;
import com.mailnaxx.service.AffiliationsService;
import com.mailnaxx.service.ProjectsService;
import com.mailnaxx.service.UsersService;
import com.mailnaxx.service.WeeklyReportsService;

@Controller
public class WeeklyReportsController {

    @Autowired
    UsersService usersService;

    @Autowired
    WeeklyReportsService weeklyReportsService;

    @Autowired
    AffiliationsService affiliationsService;

    @Autowired
    ProjectsService projectsService;

    @ModelAttribute
    WeeklyReportForm setUpForm() {
        return new WeeklyReportForm();
    }

    // 一覧画面初期表示
    @RequestMapping("/weekly-report/list")
    public String index(SearchWeeklyReportForm searchWeeklyReportForm, Model model, @AuthenticationPrincipal LoginUserDetails loginUser) {
        // 週報を全件取得
        List<WeeklyReports> weeklyReportList = weeklyReportsService.findAll();
        model.addAttribute("weeklyReportList", weeklyReportList);

        // 所属プルダウン
        List<Affiliations> affiliationList = affiliationsService.findAll();
        model.addAttribute("affiliationList", affiliationList);

        // 担当営業プルダウン
        List<Projects> projectList = projectsService.findAll();
        Set<Users> salesList = new LinkedHashSet<>();
        for (Projects p : projectList) {
            salesList.add(p.getSalesUser());
        }
        model.addAttribute("salesList", salesList);

        // 報告対象週プルダウン
        Set<LocalDate> reportDateList = new LinkedHashSet<>();
        for (WeeklyReports w : weeklyReportList) {
            reportDateList.add(w.getReportDate());
        }
        model.addAttribute("reportDateList", reportDateList);

        model.addAttribute("loginUserInfo", loginUser.getLoginUser());
        return "weekly-report/list";
    }

    // 検索処理
    @PostMapping("/weekly-report/search")
    public String search(SearchWeeklyReportForm searchWeeklyReportForm, Model model, @AuthenticationPrincipal LoginUserDetails loginUser) {
        List<WeeklyReports> weeklyReportList = weeklyReportsService.findBySearchForm(searchWeeklyReportForm);
        model.addAttribute("weeklyReportList", weeklyReportList);

        // 所属プルダウン
        List<Affiliations> affiliationList = affiliationsService.findAll();
        model.addAttribute("affiliationList", affiliationList);

        // 担当営業プルダウン
        List<Projects> projectList = projectsService.findAll();
        Set<Users> salesList = new LinkedHashSet<>();
        for (Projects p : projectList) {
            salesList.add(p.getSalesUser());
        }
        model.addAttribute("salesList", salesList);

        // 報告対象週プルダウン
        List<WeeklyReports> list = weeklyReportsService.findAll();
        Set<LocalDate> reportDateList = new LinkedHashSet<>();
        for (WeeklyReports w : list) {
            reportDateList.add(w.getReportDate());
        }
        model.addAttribute("reportDateList", reportDateList);

        model.addAttribute("loginUserInfo", loginUser.getLoginUser());
        return "weekly-report/list";
    }

    // 詳細画面初期表示
    @PostMapping("/weekly-report/detail")
    public String detail(int weeklyReportId, Model model, @AuthenticationPrincipal LoginUserDetails loginUser) {
        WeeklyReports weeklyReportInfo = weeklyReportsService.findById(weeklyReportId);
        model.addAttribute("weeklyReportInfo", weeklyReportInfo);
        model.addAttribute("loginUserInfo", loginUser.getLoginUser());
        return "weekly-report/detail";
    }

    // 登録画面初期表示
    @GetMapping("/weekly-report/create")
    public String create(Model model, @AuthenticationPrincipal LoginUserDetails loginUser) {

        // 担当営業プルダウン
        List<Projects> projectList = projectsService.findAll();
        Set<Users> salesList = new HashSet<>();
        for (Projects p : projectList) {
            salesList.add(p.getSalesUser());
        }
        model.addAttribute("salesList", salesList);

        // 現場プルダウン
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
        List<Users> userList = usersService.findAll();
        model.addAttribute("userList", userList);

        model.addAttribute("loginUserInfo", loginUser.getLoginUser());
        return "weekly-report/create";
    }
}
