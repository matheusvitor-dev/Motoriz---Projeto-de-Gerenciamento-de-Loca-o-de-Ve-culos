import { StyleSheet } from 'react-native';

export const DashboardStyles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#0F172A',
  },
  header: {
    paddingVertical: 12,
    paddingHorizontal: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#1f2f42',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  headerLeft: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 12,
  },
  logo: {
    fontSize: 22,
    fontWeight: 'bold',
    color: '#fff',
    letterSpacing: 1.5,
  },
  layoutContainer: {
    flex: 1,
    flexDirection: 'row',
  },
  sidebar: {
    width: 256,
    backgroundColor: '#192f53',
    borderRightWidth: 1,
    borderRightColor: '#1f2f42',
  },
  sidebarNav: {
    paddingVertical: 16,
    paddingHorizontal: 12,
    gap: 8,
  },
  navButton: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingVertical: 12,
    paddingHorizontal: 12,
    borderRadius: 8,
    gap: 12,
  },
  navButtonText: {
    fontSize: 14,
    fontWeight: '600',
    color: '#fff',
  },
  mainContent: {
    flex: 1,
  },
  contentContainer: {
    flex: 1,
    paddingVertical: 24,
    paddingHorizontal: 16,
  },
  welcomeSection: {
    backgroundColor: '#192f53',
    borderRadius: 8,
    padding: 16,
    marginBottom: 24,
    borderWidth: 1,
    borderColor: '#1f2f42',
  },
  welcomeTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#fff',
    marginBottom: 8,
  },
  welcomeSubtitle: {
    fontSize: 14,
    color: '#83898f',
    marginBottom: 16,
  },
  cardsGrid: {
    gap: 16,
    marginBottom: 24,
  },
  card: {
    backgroundColor: '#192f53',
    borderRadius: 8,
    padding: 16,
    borderWidth: 1,
    borderColor: '#1f2f42',
  },
  cardTitle: {
    fontSize: 14,
    fontWeight: 'bold',
    color: '#fff',
    marginBottom: 8,
  },
  cardValue: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#22C55E',
    marginBottom: 8,
  },
  cardSubtitle: {
    fontSize: 12,
    color: '#83898f',
  },
  statusSection: {
    backgroundColor: '#192f53',
    borderRadius: 8,
    padding: 16,
    borderWidth: 1,
    borderColor: '#1f2f42',
  },
  statusTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#fff',
    marginBottom: 16,
  },
  statusGrid: {
    gap: 16,
  },
  statusItem: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 12,
    paddingVertical: 12,
    paddingHorizontal: 12,
    backgroundColor: '#1E293B',
    borderRadius: 6,
    borderWidth: 1,
    borderColor: '#0b2b55',
  },
  statusDot: {
    width: 8,
    height: 8,
    borderRadius: 4,
    backgroundColor: '#22c55e',
  },
  statusText: {
    fontSize: 12,
    fontWeight: '600',
    color: '#fff',
  },
  statusSubtext: {
    fontSize: 11,
    color: '#83898f',
  },
  footer: {
    alignItems: 'center',
    gap: 8,
    marginTop: 24,
  },
  footerText: {
    fontSize: 11,
    color: '#83898f',
  },
});
