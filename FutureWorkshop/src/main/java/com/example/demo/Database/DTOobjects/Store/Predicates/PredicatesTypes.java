package com.example.demo.Database.DTOobjects.Store.Predicates;

import com.example.demo.Store.StorePurchase.predicates.PredImplementions.AlwaysTrue;

public enum PredicatesTypes {
    AlwaysTrue,
    CartPredicate,
    CategoryPredicate,
    pricePredicate,
    ProductPredicate,
    TimePredicate,
    UserIdPredicate,
    UserAgePredicate,

    DiscountPredicateAnd,
    DiscountPredicateOr,
    DiscountPredicateXor,

    PolicyPredicateAnd,
    PolicyPredicateOr,
    PolicyPredicateXor

    }
