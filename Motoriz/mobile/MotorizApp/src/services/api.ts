import axios, { AxiosResponse } from "axios";
import { Veiculo } from "../@types/Veiculos";

const api = axios.create({
    baseURL: 'http://192.168.100.6:8080/api',
    timeout: 5000,
})

export const getVeiculos = (): Promise<AxiosResponse<Veiculo[]>> => {
    return api.get<Veiculo[]>('/veiculos');
};
export const getVeiculoById = (id: string | number): Promise<AxiosResponse<Veiculo>> => {
    return api.get<Veiculo>(`/veiculos/${id}`)
};
export const getVeiculoByStatus = (status: Veiculo['status']): Promise<AxiosResponse<Veiculo[]>> => {
    return api.get<Veiculo[]>(`/veiculos/status/${status}`)
};