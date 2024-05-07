package org.example.dev_ban_hang.Repository;



import org.example.dev_ban_hang.Entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Integer> {
    //Truy vấn lấy user
    @Query("SELECT u FROM UserRoles u WHERE u.username = :username")
    UserRoles findByUsername(@Param("username") String username);

}
