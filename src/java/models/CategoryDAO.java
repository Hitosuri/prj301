package models;

import dal.Category;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CategoryDAO extends AbstractDAO<Category> {
    public ArrayList<Category> getCategories() throws Exception {
        String sql = "select * from Categories";
        return selectMany(sql);
    }
    
    public Category getCategoryById(int id) throws Exception {
        String sql = "select * from Categories where CategoryID = ?";
        return selectOne(sql, id);
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println(new CategoryDAO().getCategoryById(7));
    }

    @Override
    protected Category propMapping(ResultSet rs) throws Exception {
        return new Category(
                rs.getInt("CategoryID"),
                rs.getString("CategoryName"),
                rs.getString("Description"),
                rs.getBytes("Picture")
        );
    }
}
