import axios, { Method} from 'axios';
import qs from 'qs';
import { CLIENT_ID, CLIENT_SECRET } from './auth';

type  RequestParam = {
    method?: Method
    url: string
    data?: object | string
    params?: object
    headers?: Object
}

type LoginData = {
    username: string
    password: string
}

const baseUrl = "http://localhost:8080";

export const makeRequest = ({ method = 'GET', url, data, params, headers}: RequestParam) => {
    return axios({
        method,
        url: `${baseUrl}${url}`,
        data,
        params,
        headers
    });
}

export const makeLogin = ( loginData: LoginData ) =>{
    const token = `${CLIENT_ID}:${CLIENT_SECRET}`;
    
    const headers = {
        Authorization: `Basic ${window.btoa(token)} `,
        'Content-Type': 'application/x-www-form-urlencoded'
    }
    const payload = qs.stringify({ ...loginData, grant_type: 'password'})
    return makeRequest( { url: '/oauth/token', data: payload, method: 'POST', headers });
}