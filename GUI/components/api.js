import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost/api/v1",
});

export default api;
