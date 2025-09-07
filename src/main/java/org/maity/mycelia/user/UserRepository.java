package org.maity.mycelia.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);

    @Query("select (count(u) > 0) from UserEntity u where u.userName = :userName")
    boolean existsByUsername(@Param("userName") String userName);

}
