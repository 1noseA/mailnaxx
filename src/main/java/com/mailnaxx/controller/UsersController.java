package com.mailnaxx.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mailnaxx.constants.UserConstants;
import com.mailnaxx.entity.Affiliations;
import com.mailnaxx.entity.Users;
import com.mailnaxx.form.GroupOrder;
import com.mailnaxx.form.SearchUsersForm;
import com.mailnaxx.form.UsersForm;
import com.mailnaxx.mapper.AffiliationsMapper;
import com.mailnaxx.mapper.UsersMapper;
import com.mailnaxx.security.LoginUserDetails;
import com.mailnaxx.values.RoleClass;

@Controller
public class UsersController {

    @Autowired
    HttpSession session;

    @Autowired
    AffiliationsMapper affiliationsMapper;

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @ModelAttribute("searchUsersForm")
    public SearchUsersForm createSearchForm(){
        return new SearchUsersForm();
    }

    @RequestMapping("/user/list")
    public String index(SearchUsersForm searchUsersForm, Model model, @AuthenticationPrincipal LoginUserDetails loginUser) {
        List<Users> userList = usersMapper.findAll();
        model.addAttribute("userList", userList);
        model.addAttribute("roleClassList", RoleClass.values());

        searchUsersForm.setSearchCondition("0");
        model.addAttribute("searchUsersForm", searchUsersForm);

        boolean isAdmin = false;
        if (loginUser.getLoginUser().getRole_class().equals("4")) {
            isAdmin = true;
        }
        session.setAttribute("session_isAdmin", isAdmin);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("loginUserInfo", loginUser.getLoginUser());
        return "user/list";
    }

    /**
     * 検索処理を行う
     * @param searchForm 検索用Formオブジェクト
     * @param model Modelオブジェクト
     * @return 一覧画面のパス
     */
    @PostMapping("/user/search")
    public String search(SearchUsersForm searchUsersForm, Model model, @AuthenticationPrincipal LoginUserDetails loginUser) {
        List<Users> resultList = usersMapper.findBySearchForm(searchUsersForm);
        model.addAttribute("userList", resultList);
        model.addAttribute("roleClassList", RoleClass.values());

        boolean isAdmin = (boolean) session.getAttribute("session_isAdmin");
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("loginUserInfo", loginUser.getLoginUser());
        return "user/list";
    }

    // 詳細画面初期表示
    @PostMapping("/user/detail")
    public String detail(int userId, Model model, @AuthenticationPrincipal LoginUserDetails loginUser) {
        Users userInfo = usersMapper.findById(userId);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("loginUserInfo", loginUser.getLoginUser());
        return "user/detail";
    }

    // 登録画面初期表示
    @GetMapping("/user/create")
    public String create(@ModelAttribute UsersForm usersForm, Model model, @AuthenticationPrincipal LoginUserDetails loginUser) {

        // 所属プルダウン
        List<Affiliations> affiliationList = affiliationsMapper.findAll();
        model.addAttribute("affiliationList", affiliationList);
        model.addAttribute("notAffiliation", UserConstants.NOT_AFFILIATION);

        // 権限区分プルダウン
        model.addAttribute("roleClassList", RoleClass.values());

        model.addAttribute("loginUserInfo", loginUser.getLoginUser());
        return "user/create";
    }

    // 登録画面登録処理
    @PostMapping("/user/create")
    public String create(@ModelAttribute @Validated(GroupOrder.class) UsersForm usersForm, BindingResult result, Model model, @AuthenticationPrincipal LoginUserDetails loginUser) {
        // 入力エラーチェック
        if (result.hasErrors()) {
            // リダイレクトだと入力エラーの値が引き継がれない
            // return "redirect:/user/create";
            return create(usersForm, model, loginUser);
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
        return "redirect:/user/list";
    }

    // 論理削除処理
    @RequestMapping("/user/delete")
    public String delete(int userId, @AuthenticationPrincipal LoginUserDetails loginUser) {
        // 削除権限チェック
        if (loginUser.getLoginUser().getRole_class().equals("4")) {
            usersMapper.delete(userId);
        } else {
            // エラーメッセージを設定する
        }
        return "redirect:/user/list";
    }
}
