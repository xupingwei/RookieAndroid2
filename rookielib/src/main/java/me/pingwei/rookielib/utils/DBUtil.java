package me.pingwei.rookielib.utils;

import org.litepal.crud.DataSupport;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/10/8.
 */
public class DBUtil {


    /**
     * 保存数据
     *
     * @param ds
     */
    public static void save(DataSupport ds) {
        ds.save();
    }

    /**
     * 保存
     *
     * @param collection 被保存的对象
     */
    public static <T extends DataSupport> void saveAll(Collection<T> collection) {
        DataSupport.saveAll(collection);
    }

    /**
     * 保存并更新数据库中最新的数据
     *
     * @param ds
     * @param column
     * @param value
     */
    public static void updateWithCondition(DataSupport ds, String column, String value) {

//        if (ds.isSaved()) {
        int count = ds.updateAll(column + " = ?", value);
        if (count == 0) {   //没有更新成功或者数据库中没有保存数据时
            ds.save();
        } else {
            LoggerUtils.e("数据库Update成功");
        }
    }

    /**
     * @param aClass
     * @param conditions
     */
    public static void deleteAll(Class aClass, String... conditions) {
        DataSupport.deleteAll(aClass, conditions);
    }

    /**
     * Find all records from one table:
     *
     * @param aClass
     * @return
     */
    public static <T> List<T> findAll(Class aClass) {
        return DataSupport.findAll(aClass);
    }

    /**
     * Find firsr records from one table
     *
     * @param aClass
     * @return
     */
    public static Object findFirst(Class aClass) {
        return DataSupport.findFirst(aClass);
    }


    /**
     * @param column
     * @param value
     * @param aClass
     * @param <T>
     * @return
     */
    public static <T> List<T> findAllWithIdhash(String column, String value, Class aClass) {
        return DataSupport.where(column + " = ?", value).find(aClass);
    }

}
