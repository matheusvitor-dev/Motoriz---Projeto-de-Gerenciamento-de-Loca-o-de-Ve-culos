import { View, Text, TouchableOpacity } from 'react-native';
import { useLocation } from "wouter";
import { NotFoundStyles } from '../styles/NotFoundStyle';

export default function NotFound() {
  const [, setLocation] = useLocation();

  const handleGoHome = () => {
    setLocation("/");
  };

  return (
    <View style={NotFoundStyles.container}>
      <View style={NotFoundStyles.card}>
        {/* Icon Container */}
        <View style={NotFoundStyles.iconContainer}>
          <View style={NotFoundStyles.iconWrapper}>
            <Text style={NotFoundStyles.icon}>⚠️</Text>
          </View>
        </View>

        {/* Title and Subtitle */}
        <Text style={NotFoundStyles.title}>404</Text>
        <Text style={NotFoundStyles.subtitle}>Page Not Found</Text>

        {/* Description */}
        <Text style={NotFoundStyles.description}>
          Sorry, the page you are looking for doesn't exist.{'\n'}
          It may have been moved or deleted.
        </Text>

        {/* Buttons */}
        <View style={NotFoundStyles.buttonGroup}>
          <TouchableOpacity style={NotFoundStyles.button} onPress={handleGoHome}>
            <Text style={NotFoundStyles.buttonText}>🏠 Go Home</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
}
