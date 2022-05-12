import api from "./api";

const logout = (e, id) => {
  e.preventDefault();
  window.location.href = "/login";
  return;
  api
    .post("/logout/", {
      id: id,
    })
    .then((res) => {
      if (res.status === 200) {
        router.push("/login");
        //
      }
    })
    .catch((err) => console.error(err));
};

export default logout;
