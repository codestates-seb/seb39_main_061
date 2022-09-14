import axios from "axios";

const baseURL = axios.create({ baseURL: "http://localhost:8080" });

baseURL.defaults.headers.common["Authorization"] = "FIREBASE AUTH TOKEN";

export default baseURL;
