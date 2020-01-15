package com.wym.dao;

import com.wym.domain.LikeMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 联系人Dao 接口
 */
public interface LikeManDao extends JpaRepository<LikeMan,Integer> , JpaSpecificationExecutor<LikeMan> {
}
