package pl.mdepa.demo.accounts.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

@Service
public class RandomAmountService implements FreeAmountProvider {

  @Override
  public BigDecimal getFreeAmount() {
    return BigDecimal.valueOf(RandomUtils.nextDouble()).setScale(2, RoundingMode.HALF_UP);
  }
}
