import axios from 'axios';

export const apiCore = axios.create({
  baseURL: 'http://localhost:9090',
});

export const apiBase = axios.create({
  baseURL: 'http://localhost:8080',
});
