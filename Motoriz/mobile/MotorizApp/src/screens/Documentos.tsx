import {
  FlatList,
  Text,
  TextStyle,
  TouchableOpacity,
  View,
  ViewStyle,
} from "react-native";
import { styles } from "../styles/DocumentosStyle";
import { useMemo, useState } from "react";
import { ChevronUp, ChevronDown } from "lucide-react-native";
import {
  ContratoDoVeiculo,
  statusPagamento,
} from "../@types/ContratoDoVeiculo";

interface StatusConfig {
  label: string;
  style: TextStyle | ViewStyle;
}

const contratosExemplo: ContratoDoVeiculo[] = [
  {
    id: "1",
    modelo: "Chevrolet Onix",
    placa: "ABC-1234",
    valor: 150.0,
    statusPagamento: "PENDENTE",
    vencimento: "15-05-2027",
    cliente: "João Costa",
  },
  {
    id: "2",
    modelo: "Toyota Prius",
    placa: "XYZ-5678",
    valor: 200.0,
    statusPagamento: "PAGO",
    vencimento: "20-05-2027",
    cliente: "Maria Silva",
  },
];

export function Documentos() {
  const [contratosAtivos, setContratosAtivos] =
    useState<ContratoDoVeiculo[]>(contratosExemplo);
  const [statusFiltro, setStatusFiltro] = useState<statusPagamento | "TODOS">(
    "TODOS",
  );
  const [dropdownAberto, setDropdownAberto] = useState(false);

  const STATUS_STYLES: Record<statusPagamento, StatusConfig> = {
    PENDENTE: { label: "Pendente", style: styles.statusPendente },
    PAGO: { label: "Pago", style: styles.statusPago },
    ATRASADO: { label: "Atrasado", style: styles.statusAtrasado },
    RECUSADO: { label: "Recusado", style: styles.statusRecusado },
    CANCELADO: { label: "Cancelado", style: styles.statusCancelado },
  };

  const opcoesFiltro: (statusPagamento | "TODOS")[] = [
    "TODOS",
    "PENDENTE",
    "PAGO",
    "ATRASADO",
    "RECUSADO",
    "CANCELADO",
  ];

  const STATUS_LABELS: Record<statusPagamento | "TODOS", string> = {
    TODOS: "Todos",
    PENDENTE: "Pendente",
    PAGO: "Pago",
    ATRASADO: "Atrasado",
    RECUSADO: "Recusado",
    CANCELADO: "Cancelado",
  };

  const handleFiltrarPorStatus = (filtro: statusPagamento | "TODOS") => {
    if (filtro === statusFiltro) {
      setDropdownAberto(false);
      return;
    }
    setStatusFiltro(filtro);
    setDropdownAberto(false);
    if (filtro === "TODOS") {
      setContratosAtivos(contratosExemplo);
    } else {
      const contratosFiltrados = contratosExemplo.filter(
        (contrato) => contrato.statusPagamento === filtro,
      );
      setContratosAtivos(contratosFiltrados);
    }
  };

  const numeroDeContratos = useMemo(() => {
    return contratosAtivos.length;
  }, [contratosAtivos]);
  return (
    <View style={styles.container}>
      <View style={styles.headerListaDeContratos}>
        <View style={styles.containerDocumentos}>
          <Text style={styles.headerTitle}>Visão de Documentos</Text>
          <Text style={styles.headerListaDeContatosDescricao}>
            Veja contratos atuais e finalize devoluções
          </Text>
        </View>

        <Text style={styles.numeroDeContratos}>{numeroDeContratos} Ativos</Text>
      </View>
      <View style={styles.containerListaDeContratos}>
        <View style={styles.listaDeContratosHeader}>
          <Text style={styles.textoListaDeContratos}>Contratos</Text>
          <View style={styles.dropdownFiltrar}>
            <TouchableOpacity
              style={styles.botaoSelecao}
              onPress={() => setDropdownAberto(!dropdownAberto)}
            >
              <Text style={styles.textoSelecionado}>
                {STATUS_LABELS[statusFiltro]}
              </Text>
              {dropdownAberto ? (
                <ChevronUp size={20} color={"#E6EDF3"} />
              ) : (
                <ChevronDown size={20} color={"#E6EDF3"} />
              )}
            </TouchableOpacity>
            {dropdownAberto && (
              <View style={styles.opcoesDropdown}>
                {opcoesFiltro.map((opcao) => (
                  <TouchableOpacity
                    key={opcao}
                    style={[
                      styles.opcaoItem,
                      statusFiltro === opcao && styles.opcaoSelecionada,
                    ]}
                    onPress={() => handleFiltrarPorStatus(opcao)}
                  >
                    <View style={styles.opcaoItemContent}>
                      <Text style={styles.opcaoItemText}>
                        {STATUS_LABELS[opcao]}
                      </Text>
                    </View>
                  </TouchableOpacity>
                ))}
              </View>
            )}
          </View>
        </View>
        <FlatList
          data={contratosAtivos}
          keyExtractor={(item) => item.id}
          renderItem={({ item }) => {
            const status = STATUS_STYLES[item.statusPagamento];
            return (
              <View style={styles.listaDeContratosCard}>
                <View style={styles.contratosInfo}>
                  <View style={styles.tituloContrato}>
                    <Text style={styles.textoCliente}>{item.cliente}</Text>
                    <Text style={styles.textoPlaca}>{item.placa}</Text>
                  </View>
                  <View style={styles.descricaoContrato}>
                    <Text style={styles.textoModelo}>{item.modelo}</Text>
                    <Text style={styles.textoValor}>R$ {item.valor}</Text>
                    <Text style={styles.textoVencimento}>
                      Vencimento: {item.vencimento}
                    </Text>
                  </View>
                </View>
                <View style={styles.statusEBotaoFevolucao}>
                  <Text style={[styles.statusPagamento, status.style]}>
                    {status.label}
                  </Text>
                  <TouchableOpacity style={styles.botaoFinalizarDevolucao}>
                    <Text style={styles.textoBotaoFinalizar}>
                      Finalizar Devolução
                    </Text>
                  </TouchableOpacity>
                </View>
              </View>
            );
          }}
        />
      </View>
    </View>
  );
}
