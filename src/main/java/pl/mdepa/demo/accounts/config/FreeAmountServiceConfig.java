package pl.mdepa.demo.accounts.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("app.free-amount-service")
public class FreeAmountServiceConfig {
  String url;
  String apiKey;
  String jsonRpcVersion;
  int defaultDecimalPlaces;
}
