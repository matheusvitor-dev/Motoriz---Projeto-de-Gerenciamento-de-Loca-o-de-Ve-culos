import { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, ScrollView, Alert } from 'react-native';
import { useForm, Controller } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { RegisterStyles } from '../styles/RegisterStyle';

const registerSchema = z.object({
  name: z.string().min(3, 'Nome deve ter no mínimo 3 caracteres'),
  email: z.string().email('Email inválido'),
  password: z.string()
    .min(8, 'Senha deve ter no mínimo 8 caracteres')
    .regex(/[A-Z]/, 'Senha deve conter pelo menos uma letra maiúscula')
    .regex(/[0-9]/, 'Senha deve conter pelo menos um número'),
  confirmPassword: z.string(),
}).refine((data) => data.password === data.confirmPassword, {
  message: 'As senhas não correspondem',
  path: ['confirmPassword'],
});

type RegisterFormData = z.infer<typeof registerSchema>;

interface NavegarParaLogin {
  onMudarTela: () => void;
  onVoltarHome: () => void;
}

export default function Register({ onMudarTela, onVoltarHome}: NavegarParaLogin) {
  const [apiError, setApiError] = useState<string>('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const {
    control,
    handleSubmit,
    formState: { errors },
    watch,
  } = useForm<RegisterFormData>({
    resolver: zodResolver(registerSchema),
  });

  const name = watch('name');
  const email = watch('email');
  const password = watch('password');
  const confirmPassword = watch('confirmPassword');

  const onSubmit = async (data: RegisterFormData) => {
    setApiError('');
    setIsSubmitting(true);

    try {
      Alert.alert('Registro', `Nome: ${data.name}\nEmail: ${data.email}\nDados serão enviados ao backend`);
      setTimeout(() => {
        setIsSubmitting(false);
      }, 500);
    } catch (error: any) {
      setApiError('Erro ao criar conta');
      setIsSubmitting(false);
    }
  };

  const passwordStrength = password ? (
    password.length >= 8 &&
    /[A-Z]/.test(password) &&
    /[0-9]/.test(password)
      ? 'strong'
      : 'weak'
  ) : null;


  return (
    <View style={RegisterStyles.container}>
      <ScrollView contentContainerStyle={{ flexGrow: 1, justifyContent: 'flex-start' }}>
        <TouchableOpacity style={RegisterStyles.botaoVoltar} onPress={onVoltarHome}>
          <Text style={RegisterStyles.textoBotaoVoltar}> Voltar para Home</Text>
        </TouchableOpacity>
        <View style={RegisterStyles.formWrapper}>
          {/* Header */}
          <View style={RegisterStyles.header}>
            <Text style={RegisterStyles.logo}>MOTORIZ</Text>
            <Text style={RegisterStyles.subtitle}>Criar Nova Conta</Text>
          </View>

          {/* Card */}
          <View style={RegisterStyles.card}>
            <View style={RegisterStyles.form}>
              {/* Error Message */}
              {apiError && (
                <View style={{ backgroundColor: '#3d1f1f', padding: 12, borderRadius: 6, borderWidth: 1, borderColor: '#ff4444' }}>
                  <Text style={{ fontSize: 12, fontWeight: '600', color: '#ff4444' }}>Erro no Registro</Text>
                  <Text style={{ fontSize: 11, color: '#ff6666', marginTop: 4 }}>{apiError}</Text>
                </View>
              )}

              {/* Name Field */}
              <View style={RegisterStyles.field}>
                <Text style={RegisterStyles.label}>Nome Completo</Text>
                <Controller
                  control={control}
                  name="name"
                  render={({ field: { onChange, value } }) => (
                    <TextInput
                      style={[
                        RegisterStyles.input,
                        errors.name && RegisterStyles.inputError,
                      ]}
                      placeholder="João Silva"
                      placeholderTextColor="#666"
                      value={value}
                      onChangeText={onChange}
                    />
                  )}
                />
                {errors.name && (
                  <Text style={RegisterStyles.errorText}>{errors.name.message}</Text>
                )}
              </View>

              {/* Email Field */}
              <View style={RegisterStyles.field}>
                <Text style={RegisterStyles.label}>Email</Text>
                <Controller
                  control={control}
                  name="email"
                  render={({ field: { onChange, value } }) => (
                    <TextInput
                      style={[
                        RegisterStyles.input,
                        errors.email && RegisterStyles.inputError,
                      ]}
                      placeholder="seu.email@empresa.com"
                      placeholderTextColor="#666"
                      value={value}
                      onChangeText={onChange}
                      keyboardType="email-address"
                    />
                  )}
                />
                {errors.email && (
                  <Text style={RegisterStyles.errorText}>{errors.email.message}</Text>
                )}
              </View>

              {/* Password Field */}
              <View style={RegisterStyles.field}>
                <Text style={RegisterStyles.label}>Senha</Text>
                <Controller
                  control={control}
                  name="password"
                  render={({ field: { onChange, value } }) => (
                    <TextInput
                      style={[
                        RegisterStyles.input,
                        errors.password && RegisterStyles.inputError,
                      ]}
                      placeholder="••••••••"
                      placeholderTextColor="#666"
                      value={value}
                      onChangeText={onChange}
                      secureTextEntry
                    />
                  )}
                />
                {errors.password && (
                  <Text style={RegisterStyles.errorText}>{errors.password.message}</Text>
                )}
                {password && !errors.password && passwordStrength === 'strong' && (
                  <Text style={RegisterStyles.passwordStrength}>Senha forte</Text>
                )}
              </View>

              {/* Confirm Password Field */}
              <View style={RegisterStyles.field}>
                <Text style={RegisterStyles.label}>Confirmar Senha</Text>
                <Controller
                  control={control}
                  name="confirmPassword"
                  render={({ field: { onChange, value } }) => (
                    <TextInput
                      style={[
                        RegisterStyles.input,
                        errors.confirmPassword && RegisterStyles.inputError,
                      ]}
                      placeholder="••••••••"
                      placeholderTextColor="#666"
                      value={value}
                      onChangeText={onChange}
                      secureTextEntry
                    />
                  )}
                />
                {errors.confirmPassword && (
                  <Text style={RegisterStyles.errorText}>{errors.confirmPassword.message}</Text>
                )}
              </View>

              {/* Register Button */}
              <TouchableOpacity
                style={{ backgroundColor: '#ff6b00', padding: 12, borderRadius: 6, marginTop: 8 }}
                onPress={handleSubmit(onSubmit)}
                disabled={isSubmitting}
              >
                <Text style={{ color: '#fff', textAlign: 'center', fontWeight: '600' }}>
                  {isSubmitting ? 'Criando Conta...' : 'CRIAR CONTA'}
                </Text>
              </TouchableOpacity>
            </View>

            {/* Divider */}
            <View style={RegisterStyles.divider}>
              <View style={RegisterStyles.dividerLine} />
              <Text style={RegisterStyles.dividerText}>ou</Text>
              <View style={RegisterStyles.dividerLine} />
            </View>

            {/* Login Link */}
            <Text style={RegisterStyles.linkText}>
              Já tem conta?{' '}
              <Text
                style={RegisterStyles.linkBold}
                onPress={onMudarTela}
              >
                Fazer login
              </Text>
            </Text>
          </View>

          {/* Footer */}
          <View style={RegisterStyles.footer}>
            <Text style={RegisterStyles.footerText}>&gt; MOTORIZ_REG_v2.0.1</Text>
            <Text style={RegisterStyles.footerText}>&gt; STATUS: ONLINE</Text>
            <View style={{ flexDirection: 'row', justifyContent: 'center', gap: 8, marginTop: 16 }}>
              <View style={{ width: 6, height: 6, borderRadius: 3, backgroundColor: '#22c55e' }} />
              <Text style={{ fontSize: 11, color: '#666' }}>Sistema Operacional</Text>
            </View>
          </View>
        </View>
      </ScrollView>
    </View>
  );
}
