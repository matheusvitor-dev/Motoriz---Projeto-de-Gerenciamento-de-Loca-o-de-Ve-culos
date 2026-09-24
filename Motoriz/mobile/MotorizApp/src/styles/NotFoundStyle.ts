import { StyleSheet } from 'react-native';

export const NotFoundStyles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#0F172A',
    justifyContent: 'center',
    alignItems: 'center',
    paddingHorizontal: 16,
  },
  card: {
    backgroundColor: '#192f53',
    borderRadius: 12,
    paddingVertical: 32,
    paddingHorizontal: 24,
    alignItems: 'center',
    maxWidth: 400,
    width: '100%',
    borderWidth: 1,
    borderColor: '#1f2f42',
  },
  iconContainer: {
    marginBottom: 24,
    alignItems: 'center',
  },
  iconWrapper: {
    backgroundColor: '#EF444426',
    borderRadius: 50,
    padding: 16,
  },
  icon: {
    fontSize: 64,
    color: '#EF4444'
  },
  title: {
    fontSize: 48,
    fontWeight: 'bold',
    color: '#fff',
    marginBottom: 8,
  },
  subtitle: {
    fontSize: 20,
    fontWeight: '600',
    color: '#E6EDF3',
    marginBottom: 16,
  },
  description: {
    fontSize: 14,
    color: '#83898f',
    textAlign: 'center',
    marginBottom: 24,
    lineHeight: 22,
  },
  buttonGroup: {
    gap: 12,
    width: '100%',
  },
  button: {
    backgroundColor: '#22C55E',
    paddingVertical: 12,
    paddingHorizontal: 24,
    borderRadius: 8,
    alignItems: 'center',
    justifyContent: 'center',
  },
  buttonText: {
    color: '#fff',
    fontSize: 14,
    fontWeight: '600',
  },
});
