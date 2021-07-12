package com.placideh.tracker.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.placideh.tracker.domain.Category;
import com.placideh.tracker.exceptions.EtBadRequestException;
import com.placideh.tracker.exceptions.EtResourceNotFoundException;
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
	private static final String SQL_CREATE="INSERT INTO et_categories (user_id,title,description)VALUES(?,?,?)";
	private static final String SQL_FIND_BY_ID="SELECT c.category_id,c.user_id,c.title,c.description,COALESCE(SUM(t.amount),0 )as total_expense FROM "
			+ "et_transactions t RIGHT OUTER JOIN et_categories c ON "
			+ "c.category_id=t.category_id WHERE c.user_id=? AND c.category_id=? GROUP BY c.category_id";
	
	private static final String SQL_FIND_ALL="SELECT c.category_id,c.user_id,c.title,c.description,COALESCE(SUM(t.amount),0 )as total_expense FROM "
			+ "et_transactions t RIGHT OUTER JOIN et_categories c ON "
			+ "c.category_id=t.category_id WHERE c.user_id=? GROUP BY c.category_id";
	private static final String SQL_UPDATE="UPDATE et_categories SET title=? ,description=? WHERE user_id=? AND category_id=?";
	private static final String SQL_DELETE="DELETE FROM et_categories WHERE user_id =? AND category_id=?";
	private static final String SQL_DELETE_ALL_TRANSACTIONS="DELETE FROM et_transactions WHERE category_id=?";
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
@Override
public Integer create(Integer userId, String title, String description) throws EtBadRequestException {
	try {
		KeyHolder keyHolder=new GeneratedKeyHolder();
		
		jdbcTemplate.update(connection -> {
			PreparedStatement ps=connection.prepareStatement(SQL_CREATE,Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, userId);
			ps.setString(2,title);
			ps.setString(3, description);
			return ps;
			
		},keyHolder);
		return (Integer)keyHolder.getKey().intValue();
	}catch(Exception e) {
		throw new EtBadRequestException("Invalid request");
	}
}

@Override
public List<Category> findAll(Integer userId) throws EtResourceNotFoundException {
	
	return jdbcTemplate.query(SQL_FIND_ALL, new Object[] {userId},categoryRowMapper);
}

@Override
public Category findById(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
	try {
		return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] {userId,categoryId},categoryRowMapper);
	}catch(Exception e) {
		throw new EtResourceNotFoundException("Category not found");
	}
}

@Override
public void update(Integer userId, Integer categoryId, Category category) throws EtBadRequestException {
	try {
		jdbcTemplate.update(SQL_UPDATE,new Object[] {category.getTitle(),category.getDescription(),userId,categoryId});
	}catch(Exception e) {
		throw new EtBadRequestException("Invalid request");
	}
	
}

@Override
public void removeById(Integer userId, Integer categoryId) {
	this.removeAllCatTransactions(categoryId);
	int count=jdbcTemplate.update(SQL_DELETE,new Object[] {userId,categoryId});
}
private void removeAllCatTransactions(Integer categoryId) {
	jdbcTemplate.update(SQL_DELETE_ALL_TRANSACTIONS,new Object[] {categoryId});
}
private RowMapper<Category> categoryRowMapper=((rs,rowNum)-> {
	return new Category(rs.getInt("category_id"),
			rs.getInt("user_id"),
			rs.getString("title"),
			rs.getString("description"),
			rs.getDouble("total_expense"));
}
);



}
