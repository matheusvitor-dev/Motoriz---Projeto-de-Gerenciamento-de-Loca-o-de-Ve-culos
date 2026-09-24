import { StyleSheet } from 'react-native';

export const ComponentShowcaseStyles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#0F172A',
  },
  mainContainer: {
    flex: 1,
    paddingVertical: 16,
    paddingHorizontal: 16,
  },
  headerContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 24,
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    color: '#fff',
  },
  themeButton: {
    padding: 8,
    borderRadius: 6,
    borderWidth: 1,
    borderColor: '#1f2f42',
  },
  sectionsContainer: {
    gap: 32,
  },
  section: {
    gap: 16,
  },
  sectionTitle: {
    fontSize: 20,
    fontWeight: '600',
    color: '#fff',
  },
  card: {
    backgroundColor: '#192f53',
    borderRadius: 8,
    padding: 16,
    borderWidth: 1,
    borderColor: '#1f2f42',
  },
  footer: {
    borderTopWidth: 1,
    borderTopColor: '#1f2f42',
    paddingVertical: 12,
    alignItems: 'center',
    marginTop: 24,
  },
  footerText: {
    fontSize: 12,
    color: '#83898f',
  },
});
