import axios from "axios";

const baseURL = axios.create({ baseURL: "http://211.104.147.150:8080" });

baseURL.defaults.headers.common["Authorization"] = "FIREBASE AUTH TOKEN";

export default baseURL;
