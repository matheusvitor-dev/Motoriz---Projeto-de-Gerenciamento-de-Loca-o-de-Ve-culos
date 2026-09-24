import { useState } from 'react';
import { View, Text, ScrollView, TouchableOpacity } from 'react-native';
import { useLocation } from "wouter";
import { DashboardStyles } from '../styles/DashboardStyle';

// Remove these backend-related imports:
// import { useAuth } from '@/_core/hooks/useAuth';
// import { trpc } from '@/lib/trpc';

export default function Dashboard() {
  const [, setLocation] = useLocation();
  const [sidebarOpen, setSidebarOpen] = useState(false);

  const user = {
    name: 'João Silva',
    email: 'joao@example.com',
    id: 'USR_12345',
    role: 'admin',
    createdAt: new Date().toISOString(),
  };

  const statsCards = [
    { title: 'Veículos', value: '48', subtitle: 'Frota disponível', color: '#ff6b00' },
    { title: 'Reservas', value: '156', subtitle: 'Este mês', color: '#00d4ff' },
    { title: 'Clientes', value: '1,240', subtitle: 'Ativos', color: '#0066ff' },
    { title: 'Configurações', value: 'Admin', subtitle: 'Acesso restrito', color: '#ffcc00' },
  ];

  const statusItems = [
    { label: 'Servidor', status: 'Operacional' },
    { label: 'Banco de Dados', status: 'Conectado' },
    { label: 'API', status: 'Respondendo' },
  ];

  const handleLogout = () => {
    setLocation('/login');
  };

  return (
    <View style={DashboardStyles.container}>
      {/* Header */}
      <View style={DashboardStyles.header}>
        <View style={DashboardStyles.headerLeft}>
          <TouchableOpacity onPress={() => setSidebarOpen(!sidebarOpen)}>
            <Text style={{ fontSize: 20, color: '#fff' }}>☰</Text>
          </TouchableOpacity>
          <Text style={DashboardStyles.logo}>MOTORIZ</Text>
        </View>
        <TouchableOpacity
          onPress={handleLogout}
          style={{ paddingHorizontal: 12, paddingVertical: 8, backgroundColor: '#ff4444', borderRadius: 6 }}
        >
          <Text style={{ color: '#fff', fontSize: 12, fontWeight: '600' }}>Sair</Text>
        </TouchableOpacity>
      </View>

      {/* Layout */}
      <View style={DashboardStyles.layoutContainer}>
        {/* Sidebar */}
        {sidebarOpen && (
          <View style={DashboardStyles.sidebar}>
            <View style={DashboardStyles.sidebarNav}>
              <TouchableOpacity style={DashboardStyles.navButton}>
                <Text style={DashboardStyles.navButtonText}>🚗 Veículos</Text>
              </TouchableOpacity>
              <TouchableOpacity style={DashboardStyles.navButton}>
                <Text style={DashboardStyles.navButtonText}>📊 Reservas</Text>
              </TouchableOpacity>
              <TouchableOpacity style={DashboardStyles.navButton}>
                <Text style={DashboardStyles.navButtonText}>👥 Clientes</Text>
              </TouchableOpacity>
              <TouchableOpacity style={[DashboardStyles.navButton, { marginTop: 16, borderTopWidth: 1, borderTopColor: '#333' }]}>
                <Text style={DashboardStyles.navButtonText}>⚙️ Configurações</Text>
              </TouchableOpacity>
            </View>
          </View>
        )}

        {/* Main Content */}
        <View style={DashboardStyles.mainContent}>
          <ScrollView style={DashboardStyles.contentContainer} showsVerticalScrollIndicator={false}>
            {/* Welcome Section */}
            <View style={DashboardStyles.welcomeSection}>
              <Text style={DashboardStyles.welcomeTitle}>Bem-vindo, {user.name}!</Text>
              <Text style={DashboardStyles.welcomeSubtitle}>
                Sistema de Gestão de Locadora de Carros - Acesso Autorizado
              </Text>
              <View style={{ marginTop: 12, gap: 4 }}>
                <Text style={{ fontSize: 11, color: '#666' }}>USER_ID: {user.id}</Text>
                <Text style={{ fontSize: 11, color: '#666' }}>ROLE: {user.role.toUpperCase()}</Text>
                <Text style={{ fontSize: 11, color: '#666' }}>STATUS: ONLINE</Text>
              </View>
            </View>

            {/* Stats Cards Grid */}
            <View style={DashboardStyles.cardsGrid}>
              {statsCards.map((card, index) => (
                <View key={index} style={DashboardStyles.card}>
                  <Text style={DashboardStyles.cardTitle}>{card.title}</Text>
                  <Text style={[DashboardStyles.cardValue, { color: card.color }]}>{card.value}</Text>
                  <Text style={DashboardStyles.cardSubtitle}>{card.subtitle}</Text>
                </View>
              ))}
            </View>

            {/* Status Section */}
            <View style={DashboardStyles.statusSection}>
              <Text style={DashboardStyles.statusTitle}>Status do Sistema</Text>
              <View style={DashboardStyles.statusGrid}>
                {statusItems.map((item, index) => (
                  <View key={index} style={DashboardStyles.statusItem}>
                    <View style={DashboardStyles.statusDot} />
                    <View style={{ flex: 1 }}>
                      <Text style={DashboardStyles.statusText}>{item.label}</Text>
                      <Text style={DashboardStyles.statusSubtext}>{item.status}</Text>
                    </View>
                  </View>
                ))}
              </View>
            </View>

            {/* Footer */}
            <View style={DashboardStyles.footer}>
              <Text style={DashboardStyles.footerText}>&gt; MOTORIZ_DASHBOARD_v2.0.1</Text>
              <Text style={DashboardStyles.footerText}>
                &gt; LAST_SYNC: {new Date().toLocaleTimeString('pt-PT')}
              </Text>
            </View>
          </ScrollView>
        </View>
      </View>
    </View>
  );
}
