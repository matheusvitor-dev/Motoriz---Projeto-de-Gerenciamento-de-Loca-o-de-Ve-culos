import { StyleSheet } from 'react-native';

export const RegisterStyles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#0F172A',
    justifyContent: 'center',
    paddingHorizontal: 16,
    paddingVertical: 32,
  },
  botaoVoltar: {
    alignSelf: 'flex-start',
    marginTop: 0,
    marginBottom: 20,
    paddingVertical: 5,
    paddingHorizontal: 8,
    borderRadius: 6,
  },
  textoBotaoVoltar: {
    color: '#fff',
    fontSize: 16,
  },
  formWrapper: {
    width: '100%',
  },
  header: {
    marginBottom: 48,
    alignItems: 'center',
  },
  logo: {
    fontSize: 32,
    fontWeight: 'bold',
    color: '#fff',
    letterSpacing: 2,
    marginBottom: 12,
  },
  subtitle: {
    fontSize: 12,
    color: '#83898f',
    letterSpacing: 1,
    textTransform: 'uppercase',
  },
  card: {
    backgroundColor: '#192f53',
    borderRadius: 8,
    padding: 20,
    marginBottom: 24,
    borderWidth: 1,
    borderColor: '#1f2f42',
  },
  form: {
    gap: 20,
  },
  field: {
    gap: 8,
  },
  label: {
    fontSize: 12,
    fontWeight: '600',
    color: '#fff',
    textTransform: 'uppercase',
    letterSpacing: 0.5,
  },
  input: {
    backgroundColor: '#1E293B',
    borderWidth: 1,
    borderColor: '#1f2f42',
    borderRadius: 6,
    paddingVertical: 12,
    paddingHorizontal: 12,
    color: '#fff',
    fontSize: 14,
  },
  inputError: {
    borderColor: '#EF4444',
  },
  errorText: {
    fontSize: 12,
    color: '#EF4444',
    marginTop: 4,
  },
  passwordStrength: {
    fontSize: 12,
    color: '#22C55E',
    marginTop: 4,
  },
  button: {
    marginTop: 16,
  },
  divider: {
    flexDirection: 'row',
    alignItems: 'center',
    marginVertical: 16,
    gap: 12,
  },
  dividerLine: {
    flex: 1,
    height: 1,
    backgroundColor: '#1f2f42',
  },
  dividerText: {
    fontSize: 11,
    color: '#83898f',
    textTransform: 'uppercase',
  },
  linkText: {
    textAlign: 'center',
    fontSize: 12,
    color: '#83898f',
  },
  linkBold: {
    color: '#22C55E',
    fontWeight: '600',
  },
  footer: {
    alignItems: 'center',
    gap: 8,
  },
  footerText: {
    fontSize: 11,
    color: '#83898f',
  },
});
