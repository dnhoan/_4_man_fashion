package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.StatisticFavorite;
import com.example._4_man_fashion.dto.StatisticIncome;
import com.example._4_man_fashion.repositories.FavoriteProductRepository;
import com.example._4_man_fashion.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class StatisticServiceImpl {
    @Autowired
    private OrderRepository orderRepository;


    public List<StatisticFavorite> statisticsByBestFavoriteProducts(Date time1, Date time2){
        return this.orderRepository.statisticsByBestFavoriteProducts(time1, time2);


    }

    public List<StatisticIncome> getStatisticByDate(Date time1, Date time2) {
        return this.orderRepository.statisticByDate( time1, time2);
    }

    public List<StatisticIncome> getStatisticIncomeByYear(Integer year) {
        return this.orderRepository.getStatisticIncomeByYear(year);
    }

    public List<Integer> statisticOrderStatus(Date s_date, Date e_date) {
        return this.orderRepository.statisticOrderStatus(s_date, e_date);
    }

    public List<StatisticFavorite> statisticsByBestSellingProducts(Date time1, Date time2){
        return this.orderRepository.statisticsByBestSellingProducts(time1, time2);


    }
}
