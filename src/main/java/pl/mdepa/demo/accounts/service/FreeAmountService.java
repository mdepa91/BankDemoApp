package pl.mdepa.demo.accounts.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import pl.mdepa.demo.accounts.config.FreeAmountServiceConfig;
import pl.mdepa.demo.accounts.model.freeAmount.FreeAmountRequest;
import pl.mdepa.demo.accounts.model.freeAmount.FreeAmountRequestDecimalParams;
import pl.mdepa.demo.accounts.model.freeAmount.FreeAmountRequestParams;
import pl.mdepa.demo.accounts.model.freeAmount.FreeAmountResponse;
import pl.mdepa.demo.accounts.model.freeAmount.FreeAmountResponseRandomResult;
import pl.mdepa.demo.accounts.model.freeAmount.FreeAmountResponseResult;

@Slf4j
@Service
public class FreeAmountService implements FreeAmountProvider {
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private FreeAmountServiceConfig freeAmountServiceConfig;

  @Override
  public BigDecimal getFreeAmount() {
    StopWatch stopWatch = StopWatch.createStarted();
    FreeAmountRequest request = getFreeAmountRequest();
    ResponseEntity<FreeAmountResponse> responseEntity = restTemplate.postForEntity(freeAmountServiceConfig.getUrl(),
        request, FreeAmountResponse.class);
    log.info("Fetch freeAmount took: {}ms", stopWatch.getTime());
    return Optional.ofNullable(responseEntity.getBody())
        .map(FreeAmountResponse::getResult)
        .map(FreeAmountResponseResult::getRandom)
        .map(FreeAmountResponseRandomResult::getData)
        .map(List::iterator)
        .map(IteratorUtils::first)
        .orElseThrow(() -> new RuntimeException("Cannot fetch exchange rate from response: " + responseEntity));
  }

  private FreeAmountRequest getFreeAmountRequest() {
    FreeAmountRequestParams requestParams = getFreeAmountParams();
    return FreeAmountRequest.builder()
        .id(RandomUtils.nextLong())
        .method(requestParams.getMethodName())
        .jsonrpc(freeAmountServiceConfig.getJsonRpcVersion())
        .params(requestParams)
        .build();
  }

  private FreeAmountRequestParams getFreeAmountParams() {
    return FreeAmountRequestDecimalParams.builder()
        .apiKey(freeAmountServiceConfig.getApiKey())
        .numberOfData(1)
        .decimalPlaces(freeAmountServiceConfig.getDefaultDecimalPlaces())
        .build();
  }
}
