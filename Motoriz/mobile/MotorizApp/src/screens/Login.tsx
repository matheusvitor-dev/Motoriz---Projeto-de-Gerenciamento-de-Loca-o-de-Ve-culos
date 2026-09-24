import { useState } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  ScrollView,
  Alert,
  Button,
} from "react-native";
import { useForm, Controller } from "react-hook-form";
import { z } from "zod";
import { LoginStyles } from "../styles/LoginStyle";
import { zodResolver } from "@hookform/resolvers/zod";

const loginSchema = z.object({
  email: z.string().email("Email inválido").min(1, "Email é obrigatório"),
  password: z.string().min(6, "Senha deve ter no mínimo 6 caracteres"),
});

type LoginFormData = z.infer<typeof loginSchema>;

interface NavegarParaRegister {
  onMudarTela: () => void;
  onVoltarParaHome: () => void;
}

export default function Login({ onMudarTela, onVoltarParaHome}: NavegarParaRegister) {
  const [apiError, setApiError] = useState<string>("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormData>({
    resolver: zodResolver(loginSchema),
  });

  const onSubmit = async (data: LoginFormData) => {
    setApiError("");
    setIsSubmitting(true);

    try {
      Alert.alert(
        "Login",
        `Email: ${data.email}\nSenha será validada no backend`,
      );
      setTimeout(() => {
        setIsSubmitting(false);
      }, 500);
    } catch (error: any) {
      setApiError("Erro ao fazer login");
      setIsSubmitting(false);
    }
  };

  return (
    <View style={LoginStyles.container}>
      <ScrollView
        contentContainerStyle={{ flexGrow: 1, justifyContent: "center" }}
      >
        <TouchableOpacity style={LoginStyles.botaoVoltar} onPress={onVoltarParaHome}>
          <Text style={LoginStyles.textoBotaoVoltar}> Voltar para Home</Text>
        </TouchableOpacity>
        <View style={LoginStyles.formWrapper}>
          {/* Header */}
          <View style={LoginStyles.header}>
            <Text style={LoginStyles.logo}>MOTORIZ</Text>
            <Text style={LoginStyles.subtitle}>Sistema de Autenticação</Text>
          </View>

          {/* Card */}
          <View style={LoginStyles.card}>
            <View style={LoginStyles.form}>
              {/* Error Message */}
              {apiError && (
                <View
                  style={{
                    backgroundColor: "#3d1f1f",
                    padding: 12,
                    borderRadius: 6,
                    borderWidth: 1,
                    borderColor: "#ff4444",
                  }}
                >
                  <Text
                    style={{
                      fontSize: 12,
                      fontWeight: "600",
                      color: "#ff4444",
                    }}
                  >
                    Erro de Autenticação
                  </Text>
                  <Text
                    style={{ fontSize: 11, color: "#ff6666", marginTop: 4 }}
                  >
                    {apiError}
                  </Text>
                </View>
              )}

              {/* Email Field */}
              <View style={LoginStyles.field}>
                <Text style={LoginStyles.label}>Email</Text>
                <Controller
                  control={control}
                  name="email"
                  render={({ field: { onChange, value } }) => (
                    <TextInput
                      style={[
                        LoginStyles.input,
                        errors.email && LoginStyles.inputError,
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
                  <Text style={LoginStyles.errorText}>
                    {errors.email.message}
                  </Text>
                )}
              </View>

              {/* Password Field */}
              <View style={LoginStyles.field}>
                <Text style={LoginStyles.label}>Senha</Text>
                <Controller
                  control={control}
                  name="password"
                  render={({ field: { onChange, value } }) => (
                    <TextInput
                      style={[
                        LoginStyles.input,
                        errors.password && LoginStyles.inputError,
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
                  <Text style={LoginStyles.errorText}>
                    {errors.password.message}
                  </Text>
                )}
              </View>

              {/* Login Button */}
              <TouchableOpacity
                style={{
                  backgroundColor: "#ff6b00",
                  padding: 12,
                  borderRadius: 6,
                  marginTop: 8,
                }}
                onPress={handleSubmit(onSubmit)}
                disabled={isSubmitting}
              >
                <Text
                  style={{
                    color: "#fff",
                    textAlign: "center",
                    fontWeight: "600",
                  }}
                >
                  {isSubmitting ? "Autenticando..." : "ACESSAR SISTEMA"}
                </Text>
              </TouchableOpacity>
            </View>

            {/* Divider */}
            <View style={LoginStyles.divider}>
              <View style={LoginStyles.dividerLine} />
              <Text style={LoginStyles.dividerText}>ou</Text>
              <View style={LoginStyles.dividerLine} />
            </View>

            {/* Register Link */}
            <Text style={LoginStyles.linkText}>
              Não tem conta?{" "}
              <Text
                style={LoginStyles.linkBold}
                onPress={onMudarTela}
              >
                Criar conta
              </Text>
            </Text>
          </View>

          {/* Footer */}
          <View style={LoginStyles.footer}>
            <Text style={LoginStyles.footerText}>&gt; MOTORIZ_AUTH_v2.0.1</Text>
            <Text style={LoginStyles.footerText}>&gt; STATUS: ONLINE</Text>
            <View
              style={{
                flexDirection: "row",
                justifyContent: "center",
                gap: 8,
                marginTop: 16,
              }}
            >
              <View
                style={{
                  width: 6,
                  height: 6,
                  borderRadius: 3,
                  backgroundColor: "#22c55e",
                }}
              />
              <Text style={{ fontSize: 11, color: "#666" }}>
                Sistema Operacional
              </Text>
            </View>
          </View>
        </View>
      </ScrollView>
    </View>
  );
}
