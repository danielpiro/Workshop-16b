import api from "./api";

const logout = (id) => {
  api
    .post("/logout/", {
      id: window.userid,
    })
    .then((res) => {
      if (res.status === 200) {
        window.location.href = "/login";
      }
    })
    .catch((err) => console.error(err));
};

export default logout;
