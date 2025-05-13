import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/eleicaoGP2', // ajuste aqui se necessário
});

export default api;
