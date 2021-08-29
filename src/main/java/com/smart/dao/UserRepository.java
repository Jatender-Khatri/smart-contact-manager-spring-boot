package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
