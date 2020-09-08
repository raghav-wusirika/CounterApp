package com.example.ticker;

import com.example.ticker.service.CounterCacheInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Counter")
public class CountTickerController {

    @Autowired
    private CounterCacheInterface counterCacheInterface;

    @GetMapping(path="/GetData", produces = "application/json")
    public @ResponseBody ResponseEntity<List<String>> getMostReqData(@RequestParam int count) {
        return new ResponseEntity<List<String>>(counterCacheInterface.getMostReqData(count), HttpStatus.OK);
    }

    @PostMapping(path = "/PushData", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<List<String>> pushData(@RequestBody HashMap<String, Integer> object) {
        counterCacheInterface.updateCounter(object, 600000);
        return new ResponseEntity<List<String>>(counterCacheInterface.getMostReqData(5), HttpStatus.OK);
    }
}
