package com.mailnaxx.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mailnaxx.constants.CommonConstants;
import com.mailnaxx.entity.Affiliations;
import com.mailnaxx.entity.Users;
import com.mailnaxx.form.SearchUsersForm;
import com.mailnaxx.form.SelectForm;
import com.mailnaxx.form.UsersForm;
import com.mailnaxx.mapper.UsersMapper;
import com.mailnaxx.security.LoginUserDetails;

@Service
public class UsersService {

    @Autowired
    UsersMapper usersMapper;

    // 全件取得
    public List<Users> findAll() {
        List<Users> userList = usersMapper.findAll();
        return userList;
    }

    // 検索結果取得
    public List<Users> findBySearchForm(SearchUsersForm searchUsersForm) {
        List<Users> userList = usersMapper.findBySearchForm(searchUsersForm);
        return userList;
    }

    // 詳細情報取得
    public Users findById(int userId) {
        Users userInfo = usersMapper.findById(userId);
        return userInfo;
    }

    // 登録処理
    @Transactional
    public void insert(Users user, UsersForm usersForm, @AuthenticationPrincipal LoginUserDetails loginUser) {
        // 入力値をセットする
        user = setUserForm(user, usersForm);

        // パスワードはハッシュにする
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(usersForm.getPassword()));

        // 作成者はセッションの社員番号
        user.setCreatedBy(loginUser.getLoginUser().getUserNumber());

        // 登録
        usersMapper.insert(user);
    }

    // 更新処理
    @Transactional
    public void update(Users user, UsersForm usersForm, @AuthenticationPrincipal LoginUserDetails loginUser) {
        // 排他ロック
        user = usersMapper.forLockById(user.getUserId());

        // 入力値をセットする
        user = setUserForm(user, usersForm);

        // パスワードは入力されていたら変更
        if (usersForm.getPassword() != "") {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(usersForm.getPassword()));
            // パスワード変更日時
            user.setPassChangedDate(LocalDateTime.now());
            // 前回パスワード
            user.setOldPassword(user.getPassword());
        }

        // 更新者はセッションの社員番号
        user.setUpdatedBy(loginUser.getLoginUser().getUserNumber());

        // 更新
        usersMapper.update(user);
    }

    // 入力値をセットする
    private Users setUserForm(Users user, UsersForm usersForm) {
        // 氏名
        user.setUserName(usersForm.getUserLastName() + CommonConstants.HALF_SPACE + usersForm.getUserFirstName());
        user.setUserNameKana(usersForm.getUserLastKana() + CommonConstants.HALF_SPACE + usersForm.getUserFirstKana());

        // 入社年月
        String hireYear = usersForm.getHireYear();
        String hireMonth = usersForm.getHireMonth();
        if (hireMonth.length() == 1) {
            hireMonth = CommonConstants.FILLED_ZERO + hireMonth;
        }
        LocalDate hireDate = LocalDate.parse(hireYear + hireMonth + CommonConstants.FIRST_DAY, DateTimeFormatter.ofPattern("yyyyMMdd"));
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

        // 登録の場合
        if (user.getUserId() == 0) {
            // 社員番号生成
            if (hireMonth.length() == 1) {
                hireMonth = CommonConstants.FILLED_ZERO + hireMonth;
            }
            List<Users> usersList =  usersMapper.findAll();
            int max = (int) usersList.stream()
                    .filter(u -> u.getHireDate().isEqual(hireDate))
                    .count() + 1;
            String num = max >= 10 ? String.valueOf(max) : CommonConstants.FILLED_ZERO + String.valueOf(max);
            user.setUserNumber(hireYear + hireMonth + num);
        }
        return user;
    }

    // 論理削除処理
    @Transactional
    public void delete(SelectForm selectForm, @AuthenticationPrincipal LoginUserDetails loginUser) {
        List<Integer> idList = new ArrayList<>();
        for (int i = 0; i < selectForm.getSelectTarget().size(); i++) {
            idList.add(selectForm.getSelectTarget().get(i));
        }
        // 複数件排他ロック
        List<Users> userList = usersMapper.forLockByIdList(idList);

        for (int i = 0; i < userList.size(); i++) {
            // 更新者はセッションの社員番号
            userList.get(i).setUpdatedBy(loginUser.getLoginUser().getUserNumber());
        }

        // 論理削除
        usersMapper.delete(userList);
    }
}
