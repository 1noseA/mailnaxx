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

import com.mailnaxx.constants.CommonConstants;
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

        searchUsersForm.setSearchCondition(CommonConstants.PREFIX_MATCH);
        model.addAttribute("searchUsersForm", searchUsersForm);

        boolean isAdmin = false;
        if (loginUser.getLoginUser().getRoleClass().equals(RoleClass.AFFAIRS.getCode())) {
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
        List<Users> userList = usersMapper.findBySearchForm(searchUsersForm);
        model.addAttribute("userList", userList);
        model.addAttribute("roleClassList", RoleClass.values());

        boolean isAdmin = (boolean) session.getAttribute("session_isAdmin");
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("loginUserInfo", loginUser.getLoginUser());
        return "user/list";
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

        Users user = new Users();

        // 社員番号生成
        String hireYear = usersForm.getHireYear();
        String hireMonth = usersForm.getHireMonth();
        if (hireMonth.length() == 1) {
            hireMonth = CommonConstants.FILLED_ZERO + hireMonth;
        }
        LocalDate hireDate = LocalDate.parse(hireYear + hireMonth + CommonConstants.FIRST_DAY, DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<Users> usersList =  usersMapper.findAll();
        int max = (int) usersList.stream()
                .filter(u -> u.getHireDate().isEqual(hireDate))
                .count() + 1;
        String num = max >= 10 ? String.valueOf(max) : CommonConstants.FILLED_ZERO + String.valueOf(max);
        user.setUserNumber(hireYear + hireMonth + num);

        // 氏名
        user.setUserName(usersForm.getUserLastName() + CommonConstants.HALF_SPACE + usersForm.getUserFirstName());
        user.setUserNameKana(usersForm.getUserLastKana() + CommonConstants.HALF_SPACE + usersForm.getUserFirstKana());

        // 入社年月
        user.setHireDate(hireDate);

        // 所属
        Affiliations affiliation = new Affiliations();
        affiliation.setAffiliationId(Integer.parseInt(usersForm.getAffiliationId()));
        user.setAffiliation(affiliation);

        // 権限区分
        user.setRoleClass(usersForm.getRoleClass());

        // 生年月日
        String birthYear = usersForm.getBirthYear();
        String birthMonth = usersForm.getBirthMonth();
        String birthDay = usersForm.getBirthDay();
        if (birthMonth.length() == 1) {
            birthMonth = CommonConstants.FILLED_ZERO + birthMonth;
        }
        if (birthDay.length() == 1) {
            birthDay = CommonConstants.FILLED_ZERO + birthDay;
        }
        user.setBirthDate(LocalDate.parse(birthYear + birthMonth + birthDay, DateTimeFormatter.ofPattern("yyyyMMdd")));

        // 営業担当
        user.setSalesFlg(usersForm.getSalesFlg());

        // 郵便番号
        user.setPostCode(usersForm.getPostCode1() + CommonConstants.HALF_HYPHEN +usersForm.getPostCode2());

        // 住所
        user.setAddress(usersForm.getAddress());

        // 電話番号
        user.setPhoneNumber(usersForm.getPhoneNumber1() + CommonConstants.HALF_HYPHEN + usersForm.getPhoneNumber2() + CommonConstants.HALF_HYPHEN + usersForm.getPhoneNumber3());

        // メールアドレス
        user.setEmailAddress(usersForm.getEmailAddress());

        // パスワードはハッシュにする
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(usersForm.getPassword()));

        // 作成者はセッションのユーザID
        user.setCreatedBy("test");

        usersMapper.insert(user);
        return "redirect:/user/list";
    }

    // 論理削除処理
    @RequestMapping("/user/delete")
    public String delete(int userId, @AuthenticationPrincipal LoginUserDetails loginUser) {
        // 削除権限チェック
        if (loginUser.getLoginUser().getRoleClass().equals(RoleClass.AFFAIRS.getCode())) {
            usersMapper.delete(userId);
        } else {
            // エラーメッセージを設定する
        }
        return "redirect:/user/list";
    }

    // 詳細画面初期表示
    @PostMapping("/user/detail")
    public String detail(int userId, Model model, @AuthenticationPrincipal LoginUserDetails loginUser) {
        Users userInfo = usersMapper.findById(userId);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("roleClass", RoleClass.getViewNameByCode(userInfo.getRoleClass()));
        model.addAttribute("loginUserInfo", loginUser.getLoginUser());
        return "user/detail";
    }

    // 編集画面初期表示
    @PostMapping("/user/edit")
    public String edit(int userId, @ModelAttribute UsersForm usersForm, Model model, @AuthenticationPrincipal LoginUserDetails loginUser) {
        Users userInfo = usersMapper.findById(userId);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("roleClass", RoleClass.getViewNameByCode(userInfo.getRoleClass()));

        // Formクラスに設定
        String[] userName = userInfo.getUserName().split(CommonConstants.HALF_SPACE);
        usersForm.setUserLastName(userName[0]);
        usersForm.setUserFirstName(userName[1]);
        String[] userNameKana = userInfo.getUserNameKana().split(CommonConstants.HALF_SPACE);
        usersForm.setUserLastKana(userNameKana[0]);
        usersForm.setUserFirstKana(userNameKana[1]);
        String[] hireDate = userInfo.getHireDate().toString().split(CommonConstants.HALF_HYPHEN);
        usersForm.setHireYear(hireDate[0]);
        usersForm.setHireMonth(hireDate[1].replaceFirst("^0+", ""));
        usersForm.setAffiliationId(String.valueOf(userInfo.getAffiliation().getAffiliationId()));
        usersForm.setRoleClass(userInfo.getRoleClass());
        usersForm.setSalesFlg(userInfo.getSalesFlg());
        String[] birthDate = userInfo.getBirthDate().toString().split(CommonConstants.HALF_HYPHEN);
        usersForm.setBirthYear(birthDate[0]);
        usersForm.setBirthMonth(birthDate[1].replaceFirst("^0+", ""));
        usersForm.setBirthDay(birthDate[2].replaceFirst("^0+", ""));
        String[] postCode = userInfo.getPostCode().split(CommonConstants.HALF_HYPHEN);
        usersForm.setPostCode1(postCode[0]);
        usersForm.setPostCode2(postCode[1]);
        usersForm.setAddress(userInfo.getAddress());
        String[] phoneNumber = userInfo.getPhoneNumber().split(CommonConstants.HALF_HYPHEN);
        usersForm.setPhoneNumber1(phoneNumber[0]);
        usersForm.setPhoneNumber2(phoneNumber[1]);
        usersForm.setPhoneNumber3(phoneNumber[2]);
        usersForm.setEmailAddress(userInfo.getEmailAddress());

        // 所属プルダウン
        List<Affiliations> affiliationList = affiliationsMapper.findAll();
        model.addAttribute("affiliationList", affiliationList);
        model.addAttribute("notAffiliation", UserConstants.NOT_AFFILIATION);

        // 権限区分プルダウン
        model.addAttribute("roleClassList", RoleClass.values());

        model.addAttribute("loginUserInfo", loginUser.getLoginUser());
        return "user/create";
    }
}
