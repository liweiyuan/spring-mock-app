import http from 'k6/http';
import { sleep } from 'k6';

export default function () {
    // 替换为实际的基准 URL 和 API
    const baseUrl = 'http://localhost:8080/api/products';

    http.get(baseUrl);  // 获取所有产品
    http.get(`${baseUrl}/1`);  // 获取 ID 为 1 的产品
    sleep(1);
}

export const options = {
    vus: 10,        // 10 个虚拟用户
    duration: '30s', // 持续时间 30s
};