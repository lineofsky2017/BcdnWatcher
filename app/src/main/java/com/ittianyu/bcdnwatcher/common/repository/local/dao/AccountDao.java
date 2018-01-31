package com.ittianyu.bcdnwatcher.common.repository.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ittianyu.bcdnwatcher.common.bean.AccountBean;

import java.util.List;


/**
 * Created by 86839 on 2017/10/7.
 */
@Dao
public interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long addOrUpdate(AccountBean account);

    @Delete
    void delete(AccountBean account);

    @Query("select * from account")
    List<AccountBean> queryAll();
}
