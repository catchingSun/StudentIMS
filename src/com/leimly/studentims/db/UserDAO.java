package com.leimly.studentims.db;

import com.leimly.studentims.entity.User;

/**
 * Created by lizm on 17-12-17.
 */
public interface UserDAO {
    public Boolean searchUser(User user);

    public Boolean deleteUser(User user);

    public Boolean addUser(User user);

    public Boolean searchUserByUserName(User user);

}
