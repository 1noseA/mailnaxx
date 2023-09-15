package com.mailnaxx.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.mailnaxx.entity.Users;
import com.mailnaxx.form.SearchUsersForm;

@Mapper
public interface UsersMapper {

    // ログイン
    public Optional<Users> findLoginUser(String userNumber);

    // ログイン成功時更新
    public void update(String userNumber);

    // 全件取得
    public List<Users> findAll();

    // 検索
    public List<Users> findBySearchForm(SearchUsersForm searchUsersForm);

    // 1件取得
    public Users findById(int userId);

    // 営業担当取得
    public List<Users> findBySales();

    // 登録
    public void insert(Users users);

    // 削除
    public void delete(int userId);
}
