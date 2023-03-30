package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.Service.StatisticServiceImpl;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.ResponseDTO;
import com.example._4_man_fashion.dto.StatisticFavorite;
import com.example._4_man_fashion.dto.StatisticIncome;
import com.example._4_man_fashion.entities.Customer;
import com.example._4_man_fashion.utils.ApiResponse;
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
    private StatisticServiceImpl statisticService;


    @GetMapping("statisticIncome/date")
    public ResponseEntity<ApiResponse<List<StatisticIncome>>> getStatisticByDate(
            @RequestParam("s_date") String s_date,
            @RequestParam("e_date") String e_date
    ) {
        try {
            Date sDate = new SimpleDateFormat("MM/dd/yyyy").parse(s_date);
            Date eDate = new SimpleDateFormat("MM/dd/yyyy").parse(e_date);

            List<StatisticIncome> statisticIncomes = this.statisticService
                    .getStatisticByDate(sDate,eDate);
            return ResponseEntity.ok(ApiResponse.success(statisticIncomes));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    @GetMapping("statisticFavoriteProduct")
    public ResponseEntity<ApiResponse<List<StatisticFavorite>>>statisticOrderStatus(
            @RequestParam("time1") String time1,
            @RequestParam("time2") String time2
    ) {
        try {
            Date time_1 = new SimpleDateFormat("dd/MM/yyyy").parse(time1);
            Date time_2 = new SimpleDateFormat("dd/MM/yyyy").parse(time2);
            List<StatisticFavorite> statisticFavorites = this.statisticService.statisticsByBestSellingProducts(time_1, time_2);
            return ResponseEntity.ok(ApiResponse.success(statisticFavorites));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("statisticIncome/year/{year}")
    public ResponseEntity<ApiResponse<List<StatisticIncome>>> getStatisticIncomeByYear(
            @PathVariable("year") Integer year
    ) {
        List<StatisticIncome> statisticIncomes = this.statisticService.getStatisticIncomeByYear(year);
        return ResponseEntity.ok(ApiResponse.success(statisticIncomes));

    }
}
