import React from 'react';
import { SafeAreaView, ScrollView, StyleSheet, Text, View } from 'react-native';

// Matches OriginBadgeColors.ReactNative (#4C1D95) from the native OriginBadge.kt,
// so the banner reads as the RN counterpart of the native green "NATIVE SCREEN" badge.
const REACT_NATIVE_BANNER_COLOR = '#4C1D95';

export default function TournamentDetailScreen(props) {
  const {
    tournamentName,
    modality,
    format,
    participantCount,
    status,
  } = props;

  return (
    <SafeAreaView style={styles.safeArea}>
      <View style={styles.banner}>
        <Text style={styles.bannerText}>REACT NATIVE SCREEN</Text>
      </View>
      <ScrollView contentContainerStyle={styles.content}>
        <Text style={styles.title}>{tournamentName}</Text>
        {!!modality && (
          <DetailRow label="Modality" value={modality} />
        )}
        {!!format && (
          <DetailRow label="Format" value={format} />
        )}
        {participantCount != null && (
          <DetailRow label="Participants" value={String(participantCount)} />
        )}
        {!!status && (
          <DetailRow label="Status" value={status} />
        )}
      </ScrollView>
    </SafeAreaView>
  );
}

function DetailRow({ label, value }) {
  return (
    <View style={styles.row}>
      <Text style={styles.rowLabel}>{label}</Text>
      <Text style={styles.rowValue}>{value}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: '#FFFFFF',
  },
  banner: {
    width: '100%',
    paddingVertical: 8,
    backgroundColor: REACT_NATIVE_BANNER_COLOR,
  },
  bannerText: {
    color: '#FFFFFF',
    fontWeight: 'bold',
    fontSize: 12,
    letterSpacing: 1,
    textAlign: 'center',
  },
  content: {
    padding: 20,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#1C1B1F',
    marginBottom: 16,
  },
  row: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingVertical: 10,
    borderBottomWidth: StyleSheet.hairlineWidth,
    borderBottomColor: '#CCCCCC',
  },
  rowLabel: {
    fontSize: 14,
    color: '#6B6B6B',
  },
  rowValue: {
    fontSize: 14,
    color: '#1C1B1F',
    fontWeight: '600',
  },
});
