package com.sociallogin.demo.repository;


import com.sociallogin.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepositry를 상속했기 때문에 @Repository 어노테이션이 없어도 됨
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
