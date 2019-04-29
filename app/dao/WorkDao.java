package dao;

import core.dbutils.DBException;
import core.dbutils.DBManager;
import dto.WorkQuery;
import models.Work;


import java.util.ArrayList;
import java.util.List;


public class WorkDao extends BaseDao<Work> {
    public static List<Work> list(WorkQuery req) throws DBException {
        StringBuffer sql = new StringBuffer("select * from work where 1=1");
        List<Work> works;
        if (req.getStatus() != null) {
            String status = req.getStatus();
            sql.append(" and status=? ");
            works = DBManager.queryForList(Work.class, sql.toString(), status);
        } else {
            works = DBManager.queryForList(Work.class, sql.toString());
        }
        return works;
    }

    public static int update(int id) throws DBException {
        String sql = "update work set status='已邀请' where id=?";
        int update = DBManager.update(sql, id);
        return update;
    }

    public static int reject(int id) throws DBException {
        String sql = "update work set status='已拒绝' where id=?";
        int update = DBManager.update(sql, id);
        return update;
    }

}

