package models;

import dal.DBContext;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLType;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDAO<T> extends DBContext {

    private int maxPageItem = 12;
    private int totalPage;
    private int maxTotalPage = 1000;
    private ArrayList<T> itemList;
    private static Map<Class<?>, Integer> sqlTypes;

    static {
        sqlTypes = new HashMap<>();
        sqlTypes.put(Integer.class, Types.INTEGER);
        sqlTypes.put(String.class, Types.NVARCHAR);
        sqlTypes.put(Double.class, Types.DOUBLE);
        sqlTypes.put(Date.class, Types.DATE);
        sqlTypes.put(Boolean.class, Types.BOOLEAN);
        sqlTypes.put(Time.class, Types.TIME);
        sqlTypes.put(Timestamp.class, Types.TIMESTAMP);
        sqlTypes.put(byte[].class, Types.BINARY);
    }

    protected AbstractDAO() {
    }

    private void setParameter(PreparedStatement ps, Object... params) throws Exception {
        int index = 1;
        for (Object param : params) {
            Class<?> clazz = param.getClass();
            if (!sqlTypes.containsKey(clazz)) {
                throw new Exception(param.getClass() + " haven't been supported yet.");
            }
            ps.setObject(index++, param, sqlTypes.get(param.getClass()));
        }
    }

    protected abstract T propMapping(ResultSet rs) throws Exception;

    protected ArrayList<T> selectMany(String sql, Object... params) throws Exception {
        ArrayList<T> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            setParameter(ps, params);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(propMapping(rs));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return list;
    }

    protected T selectOne(String sql, Object... params) throws Exception {
        T obj = null;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            setParameter(ps, params);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = propMapping(rs);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return obj;
    }

    protected <R> R selectScalar(String sql, Class<R> clazz, Object... params) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            setParameter(ps, params);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return clazz.cast(rs.getObject(1));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    protected int insert(String sql, Object... params) throws Exception {
        int id = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setParameter(ps, params);
            int result = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return id;
    }

    protected int update(String sql, Object... params) throws Exception {
        int state = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            setParameter(ps, params);
            state = ps.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return state;
    }

    public ArrayList<T> getItemsInPage(int currentPage) {
        ArrayList<T> list = new ArrayList<>();
        if (this.itemList != null) {
            int index = (currentPage - 1) * this.maxPageItem;
            int end = Math.min(currentPage * this.maxPageItem, this.itemList.size());
            while (index < end) {
                list.add(this.itemList.get(index++));
            }
        }
        return list;
    }

    public int[] getPageRange(int currentPage) {
        int totalPageLimit = this.maxTotalPage;
        int[] pageRange = {currentPage, currentPage};
        while (totalPageLimit > 1 && (pageRange[0] != 1 || pageRange[1] != totalPage)) {
            if (pageRange[0] != 1) {
                pageRange[0]--;
                totalPageLimit--;
            }
            if (totalPageLimit <= 1) {
                break;
            }
            if (pageRange[1] != totalPage) {
                pageRange[1]++;
                totalPageLimit--;
            }
        }
        return pageRange;
    }

    public int getMaxPageItem() {
        return maxPageItem;
    }

    public void setMaxPageItem(int maxPageItem) {
        this.maxPageItem = maxPageItem;
        this.totalPage = (int) Math.ceil(this.itemList.size() / ((double) maxPageItem));
        if (this.totalPage == 0) {
            this.totalPage = 1;
        }
    }

    public ArrayList<T> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<T> itemList) {
        this.itemList = itemList;
        this.totalPage = (int) Math.ceil(itemList.size() / ((double) this.maxPageItem));
        if (this.totalPage == 0) {
            this.totalPage = 1;
        }
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getMaxTotalPage() {
        return maxTotalPage;
    }

    public void setMaxTotalPage(int maxTotalPage) {
        this.maxTotalPage = maxTotalPage;
    }

}
