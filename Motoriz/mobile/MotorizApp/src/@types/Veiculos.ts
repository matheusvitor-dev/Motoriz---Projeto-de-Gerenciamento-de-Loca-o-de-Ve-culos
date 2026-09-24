export type statusVeiculo = 'DISPONIVEL' | 'ALUGADO' | 'MANUTENCAO';

export interface Veiculo {
  id: string;
  placa: string;
  modelo: string;
  quilometragemAtual: number;
  status: statusVeiculo;
}