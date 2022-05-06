import { useEffect, useState } from "react";

const Products = (props) => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  useEffect(() => {
    const fetchApi = async () => {
      const response = await axios.get("https://fakestoreapi.com/products");
      setIsLoading(!isLoading);
      const { data } = response;
      setProducts(data);
    };
    fetchApi();
  }, []);

  const filter = (pred) => products.filter((item) => pred(item));

  return <div>{products}</div>;
};

export default Products;
