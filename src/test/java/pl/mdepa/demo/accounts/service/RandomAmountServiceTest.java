package pl.mdepa.demo.accounts.service;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class)
class RandomAmountServiceTest {
  @InjectMocks
  private RandomAmountService randomAmountService;

  @BeforeTestClass
  void beforeClass() {
    MockitoAnnotations.openMocks(this);
  }

  @RepeatedTest(value = 100)
  void getFreeAmount() {
    assertThat(randomAmountService.getFreeAmount()).isPositive();
  }
}
