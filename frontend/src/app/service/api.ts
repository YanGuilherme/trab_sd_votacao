import axios from 'axios';

export const apiCore = axios.create({
  baseURL: 'http://192.168.3.4:8081',
});

export const apiBase = axios.create({
  baseURL: 'http://192.168.3.4:8080', // por enquanto, fazer o front trocar
});
