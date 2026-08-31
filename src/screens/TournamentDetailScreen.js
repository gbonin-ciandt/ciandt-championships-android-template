import React from 'react';
import { Pressable, SafeAreaView, ScrollView, StyleSheet, Text, View } from 'react-native';
import BracketView from '../components/BracketView';
import NavigationBridge from '../specs/NativeNavigationBridge';
import { generateBracket } from '../utils/tournamentBracket';

// Matches OriginBadgeColors.ReactNative (#4C1D95) from the native OriginBadge.kt,
// so the banner reads as the RN counterpart of the native green "NATIVE SCREEN" badge.
const REACT_NATIVE_BANNER_COLOR = '#4C1D95';

export default function TournamentDetailScreen(props) {
  const {
    tournamentName,
    modality,
    format,
    formatKey,
    participantCount,
    status,
  } = props;

  const count = participantCount ?? 0;
  const participantNames = Array.from({ length: count }, (_, i) => `Player ${i + 1}`);
  const bracket = generateBracket(formatKey, participantNames);

  return (
    <SafeAreaView style={styles.safeArea}>
      <View style={styles.banner}>
        <Text style={styles.bannerText}>REACT NATIVE SCREEN</Text>
      </View>
      <ScrollView contentContainerStyle={styles.content}>
        <Text style={styles.title}>{tournamentName}</Text>
        {!!modality && <DetailRow label="Modality" value={modality} />}
        {!!format && <DetailRow label="Format" value={format} />}
        {participantCount != null && (
          <DetailRow label="Participants" value={String(participantCount)} />
        )}
        {!!status && <DetailRow label="Status" value={status} />}

        <Section title="Participants">
          {participantNames.map((name) => (
            <Text key={name} style={styles.participantName}>
              {name}
            </Text>
          ))}
        </Section>

        {bracket && (
          <Section title="Bracket">
            <BracketView bracket={bracket} />
          </Section>
        )}

        <View style={styles.navigationButtons}>
          <NavButton label="View History" onPress={() => NavigationBridge.openHistory()} />
          <NavButton label="View Ranking" onPress={() => NavigationBridge.openRanking()} />
        </View>
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

function Section({ title, children }) {
  return (
    <View style={styles.section}>
      <Text style={styles.sectionTitle}>{title}</Text>
      {children}
    </View>
  );
}

function NavButton({ label, onPress }) {
  return (
    <Pressable style={styles.navButton} onPress={onPress}>
      <Text style={styles.navButtonText}>{label}</Text>
    </Pressable>
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
  section: {
    marginTop: 24,
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#1C1B1F',
    marginBottom: 8,
  },
  participantName: {
    fontSize: 14,
    color: '#1C1B1F',
    paddingVertical: 4,
  },
  navigationButtons: {
    marginTop: 24,
    gap: 12,
  },
  navButton: {
    backgroundColor: '#4C1D95',
    borderRadius: 8,
    paddingVertical: 12,
    alignItems: 'center',
  },
  navButtonText: {
    color: '#FFFFFF',
    fontWeight: '600',
    fontSize: 14,
  },
});
