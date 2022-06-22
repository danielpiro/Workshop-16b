import Menu from "../components/menu";
import SearchBarStores from "../components/search-bar-stores";
import { useState, useEffect } from "react";
import StoreCardStores from "../components/store-card-stores";
import api from "../components/api";
import Link from "next/link";
import createNotification from "../components/norification";
import { useCookies } from "react-cookie";

const Stores = () => {
  const [stores, setStores] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [storeSearch, setStoreSearch] = useState("");
  const [storeMap, setStoreMap] = useState([]);
  const [page, setPage] = useState(0);
  const [allowToManageStores, setAllowToManageStores] = useState([]);
  const [owningStores, setOwningStores] = useState([]);

  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
    "session",
  ]);

  useEffect(() => {
    setIsLoading(!isLoading);
    const fetchData = async () => {
      return await api
        .get(
          `store/owner/permmitions?sessionID=${cookies.session}&userId=${cookies.userId}`
        )
        .then((res) => {
          const { data } = res;
          if (data.success) {
            data.value.map((store) => {
              allowToManageStores.push(store.storeId);
            });
          } else {
            createNotification("error", data.reason)();
          }
        })
        .then(async () => {
          return await api
            .get(
              `store/manager/permmitions?sessionID=${cookies.session}&userId=${cookies.userId}`
            )
            .then((res) => {
              const { data } = res;
              if (data.success) {
                data.value.map((store) => {
                  owningStores.push(store.storeId);
                });
              } else {
                createNotification("error", data.reason)();
              }
            });
        })
        .then(async () => {
          return await api
            .get(
              `/store/all/?sessionID=${cookies.session}&userId=${cookies.userId}`
            )
            .then((res) => {
              const { data } = res;
              if (data.success) {
                setStores(data.value);
                stores.map((store) => (
                  <Link
                    href={`stores/view/${store.storeId}`}
                    key={store.storeId}
                  />
                ));
              } else {
                createNotification("error", data.reason)();
              }
            })
            .then(async () => {
              return await api
                .get(
                  `/store-products/all/?sessionID=${cookies.session}&userId=${cookies.userId}`
                )
                .then((res) => {
                  const { data } = res;
                  if (data.success) {
                    setStoreMap(data.value);
                  } else {
                    createNotification("error", data.reason)();
                  }
                });
            })
            .catch((err) => console.log(err));
        });
    };
    fetchData();
  }, []);

  const onNext = (e) => {
    e.preventDefault();
    if (page + 1 > stores.length / 12) return;
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
        <SearchBarStores setStoreSearch={setStoreSearch} />
      </div>

      {!isLoading ? (
        <div className="my-4 d-flex justify-content-center">
          <div className="table-borderless w-75">
            <ul className="list-group-dashboard">
              {stores
                .filter((stores) => stores.storeId.includes(storeSearch))
                .slice(12 * page, 12 * (page + 1))
                .map((store) => {
                  return (
                    <li className="list-group-item" key={store.storeId}>
                      <StoreCardStores
                        store={store}
                        storeMap={storeMap}
                        allowToManageStores={allowToManageStores}
                        owningStores={owningStores}
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
                    disabled={page === stores.length / 12 ? true : false}
                  >
                    Next
                  </button>
                </li>
              </ul>
            </nav>
          </div>
        </div>
      ) : (
        <div className="container h-100 my-6">
          <div className="row align-items-center justify-content-center">
            <div className="spinner-border" />
          </div>
        </div>
      )}
    </>
  );
};

export default Stores;
