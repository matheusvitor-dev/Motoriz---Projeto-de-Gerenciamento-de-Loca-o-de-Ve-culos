import { BottomTabNavigationOptions } from '@react-navigation/bottom-tabs';
import { StyleSheet } from 'react-native';

export const tabOptions: BottomTabNavigationOptions = {
  tabBarStyle: {
    backgroundColor: '#192f53',
    height: 70,
    paddingBottom: 10,
    paddingTop: 10,
    borderTopWidth: 0,
    elevation: 10,
    shadowOpacity: 0.3,
    justifyContent: 'space-around',
  },
  tabBarActiveTintColor: '#fff',
  tabBarInactiveTintColor: '#b5bcc2',
  tabBarLabelStyle: {
    fontSize: 13,
    fontWeight: '600',
  },
}

export const style = StyleSheet.create({
  headerDate: {
    color: '#b5bcc2',
    fontSize: 14,
    marginRight: 10,
  }
})
