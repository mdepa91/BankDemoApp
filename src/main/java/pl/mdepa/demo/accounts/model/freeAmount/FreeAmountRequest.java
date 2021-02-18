package pl.mdepa.demo.accounts.model.freeAmount;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FreeAmountRequest {
  private String jsonrpc;
  private String method;
  private FreeAmountRequestParams params;
  private Long id;

  @JsonProperty("method")
  public String getMethod() {
    return Optional.ofNullable(params)
        .map(FreeAmountRequestParams::getMethodName)
        .orElse(null);
  }
}
