package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.configs.security.UserDetailsImpl;
import com.example._4_man_fashion.dto.*;
import com.example._4_man_fashion.models.JwtResponse;
import com.example._4_man_fashion.repositories.FavoriteProductRepository;
import com.example._4_man_fashion.repositories.OrderRepository;
import com.example._4_man_fashion.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MailService mailService;


    public List<StatisticFavorite> statisticsByBestFavoriteProducts(Date time1, Date time2){
        return this.orderRepository.statisticsByBestFavoriteProducts(time1, time2);


    }

    @Override
    public List<StatisticFavorite> statisticsByBestFavoriteProductsOnline() {
        return this.orderRepository.statisticsByBestFavoriteProductsOnline();
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

    public int getOrderTotal(Date time1, Date time2){
        return this.orderRepository.getOrdersTotal(time1, time2);
    }

    public int getOrderTotalIsCheckout(Date time1, Date time2){
        return this.orderRepository.getOrderTotalIsCheckout(time1, time2);
    }

    public List<StatisticRevenue> getRevenue(Date time1, Date time2){
        return this.orderRepository.getRevenue(time1, time2);
    }

    @Override
    public StatisticRevenue getRevenueTotal(Date time1, Date time2)  {
        return this.orderRepository.getTotalRevenue(time1, time2);
    }

    // Gửi mail
    public StatisticRevenue getRevenueAndQuantityOrder(Date time1, Date time2) {

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (principal instanceof UserDetailsImpl) {
//            String username = ((UserDetailsImpl)principal).getEmail();
//            System.out.println("haha" + username);
//        } else {
//            String username = principal.toString();
//            System.out.println("1111" + username);
//        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println("hhhh" + userDetails.getEmail());


        Optional<StatisticRevenue> statisticRevenue = this.orderRepository.getRevenueAndQuantityOrder(time1, time2);


        StatisticRevenue statisticRevenue1 = statisticRevenue.get();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date1= formatter.format(time1);
        String date2= formatter.format(time2);
        Long dt = statisticRevenue1.getDt_store() + statisticRevenue1.getDt_online();
        Integer sl = statisticRevenue1.getSl_online() + statisticRevenue1.getSl_store();

        try {
            DataMailDTO dataMail = new DataMailDTO();

            dataMail.setTo(userDetails.getEmail());
            dataMail.setSubject(Const.SEND_MAIL_STATISTIC_SUBJECT.SUBJECT);

            Map<String, Object> props = new HashMap<>();
            props.put("name", userDetails.getCustomerDto().getCustomerName());
            props.put("time1", date1);
            props.put("time2", date2);
            props.put("dt_store", statisticRevenue1.getDt_store());
            props.put("dt_online", statisticRevenue1.getDt_online());
            props.put("sl_store", statisticRevenue1.getSl_store());
            props.put("sl_online", statisticRevenue1.getSl_online());
            props.put("sl", sl);
            props.put("dt", dt);
            dataMail.setProps(props);
            mailService.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME_STATISTIC.FILE_NAME);

        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return statisticRevenue.get();
    }
}
