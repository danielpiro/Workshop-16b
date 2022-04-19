package DomainLayer.ExternalService;

import DomainLayer.DealDetails;
import DomainLayer.ExternalService.PassiveObjects.ExternalFinanceService;
import DomainLayer.ExternalService.ExternalService;
import DomainLayer.ExternalService.PassiveObjects.ExternalFinanceService;

public class ProductFinanceService implements ExternalService {

    private ExternalFinanceService FinanceService;

    public ProductFinanceService(ExternalFinanceService FinanceService){
        this.FinanceService = FinanceService;
    }

    @Override
    public boolean connect() {
        try{
            this.FinanceService.connect();
            return true;
        }catch(Exception e){
            return false;
        }
    }


    public boolean tryToBuy(DealDetails details){
        return FinanceService.purchase(
                details.getBuyer_name(),
                details.getCreditCardNumber(),
                details.getExpireDate(),
                details.getCvv()
        );
    }
}
