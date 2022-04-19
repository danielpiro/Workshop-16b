package DomainLayer.ExternalService;

import DomainLayer.ExternalService.PassiveObjects.ExternalSupplyService;
import DomainLayer.ExternalService.ExternalService;
import DomainLayer.ExternalService.PassiveObjects.ExternalSupplyService;
import DomainLayer.OrderDetails;

public class ProductSupplyService implements ExternalService {

    private ExternalSupplyService SupplyService;

    public ProductSupplyService(ExternalSupplyService SupplyService){
        this.SupplyService = SupplyService;
    }

    @Override
    public boolean connect() {
        try{
            this.SupplyService.connect();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean order(OrderDetails orderDetails){
        return this.SupplyService.order(orderDetails);
    }
}
