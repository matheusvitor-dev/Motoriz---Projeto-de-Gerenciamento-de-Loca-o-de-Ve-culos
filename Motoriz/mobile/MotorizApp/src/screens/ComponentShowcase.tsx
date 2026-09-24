import { View, Text, ScrollView, TouchableOpacity } from 'react-native';
import { ComponentShowcaseStyles } from '../styles/ComponentShowcaseStyle';

export default function ComponentsShowcase() {
  return (
    <View style={ComponentShowcaseStyles.container}>
      <ScrollView showsVerticalScrollIndicator={false}>
        <View style={ComponentShowcaseStyles.mainContainer}>
          {/* Header */}
          <View style={ComponentShowcaseStyles.headerContainer}>
            <Text style={ComponentShowcaseStyles.title}>Shadcn/ui Components</Text>
            <TouchableOpacity style={ComponentShowcaseStyles.themeButton}>
              <Text style={{ fontSize: 16, color: '#fff' }}>🌙</Text>
            </TouchableOpacity>
          </View>

          {/* Sections */}
          <View style={ComponentShowcaseStyles.sectionsContainer}>
            {/* Buttons Section */}
            <View style={ComponentShowcaseStyles.section}>
              <Text style={ComponentShowcaseStyles.sectionTitle}>Buttons</Text>
              <View style={ComponentShowcaseStyles.card}>
                <TouchableOpacity style={{ backgroundColor: '#fff', padding: 12, borderRadius: 6, marginBottom: 8 }}>
                  <Text style={{ color: '#000', textAlign: 'center', fontWeight: '600' }}>Default</Text>
                </TouchableOpacity>
                <TouchableOpacity style={{ backgroundColor: '#666', padding: 12, borderRadius: 6, marginBottom: 8 }}>
                  <Text style={{ color: '#fff', textAlign: 'center', fontWeight: '600' }}>Secondary</Text>
                </TouchableOpacity>
                <TouchableOpacity style={{ backgroundColor: '#ff4444', padding: 12, borderRadius: 6 }}>
                  <Text style={{ color: '#fff', textAlign: 'center', fontWeight: '600' }}>Destructive</Text>
                </TouchableOpacity>
              </View>
            </View>

            {/* Colors Section */}
            <View style={ComponentShowcaseStyles.section}>
              <Text style={ComponentShowcaseStyles.sectionTitle}>Colors</Text>
              <View style={ComponentShowcaseStyles.card}>
                <View style={{ backgroundColor: '#ff6b00', padding: 16, borderRadius: 6, marginBottom: 8 }}>
                  <Text style={{ color: '#fff' }}>Primary - #ff6b00</Text>
                </View>
                <View style={{ backgroundColor: '#1a1a1a', borderWidth: 1, borderColor: '#333', padding: 16, borderRadius: 6, marginBottom: 8 }}>
                  <Text style={{ color: '#fff' }}>Card - #1a1a1a</Text>
                </View>
                <View style={{ backgroundColor: '#999', padding: 16, borderRadius: 6 }}>
                  <Text style={{ color: '#fff' }}>Muted - #999</Text>
                </View>
              </View>
            </View>

            {/* Text Section */}
            <View style={ComponentShowcaseStyles.section}>
              <Text style={ComponentShowcaseStyles.sectionTitle}>Typography</Text>
              <View style={ComponentShowcaseStyles.card}>
                <Text style={{ fontSize: 24, fontWeight: 'bold', color: '#fff', marginBottom: 12 }}>Heading</Text>
                <Text style={{ fontSize: 16, color: '#999', marginBottom: 12 }}>Subtitle text</Text>
                <Text style={{ fontSize: 14, color: '#fff' }}>Body text</Text>
              </View>
            </View>
          </View>

          {/* Footer */}
          <View style={ComponentShowcaseStyles.footer}>
            <Text style={ComponentShowcaseStyles.footerText}>Shadcn/ui Component Showcase</Text>
          </View>
        </View>
      </ScrollView>
    </View>
  );
}
