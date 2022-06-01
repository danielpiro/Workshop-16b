import Menu from "../components/menu";
import SearchBar from "../components/search-bar";
import { useState, useEffect } from "react";
import Card from "../components/card";
import api from "../components/api";

const Dashboard = () => {
  const [products, setProducts] = useState([]);
  const [searchProducts, setSearchProducts] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [page, setPage] = useState(0);
  const [searchBy, setSearchBy] = useState("");

  useEffect(() => {
    setIsLoading(!isLoading);
    const fetchData = async () => {
      await api
        .get("/products/all")
        .then((res) => {
          const { data } = res;
          if (data.success) {
            setIsLoading(!isLoading);
            setProducts(data.value);
          }
        })
        .catch((err) => console.log(err));
    };
    fetchData();
  }, []);

  const onNext = (e) => {
    e.preventDefault();
    if (page + 1 > products.length / 12) return;
    setPage(page + 1);
  };

  const onPrevious = (e) => {
    e.preventDefault();
    if (page - 1 < 0) return;
    setPage(page - 1);
  };
  return (
    <>
      <Menu />
      <div className="my-4">
        <SearchBar
          setSearchProducts={setSearchProducts}
          setSearchBy={setSearchBy}
        />
      </div>
      <div className="my-4 d-flex justify-content-center">
        {!isLoading ? (
          <div style={{ display: "table", width: "100%" }}>
            <ul className="list-group-dashboard">
              {products
                .filter((product) => {
                  if (searchBy === "name") {
                    return product.name.toLowerCase().includes(searchProducts);
                  } else if (searchBy === "category") {
                    return product.category
                      .toLowerCase()
                      .includes(searchProducts);
                  } else {
                    return product.price.toLowerCase().includes(searchProducts);
                  }
                })
                .slice(12 * page, 12 * (page + 1))
                .map((product) => {
                  return (
                    <li className=" list-group-item" key={product.id}>
                      {console.log(product)}
                      <Card
                        value={product.id}
                        title={product.name}
                        price={product.price}
                        quantity={product.quantity}
                        storeId={product.storeId}
                        category={product.category}
                      />
                    </li>
                  );
                })}
            </ul>
            <nav aria-label="Search results pages">
              <ul className="pagination justify-content-center">
                <li className="page-item">
                  <button
                    className="page-link"
                    onClick={onPrevious}
                    disabled={page === 0 ? true : false}
                  >
                    Previous
                  </button>
                </li>
                <li className="page-item">
                  <button
                    className="page-link ms-3"
                    onClick={onNext}
                    disabled={page === products.length / 12 ? true : false}
                  >
                    Next
                  </button>
                </li>
              </ul>
            </nav>
          </div>
        ) : (
          <div className="container h-100 my-6">
            <div className="row align-items-center justify-content-center">
              <div className="spinner-border" />
            </div>
          </div>
        )}
      </div>
    </>
  );
};

export default Dashboard;
