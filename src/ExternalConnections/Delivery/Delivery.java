package ExternalConnections.Delivery;

public abstract class Delivery {
        protected boolean connected;
        private String name;
        private int identifier;
        private boolean taken;

        public Delivery(boolean connected, String name, int identifier, boolean taken) {
            this.connected = connected;
            this.name = name;
            this.identifier = identifier;
            this.taken = taken;
        }

        //-1 already taken,
        public synchronized int delivery (float total){

            if(taken == true)
                return -1;

            int ans = internalDelivery(total);
            taken=false;
            return ans;

        }

        protected abstract int internalDelivery(float total);

        // use secure key to connect
        public abstract boolean connect (int key);


        public boolean isConnected(){
            return connected;
        }

        public String getName(){
            return name;
        }

        public boolean isTaken(){
            return taken;
        }
}
