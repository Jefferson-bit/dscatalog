import axios, { Method} from 'axios';

type  RequestParam = {
    method?: Method
    url: string
    data?: object
    params?: object
}

const baseUrl = "http://localhost:3000";

export const makeRequest = ({ method = 'GET', url, data, params}: RequestParam) => {
    return axios({
        method,
        url: `${baseUrl}${url}`,
        data,
        params
    });
}