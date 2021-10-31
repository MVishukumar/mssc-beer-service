package guru.springframework.msscbeerservice.service.order;

import guru.sfg.brewery.model.BeerOrderDto;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidator {

  private final BeerRepository beerRepository;

  public Boolean validateOrder(BeerOrderDto beerOrderDto) {
    AtomicInteger beerNotFound = new AtomicInteger();

    beerOrderDto.getBeerOrderLines()
        .forEach(orderLine -> {
          if(beerRepository.findByUpc(orderLine.getUpc()) == null) {
            beerNotFound.incrementAndGet();
          }
        });

    return beerNotFound.get() == 0;
  }
}
