package cn.itcast.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.system.Permission;

public interface PermissionRepository extends JpaRepository<Permission,Integer>{

}
