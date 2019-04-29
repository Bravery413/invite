package controllers;


import core.dbutils.DBException;
import dao.WorkDao;
import dto.WorkQuery;
import models.Work;
import play.mvc.*;
import sun.java2d.opengl.WGLSurfaceData;
import utils.BeanUtils;

import java.util.Collections;
import java.util.List;


public class Application extends BaseController {
    public static WorkDao workDao=new WorkDao();

    public static void index() {
        render("Application/index.html");
    }
    public static void list() throws DBException {
        WorkQuery req = getDTO(WorkQuery.class);
        List<Work> works = WorkDao.list(req);
        Collections.sort(works);
        renderJSON(works);
    }

    public static void add() throws DBException {
        WorkQuery req = getDTO(WorkQuery.class);
        Work work = new Work();
        BeanUtils.copyProperties(work,req);
        work.setStatus("邀请中");
        Long save = workDao.save(work);
        renderJSON(save);
    }
    public static void delete(int id) throws DBException {
        long iid=id;
        workDao.delete(iid);
        render("Application/index.html");
    }
    public static void accept(int id) throws DBException {
        workDao.update(id);
        render("Application/index.html");
    }
    public static void reject(int id) throws DBException {
        workDao.reject(id);
        render("Application/index.html");
    }




}