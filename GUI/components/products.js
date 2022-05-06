const products = () => {
  return async () => {
    //need to replace with real api
    const response = await axios.get("https://fakestoreapi.com/products");
    const { data } = response;
    return data;
  };
};

export default products;
