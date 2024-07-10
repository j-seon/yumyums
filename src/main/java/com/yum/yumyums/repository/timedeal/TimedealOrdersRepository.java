package com.yum.yumyums.repository.timedeal;

import com.yum.yumyums.entity.timedeal.Timedeal;
import com.yum.yumyums.entity.timedeal.TimedealOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimedealOrdersRepository extends JpaRepository<TimedealOrders, Integer> {
}
