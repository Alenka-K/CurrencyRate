package com.example.currencyrate.requester;


import java.time.LocalDate;

public interface NBURequester {

    String getStringFromXml(LocalDate date);
}
