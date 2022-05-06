const shoppingCart = () => {
  const [shoppingCart, setShoppingCart] = useState([]);
  const cartsToProducts = new Map();
  const addNewCart = (cart) => {
    setShoppingCart([...setShoppingCart, cart]);
  };
  const getAllCarts = () => shoppingCart;
  const addProduct = (product) => {
    console.log(product);
  };
};

export default shoppingCart;
