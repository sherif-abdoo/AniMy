package com.AniMy.repository;

import com.AniMy.models.FriendsStatus;
import com.AniMy.models.User;
import com.AniMy.models.UserFriends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFriendsRepository extends JpaRepository<UserFriends,Integer> {



    @Query("""
    SELECT uf FROM UserFriends uf 
    WHERE 
        (uf.user1 = :user1 AND uf.user2 = :user2) 
        OR 
        (uf.user1 = :user2 AND uf.user2 = :user1)
""")
    Optional<UserFriends> findByUser1AndUser2(
            @Param("user1") User user1,
            @Param("user2") User user2
    );


    @Query("SELECT uf FROM UserFriends uf WHERE (uf.user1 = :u1 AND uf.user2 = :u2) OR (uf.user1 = :u2 AND uf.user2 = :u1)")
    Optional<UserFriends> findFriendship(@Param("u1") User u1, @Param("u2") User u2);

    List<UserFriends> findAllByUser1OrUser2(User user1 , User user2);
    @Query("""
    SELECT uf FROM UserFriends uf 
    WHERE 
        (uf.user1 = :user1 AND uf.status = :status) 
        OR 
        (uf.user2 = :user1 AND uf.status = :status)
""")
    List<UserFriends> findByUserAndStatus(
            @Param("user1") User user1,
            @Param("status") FriendsStatus status
    );


    List<UserFriends> findByUser2AndStatus(User user2 , FriendsStatus status);

    @Query("""
    SELECT uf.status FROM UserFriends uf 
    WHERE (uf.user1 = :user1 AND uf.user2 = :user2) 
    OR (uf.user1 = :user2 AND uf.user2 = :user1)
""")
    Optional<FriendsStatus> getStatusByUser1AndUser2(
            @Param("user1") User user1,
            @Param("user2") User user2
    );


}
