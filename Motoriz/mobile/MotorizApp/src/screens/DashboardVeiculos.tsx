import {
  FlatList,
  Text,
  TextStyle,
  TouchableOpacity,
  View,
  ViewStyle,
} from 'react-native';
import { styles } from '../styles/DashboardVeiculosStyle';
import React, { useEffect, useMemo, useState } from 'react';
import { getVeiculoByStatus, getVeiculos } from '../services/api';
import { ChevronUp, ChevronDown, MapPin } from 'lucide-react-native';
import { statusVeiculo, Veiculo } from '../@types/Veiculos';

interface StatusConfig {
  label: string
  style: TextStyle | ViewStyle;
}

export function DashboardVeiculos() {
  const [veiculos, setVeiculos] = useState<Veiculo[]>([]);
  const [statusFiltro, setStatusFiltro] = useState<statusVeiculo | 'TODOS'>(
    'TODOS',
  );
  const [dropdownAberto, setDropdownAberto] = useState(false);

  const STATUS_LABELS: Record<statusVeiculo | 'TODOS', string> = {
    TODOS: 'Todos',
    DISPONIVEL: 'Disponível',
    ALUGADO: 'Alugado',
    MANUTENCAO: 'Em Manutenção',
  };

  const opcoesFiltro: (statusVeiculo | 'TODOS')[] = [
    'TODOS',
    'ALUGADO',
    'DISPONIVEL',
    'MANUTENCAO',
  ];

  useEffect(() => {
    carregarVeiculos();
  }, []);

  const carregarVeiculos = async () => {
    try {
      const response = await getVeiculos();
      setVeiculos(response.data);
    } catch (error) {
      console.log('Erro ao carregar veículos:', error);
    }
  };

  const handleFiltrarPorStatus = async (filtro: statusVeiculo | 'TODOS') => {
    if (filtro === statusFiltro) {
      setDropdownAberto(false);
      return;
    }
    setStatusFiltro(filtro);
    setDropdownAberto(false);

    if (filtro === 'TODOS') {
      await carregarVeiculos();
    } else {
      try {
        const response = await getVeiculoByStatus(filtro);
        setVeiculos(response.data);
      } catch (error) {
        console.log('Erro ao carregar veículos por filtro:', error);
      }
    }
  };
  const renderFooter = () => (
    <TouchableOpacity style={styles.footerVerMaisVeiculos}>
      <Text style={styles.verMaisVeiculos}>Ver mais veículos</Text>
    </TouchableOpacity>
  );

  const STATUS_CFG: Record<Veiculo['status'], StatusConfig> = {
    DISPONIVEL: { label: 'Disponível', style: styles.statusDisponivel },
    ALUGADO: { label: 'Alugado', style: styles.statusAlugado },
    MANUTENCAO: { label: 'Em Manutenção', style: styles.statusManutencao },
  };

  const statsVeiculos = useMemo(() => {
    const total = veiculos.length;
    const disponiveis = veiculos.filter(v => v.status === 'DISPONIVEL').length;
    const alugados = veiculos.filter(v => v.status === 'ALUGADO').length;
    const manutencao = veiculos.filter(v => v.status === 'MANUTENCAO').length;
    return {
      total,
      disponiveis,
      alugados,
      manutencao,
    };
  }, [veiculos]);

  const porcentagem = useMemo(() => {
    return statsVeiculos.total > 0
      ? Math.round((statsVeiculos.alugados / statsVeiculos.total) * 100)
      : 0;
  }, [statsVeiculos]);

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerTitle}>Visão Geral da Frota</Text>
        <View style={styles.containerInfos}>
          <View style={styles.infoCard}>
            <Text style={styles.cardLabel}>Locações Ativas</Text>
            <Text style={styles.cardValue}>{statsVeiculos.total}</Text>
            <Text style={styles.cardDescription}>
              {porcentagem}% de utilização de frota
            </Text>
          </View>
          <View style={styles.infoCard}>
            <Text style={styles.cardLabel}>Carros Disponíveis</Text>
            <Text style={styles.cardValue}>{statsVeiculos.disponiveis}</Text>
            <Text style={styles.cardDescription}>Prontos para locação</Text>
          </View>
          <View style={styles.infoCard}>
            <Text style={styles.cardLabel}>Em Manutenção</Text>
            <Text style={styles.cardValue}>{statsVeiculos.manutencao}</Text>
            <Text style={styles.cardDescription}>Aguardando serviço</Text>
          </View>
          <View style={styles.infoCard}>
            <Text style={styles.cardLabel}>Pagamentos Pendentes</Text>
            <Text style={styles.cardValue}>1</Text>
            <Text style={styles.cardDescription}>De locações ativas</Text>
          </View>
        </View>
      </View>
      <View style={styles.veiculosContainer}>
        <View style={styles.veiculosHeader}>
          <Text style={styles.veiculosHeaderTitle}>Veículos Ativos</Text>
          <View style={styles.dropdownFiltrar}>
            <TouchableOpacity
              style={styles.botaoSelecao}
              onPress={() => setDropdownAberto(!dropdownAberto)}
            >
              <Text style={styles.textoSelecionado}>
                {STATUS_LABELS[statusFiltro]}
              </Text>
              {dropdownAberto ? <ChevronUp size={20} color={'#E6EDF3'}/> : <ChevronDown size={20} color={'#E6EDF3'}/>}
            </TouchableOpacity>
            {dropdownAberto && (
              <View style={styles.opcoesDropdown}>
                {opcoesFiltro.map(opcao => (
                  <TouchableOpacity
                    key={opcao}
                    style={[
                      styles.opcaoItem,
                      statusFiltro === opcao && styles.opcaoSelecionada,
                    ]}
                    onPress={() => handleFiltrarPorStatus(opcao)}
                  >
                    <View style={styles.opcaoItemContent}>
                      <Text style={styles.opcaoItemText}>{STATUS_LABELS[opcao]}</Text>
                    </View>
                  </TouchableOpacity>
                ))}
              </View>
            )}
          </View>
        </View>

        <View style={styles.veiculosList}>
          <FlatList
            data={veiculos}
            keyExtractor={item => item.id.toString()}
            renderItem={({ item }) => {
              const status = STATUS_CFG[item.status];
              return (
                <View style={styles.veiculosCard}>
                  <View style={styles.veiculosInfo}>
                    <Text style={styles.veiculosPlaca}>{item.placa}</Text>
                    <Text style={styles.veiculosModelo}>{item.modelo}</Text>
                    <Text style={styles.veiculosKm}>
                      KM: {item.quilometragemAtual}
                    </Text>
                  </View>
                  <View style={styles.statusEBotaoLocalizar}>
                    <Text style={[styles.veiculosStatus, status.style]}>
                      {status.label}
                    </Text>
                    <TouchableOpacity style={styles.botaoLocalizar}>
                      <MapPin size={17} color={'#E6EDF3'}/>
                      <Text style={styles.textLocalizar}>Localizar</Text>
                    </TouchableOpacity>
                  </View>
                </View>
              );
            }}
            ListFooterComponent={veiculos.length > 3 ? renderFooter : null}
          />
        </View>
      </View>
    </View>
  );
}
