package pl.mdepa.demo.accounts.model.freeAmount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreeAmountResponse {
  private Long id;
  private FreeAmountResponseResult result;
  private FreeAmountResponseError error;
}
