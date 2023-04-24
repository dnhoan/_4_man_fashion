package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.StatisticFavorite;
import com.example._4_man_fashion.dto.StatisticIncome;
import com.example._4_man_fashion.dto.StatisticRevenue;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface StatisticService {

    List<StatisticFavorite> statisticsByBestFavoriteProducts(Date time1, Date time2);
    List<StatisticFavorite> statisticsByBestFavoriteProductsOnline();
    List<StatisticIncome> getStatisticByDate(Date time1, Date time2);
    List<StatisticIncome> getStatisticIncomeByYear(Integer year);
    List<Integer> statisticOrderStatus(Date s_date, Date e_date);
    List<StatisticFavorite> statisticsByBestSellingProducts(Date time1, Date time2);
    int getOrderTotal(Date time1, Date time2);
    int getOrderTotalIsCheckout(Date time1, Date time2);
    List<StatisticRevenue> getRevenue(Date time1, Date time2);
    StatisticRevenue getRevenueTotal(Date time1, Date time2);
}
