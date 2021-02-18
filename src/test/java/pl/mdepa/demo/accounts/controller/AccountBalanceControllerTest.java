package pl.mdepa.demo.accounts.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import pl.mdepa.demo.accounts.entity.BankAccount;
import pl.mdepa.demo.accounts.model.freeAmount.FreeAmountRequest;
import pl.mdepa.demo.accounts.model.freeAmount.FreeAmountResponse;
import pl.mdepa.demo.accounts.model.freeAmount.FreeAmountResponseRandomResult;
import pl.mdepa.demo.accounts.model.freeAmount.FreeAmountResponseResult;
import pl.mdepa.demo.accounts.repository.BankAccountRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountBalanceControllerTest {
  private static ObjectWriter objectWriter;

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private BankAccountRepository bankAccountRepository;
  @MockBean
  private RestTemplate restTemplate;

  //  @Autowired
  //  private AccountBalanceController controller;

  @BeforeAll
  public static void beforeClass() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    objectWriter = mapper.writer().withDefaultPrettyPrinter();
  }

  @BeforeEach
  public void beforeMethod() {
    bankAccountRepository.deleteAll();
  }

  @Test
  void testPositiveValues() throws Exception {
    // given
    prepareAccount(1.22);
    FreeAmountResponse response = getResponseWithValues(0.20);
    when(restTemplate.postForEntity(anyString(), any(FreeAmountRequest.class), eq(FreeAmountResponse.class)))
        .thenReturn(ResponseEntity.ok(response));

    // when-then
    mockMvc.perform(get("/account/balance/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.balance").value(1.22))
        .andExpect(jsonPath("$.freeAmount").value(0.20))
        .andExpect(jsonPath("$.availableBalance").value(1.42));
  }

  @Test
  void testZeroValues() throws Exception {
    // given
    prepareAccount(0.0);
    FreeAmountResponse response = getResponseWithValues(0.0);
    when(restTemplate.postForEntity(anyString(), any(FreeAmountRequest.class), eq(FreeAmountResponse.class)))
        .thenReturn(ResponseEntity.ok(response));

    // when-then
    mockMvc.perform(get("/account/balance/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.balance").value(0))
        .andExpect(jsonPath("$.freeAmount").value(0))
        .andExpect(jsonPath("$.availableBalance").value(0));
  }

  @Test
  void testNegativeValues() throws Exception {
    // given
    prepareAccount(1.0);
    FreeAmountResponse response = getResponseWithValues(-2.0);
    when(restTemplate.postForEntity(anyString(), any(FreeAmountRequest.class), eq(FreeAmountResponse.class)))
        .thenReturn(ResponseEntity.ok(response));

    // when-then
    mockMvc.perform(get("/account/balance/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.balance").value(1.0))
        .andExpect(jsonPath("$.freeAmount").value(-2))
        .andExpect(jsonPath("$.availableBalance").value(0));
  }

  @SuppressWarnings("unchecked")
  @Test
  void testAllAccountBalances() throws Exception {
    // given
    prepareAccount(22.22);
    prepareAccount(11.11);
    FreeAmountResponse response1 = getResponseWithValues(1.0);
    FreeAmountResponse response2 = getResponseWithValues(2.0);
    when(restTemplate.postForEntity(anyString(), any(FreeAmountRequest.class), eq(FreeAmountResponse.class)))
        .thenReturn(ResponseEntity.ok(response1), ResponseEntity.ok(response2));

    // when-then
    mockMvc.perform(get("/account/balance").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.balance").value(33.33))
        .andExpect(jsonPath("$.freeAmount").value(3.0))
        .andExpect(jsonPath("$.availableBalance").value(36.33));
  }

  private FreeAmountResponse getResponseWithValues(Double... freeAmount) {
    List<BigDecimal> values = Arrays.stream(freeAmount)
        .map(BigDecimal::valueOf)
        .collect(Collectors.toList());
    FreeAmountResponseRandomResult randomResult = FreeAmountResponseRandomResult.builder()
        .data(values)
        .build();
    return FreeAmountResponse.builder()
        .result(new FreeAmountResponseResult(randomResult))
        .build();
  }

  private void prepareAccount(double balance) {
    bankAccountRepository.save(BankAccount.builder()
        .balance(BigDecimal.valueOf(balance))
        .build());
  }
}
