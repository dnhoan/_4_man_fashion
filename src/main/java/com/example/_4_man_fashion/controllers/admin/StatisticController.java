package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.Service.StatisticService;
import com.example._4_man_fashion.Service.StatisticServiceImpl;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.ResponseDTO;
import com.example._4_man_fashion.dto.StatisticFavorite;
import com.example._4_man_fashion.dto.StatisticIncome;
import com.example._4_man_fashion.dto.StatisticRevenue;
import com.example._4_man_fashion.entities.Customer;
import com.example._4_man_fashion.utils.ApiResponse;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
@CrossOrigin("*")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;


    @GetMapping("statisticIncome/date")
    public ResponseEntity<ApiResponse<List<StatisticIncome>>> getStatisticByDate(
            @RequestParam("time1") String s_date,
            @RequestParam("time2") String e_date
    ) {
        try {
            Date sDate = new SimpleDateFormat("dd/MM/yyyy").parse(s_date);
            Date eDate = new SimpleDateFormat("dd/MM/yyyy").parse(e_date);

            List<StatisticIncome> statisticIncomes = this.statisticService
                    .getStatisticByDate(sDate,eDate);
            return ResponseEntity.ok(ApiResponse.success(statisticIncomes));
        } catch (Exception e) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }


    }

    @GetMapping("statisticFavoriteProduct")
    public ResponseEntity<ApiResponse<List<StatisticFavorite>>>statisticFavoriteProducts(
            @RequestParam("time1") String time1,
            @RequestParam("time2") String time2
    ) {
        try {
            Date time_1 = new SimpleDateFormat("dd/MM/yyyy").parse(time1);
            Date time_2 = new SimpleDateFormat("dd/MM/yyyy").parse(time2);
            List<StatisticFavorite> statisticFavorites = this.statisticService.statisticsByBestFavoriteProducts(time_1, time_2);
            return ResponseEntity.ok(ApiResponse.success(statisticFavorites));

        } catch (Exception e) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }
    }

    @GetMapping("statisticIncome/year/{year}")
    public ResponseEntity<ApiResponse<List<StatisticIncome>>> getStatisticIncomeByYear(
            @PathVariable("year") Integer year
    ) {
        List<StatisticIncome> statisticIncomes = this.statisticService.getStatisticIncomeByYear(year);
        return ResponseEntity.ok(ApiResponse.success(statisticIncomes));
    }

    @GetMapping("statisticOrderStatus")
    public ResponseEntity<ApiResponse<List<Integer>>> statisticOrderStatus(
            @RequestParam("time1") String s_date,
            @RequestParam("time2") String e_date
    ) {
        try {
            Date sDate = new SimpleDateFormat("dd/MM/yyyy").parse(s_date);
            Date eDate = new SimpleDateFormat("dd/MM/yyyy").parse(e_date);
            List<Integer> statisticOrderStatus = this.statisticService.statisticOrderStatus(sDate, eDate);
            return ResponseEntity.ok(ApiResponse.success(statisticOrderStatus));
        } catch (Exception e) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }
    }

    @GetMapping("statisticBestSellingProduct")
    public ResponseEntity<ApiResponse<List<StatisticFavorite>>>statisticBestSellingProducts(
            @RequestParam("time1") String time1,
            @RequestParam("time2") String time2
    ) {
        try {
            Date time_1 = new SimpleDateFormat("dd/MM/yyyy").parse(time1);
            Date time_2 = new SimpleDateFormat("dd/MM/yyyy").parse(time2);
            List<StatisticFavorite> statisticFavorites = this.statisticService.statisticsByBestSellingProducts(time_1, time_2);
            return ResponseEntity.ok(ApiResponse.success(statisticFavorites));

        } catch (Exception e) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }
    }

    @GetMapping("getOrderTotalIsCheckout")
    public ResponseEntity<ApiResponse<Integer>>getOrderTotalIsCheckout(
            @RequestParam("time1") String time1,
            @RequestParam("time2") String time2
    ) {
        try {
            Date time_1 = new SimpleDateFormat("dd/MM/yyyy").parse(time1);
            Date time_2 = new SimpleDateFormat("dd/MM/yyyy").parse(time2);
            int amount = this.statisticService.getOrderTotalIsCheckout(time_1, time_2);
            return ResponseEntity.ok(ApiResponse.success(amount));
        } catch (Exception e) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }
    }

    @GetMapping("getOrderTotal")
    public ResponseEntity<ApiResponse<Integer>>getOrderTotal(
            @RequestParam("time1") String time1,
            @RequestParam("time2") String time2
    ) {
        try {
            Date time_1 = new SimpleDateFormat("dd/MM/yyyy").parse(time1);
            Date time_2 = new SimpleDateFormat("dd/MM/yyyy").parse(time2);
            int amount = this.statisticService.getOrderTotal(time_1, time_2);
            return ResponseEntity.ok(ApiResponse.success(amount));
        } catch (Exception e) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }
    }

    @GetMapping("getRevenue")
    public ResponseEntity<ApiResponse<StatisticRevenue>>getRevenue(
            @RequestParam("time1") String time1,
            @RequestParam("time2") String time2
    ) {
        try {
            Date time_1 = new SimpleDateFormat("dd/MM/yyyy").parse(time1);
            Date time_2 = new SimpleDateFormat("dd/MM/yyyy").parse(time2);
            StatisticRevenue revenue = this.statisticService.getRevenueTotal(time_1, time_2);
            return ResponseEntity.ok(ApiResponse.success(revenue));
        } catch (Exception e) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }
    }

    @GetMapping("getRevenueTotal")
    public ResponseEntity<ApiResponse<StatisticRevenue>>getRevenueTotal(
            @RequestParam("time1") String time1,
            @RequestParam("time2") String time2
    ) {
        try {
            Date time_1 = new SimpleDateFormat("dd/MM/yyyy").parse(time1);
            Date time_2 = new SimpleDateFormat("dd/MM/yyyy").parse(time2);
            StatisticRevenue revenue = this.statisticService.getRevenueTotal(time_1, time_2);
            return ResponseEntity.ok(ApiResponse.success(revenue));
        } catch (Exception e) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }
    }
}
