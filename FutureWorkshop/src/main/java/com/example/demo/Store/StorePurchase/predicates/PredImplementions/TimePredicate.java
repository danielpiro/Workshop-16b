package main.java.com.example.demo.Store.StorePurchase.predicates.PredImplementions;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import main.java.com.example.demo.Store.StorePurchase.PurchasableProduct;
import main.java.com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;
import main.java.com.example.demo.Store.StorePurchase.predicates.PredicateTimeType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class TimePredicate implements PolicyPredicate {
    private LocalDateTime StartTime;
    private LocalDateTime endTime;

    private PredicateTimeType type;

    public TimePredicate(LocalDateTime startTime, LocalDateTime endTime, PredicateTimeType type) {
        StartTime = startTime;
        this.endTime = endTime;
        this.type = type;
    }

    @Override
    public boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        switch (type){
            case OnHoursOfTheDay:
                return OnHoursOfTheDay();
            case OnDaysOfTheWeek:
                return DaysOfTheWeek();
            case OnDayOfMonth:
                return OnDayOfMonth();
        }
        throw new RuntimeException("PredicateTimeType doesn't exist ");
    }

    public boolean OnDayOfMonth(){
        int dayOfMonth = LocalDateTime.now().getDayOfMonth();
        return StartTime.getDayOfMonth() <= dayOfMonth && dayOfMonth <= endTime.getDayOfMonth();
    }
    public boolean OnHoursOfTheDay() {
        LocalTime now = LocalTime.now();
        return now.isAfter(StartTime.toLocalTime())  &&  now.isBefore(endTime.toLocalTime()) ;
    }
    public boolean DaysOfTheWeek() {
        int today = LocalDateTime.now().getDayOfWeek().getValue();
        return today >= StartTime.getDayOfWeek().getValue() &&  today <= endTime.getDayOfWeek().getValue() ;
    }


}
