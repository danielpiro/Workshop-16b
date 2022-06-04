import Menu from "../components/menu";
import Link from "next/link";
import { useRouter } from "next/router";

const AdminActions = () => {

  return (
    <>
      <Menu /> {/*Should be Admin Only!!!*/}
      <div className="text-center my-5">
        <h1>Admin Actions</h1>
      </div>
      <div className="container d-flex justify-content-center">
        <div className="row w-50">
          <div className="card mb-3">
            <div className="card-body">
              <Link href="/admin-view-user-puchase-history">
                <a>See all purchase history of specific user</a>
              </Link>
            </div>
          </div>
          <br />
          <div className="card mb-3">
            <div className="card-body">
              <Link href="/admin-view-store-puchase-history">
                <a>See all purchase history of specific store</a>
              </Link>
            </div>
          </div>
          <br />
          <div className="card  mb-3">
            <div className="card-body">
              <Link href="/unregister-user">
                <a>Unregister user</a>
              </Link>
            </div>
          </div>
          <br />
          <div className="card  mb-3">
            <div className="card-body">
              <Link href="/assign-new-admin">
                <a>Assign user to admin in the system</a>
              </Link>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default AdminActions;
