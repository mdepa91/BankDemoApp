package pl.mdepa.demo.accounts.model.freeAmount;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class FreeAmountRequestParams {
  private String apiKey;

  @JsonProperty("n")
  private int numberOfData;

  @JsonIgnore
  public abstract String getMethodName();
}
