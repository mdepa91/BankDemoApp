package pl.mdepa.demo.accounts.model.freeAmount;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FreeAmountRequestIntegerParams extends FreeAmountRequestParams {
  public static final String METHOD_NAME = "generateIntegers";
  @JsonProperty("min")
  private int lowestValue;
  @JsonProperty("max")
  private int highestValue;

  @Builder
  public FreeAmountRequestIntegerParams(String apiKey, int numberOfData, int lowestValue, int highestValue) {
    super(apiKey, numberOfData);
    this.lowestValue = lowestValue;
    this.highestValue = highestValue;
  }

  @Override
  public String getMethodName() {
    return METHOD_NAME;
  }
}
