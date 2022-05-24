import Menu from "../components/menu";
import { useState, useEffect } from "react";
import api from "../components/api";
import StoreCard from "../components/store-card";

const AddNewStoreSupply = () => {
  const [store, setStore] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [searchValue, setSearchValue] = useState("");
  const [newProduct, setNewProduct] = useState({
      productName: "",
      productType: "",
      productQunatity: "",
      productPrice: "",
  })
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!

  const onSearch = (e) => {
    e.preventDefault();
    setIsLoading(!isLoading);
    api.get("/search/store", { params: { name: searchValue } }).then((res) => {
      if (res.status === 200) {
        setStore([res.data]);
        setIsLoading(!isLoading);
      }
    });
  };

  // const onSelectStore = (e) => { //TODO: define the store we are going to add product to..
  //   e.preventDefault();
  //   setStore(e.target.value);
  // }

  const onAddProduct = async (e) => {
    e.preventDefault();
    //console.log(newProduct); ...........
    if (store !== "" && newProduct.productName !== "" && newProduct.productType !== ""
        && newProduct.productQunatity !== "" && newProduct.productPrice !== "") {
      
      const fakeProduct = {
        name: newProduct.productName,
        type: newProduct.productType,
        quantity: newProduct.productQunatity,
        price: newProduct.productPrice,
      };
      await api
        .post(
          `/store/?` , fakeProduct
        )
        .then((res) => {
          if (res.status === 200) {
            const { data } = res;
            console.log(data);
            createNotification("success", "Added product successfully", () =>
              router.push("/dashboard")
            )();
          } else {
            const { data } = res;
            console.log(data);
            createNotification("error", "failure to add product!")();
          }
        })
        .catch((err) => console.log("err"));
    } else {
      createNotification(
        "error",
        "at least one of the inputs was not valid, please try again"
      )();
    }
  }

  return (
    <>
      <Menu/>
      <div className="container m-auto w-100">
        <span className="text-center my-4">
          <h3>Add new store's supply</h3>
        </span>
        <nav className="navbar navbar-expand-lg bg-secondery d-flex justify-content-center">
          <form className="row form-inline col-4">
            <input
              className="form-control mr-sm-2 m-2"
              type="search"
              placeholder="Enter store name"
              aria-label="Search"
              onChange={(e) =>
                setSearchValue((prevState) => ({
                    ...prevState,
                    searchValue: e.target.value,
                }))
            }
            />

            <div className="d-flex justify-content-center">
              <button className="btn btn-primary my-3" onClick={onSearch}>
                Search
              </button>
            </div>
          </form>
        </nav>
        <ul>
          <StoreCard />
          {!isLoading ? (
            store.map((shop) => {
              return (
                <div>
                  <li key={shop.id}>{shop}</li>
                </div>
              );
            })
          ) : (
            <div className="container">
              <div className="d-flex justify-content-center">
                <div className="spinner-border my-5 me-4" />
              </div>
            </div>
          )}
        </ul>

        <div className="container">
            <div className="row d-flex justify-content-center m-3" style={{width: "50%"}}>
                <div className="input-group mb-3">
                    <span className="input-group-text" id="basic-addon1">New Product Name</span>
                    <input 
                        type="text" className="form-control" placeholder="Enter New Product Name" 
                        aria-label="ProductName" aria-describedby="basic-addon1" 
                        value={newProduct.productName}
                        onChange={(e) =>
                            setNewProduct((prevState) => ({
                                ...prevState,
                                productName: e.target.value,
                            }))
                        }
                    />
                </div>
                <div className="input-group mb-3">
                    <span className="input-group-text" id="basic-addon1">New Product Type</span>
                    <input type="text" className="form-control" placeholder="Enter New Product Type" 
                        aria-label="ProductType" aria-describedby="basic-addon1" 
                        value={newProduct.productType}
                        onChange={(e) =>
                            setNewProduct((prevState) => ({
                                ...prevState,
                                productType: e.target.value,
                            }))
                        }
                    />
                </div>
                <div className="input-group mb-3">
                    <span className="input-group-text" id="basic-addon1">New Product Quantity</span>
                    <input type="text" className="form-control" placeholder="Enter New Product Quantity" 
                        aria-label="ProductQuantity" aria-describedby="basic-addon1" 
                        value={newProduct.productQunatity}
                        onChange={(e) =>
                            setNewProduct((prevState) => ({
                                ...prevState,
                                productQunatity: e.target.value,
                            }))
                        }
                    />
                </div>
                <div className="input-group mb-3">
                    <span className="input-group-text" id="basic-addon1">New Product Price</span>
                    <input 
                        type="text" className="form-control" placeholder="Enter New Product Price" 
                        aria-label="ProductPrice" aria-describedby="basic-addon1" 
                        value={newProduct.productPrice}
                        onChange={(e) =>
                            setNewProduct((prevState) => ({
                                ...prevState,
                                productPrice: e.target.value,
                            }))
                        }/>
                </div> 
                <button className="btn btn-primary mr-lg-3" style={{ width: "50%" }} onClick={onAddProduct}>
                    Add New Product To Store
                </button> 
            </div>
        </div>
      </div>
    </>
  );
};

export default AddNewStoreSupply;
