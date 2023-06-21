package com.sociallogin.demo.model;

import com.sociallogin.demo.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    private String gender;
    private RoleType role;
    private String provider; // OAuth를 위해 구성한 추가 필드
    private String providerId; // OAuth를 위해 구성한 추가 필드
    @CreationTimestamp
    private Timestamp createDate;
}
