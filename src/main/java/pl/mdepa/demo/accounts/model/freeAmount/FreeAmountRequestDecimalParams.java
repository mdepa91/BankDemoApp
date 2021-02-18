package pl.mdepa.demo.accounts.model.freeAmount;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FreeAmountRequestDecimalParams extends FreeAmountRequestParams {
  private static final String METHOD_NAME = "generateDecimalFractions";
  @JsonProperty("decimalPlaces")
  private int decimalPlaces;

  @Builder
  public FreeAmountRequestDecimalParams(String apiKey, int numberOfData, int decimalPlaces) {
    super(apiKey, numberOfData);
    this.decimalPlaces = decimalPlaces;
  }

  @Override
  public String getMethodName() {
    return METHOD_NAME;
  }
}
