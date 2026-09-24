
export type statusPagamento =
  | "PENDENTE"
  | "PAGO"
  | "ATRASADO"
  | "RECUSADO"
  | "CANCELADO";


export interface ContratoDoVeiculo {
  id: string;
  modelo: string;
  placa: string;
  valor: number;
  statusPagamento: statusPagamento;
  vencimento: string;
  cliente: string;
}