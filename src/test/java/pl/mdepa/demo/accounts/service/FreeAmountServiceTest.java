package pl.mdepa.demo.accounts.service;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.web.client.RestTemplate;

import pl.mdepa.demo.accounts.config.FreeAmountServiceConfig;
import pl.mdepa.demo.accounts.model.freeAmount.FreeAmountRequest;
import pl.mdepa.demo.accounts.model.freeAmount.FreeAmountResponse;
import pl.mdepa.demo.accounts.model.freeAmount.FreeAmountResponseRandomResult;
import pl.mdepa.demo.accounts.model.freeAmount.FreeAmountResponseResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FreeAmountServiceTest {
  @InjectMocks
  private FreeAmountService freeAmountService;
  @Mock
  private FreeAmountServiceConfig config;
  @Mock
  private RestTemplate restTemplate;

  @BeforeTestClass
  void beforeClass() {
    MockitoAnnotations.openMocks(this);
    reset(config);
  }

  @BeforeEach
  void setUp() {
    when(config.getUrl())
        .thenReturn("http://localhost");
  }

  @Test
  void getFreeAmount() {
    // given
    FreeAmountResponse response = buildResponse(22.111);
    when(restTemplate.postForEntity(anyString(), any(FreeAmountRequest.class), eq(FreeAmountResponse.class)))
        .thenReturn(ResponseEntity.ok(response));
    // when
    BigDecimal freeAmount = freeAmountService.getFreeAmount();

    //then
    assertThat(freeAmount).isEqualByComparingTo("22.111");
  }

  private FreeAmountResponse buildResponse(double amount) {
    FreeAmountResponseRandomResult randomResult = FreeAmountResponseRandomResult.builder()
        .data(List.of(BigDecimal.valueOf(amount)))
        .build();
    FreeAmountResponseResult result = new FreeAmountResponseResult(randomResult);
    FreeAmountResponse response = FreeAmountResponse.builder()
        .result(result)
        .build();
    return response;
  }
}
