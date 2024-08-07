package com.yum.yumyums.service;

import com.yum.yumyums.dto.FaqDTO;
import com.yum.yumyums.dto.orders.OrdersDTO;
import com.yum.yumyums.dto.orders.OrdersDetailDTO;
import com.yum.yumyums.entity.Faq;
import com.yum.yumyums.entity.orders.Orders;
import com.yum.yumyums.entity.orders.OrdersDetail;
import com.yum.yumyums.entity.orders.OrdersStatus;
import com.yum.yumyums.entity.review.Review;
import com.yum.yumyums.repository.FaqRepository;
import com.yum.yumyums.repository.orders.OrdersDetailRepository;
import com.yum.yumyums.repository.orders.OrdersRepository;
import com.yum.yumyums.repository.orders.OrdersStatusRepository;
import com.yum.yumyums.repository.review.ReviewRepository;
import com.yum.yumyums.repository.seller.StoreRepository;
import com.yum.yumyums.repository.seller.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("dashBoardService")
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersDetailRepository ordersDetailRepository;

    @Autowired
    private OrdersStatusRepository ordersStatusRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MenuRepository menuRepository;

    public List<Review> getReviewsAfter(LocalDateTime createTime) {
        return reviewRepository.findByCreateTimeAfter(createTime);
    }

    public List<Review> getReviewsAfterCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return getReviewsAfter(currentDateTime);
    }

    public String getAverageRateByStoreId(int storeId) {
        System.out.println("getAverageRateByStoreId");
        return formatToOneDecimalPlace(reviewRepository.findAverageByStoreId(storeId));
    }

    public String getAverageRateBeforeTodayByStoreId(int storeId) {
        System.out.println("getAverageRateBeforeTodayByStoreId");
        return formatToOneDecimalPlace(reviewRepository.findAverageRateBeforeTodayByStoreId(storeId));
    }

    @Override
    public int getTodayTotalSalesVolumeByStoreId(int intStoreId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        return ordersRepository.findTotalSalesForTodayByStoreId(intStoreId, startOfDay, endOfDay);
    }

    @Override
    public long countOrdersForTodayByStoreId(int storeId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        Long orderCount = ordersRepository.countOrdersForTodayByStoreId(storeId, startOfDay, endOfDay);
        return (orderCount != null) ? orderCount : 0;
    }

    @Override
    public String getDifferBetweenTodayAndYesterDayRate(int intStoreId) {
        System.out.println("reviewRepository.findAverageByStoreId(intStoreId)-reviewRepository.findAverageRateBeforeTodayByStoreId(intStoreId) : "+(reviewRepository.findAverageByStoreId(intStoreId)-reviewRepository.findAverageRateBeforeTodayByStoreId(intStoreId)));
        return formatToOneDecimalPlace(reviewRepository.findAverageByStoreId(intStoreId)-reviewRepository.findAverageRateBeforeTodayByStoreId(intStoreId));

    }

    @Override
    public List<HashMap<String, Object>> getOrderHashListByStoreId(int storeId) {
        List<HashMap<String, Object>> returnHashList = new ArrayList<>();


        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        List<OrdersDTO> ordersDTOList = new ArrayList<>();
        List<Orders> ordersList = ordersRepository.findByStoreIdAndDay(storeId, startOfDay, endOfDay);


        for(Orders orders : ordersList){
            HashMap<String, Object> returnHash = new HashMap<>();
            OrdersDTO ordersDTO = orders.entityToDto();
            List<OrdersDetailDTO> ordersDetailDTO = new ArrayList<>();
            List<OrdersDetail> ordersDetailList = ordersDetailRepository.findAllByOrdersId(ordersDTO.getId());

            for(OrdersDetail ordersDetail : ordersDetailList){
                ordersDetailDTO.add(ordersDetail.entityToDto());
            }
            OrdersStatus orderState = ordersStatusRepository.findStateByOrdersId(ordersDTO.getId());
            returnHash.put("orders",ordersDTO);
            returnHash.put("ordersDetailList",ordersDetailDTO);
            returnHash.put("ordersState",orderState.getState().getKorName());
            returnHashList.add(returnHash);
        }
        return returnHashList;
    }

    @Override
    public List<Map<String, Object>> findYearlySumsByStoreId(int intStoreId) {
        return ordersRepository.findYearlySumsByStoreId(intStoreId);
    }
    @Override
    public List<Map<String, Object>> findMonthlySumsByStoreId(int intStoreId) {
        return ordersRepository.findMonthlySumsByStoreId(intStoreId);
    }
    @Override
    public List<Map<String, Object>> findDailySumsByStoreId(int intStoreId) {
        return ordersRepository.findDailySumsByStoreId(intStoreId);
    }

    @Override
    public List<OrdersDetailDTO> findAllByStoreId(int intStoreId) {
        List<OrdersDetail> returnEntityList = ordersDetailRepository.findAllByStoreId(intStoreId);
        List<OrdersDetailDTO> returnDTOList = new ArrayList<>();
        for (OrdersDetail returnEntity : returnEntityList){
            returnEntity.setStore(storeRepository.findById(returnEntity.getStore().getId()).get());
            returnEntity.setOrders(ordersRepository.findById(returnEntity.getOrders().getId()).get());

            returnDTOList.add(returnEntity.entityToDto());
        }
        System.out.println(returnDTOList);
        return returnDTOList;
    }

    @Override
    public List<Map<String, Object>> findMenuInfoList(int intStoreId) {
        //        for(Map<String, Object> t:test){
//            System.out.println(t.get("category"));
//            System.out.println(t.get("price"));
//            System.out.println(t.get("name"));
//            System.out.println(t.get("cookingTime"));
//            System.out.println(t.get("orderCount"));
//            System.out.println(t.get("avgRate"));
//        }


        return menuRepository.findMenuStatsByStoreId(intStoreId);
    }

    private String formatToOneDecimalPlace(Double value) {
        if (value == null) {
            return "0.0"; // null 값을 0.0으로 처리
        }
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(value);
    }
}
