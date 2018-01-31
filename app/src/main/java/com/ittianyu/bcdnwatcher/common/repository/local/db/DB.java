package com.ittianyu.bcdnwatcher.common.repository.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ittianyu.bcdnwatcher.common.bean.AccountBean;
import com.ittianyu.bcdnwatcher.common.repository.local.dao.AccountDao;

/**
 * Created by 86839 on 2017/10/7.
 */
@Database(entities = {AccountBean.class}, version = 1, exportSchema = false)
public abstract class DB extends RoomDatabase {
    public abstract AccountDao getAccountDao();

}
