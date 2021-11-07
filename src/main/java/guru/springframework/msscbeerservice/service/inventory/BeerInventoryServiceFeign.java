package guru.springframework.msscbeerservice.service.inventory;

import guru.springframework.msscbeerservice.service.inventory.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Profile("local-discovery")
@Service
public class BeerInventoryServiceFeign implements BeerInventoryService {

    private final InventoryServiceFeignClient inventoryServiceFeignClient;

    @Override
    public Integer getOnhandInventory(@PathVariable("beerId") UUID beerId) {
      log.debug("Calling Inventory Service, beerId: " + beerId);

      ResponseEntity<List<BeerInventoryDto>> responseEntity = inventoryServiceFeignClient.getOnhandInventory(beerId);

      Integer onHandInventory = Objects.requireNonNull(responseEntity.getBody())
              .stream()
              .mapToInt(BeerInventoryDto::getQuantityOnHand)
              .sum();

      log.debug("Returning onHandInventory: " + onHandInventory);

      return onHandInventory;
    }

}
