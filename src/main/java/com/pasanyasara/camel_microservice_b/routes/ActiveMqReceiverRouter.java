package com.pasanyasara.camel_microservice_b.routes;

import com.pasanyasara.camel_microservice_b.CurrencyExchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ActiveMqReceiverRouter extends RouteBuilder {

    @Autowired
    private MyCurrencyExchangeProcessor myCurrencyExchangeProcessor;

    @Override
    public void configure() throws Exception {
        from("activemq:my-activemq-queue")
                .to("log:received-message-from-active-mq")
                .unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
                .bean(myCurrencyExchangeProcessor);
    }
}

@Component
class MyCurrencyExchangeProcessor {
    private static final Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeProcessor.class);

    public void processMessage(CurrencyExchange currencyExchange) {
        logger.info("Received currency exchange: {}", currencyExchange);
        logger.info("Processing conversionMultiple: {}", currencyExchange.getConversionMultiple());
    }
}

