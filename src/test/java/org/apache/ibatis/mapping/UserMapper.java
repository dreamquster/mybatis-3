package org.apache.ibatis.mapping;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.ResultMap;

import java.util.List;

/**
 * Created by root on 15-4-29.
 */
public interface UserMapper {
    @Select("select * from users")
    @Results({
        @Result(property = "id", column = "id", id=true),
        @Result(property = "firstName", column = "first_name"),
        @Result(property = "lastName", column = "last_name")
    })
    List<User> getUsers();

    @Select("select * from users where id = #{value}")
    @ResultMap("User")
    User getUserById(int id);

    @Select("select * from users where first_name = #{value}")
    @ResultMap("User")
    List<User> getUsersByFirstName(String firstName);
}
