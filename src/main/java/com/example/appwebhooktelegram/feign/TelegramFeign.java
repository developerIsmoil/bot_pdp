package com.example.appwebhooktelegram.feign;

import com.example.appwebhooktelegram.payload.ResultMessage;
import com.example.appwebhooktelegram.payload.SendPhoto;
import com.example.appwebhooktelegram.utils.RestConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.telegram.telegrambots.meta.api.methods.send.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@FeignClient(url = RestConstants.TELEGRAM_BASE_URL_WITHOUT_BOT, name = "TelegramFeign")
public interface TelegramFeign {

    @PostMapping("bot{path}/sendPhoto")
    ResultMessage sendPhoto(@PathVariable String path, @RequestBody SendPhoto sendPhoto);

    @PostMapping("bot{path}/sendMessage")
    ResultMessage sendMessage(@PathVariable String path, @RequestBody SendMessage sendMessage);

    @PostMapping("bot{path}/sendInvoice")
    ResultMessage sendInvoice(@PathVariable String path, @RequestBody SendInvoice sendMessage);

}
