import React, { useState } from 'react';
import {
  Pressable,
  SafeAreaView,
  ScrollView,
  StyleSheet,
  Text,
  TextInput,
  View,
} from 'react-native';
import BracketView from '../components/BracketView';
import TournamentBridge from '../specs/NativeTournamentBridge';
import type { Bracket } from '../specs/NativeTournamentBridge';

const REACT_NATIVE_BANNER_COLOR = '#4C1D95';

const FORMATS = [
  { key: 'SINGLE_ELIMINATION', label: 'Single elimination' },
  { key: 'ROUND_ROBIN', label: 'Round robin' },
  { key: 'SWISS', label: 'Swiss' },
];

export default function CreateTournamentScreen() {
  const [name, setName] = useState('');
  const [modality, setModality] = useState('');
  const [formatKey, setFormatKey] = useState(FORMATS[0].key);
  const [participantInput, setParticipantInput] = useState('');
  const [participants, setParticipants] = useState<string[]>([]);
  const [bracket, setBracket] = useState<Bracket | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [created, setCreated] = useState(false);

  const canSubmit = name.trim().length > 0 && participants.length >= 2;

  function addParticipant() {
    const trimmed = participantInput.trim();
    if (!trimmed) return;
    setParticipants((prev) => [...prev, trimmed]);
    setParticipantInput('');
  }

  function removeParticipant(index: number) {
    setParticipants((prev) => prev.filter((_, i) => i !== index));
  }

  function handlePreview() {
    setError(null);
    try {
      setBracket(TournamentBridge.generateBracket(formatKey, participants));
    } catch (e) {
      setBracket(null);
      setError(e instanceof Error ? e.message : String(e));
    }
  }

  function handleCreate() {
    setError(null);
    try {
      const result = TournamentBridge.createTournament(name, modality, formatKey, participants);
      setBracket(result);
      setCreated(true);
    } catch (e) {
      setError(e instanceof Error ? e.message : String(e));
    }
  }

  return (
    <SafeAreaView style={styles.safeArea}>
      <View style={styles.banner}>
        <Text style={styles.bannerText}>REACT NATIVE SCREEN</Text>
      </View>
      <ScrollView contentContainerStyle={styles.content}>
        <Text style={styles.title}>Create Tournament</Text>

        {created ? (
          <View>
            <Text style={styles.successText}>
              "{name}" was created. Go back to see it in the tournament list.
            </Text>
            {bracket && (
              <Section title="Bracket">
                <BracketView bracket={bracket} />
              </Section>
            )}
          </View>
        ) : (
          <View>
            <Field label="Tournament name">
              <TextInput style={styles.input} value={name} onChangeText={setName} placeholder="e.g. Sinuca Q4 2026" />
            </Field>
            <Field label="Modality">
              <TextInput style={styles.input} value={modality} onChangeText={setModality} placeholder="e.g. Sinuca" />
            </Field>

            <Section title="Format">
              <View style={styles.formatRow}>
                {FORMATS.map((format) => (
                  <Pressable
                    key={format.key}
                    style={[styles.formatChip, formatKey === format.key && styles.formatChipSelected]}
                    onPress={() => setFormatKey(format.key)}
                  >
                    <Text
                      style={[
                        styles.formatChipText,
                        formatKey === format.key && styles.formatChipTextSelected,
                      ]}
                    >
                      {format.label}
                    </Text>
                  </Pressable>
                ))}
              </View>
            </Section>

            <Section title="Participants">
              <View style={styles.participantInputRow}>
                <TextInput
                  style={[styles.input, styles.participantInput]}
                  value={participantInput}
                  onChangeText={setParticipantInput}
                  placeholder="Participant name"
                  onSubmitEditing={addParticipant}
                />
                <Pressable style={styles.addButton} onPress={addParticipant}>
                  <Text style={styles.addButtonText}>Add</Text>
                </Pressable>
              </View>
              {participants.length === 0 && (
                <Text style={styles.hint}>Add at least 2 participants.</Text>
              )}
              {participants.map((participant, index) => (
                <View key={`${participant}-${index}`} style={styles.participantRow}>
                  <Text style={styles.participantName}>{participant}</Text>
                  <Pressable onPress={() => removeParticipant(index)}>
                    <Text style={styles.removeText}>Remove</Text>
                  </Pressable>
                </View>
              ))}
            </Section>

            {!!error && <Text style={styles.errorText}>{error}</Text>}

            <View style={styles.actions}>
              <ActionButton label="Preview Bracket" onPress={handlePreview} disabled={participants.length < 2} secondary />
              <ActionButton label="Create Tournament" onPress={handleCreate} disabled={!canSubmit} />
            </View>

            {bracket && (
              <Section title="Bracket preview">
                <BracketView bracket={bracket} />
              </Section>
            )}
          </View>
        )}
      </ScrollView>
    </SafeAreaView>
  );
}

