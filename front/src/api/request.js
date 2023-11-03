import axios from 'axios'
import { getToken } from '@/utils/token'

const request = axios.create({
    baseURL: 'http://127.0.0.1:9000/',
    timeout: 2000,
});

//请求拦截
request.interceptors.request.use((config)=>{
    const token=getToken();
    if(token){
        config.headers.Authorization=token;
    }
    return config;
});

//响应拦截
request.interceptors.response.use(
    (res)=>{
        if(!res || res.status != 200){
            console.log("---err---")
            console.log(res);
        }
        return res;
    },
    (err)=>{
        console.log(err);
    }
)

export default request;
