package com.mailnaxx.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import com.mailnaxx.form.SearchUsersForm;
import com.mailnaxx.form.UsersForm;
import com.mailnaxx.mapper.AffiliationsMapper;
import com.mailnaxx.mapper.UsersMapper;
import com.mailnaxx.security.LoginUserDetails;
import com.mailnaxx.service.UsersService;
import com.mailnaxx.validation.All;
import com.mailnaxx.validation.GroupOrder;
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
    UsersService usersService;

    @ModelAttribute("searchUsersForm")
    public SearchUsersForm createSearchForm(){
        return new SearchUsersForm();
    }

    // 一覧画面初期表示
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
     * 検索処理
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
        model.addAttribute("userId", 0);

        // 所属プルダウン
        List<Affiliations> affiliationList = affiliationsMapper.findAll();
        model.addAttribute("affiliationList", affiliationList);
        model.addAttribute("notAffiliation", UserConstants.NOT_AFFILIATION);

        // 権限区分プルダウン
        model.addAttribute("roleClassList", RoleClass.values());

        model.addAttribute("loginUserInfo", loginUser.getLoginUser());
        return "user/create";
    }

    // 登録処理
    @PostMapping("/user/create")
    public String create(@ModelAttribute @Validated(All.class) UsersForm usersForm, BindingResult result, Model model, @AuthenticationPrincipal LoginUserDetails loginUser) {
        // 入力エラーチェック
        if (result.hasErrors()) {
            // リダイレクトだと入力エラーの値が引き継がれない
            // return "redirect:/user/create";
            return create(usersForm, model, loginUser);
        }

        Users user = new Users();

        // 登録サービス実行
        usersService.insert(user, usersForm, loginUser);

        return "redirect:/user/list";
    }

    // 論理削除処理
    @RequestMapping("/user/delete")
    public String delete(int userId, @AuthenticationPrincipal LoginUserDetails loginUser) {

        // 削除権限チェック
        if (loginUser.getLoginUser().getRoleClass().equals(RoleClass.AFFAIRS.getCode())) {
            Users user = new Users();
            user.setUserId(userId);
            usersService.delete(user, loginUser);
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
        model.addAttribute("userId", userId);
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

    // 更新処理
    @Transactional
    @PostMapping("/user/update")
    public String update(int userId, @ModelAttribute @Validated(GroupOrder.class) UsersForm usersForm, BindingResult result, Model model, @AuthenticationPrincipal LoginUserDetails loginUser) {
        // 入力エラーチェック
        if (result.hasErrors()) {
            // パスワード入力必須エラーはスルーする
            return edit(userId, usersForm, model, loginUser);
        }

        Users user = new Users();
        user.setUserId(userId);

        // 更新サービス実行
        usersService.update(user, usersForm, loginUser);

        return "redirect:/user/list";
    }
}