function Field({ label, children }: { label: string; children: React.ReactNode }) {
  return (
    <View style={styles.field}>
      <Text style={styles.fieldLabel}>{label}</Text>
      {children}
    </View>
  );
}

function Section({ title, children }: { title: string; children: React.ReactNode }) {
  return (
    <View style={styles.section}>
      <Text style={styles.sectionTitle}>{title}</Text>
      {children}
    </View>
  );
}

function ActionButton({
  label,
  onPress,
  disabled,
  secondary,
}: {
  label: string;
  onPress: () => void;
  disabled?: boolean;
  secondary?: boolean;
}) {
  return (
    <Pressable
      style={[styles.actionButton, secondary && styles.actionButtonSecondary, disabled && styles.actionButtonDisabled]}
      onPress={onPress}
      disabled={disabled}
    >
      <Text
        style={[styles.actionButtonText, secondary && styles.actionButtonTextSecondary]}
      >
        {label}
      </Text>
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
  field: {
    marginBottom: 16,
  },
  fieldLabel: {
    fontSize: 14,
    color: '#6B6B6B',
    marginBottom: 6,
  },
  input: {
    borderWidth: StyleSheet.hairlineWidth,
    borderColor: '#CCCCCC',
    borderRadius: 8,
    paddingHorizontal: 12,
    paddingVertical: 10,
    fontSize: 14,
    color: '#1C1B1F',
  },
  section: {
    marginTop: 8,
    marginBottom: 16,
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#1C1B1F',
    marginBottom: 8,
  },
  formatRow: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: 8,
  },
  formatChip: {
    borderWidth: StyleSheet.hairlineWidth,
    borderColor: '#4C1D95',
    borderRadius: 16,
    paddingHorizontal: 14,
    paddingVertical: 8,
  },
  formatChipSelected: {
    backgroundColor: '#4C1D95',
  },
  formatChipText: {
    fontSize: 13,
    color: '#4C1D95',
    fontWeight: '600',
  },
  formatChipTextSelected: {
    color: '#FFFFFF',
  },
  participantInputRow: {
    flexDirection: 'row',
    gap: 8,
  },
  participantInput: {
    flex: 1,
  },
  addButton: {
    backgroundColor: '#4C1D95',
    borderRadius: 8,
    paddingHorizontal: 16,
    justifyContent: 'center',
  },
  addButtonText: {
    color: '#FFFFFF',
    fontWeight: '600',
    fontSize: 14,
  },
  hint: {
    fontSize: 13,
    color: '#6B6B6B',
    marginTop: 8,
  },
  participantRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: 8,
    borderBottomWidth: StyleSheet.hairlineWidth,
    borderBottomColor: '#EEEEEE',
  },
  participantName: {
    fontSize: 14,
    color: '#1C1B1F',
  },
  removeText: {
    fontSize: 13,
    color: '#B3261E',
    fontWeight: '600',
  },
  errorText: {
    fontSize: 14,
    color: '#B3261E',
    marginBottom: 12,
  },
  successText: {
    fontSize: 16,
    color: '#1C1B1F',
    marginBottom: 16,
  },
  actions: {
    gap: 12,
    marginTop: 8,
  },
  actionButton: {
    backgroundColor: '#4C1D95',
    borderRadius: 8,
    paddingVertical: 12,
    alignItems: 'center',
  },
  actionButtonSecondary: {
    backgroundColor: '#FFFFFF',
    borderWidth: 1,
    borderColor: '#4C1D95',
  },
  actionButtonDisabled: {
    opacity: 0.4,
  },
  actionButtonText: {
    color: '#FFFFFF',
    fontWeight: '600',
    fontSize: 14,
  },
  actionButtonTextSecondary: {
    color: '#4C1D95',
  },
});
